package com.cloudg.board;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class DbTestRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DbTestRunner(DataSource dataSource) {
       this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DB 연결 테스트 시작...");
        System.out.println("DataSource: " + dataSource);
        System.out.println("DB 연결 성공!");
    }
}
