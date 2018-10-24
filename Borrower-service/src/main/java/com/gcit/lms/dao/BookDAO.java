package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Book;

@Component
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>> {
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	AuthorDAO adao;
	
	// ********************************************************************************************************
	//
	public void addBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}

	// ********************************************************************************************************
	//
	public Integer addBookWithID(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}

	// ********************************************************************************************************
	//
	public void editBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	// ********************************************************************************************************
	//
	public void deleteBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	// ********************************************************************************************************
	//
	public List<Book> readAllBooks()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book", this);
	}

	// ********************************************************************************************************
	//
	public List<Book> readBookById(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book where bookId=?", new Object[] { id }, this);
	}

	// ********************************************************************************************************
	//
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setAuthors(adao.getAuthorsByBookId(rs.getInt("bookId")));
			book.setGenres(gdao.getGenresByBookId(rs.getInt("bookId")));
			try {
				book.setPublisher(pdao.readPublisherByPK(rs.getInt("pubId")));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			books.add(book);
		}
		return books;
	}

	// ****************************************************************************************************************
	//
	public List<Book> getBooksByAuthorID(Integer authorId) {
		return libraryTemplate.query(
				"Select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)",
				new Object[] { authorId }, this);
	}

	// ****************************************************************************************************************
	//
	public List<Book> getBooksByGenreID(Integer genreId) {
		return libraryTemplate.query(
				"Select * from tbl_book where bookId IN (select bookId from tbl_book_genres where genre_id = ?)",
				new Object[] { genreId }, this);
	}

	// ********************************************************************************************************
	//
	public List<Book> readBookByPubId(Integer id) {
		return libraryTemplate.query("Select * from tbl_book where pubId=?", new Object[] { id }, this);
	}
}
