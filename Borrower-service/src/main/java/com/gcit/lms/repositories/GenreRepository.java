package com.gcit.lms.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	@Query("from Genre where genreName like %:name%")
	public List<Genre> readGenres(@Param(value = "name") String genreName);
	
	@Query("from Genre where genreName=:name")
	public Genre findByName(@Param(value = "name") String genreName);
	
	@Query("from Genre where genreId=:id")
	public Genre readGenreById(@Param(value = "id") Integer id);
	
	@Modifying
	@Transactional
	@Query("update Genre g set g.genreName=?1 where g.genreId=?2")
	public void editGenre(String gName, Integer gId);
}
