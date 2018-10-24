package com.gcit.lms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
	
	@Query("from Library where branchName like %:name%")
	public List<Library> readLibraries(@Param(value = "name") String branchName);
	
	@Query("from Library where branchId=:id")
	public Library readLibraryById(@Param(value = "id") Integer id);
}
