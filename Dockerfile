# jdk17 Image Start
FROM openjdk:17

# JAR_FILE 인자 설정 (default value provided for build context)
ARG JAR_FILE=build/libs/myapp.jar

# JAR 파일 복제
COPY ${JAR_FILE} app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]

