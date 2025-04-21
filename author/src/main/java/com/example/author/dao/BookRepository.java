package com.example.author.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.author.model.Book;
	 

	 
	@Repository
	public interface BookRepository
		extends CrudRepository<Book, Integer> {
		
	}

