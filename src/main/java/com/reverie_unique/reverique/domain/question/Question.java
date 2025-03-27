package com.reverie_unique.reverique.domain.question;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 방식으로 ID 생성
    private Long id;  // PK 필드

    @Column(name = "content")
    private String content;  // name 컬럼을 매핑한 필드

    // 기본 생성자
    public Question() {
    }

    // 전체 필드를 사용하는 생성자
    public Question(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    // toString() 메서드
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", context='" + content + '\'' +
                '}';
    }
}
