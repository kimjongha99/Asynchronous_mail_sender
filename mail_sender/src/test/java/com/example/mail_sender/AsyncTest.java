package com.example.mail_sender;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private Long id;
    private String name;
}

@Configuration
@EnableAsync
class AsyncConfig {
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}

@Service
@Slf4j
class UserService {

    @Async("asyncExecutor")
    public CompletableFuture<User> findUserById(Long id) {
        log.info("스레드: {}에서 ID: {}로 사용자 찾기", id, Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                log.info("Found user: {}", id);
                return new User(id, "User " + id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<User>> findAllUsers() {
        log.info("스레드에서 모든 사용자 찾기: {}", Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                List<User> users = Arrays.asList(
                        new User(1L, "User 1"),
                        new User(2L, "User 2")
                );
                log.info("Found all users: {}", users);
                return users;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }

    @Async("asyncExecutor")
    public CompletableFuture<User> saveUser(User user) {
        log.info("사용자 저장: {} 스레드: {}", user, Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                log.info("저장된 사용자: {}", user);
                return user;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }
}

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AsyncTest {

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    @DisplayName("단일 사용자 조회 테스트")
    void findUserById_test() {
        // given
        Long userId = 1L;

        // when
        CompletableFuture<User> futureUser = userService.findUserById(userId);

        // then
        User user = assertDoesNotThrow(() -> futureUser.get(2, TimeUnit.SECONDS));

        assertThat(user)
                .isNotNull()
                .extracting(User::getId, User::getName)
                .containsExactly(userId, "User " + userId);
    }

    @Test
    @Order(2)
    @DisplayName("전체 사용자 조회 테스트")
    void findAllUsers_test() {
        // when
        CompletableFuture<List<User>> futureUsers = userService.findAllUsers();

        // then
        List<User> users = assertDoesNotThrow(() -> futureUsers.get(3, TimeUnit.SECONDS));

        assertThat(users)
                .hasSize(2)
                .extracting(User::getName)
                .containsExactly("User 1", "User 2");
    }

    @Test
    @Order(3)
    @DisplayName("사용자 저장 테스트")
    void saveUser_test() {
        // given
        User newUser = new User(3L, "User 3");

        // when
        CompletableFuture<User> futureSavedUser = userService.saveUser(newUser);

        // then
        User savedUser = assertDoesNotThrow(() -> futureSavedUser.get(2, TimeUnit.SECONDS));

        assertThat(savedUser)
                .isNotNull()
                .extracting(User::getId, User::getName)
                .containsExactly(3L, "User 3");
    }

    @Test
    @Order(4)
    @DisplayName("병렬 실행 테스트")
    void parallelExecution_test() {
        // given
        long startTime = System.currentTimeMillis();

        // when
        CompletableFuture<User> futureUser1 = userService.findUserById(1L);
        CompletableFuture<User> futureUser2 = userService.findUserById(2L);
        CompletableFuture<List<User>> futureAllUsers = userService.findAllUsers();

        // then
        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                futureUser1,
                futureUser2,
                futureAllUsers
        );

        assertDoesNotThrow(() -> allOf.get(5, TimeUnit.SECONDS));

        User user1 = futureUser1.join();
        User user2 = futureUser2.join();
        List<User> allUsers = futureAllUsers.join();

        long duration = System.currentTimeMillis() - startTime;

        assertThat(user1.getName()).isEqualTo("User 1");
        assertThat(user2.getName()).isEqualTo("User 2");
        assertThat(allUsers).hasSize(2);

        assertThat(duration).isLessThan(4000L);
        ;
    }

    @Test
    @Order(4)
    @DisplayName("병렬 실행과 순차 실행 비교 테스트")
    void compareParallelAndSequential_test() {
        // 1. 순차 실행 시간 측정
        long sequentialStart = System.currentTimeMillis();

        User user1 = userService.findUserById(1L).join();  // 1초 소요
        User user2 = userService.findUserById(2L).join();  // 1초 소요
        List<User> allUsers = userService.findAllUsers().join();  // 2초 소요

        long sequentialDuration = System.currentTimeMillis() - sequentialStart;

        // 2. 병렬 실행 시간 측정
        long parallelStart = System.currentTimeMillis();

        CompletableFuture<User> futureUser1 = userService.findUserById(1L);
        CompletableFuture<User> futureUser2 = userService.findUserById(2L);
        CompletableFuture<List<User>> futureAllUsers = userService.findAllUsers();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                futureUser1,
                futureUser2,
                futureAllUsers
        );

        allOf.join();  // 모든 작업이 완료될 때까지 대기

        User parallelUser1 = futureUser1.join();
        User parallelUser2 = futureUser2.join();
        List<User> parallelAllUsers = futureAllUsers.join();

        long parallelDuration = System.currentTimeMillis() - parallelStart;

        // 3. 결과 검증
        // 순차 실행은 약 4초 정도 소요 (1초 + 1초 + 2초)
        assertThat(sequentialDuration).isGreaterThan(3800L);

        // 병렬 실행은 약 2초 정도 소요 (가장 오래 걸리는 작업인 2초에 수렴)
        assertThat(parallelDuration).isLessThan(3100L);

        // 병렬 실행이 순차 실행보다 빠른지 확인
        assertThat(parallelDuration).isLessThan(sequentialDuration);

        // 실행 시간 출력
        System.out.println("순차 실행 시간: " + sequentialDuration + "ms");
        System.out.println("병렬 실행 시간: " + parallelDuration + "ms");
        System.out.println("시간 차이: " + (sequentialDuration - parallelDuration) + "ms");

        // 결과가 동일한지 확인
        assertThat(user1).isEqualTo(parallelUser1);
        assertThat(user2).isEqualTo(parallelUser2);
        assertThat(allUsers).isEqualTo(parallelAllUsers);
    }

    @Test
    @Order(5)
    @DisplayName("여러 작업의 병렬 실행과 순차 실행 비교")
    void compareManyTasksParallelAndSequential_test() {
        int taskCount = 5;  // 실행할 작업 수

        // 1. 순차 실행
        long sequentialStart = System.currentTimeMillis();

        List<User> sequentialResults = new ArrayList<>();
        for (int i = 1; i <= taskCount; i++) {
            User user = userService.findUserById((long) i).join();
            sequentialResults.add(user);
        }

        long sequentialDuration = System.currentTimeMillis() - sequentialStart;

        // 2. 병렬 실행
        long parallelStart = System.currentTimeMillis();

        List<CompletableFuture<User>> futures = new ArrayList<>();
        for (int i = 1; i <= taskCount; i++) {
            CompletableFuture<User> future = userService.findUserById((long) i);
            futures.add(future);
        }

        List<User> parallelResults = CompletableFuture.allOf(
                        futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        long parallelDuration = System.currentTimeMillis() - parallelStart;

        // 3. 결과 출력 및 검증
        System.out.println("=== " + taskCount + "개 작업 실행 결과 ===");
        System.out.println("순차 실행 시간: " + sequentialDuration + "ms");
        System.out.println("병렬 실행 시간: " + parallelDuration + "ms");
        System.out.println("시간 차이: " + (sequentialDuration - parallelDuration) + "ms");
        System.out.println("성능 향상: " + String.format("%.1f", (double)sequentialDuration/parallelDuration) + "배");

        // 결과가 동일한지 확인
        assertThat(parallelResults).hasSameSizeAs(sequentialResults);
        assertThat(parallelResults).containsExactlyElementsOf(sequentialResults);

        // 병렬 실행이 순차 실행보다 빠른지 확인
        assertThat(parallelDuration).isLessThan(sequentialDuration);
    }

}