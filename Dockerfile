# 1. 자바 17 버전 환경을 가져옵니다.
FROM eclipse-temurin:17-jdk-jammy

# 2. 작업 폴더를 설정합니다.
WORKDIR /app

# 3. 프로젝트의 모든 파일을 복사합니다.
COPY . .

# 4. (중요) 윈도우에서 업로드 시 발생하는 줄바꿈 에러를 해결합니다.
RUN sed -i 's/\r$//' gradlew

# 5. 실행 권한을 부여하고 빌드(조립)를 시작합니다.
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# 6. 만들어진 실행 파일을 찾기 쉽게 이름을 바꿉니다.
RUN mv build/libs/*.jar app.jar

# 7. 서버를 실행합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]