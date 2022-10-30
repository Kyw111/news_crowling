package com.example.demo.Service;

import com.example.demo.dto.DefaultMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewMessageService {

    private final KakaoMessageService kakaoMessageService;
    private final NewsCrowling newsCrowling;

    public boolean createMsg() {

        String news = newsCrowling.newsCompanyIssue();

        DefaultMessageDTO defaultMessageDTO = DefaultMessageDTO
                .builder()
                .objType("text")
                .text(news)
                .webUrl("https://developers.kakao.com")
                .mobileUrl("https://developers.kakao.com")
                .btnTitle("주요 뉴스 모아보기")
                .build();

        String authToken = AuthService.authToken;
        return kakaoMessageService.sendMessage(authToken, defaultMessageDTO);
    }

}
