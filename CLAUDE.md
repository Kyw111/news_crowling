# news_crowling 프로젝트

## 프로젝트 개요
네이버 뉴스를 자동으로 크롤링하여 카카오톡 나에게 보내기 API로 전송하는 Spring Boot 애플리케이션.
개인 사이드 프로젝트.

## 기술 스택
- **Java 17**, Spring Boot 2.7.5, Gradle
- **MariaDB** (localhost:3306, DB명: naver_news)
- **JPA/Hibernate** (ddl-auto: update — 테이블 자동 생성)
- **Jsoup** — 네이버 뉴스 HTML 크롤링
- **Lombok** — @Getter, @Builder

## 프로젝트 구조

```
src/main/java/com/example/demo/
├── NewApplication.java             # 진입점 (@SpringBootApplication, @EnableScheduling)
├── Constant.java                   # 카카오 API 관련 상수 모음 (git 제외 — .gitignore)
├── Constant.example.java           # Constant.java 작성 가이드용 예시 파일 (커밋 O)
├── API/
│   └── NewsAPI.java                # REST 컨트롤러 (엔드포인트 3개)
├── Service/
│   ├── HttpCallService.java        # HTTP 요청 공통 처리 (기본 클래스)
│   ├── AuthService.java            # 카카오 OAuth 토큰 발급
│   ├── KakaoMessageService.java    # 카카오톡 메시지 전송
│   ├── NewsCrowling.java           # 네이버 뉴스 크롤링 + 스케줄러
│   └── CreateNewMessageService.java # 뉴스 크롤링 후 메시지 생성/전송
└── dto/
    └── DefaultMessageDTO.java      # 카카오 메시지 DTO (@Getter, @Builder)
```

## API 엔드포인트

| 메소드 | URL | 설명 |
|--------|-----|------|
| GET | `/issue?code={code}` | 카카오 OAuth 콜백 — 토큰 발급 후 뉴스 메시지 전송 |
| GET | `/news` | 네이버 인기 뉴스 랭킹 크롤링 결과 반환 (plain text) |
| GET | `/issues` | 언론사별 주요 뉴스 크롤링 결과 반환 (plain text) |

## 동작 흐름

### 수동 실행 (카카오 OAuth)
```
카카오 로그인 → /issue?code=... 콜백
→ AuthService: 인가코드 → access_token 교환
→ NewsCrowling: 언론사별 뉴스 크롤링
→ DefaultMessageDTO 생성
→ KakaoMessageService: 카카오톡 나에게 보내기
```

### 자동 스케줄러
```
매일 오전 8:30 (cron: "0 30 8 * * *")
→ NewsCrowling.scheduler()
→ newsCompanyIssue() 크롤링 실행
```

## 상수 관리 (Constant.java)

`AuthService`에서 `static import`로 사용. `Constant.java`는 `.gitignore`에 등록되어 있어 커밋되지 않음.
새 PC에서 작업 시 `Constant.example.java`를 참고하여 `Constant.java`를 직접 생성해야 함.

| 상수명 | 설명 | 확인 위치 |
|--------|------|-----------|
| `AUTH_URL` | 카카오 토큰 발급 URL | 고정값 (변경 불필요) |
| `KAKAO_API_KEY` | 카카오 REST API 키 | 카카오 디벨로퍼스 > 앱 키 > REST API 키 |
| `KAKAO_REDIRECT_URL` | OAuth 리다이렉트 URL | 카카오 디벨로퍼스 > 카카오 로그인 > Redirect URI |
| `CLIENT_SECRET_KEY` | 카카오 클라이언트 시크릿 | 카카오 디벨로퍼스 > 카카오 로그인 > 보안 |
| `APP_TYPE_URL_ENCODED` | Content-Type 상수 | HttpCallService에 정의 |

## 로컬 실행 환경 설정

### 1. JDK
- 필요 버전: **JDK 17**
- IntelliJ에서 아래 3곳 모두 JDK 17로 설정
  - `File > Project Structure > Project` — SDK, Language level
  - `File > Project Structure > Modules` — Language level
  - `Settings > Build Tools > Gradle` — Gradle JVM

### 2. MariaDB
- 최신 버전 설치 후 DB 생성 (테이블은 JPA가 자동 생성)
```sql
CREATE DATABASE naver_news;
```
- 계정: `root` / 비밀번호: `root` (application.yml 기준)

### 3. Constant.java 생성
- `Constant.example.java`를 복사하여 `Constant.java`로 이름 변경 후 키 값 채워넣기
- 값이 없으면 앱 실행 시 컴파일 에러 발생

## 프론트엔드 연동

- 별도 React 프로젝트: `news_crowling_front/dailyNews-frontEnd`
- `localhost:3000` 접속 시 `/news` 자동 호출 → 뉴스 목록 테이블 표시 (20개 페이징)
- 백엔드 먼저 실행 후 프론트엔드 실행 필요
```bash
npm install   # 최초 1회
npm start
```

## 크롤링 대상 URL
- 인기 뉴스: `https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111`
- 언론사별 뉴스: `https://news.naver.com/main/officeList.naver`

> 네이버 HTML 구조 변경 시 크롤링 셀렉터 수정 필요 (`NewsCrowling.java`)

## Docker 배포

### 구성
- **백엔드 Dockerfile**: 멀티스테이지 빌드 (eclipse-temurin:17-jdk-alpine → 17-jre-alpine), 포트 8080
- **프론트엔드 Dockerfile**: Node 18 빌드 → Nginx Alpine 서빙, 포트 80
- **docker-compose.yml**: MariaDB 10.6 + 백엔드 + 프론트엔드 통합 관리

### 실행
```bash
docker-compose up --build
```

### 주의사항
- `Constant.java`는 git 제외 파일이므로 Docker 빌드 전 존재 여부 확인
- DB 볼륨은 docker-compose에서 별도 정의 필요 (데이터 영속성)

## 개발 환경 정보

- OS: Windows 11
- JDK 17 위치: `C:\Java\Open\java17`
- Python3: 설치됨
- Docker: 설치됨
