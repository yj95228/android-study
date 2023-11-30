package com.scsa.andr.memo4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoDto implements Serializable {

    String title;
    String body;

    public MemoDto(String title, String body, long reg_date) {
        this.title = title;
        this.body = body;
        this.reg_date = reg_date;
    }

    long reg_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public long getReg_date() {
        return reg_date;
    }

    public void setReg_date(long reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "MemoDto{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", reg_date=" + reg_date +
                '}';
    }

}
