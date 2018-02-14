package com.javasree.spring.familytree.web.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeRepository extends PagingAndSortingRepository<FamilyTree, Long>{

}
