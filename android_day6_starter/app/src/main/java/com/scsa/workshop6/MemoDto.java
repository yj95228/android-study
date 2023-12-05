package com.scsa.workshop6;

import java.io.Serializable;
import java.util.Date;

public class MemoDto implements Serializable {
    private int id;
    private String title;
    private String body;
    private long regDate;

    public MemoDto(int id, String title, String body, long date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = date;
    }

    public MemoDto(String title, String body, long date) {
        this.title = title;
        this.body = body;
        this.regDate = date;
    }
    public MemoDto(){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "MemoDto{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", regDate='" + new Date(regDate) + '\'' +
                '}';
    }
}
