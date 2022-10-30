package com.example.demo.API;

import com.example.demo.Service.AuthService;
import com.example.demo.Service.CreateNewMessageService;
import com.example.demo.Service.NewsCrowling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsAPI {

    private final NewsCrowling newsCrowling;
    private final AuthService authService;
    private final CreateNewMessageService createNewMessageService;

    /**
     * 카카오 메시지 API 연동 - 인가 코드 활용 token 값 받아와서 메시지전송까지 ok
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping("/issue")
    public String serviceStart(@RequestParam String code) {
//        System.out.println(">> 인가 code : " + code); // code 들어오는거 확인
        if(authService.getKakaoAuthToken(code)) { 
            createNewMessageService.createMsg();
            return "메시지 전송 성공";
        }else {
            return "토큰발급 실패";
        }
    }

    /** 자동 스케줄러 방식으로 개발 예정 */
//    @ResponseBody
//    @GetMapping("/issue")
//    public void news(@RequestParam String code) {
//        if (code != null) {
////            String token = authService.getKakaoAuthTokenString(code);
//            createNewMessageService.createMsg();
//        }
//    }

    /**
     * 뉴스 랭킹 모두 크롤링 결과
     * @return
     */
    @GetMapping("/news")
    public ResponseEntity naverNews() {
        return ResponseEntity.ok().body(newsCrowling.naverRankingNews());
    }

    /**
     * 언론사별 주요 뉴스 크롤링 결과
     * @return
     */
    @GetMapping("/issues")
    public ResponseEntity todayIssues() {
        return ResponseEntity.ok().body(newsCrowling.newsCompanyIssue());
    }

}
