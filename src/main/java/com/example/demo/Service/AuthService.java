package com.example.demo.Service;

import com.example.demo.Constant;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.logging.Logger;

import static com.example.demo.Constant.*;

@Service
public class AuthService extends HttpCallService{

//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static String authToken;

    public boolean getKakaoAuthToken(String code)  {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refrashToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", KAKAO_API_KEY);
        parameters.add("redirect_url", KAKAO_REDIRECT_URL);
        parameters.add("client_secret", CLIENT_SECRET_KEY);

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refrashToken = jsonData.get("refresh_token").toString();
        if(accessToken.isEmpty() || refrashToken.isEmpty()) {
//            logger.debug("토큰발급에 실패했습니다.");
            System.out.println("토큰발급에 실패했습니다.");
            return false;
        }else {
            authToken = accessToken;
            return true;
        }
    }

    public String getKakaoAuthTokenString(String code)  {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refrashToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", KAKAO_API_KEY);
        parameters.add("redirect_url", KAKAO_REDIRECT_URL);
        parameters.add("client_secret", CLIENT_SECRET_KEY);

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refrashToken = jsonData.get("refresh_token").toString();
            authToken = accessToken;
            return authToken;
    }




}
