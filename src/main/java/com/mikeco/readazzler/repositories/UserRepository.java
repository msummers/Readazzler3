package com.mikeco.readazzler.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	public List<User> findByName(String name);
}
