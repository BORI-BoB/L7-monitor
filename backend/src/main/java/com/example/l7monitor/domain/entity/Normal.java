package com.example.l7monitor.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Normal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    private LocalDateTime timestamp;
    private String method;
    private String uri;
    private String protocol;
    private Integer resCode;
    private Integer resDataSize;
    private String referer;
    private String userAgent;

}