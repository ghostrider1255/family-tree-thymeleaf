package com.javasree.spring.familytree.web.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javasree.spring.familytree.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

	public List<Profile> findByParentIdAndLifePartner(Long parentId, boolean isParent);
	
	public List<Profile> findByParentId(Long parentId);
	
	@Query("select CASE WHEN COUNT(p) > 0 THEN true ELSE false END from Profile p where p.parentId = :parentId")
	public boolean existsByParentId(@Param("parentId") Long parentId);
	
	@Query("select p from Profile p where p.lifePartner = TRUE and p.parentId = :parentId")
	public Profile getLifePartner(@Param("parentId") Long parentId);
}
