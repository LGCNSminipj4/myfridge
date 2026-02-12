# 🧊 MyFridge Backend

Spring Boot 기반 백엔드 서버입니다.

---

# 📌 1. 프로젝트 클론

```bash
git clone https://github.com/LGCNSminipj4/myfridge.git
```

---

# 📌 2. 설정 파일 준비

## ✅ 1) 환경 변수 설정 (.env)

프로젝트 루트에서 `.env.example` 파일을 복사합니다.

### Mac / Linux

```bash
cp .env.example .env
```

### Windows PowerShell

```powershell
copy .env.example .env
```

`.env` 파일을 열고 실제 값으로 수정하세요:

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=your_database_name_here
DB_USERNAME=your_database_username_here
DB_PASSWORD=your_database_password_here

# MariaDB 옵션
DB_OPTIONS=authenticationPlugins=org.mariadb.jdbc.plugin.authentication.standard.StandardAuthentication&usePamAuth=false&useGssApi=false&useSsl=false

# JWT
JWT_SECRET=your_jwt_secret_key_here

# YOUTUBE
YOUTUBE_API_KEY=your_youtube_api_key_here
```

⚠️ `.env` 파일은 절대 Git에 커밋하지 마세요.

---

# 📌 3. DB 초기 세팅 방법

## 1️⃣ MariaDB 실행

MariaDB가 실행 중인지 확인하세요.

---

## 2️⃣ 데이터베이스 생성

```sql
CREATE DATABASE refrigerator_service;
```

---

## 3️⃣ 테이블 생성

`src/main/resources` 폴더에 있는 파일 실행:

```
refrigerator_service.sql
```

## 4️⃣ 선호 태그 데이터 insert

아래 파일 실행:

```
prefer_tag_seed.sql
```

이 과정을 완료하면:

- 테이블 생성 완료
- 선호 태그 기본 데이터 insert 완료

---

# 📌 4. 의존성 설치

Gradle 사용 시:

```bash
./gradlew build
```

Windows:

```powershell
gradlew.bat build
```

Maven 사용 시:

```bash
mvn clean install
```

---

# 📌 5. 서버 실행 방법

## ✅ Gradle

```bash
./gradlew bootRun
```

Windows:

```powershell
gradlew.bat bootRun
```

## ✅ Maven

```bash
mvn spring-boot:run
```

---

# 📌 6. ngrok 사용 방법 (외부 접근용)

1️⃣ ngrok 설치
[https://ngrok.com/download](https://ngrok.com/download)

2️⃣ 로그인 (토큰 등록)

```bash
ngrok config add-authtoken YOUR_AUTH_TOKEN
```

3️⃣ Spring Boot 실행 (기본 포트 8080)

4️⃣ 터널 열기

```bash
ngrok http 8080
```

5️⃣ 생성된 HTTPS 주소 확인

```
https://xxxx-xxxx.ngrok-free.app
```

이 주소를 프론트엔드 API Base URL로 사용하면 됩니다.

⚠️ 서버 재시작 시 ngrok 주소가 변경될 수 있습니다.

---

# 📌 7. 서버 확인

```
http://localhost:8080
```

Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📌 기술 스택

- Spring Boot 4
- JWT
- MyBatis
- WebClient
- MariaDB
- YouTube Data API v3
