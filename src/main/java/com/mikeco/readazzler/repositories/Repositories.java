package com.mikeco.readazzler.repositories;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Repositories implements InitializingBean {
	private static Repositories instance;

	public static Repositories getInstance() {
		return instance;
	}

	@Autowired
	private CategoryRepository categoryRespository;

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = this;
	}

	public CategoryRepository getCategoryRespository() {
		return categoryRespository;
	}

}