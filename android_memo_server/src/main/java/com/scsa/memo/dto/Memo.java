package com.scsa.memo.dto;


public class Memo{
	private int id;
	private String title;
	private String body;
	private long regDate;
	
	
	
	public Memo(int id, String title, String body, long regDate) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
	}
	
	public Memo() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
		return "Memo [id=" + id + ", title=" + title + ", body=" + body + ", regDate=" + regDate + "]";
	}
	
}	