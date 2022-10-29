package com.example.demo.API;

import com.example.demo.Service.NewsCrowling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsAPI {

    private final NewsCrowling newsCrowling;

    @GetMapping("/news")
    public ResponseEntity naverNews() {
        return ResponseEntity.ok().body(newsCrowling.naverRankingNews());
    }

    @GetMapping("/issue")
    public ResponseEntity todayIssues() {
        return ResponseEntity.ok().body(newsCrowling.newsCompanyIssue());
    }

}
