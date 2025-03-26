# 1. OpenJDK 이미지 사용 (Java 17 버전)
FROM maven:3.8.3-openjdk-17 AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 애플리케이션 소스 복사
COPY . .

# 4. 애플리케이션 빌드 (JAR 파일 생성)
RUN mvn clean package -DskipTests

# 5. 새로운 이미지에서 JAR 파일 복사
FROM openjdk:17-jdk-slim

COPY --from=build /app/target/reverique-0.0.1-SNAPSHOT.jar /app/reverique-0.0.1-SNAPSHOT.jar

# 6. 애플리케이션 실행 명령어
CMD ["java", "-jar", "/app/reverique-0.0.1-SNAPSHOT.jar"]

# 7. 포트 노출 (Spring Boot 기본 포트)
EXPOSE 8080
