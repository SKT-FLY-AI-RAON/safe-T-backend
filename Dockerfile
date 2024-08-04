# Dockerfile

# jdk17 Image Start
FROM openjdk:17

# 인자 설정 - JAR_File
ARG JAR_FILE=build/libs/*.jar

# jar 파일 복제
COPY ${JAR_FILE} app.jar

# 애플리케이션 설정 파일을 /config로 복사
COPY application.yml src/main/resources/application.yml

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
