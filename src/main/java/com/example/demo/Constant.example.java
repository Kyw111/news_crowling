package com.example.demo;

/**
 * 실제 키 값은 Constant.java 에 작성하세요.
 * Constant.java 는 .gitignore 에 등록되어 있어 커밋되지 않습니다.
 *
 * 사용 방법:
 * 1. 이 파일을 복사하여 Constant.java 로 이름 변경
 * 2. 아래 항목을 카카오 디벨로퍼스(developers.kakao.com)에서 확인 후 채워넣기
 *    - 앱 키 > REST API 키     → KAKAO_API_KEY
 *    - 카카오 로그인 > 보안    → CLIENT_SECRET_KEY
 *    - 카카오 로그인 > Redirect URI → KAKAO_REDIRECT_URL
 */
public class Constant {

    // 카카오 토큰 발급 URL (변경 불필요)
    public static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";

    // 카카오 REST API 키 (앱 키 > REST API 키)
    public static final String KAKAO_API_KEY = "여기에_REST_API_키_입력";

    // 카카오 OAuth 리다이렉트 URL (카카오 로그인 > Redirect URI 에 등록된 값과 동일해야 함)
    public static final String KAKAO_REDIRECT_URL = "http://localhost:8080/issue";

    // 카카오 클라이언트 시크릿 (카카오 로그인 > 보안 > Client Secret)
    public static final String CLIENT_SECRET_KEY = "여기에_클라이언트_시크릿_입력";

}
