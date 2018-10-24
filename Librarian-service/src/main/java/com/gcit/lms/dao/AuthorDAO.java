package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Author;


@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>> {

	@Autowired
	BookDAO bdao;
	
	//***************************************************************************************************************************
	//
	public void addAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}
	//***************************************************************************************************************************
	//
	public void editAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}
	//***************************************************************************************************************************
	//
	public void deleteAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}
	//***************************************************************************************************************************
	//
	public List<Author> readAllAuthors()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_author", this);
	}
	//***************************************************************************************************************************
	//
	public List<Author> readAllAuthorsByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return libraryTemplate.query("Select * from tbl_author where authorName like ?", new Object[] { searchString },
				this);
	}
	//***************************************************************************************************************************
	//
	public Author readAuthorByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Author> authors = libraryTemplate.query("Select * from tbl_author where authorId = ?", new Object[] { pk },
				this);
		if (authors != null) {
			return authors.get(0);
		} else {
			return null;
		}
	}	
	//***************************************************************************************************************************
	//
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			author.setBooks(bdao.getBooksByAuthorID(rs.getInt("authorId")));
			authors.add(author);
		}
		return authors;
	}
	
	// ****************************************************************************************************************
	//
	public List<Author> getAuthorsByBookId(Integer bookId) {
		return libraryTemplate.query(
				"Select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)",
				new Object[] { bookId }, this);
	}
}
