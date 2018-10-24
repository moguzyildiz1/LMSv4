/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author moguzyildiz
 *
 */
@Entity
@Proxy(lazy=true)
@Table(name="tbl_book", catalog="library")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="bookId", scope=Book.class)
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer bookId;
	
	@NotEmpty
	@Length(max=45)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pubId")
	@JsonBackReference(value="pubToBook")
	private Publisher publisher;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "tbl_book_authors", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = { @JoinColumn(name = "authorId") })
	@JsonBackReference(value="authorToBook")//!!!!!BU 415 problemimi cozdu!!!!! nasil oldu bilmiyorum...
	private List<Author> authors;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "tbl_book_genres", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = { @JoinColumn(name = "genre_id") })
	@JsonBackReference(value="genreToBook")
	private List<Genre> genres;
	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return this.publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publish) {
		this.publisher = publish;
	}
	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	/**
	 * @return the genres
	 */
	public List<Genre> getGenres() {
		return genres;
	}
	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}
