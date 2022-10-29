package com.example.demo.Service;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NewsCrowling {

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
    public ResponseEntity newsCompanyIssue() {
        try {
            String url = "https://news.naver.com/main/officeList.naver"; // 네이버 언론사별 주요 뉴스 주소
            Document doc = Jsoup.connect(url).get();

            Elements elements = doc.select("ul.list_txt > li" );
            elements.stream().forEach(element -> {
                System.out.println("- " + element.text());
            });

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
