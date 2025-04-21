package com.example.author.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "book")
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	private String bookName;
	private float price;
	@OneToOne
	@JoinColumn(name = "authorId")//Foreign key in bookTable
	private Author author;
	public Book() {
		super();
	}
	public Book(String bookName, float price) {
		super();
		this.bookName = bookName;
		this.price = price;
	}
 
	public int getBookId() {
		return bookId;
	}
 
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
 
	public String getBookName() {
		return bookName;
	}
 
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
 
	public Author getAuthor() {
		return author;
	}
 
	public void setAuthor(Author author) {
		this.author = author;
	}
 
	public float getPrice() {
		return price;
	}
 
	public void setPrice(float price) {
		this.price = price;
	}
 
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", price=" + price + "]";
	}

 
}
