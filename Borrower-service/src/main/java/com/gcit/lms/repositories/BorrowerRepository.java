package com.gcit.lms.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
	
	@Query("from Borrower where name like %:bname%")
	public List<Borrower> readBorrowers(@Param(value = "bname") String bname);
	
	@Query("from Borrower where cardNo=:cardNo")
	public Borrower readBorrowerByNo(@Param(value = "cardNo") Integer cardNo);
	

	@Modifying
	@Transactional
	@Query("update Borrower b set b.borrowerName=?1 where b.cardNo=?2")
	public void editBorrower(String bName, Integer bId);
}
