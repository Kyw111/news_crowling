package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DefaultMessageDTO {

    private String objType;
    private String text;
    private String webUrl;
    private String mobileUrl;
    private String btnTitle;

    @Builder
    public DefaultMessageDTO(String objType, String text, String webUrl, String mobileUrl, String btnTitle) {
        this.objType = objType;
        this.text = text;
        this.webUrl = webUrl;
        this.mobileUrl = mobileUrl;
        this.btnTitle = btnTitle;
    }
}
