# Reverique

![License](https://img.shields.io/badge/license-MIT-blue.svg)  
Reverique는 연인들을 위한 질문 및 답변 서비스입니다. 💙  

## 🏗️ 프로젝트 개요
Reverique는 연인들이 서로의 생각을 공유하고 소통할 수 있도록 돕는 서비스입니다.  
질문을 주고받고, 답변을 저장하며 추억을 남길 수 있습니다.  

## ✨ 주요 기능
- 💌 **질문 등록**: 연인이 서로 질문을 등록할 수 있습니다.  
- ✏️ **답변 작성**: 질문에 대한 답변을 남길 수 있습니다.  
- 📜 **페이지네이션 적용**: 답변 목록을 페이지 단위로 조회할 수 있습니다.  
- 🔐 **JWT 기반 인증**: 보안 강화를 위해 JWT 인증 방식을 사용합니다.  

## 🛠 기술 스택
### Backend
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT (Json Web Token)**
- **Swagger**

### Deployment
- **Render** (배포 플랫폼)
- **Docker**

## 📥 API 명세
Reverique의 API 명세는 Swagger 문서를 통해 확인할 수 있습니다.  
👉 [https://reverique.onrender.com/swagger-ui.html](https://reverique.onrender.com/swagger-ui.html)

### 주요 API 엔드포인트
- **질문 목록 조회**
  - `GET /questions`
  - 요청 예시: `GET /questions?userId=1&coupleId=2&page=0&size=10`
  - 응답 예시: 200 OK, 10개의 질문 정보 (페이징 포함)

- **답변 작성**
  - `POST /answers`
  - 요청 예시:
    ```json
    {
      "userId": 1,
      "coupleId": 2,
      "questionId": 5,
      "answer": "I love you!"
    }
    ```
  - 응답 예시: 201 Created

- **답변 조회 (페이지네이션)**
  - `GET /answers/received`
  - 요청 예시: `GET /answers/received?userId=1&coupleId=2&page=0&size=10`
  - 응답 예시: 200 OK, 10개의 답변 정보 (페이징 포함)

