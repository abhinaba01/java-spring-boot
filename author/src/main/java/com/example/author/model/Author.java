package com.example.author.model;


	
	
	

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "author")
public class Author 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int authorId;
 
	private String authorName;
	@OneToOne(mappedBy = "author", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.LAZY)
	private Book book;
 
	public Author() {
		super();
	}
	public Author(String authorName) {
		super();
		this.authorName = authorName;
	}
 
	public int getAuthorId() {
		return authorId;
	}
 
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
 
	public String getAuthorName() {
		return authorName;
	}
 
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

 
	public Book getBook() {
		return book;
	}
 
	public void setBook(Book book) {
		this.book = book;
		if(book!=null)
			book.setAuthor(this);
	}
 
	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", authorName=" + authorName + "]";
	}


}
