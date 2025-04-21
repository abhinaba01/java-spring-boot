package com.example.author.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 

import com.example.author.model.Author;
 
@Repository
public interface AuthorRepository 
	extends CrudRepository<Author, Integer> {
	
 
}
