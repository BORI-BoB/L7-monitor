package com.example.l7monitor.domain.repository;

import com.example.l7monitor.domain.entity.Total;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class TotalRepositoryTest {
    @Autowired
    private TotalRepository totalRepository;

    private static final LocalDateTime[] times = {
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(4)),
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(6)),
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(7)),
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(8)),
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(11)),
            LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(12)),
    };

    @BeforeEach
    void init() {
        IntStream.range(0, 20).forEach(i -> generateNormalLogData(totalRepository, i));
    }


    @Test
    @DisplayName("특정 시간대에 존재하는 모든 로그 개수를 출력 - 성공")
    void countByTimestampBetween_success() {

        LocalDateTime from = LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(10)); // 현재 시간 -10 분
        LocalDateTime to = LocalDateTime.now(); // 현재 시간 -5 분

        long count = totalRepository.countByTimestampBetween(from, to);

        assertEquals(4, count);
    }

    @Test
    @DisplayName("24 시간 내에 발생한 상위 100개의 로그를 반환")
    void findTop100ByTimestampBetween_success() {

        List<Total> logs = new ArrayList<>();

        IntStream.range(0, 105).forEach(i -> {
            logs.add(Total.builder()
                    .ip("192.168.0.5")
                    .timestamp(LocalDateTime.now().minusMinutes(i))
                    .method("GET")
                    .uri("/images/pharming_img.png")
                    .protocol("HTTP/1.1")
                    .resCode(200)
                    .resDataSize(53529)
                    .referer("https://google.com")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
                    .build());
        });

        totalRepository.saveAll(logs);

        List<Total> top100Logs = totalRepository.findTop100ByTimestampBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now());

        assertEquals(100, top100Logs.size());
    }

    private static void generateNormalLogData(JpaRepository normalRepository, int sequenceNumber) {

        if(sequenceNumber >= times.length) {
            sequenceNumber = times.length - 1;
        }

        String ip = "192.168.0." + sequenceNumber;

        Total total = Total.builder()
                .ip(ip)
                .timestamp(times[sequenceNumber])
                .method("GET")
                .uri("/images/pharming_img.png")
                .protocol("HTTP/1.1")
                .resCode(200)
                .resDataSize(53529)
                .referer("https://google.com")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();

        normalRepository.save(total);
    }
}