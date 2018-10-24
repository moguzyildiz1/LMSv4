package com.gcit.lms.entity;

import org.springframework.stereotype.Component;

@Component
public class Inventory {

	private Library library;
	private Book book;
	private Integer copies;

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book
	 *            the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return the copies
	 */
	public Integer getCopies() {
		return copies;
	}

	/**
	 * @param copies
	 *            the copies to set
	 */
	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	/**
	 * @return the library
	 */
	public Library getLibrary() {
		return library;
	}

	/**
	 * @param library
	 *            the library to set
	 */
	public void setLibrary(Library library) {
		this.library = library;
	}
}
