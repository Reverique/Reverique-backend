package com.reverie_unique.reverique.domain.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "example")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // UUID 자동 생성
    private UUID id;  // PK 필드

    @Column(name = "name")  // name 컬럼만 있음
    private String name;  // name 컬럼을 매핑한 필드
}