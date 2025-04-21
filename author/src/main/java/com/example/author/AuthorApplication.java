package com.example.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.author.bean.AuthorBean;

@SpringBootApplication
@ComponentScan("com.example.author.bean")
public class AuthorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AuthorApplication.class, args);

		AuthorBean authorBookBran = context.getBean("Author_Book_Bean", AuthorBean.class);
		authorBookBran.saveAuthorWithBook();
		authorBookBran.findAuthorWithBook(1);
		authorBookBran.findBookWithAuthor(1);
	}

}
