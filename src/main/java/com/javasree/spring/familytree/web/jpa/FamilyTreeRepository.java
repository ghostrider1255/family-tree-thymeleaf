package com.javasree.spring.familytree.web.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeRepository extends JpaRepository<FamilyTree, Long>{

}
