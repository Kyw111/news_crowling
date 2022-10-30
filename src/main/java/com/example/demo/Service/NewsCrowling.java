package com.example.demo.Service;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NewsCrowling {

    private static final String TITLE = " 기준 - 네이버 언론사별 주요 뉴스";
    private static final String NEWS_ISSUE_URL = "https://news.naver.com/main/officeList.naver";

    /**
     * 네이버 뉴스 랭킹 - 전체
     * @return
     */
    public String naverRankingNews() {
        try {
            String url = "https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111"; // 네이버 뉴스 랭킹 주소
            Connection connect = Jsoup.connect(url);
            Document doc = connect.get();

            Elements elements = doc.select("div.list_content > a.list_title");

            elements.stream().forEach(element -> {
                System.out.println("- " + element.text());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 언론사별 주요 뉴스
     * @return
     */
    public String newsCompanyIssue() {

        try {
            String url = NEWS_ISSUE_URL; // 네이버 언론사별 주요 뉴스 주소
            Document doc = Jsoup.connect(url).get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분");
            String now = LocalDateTime.now().format(formatter);

            StringBuilder sb = new StringBuilder();
            sb.append(now + TITLE + " \n\n");

            Elements elements = doc.select("ul.list_txt > li" );
            elements.stream().forEach(element -> {
                System.out.println("- " + element.text());
                sb.append("- "+element.text() +"\n");
            });

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Scheduled(cron = "0 30 8 * * *") // 매일 오전 8시 30분에 실행
    public void scheduler() {
        this.newsCompanyIssue();
    }



}
