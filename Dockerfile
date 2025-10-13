# Dockerfile 수정본

# 1. 빌드 스테이지: (이름을 'builder'로 지정)
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
# 🚨🚨🚨 수정된 부분: gradlew 파일과 gradle 디렉토리를 명시적으로 복사합니다. 🚨🚨🚨
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
COPY src src

# Gradle 빌드 실행: JAR 파일 생성
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# ----------------------------------------------------

# 2. 실행 스테이지: 가벼운 JRE 환경에서 JAR 파일만 실행
FROM eclipse-temurin:17-jre-jammy
# 빌드 스테이지에서 만든 JAR 파일을 복사
COPY --from=builder /app/build/libs/app.jar app.jar
# 컨테이너 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]