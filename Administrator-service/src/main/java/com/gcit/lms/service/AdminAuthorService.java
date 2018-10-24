package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gcit.lms.entity.Author;
import com.gcit.lms.repositories.AuthorRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminAuthorService {

	@Autowired
	AuthorRepository authorRepo;

	// ***********************************************************************************************
	// Returns specified author by name or all authors with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<List<Author>>> readAuthors() {

		ResultServe<List<Author>> result = new ResultServe<>();
		List<Author> authors = new ArrayList<>();
		try {
			authors = (List<Author>) authorRepo.findAll();
			result.setData(authors);
			result.setMessage("All authors returned successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***********************************************************************************************
	// Returns specified author by name with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Author>> readAuthor(String name) {

		ResultServe<Author> result = new ResultServe<>();
		Author author = new Author();
		try {
			if (!name.isEmpty()) {
				author = authorRepo.readAuthors(name).get(0);
				result.setData(author);
				result.setMessage("Author: " + author.getAuthorName() + " returned successfully.");
			} else {
				result.setData(author);
				result.setMessage("Unvalid name entry");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			result.setMessage("Unvalid name entry");
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	// **********************************************************************************************
	//
	public Integer saveAuthorWithId(String aName) {

		Integer newId = 0;
		Author author = new Author();
		author.setAuthorName(aName);
		try {
			newId = (authorRepo.saveAndFlush(author)).getAuthorId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newId;
	}
	// ***********************************************************************************************
	// Returns the author object by Id. If cannot finds returns HttpStatus code
	// NO_CONTENT
	//
	public ResponseEntity<ResultServe<Author>> readAuthorById(Integer id) {

		ResultServe<Author> result = new ResultServe<>();
		try {
			result.setData(authorRepo.readAuthorsById(id));
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	// ***********************************************************************************************
	// Deletes author. And returns deleted author object with HttpStatus code OK.
	//
	public ResponseEntity<ResultServe<Author>> deleteAuthor(Integer id) {

		ResultServe<Author> result = new ResultServe<>();
		try {
			Author author = authorRepo.readAuthorsById(id);
			if (author == null) {				
				result.setData(author);
				result.setMessage("Cannot found such a record with id: " + id);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			} else {
				authorRepo.deleteById(id);
				result.setMessage(author.getAuthorName() + " succesfully deleted from library.");
			}
		} catch (Exception e) {
			result.setMessage("Database access error");
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	// ***********************************************************************************************
	// Save Author object to the database. Returns saved author object with
	// HttpStatus code ACCEPTED.
	//
	public ResponseEntity<ResultServe<Author>> saveAuthor(Author author) {
		ResultServe<Author> result = new ResultServe<>();
		try {
			result.setData(authorRepo.saveAndFlush(author));
			result.setMessage(author.getAuthorName() + " succesfully saved to library.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
