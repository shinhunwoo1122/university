package com.example.university.controller;

import com.example.university.dto.UserDto;
import com.example.university.service.RedisService;
import com.example.university.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis")
public class RedisTestController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisUtil redisUtil;
    private final RedisService redisService;

    // Strings: 단순 카운팅
    @GetMapping("/count")
    public ResponseEntity<String> addToCount(){

        Long count = redisTemplate.opsForValue().increment("visitor_count");

        return ResponseEntity.ok().body("총 방문 횟수 " + count);
    }

    // Strings 객체 저장 (set with TTL)
    @GetMapping("/save-user")
    public ResponseEntity<?> saveUser(){
        UserDto userDto = UserDto.builder()
                .id("a004466")
                .name("신헌우")
                .email("shinhunwoo@naver11111.com")
                .build();

        redisTemplate.opsForValue().set("user:shw", userDto, Duration.ofMinutes(300));

        Optional<UserDto> getUserDto = redisUtil.getData("user:shw", UserDto.class);

        if (getUserDto.isPresent()){
            log.info("dto user -> {}", getUserDto);
        }

        return ResponseEntity.ok().body("등록");
    }
    // hashes: 정보 저장 (객체의 특정 필드만 다룰 때)
    @GetMapping("/hash")
    public ResponseEntity<?> testHash(@RequestParam Integer id, @RequestParam String field, @RequestParam String value){
        String key = "shop:user:"+id;
        //key(키), field(필드 명), value(값) 저장
        redisTemplate.opsForHash().put(key, field, value);
        //전체 조회
        Map<Object, Object> shopInfo = redisTemplate.opsForHash().entries(key);
        return ResponseEntity.ok().body(shopInfo);
    }

    // Lists: 최근 활동 로그 (최신순 5개)
    @GetMapping("/list")
    public ResponseEntity<?> testList(@RequestParam String text){
        String key = "list:log";

        // 왼쪽으로 푸시
        redisTemplate.opsForList().leftPush(key, text);

        // 최근 5개만 남기고 trim으로 나머지 삭제
        //redisTemplate.opsForList().trim(key, 0, 4);

        return ResponseEntity.ok().body(redisTemplate.opsForList().range(key, 0, -1));
    }
    // 선착순 줄 세우기
    @GetMapping("/event/apply")
    public ResponseEntity<?> applyEvent(@RequestParam String userId){
        String key = "event:queue";

        // 오른쪽(뒤)로 유저 ID넣어서 list에 줄세움
        redisTemplate.opsForList().rightPush(key, userId);

        // 현재 내 앞에 몇 명이나 있는지 확인
        Long rank = redisTemplate.opsForList().size(key);

        return ResponseEntity.ok().body(userId + "님 응모 완료! 현재 대기 순번 : " + rank);
    }
    // 선착순 당첨자 처리 (순서대로 꺼내기)
    @GetMapping("/event/process")
    public ResponseEntity<?> processEvent(){
        String key = "event:queue";

        //가장 먼저 들어온 사람 (왼쪽 끝)에서 하나를 꺼냄
        Object winner = redisTemplate.opsForList().leftPop(key);

        return ResponseEntity.ok().body(winner == null ? "대기자가 없습니다." : "당첨자 처리 : " + winner);
    }
    // sets을 이용한 중복 방지 선착순 응모
    @GetMapping("/event/apply-unique")
    public ResponseEntity<?> applyEventUnique(@RequestParam String userId){
        String setKey = "event:applied:set";
        String queueKey = "event:queue";
        // set에 먼저 넣어봄 (add는 성공시 1, 중복시 0 반환)
        Long result = redisTemplate.opsForSet().add(setKey, userId);

        if(result != null && result == 0){
            return ResponseEntity.ok().body("이미 응모하셨습니다.");
        }
        // 2. 처음 응모한 사람만 리스트(줄)에 추가
        redisTemplate.opsForList().rightPush(queueKey, userId);
        Long rank = redisTemplate.opsForList().size(queueKey);

        return ResponseEntity.ok().body(userId + "님 응모 완료! 현재 대기 순번: " + rank);
    }

    @GetMapping("/rank/add")
    public ResponseEntity<?> addRank(@RequestParam String userName, @RequestParam double score){
        String key = "game:ranking";

        //zSet에 데이터와 점수 추가
        redisTemplate.opsForZSet().add(key, userName, score);

        //나의 현재 순위 확인 (내림차순)
        Long myRank = redisTemplate.opsForZSet().reverseRank(key, userName);

        return ResponseEntity.ok().body(userName + "님의 현재 순위" + (myRank + 1) + "위");
    }

    @GetMapping("/rank/top3")
    public ResponseEntity<?> getTopRank(){
        String key = "game:ranking";
        //상위 3명 가져오기 (0번부터 2번까지)
        return ResponseEntity.ok().body(redisTemplate.opsForZSet().reverseRange(key, 0, 2));
    }

    @GetMapping("/lock")
    public ResponseEntity<?> testLock(@RequestParam String lockName){
        String key = "lock:" + lockName;

        // setIfAbsent (SETNX): 키가 없을 때만 저장 (락 획득)
        // 10초 후에 자동으로 락이 풀리도록 설정 (Deadlock 방지)
        Boolean isLocked = redisTemplate.opsForValue()
                .setIfAbsent(key, "locked", Duration.ofSeconds(10));

        if(Boolean.FALSE.equals(isLocked)){
            return ResponseEntity.ok().body("다른 서버가 작업 중입니다. 다시 시도해 주세요");
        }

        try {
            Thread.sleep(5000);
            return ResponseEntity.ok().body("작업 완료");
        } catch (InterruptedException e){
            return ResponseEntity.internalServerError().build();
        } finally {
            redisTemplate.delete(key);
        }
    }





}
