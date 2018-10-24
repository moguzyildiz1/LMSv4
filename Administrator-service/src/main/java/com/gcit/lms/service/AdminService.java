package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.util.ErrorResponse;

@RestController
public class AdminService {

	@Autowired
	BookDAO bdao;

	@Autowired
	AuthorDAO adao;

	//***********************************************************************************************
	//
	@RequestMapping(value = "/readAllAuthors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> readAllAuthors(@RequestParam String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			if (!searchString.isEmpty()) {
				authors = adao.readAllAuthorsByName(searchString);
			} else {
				authors = adao.readAllAuthors();
			}
			for (Author a : authors) {
				a.setBooks(bdao.getBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//***********************************************************************************************
	//
	@RequestMapping(value = "/readAuthorsByName/{searchString}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Author> readAuthorsByName(@PathVariable("searchString") String searchString) {
		try {
			List<Author> authors = adao.readAllAuthorsByName(searchString);
			for (Author a : authors) {
				a.setBooks(bdao.getBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//***********************************************************************************************
	//
	@RequestMapping(value = "/saveAuthor", method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public ErrorResponse saveAuthor(@RequestBody Author author) {
		ErrorResponse resp = new ErrorResponse();
		try {
			if (author.getAuthorId() != null && author.getAuthorName() != null) {
				adao.editAuthor(author);
				resp.setErrorMessage("Auther updated sucessfully");
			} else if (author.getAuthorId() != null) {
				adao.deleteAuthor(author);
				resp.setErrorMessage("Auther deleted sucessfully");
			} else {
				adao.addAuthor(author);
				resp.setErrorMessage("Auther added sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	//*************************************************************************************************
	//
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces="application/json")
	public Author initAuthor(){
		return new Author();
	}	
}
