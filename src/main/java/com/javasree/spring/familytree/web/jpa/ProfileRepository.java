package com.javasree.spring.familytree.web.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javasree.spring.familytree.model.profile.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

	public List<Profile> findByParentId(Long parentId);
	
	@Query("select CASE WHEN COUNT(p) > 0 THEN true ELSE false END from Profile p where p.parentId = :parentId")
	public boolean existsByParentId(@Param("parentId") Long parentId);
}
