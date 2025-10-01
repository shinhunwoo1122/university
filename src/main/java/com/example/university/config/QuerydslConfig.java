package com.example.university.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {
    //JPA가 관리하는 EntityManager를 주입
    @PersistenceContext
    private EntityManager em;

    //JPAQueryFactory를 spring bean으로 등록
    //jpaQueryFactory는 entityManager가 생성자로 필요하기때문에 주입해줌
    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }

}
