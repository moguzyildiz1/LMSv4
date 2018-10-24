package com.gcit.lms.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
	
	@Query("from Publisher where publisherName like %:name%")
	public List<Publisher> readPublishers(@Param(value = "name") String publisherName);
	
	@Query("from Publisher where publisherId=:id")
	public Publisher readPublisherById(@Param(value = "id") Integer id);
	
	@Modifying
	@Transactional
	@Query("update Publisher p set p.pubName=?1 where p.publisherId=?2")
	public void editPublisher(String pName, Integer pId);
}
