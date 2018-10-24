/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author moguzyildiz
 *
 */
@Entity
@Table(name="tbl_genre", catalog="library")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="genreId", scope=Publisher.class)
public class Genre {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="genre_id")
	private Integer genreId;
	
	@Column(name="genre_name")
	@NotEmpty
	@Length(max=45)
	private String genreName;
	
	//In book entity fetchType is EAGER. So here it's LAZY
	//And no need to write any query
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="genres")//this genres is Book entity's List<Genre> genres!
	private List<Book> books;
	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
