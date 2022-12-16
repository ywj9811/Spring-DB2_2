package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SpringBootTest
public class InitTxTest {
    
    @Test
    void go() {
        // 초기화 코드는 스프링이 초기화 시점에서 실행
        //@PostConstruct 는 초기화 시점에서 자동으로 실행
        //@EventListener(ApplicationReadyEvent.class) 는 컨테이너가 로딩 완료시점에 자동으로 실행
    }
    
    @TestConfiguration
    static class InitTxTestConfig {
        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        @PostConstruct
        @Transactional
        public void initV1() {
            boolean activeTx = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct tx active = {}", activeTx);
        }

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean activeTx = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @EventListener(ApplicationReadyEvent.class) tx active = {}", activeTx);
        }
    }
}
