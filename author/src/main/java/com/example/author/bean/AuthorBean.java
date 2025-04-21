package com.example.author.bean;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 

import com.example.author.dao.AuthorRepository;
import com.example.author.dao.BookRepository;
import com.example.author.model.Author;
import com.example.author.model.Book;
 
@Component("Author_Book_Bean")
public class AuthorBean
{
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;
	public AuthorBean() {
		super();
	}
	public AuthorBean(BookRepository bookRepository,
			AuthorRepository authorRepository) 
	{
		super();
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
      }
	public void saveAuthorWithBook()
	{
		Author author = new Author("J.K Rowling");
		Book  book = new Book("Harry Potter and the Chamber of Secrets", 1500);
		author.setBook(book);
		authorRepository.save(author);
	}
 
	public void findAuthorWithBook(int aid) {
		Author author = 
				authorRepository.findById(aid).orElse(null);
		System.out.println("Found Author with Book:");
		System.out.println("Author Id: " + author.getAuthorId());
		System.out.println("Author Name: " + author.getAuthorName());
		System.out.println("Book Id: " + author.getBook().getBookId());
		System.out.println("Book Name: " + author.getBook().getBookName());
		System.out.println("Book Price: " + author.getBook().getPrice());
	}
	public void findBookWithAuthor(int bid) {
		Book book = 
				bookRepository.findById(bid).orElse(null);
		System.out.println("Found Book with Author:");
		System.out.println("Book Id: " + book.getBookId());
		System.out.println("Book Name: " + book.getBookName());
		System.out.println("Author Id: " + book.getAuthor().getAuthorId());
		System.out.println("Author Name: " + book.getAuthor().getAuthorName());
	}
}
