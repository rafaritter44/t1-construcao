package br.pucrs.construcao.t1.backend.dto;

public class Book {
	
	private String title;
	private String author;
	private int totalPages;
	private int readPages;
	
	public Book() {}
	
	public Book(String title, String author, int totalPages, int readPages) {
		this.title = title;
		this.author = author;
		this.totalPages = totalPages;
		this.readPages = readPages;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public int getReadPages() {
		return readPages;
	}
	
	public void setReadPages(int readPages) {
		this.readPages = readPages;
	}
	
}
