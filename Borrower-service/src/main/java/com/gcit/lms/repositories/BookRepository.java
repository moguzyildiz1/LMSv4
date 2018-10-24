package com.gcit.lms.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	@Query("from Book where title like %:title%")
	public List<Book> readBooks(@Param(value = "title") String title);
	
	@Query("from Book where bookId=:id")
	public Book readBookById(@Param(value = "id") Integer id);
	
	@Modifying
	@Transactional
	@Query("update Book b set b.title=?1 where b.bookId=?2")
	public void editBook(String bTitle, Integer bId);
}
