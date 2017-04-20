package com.mikeco.readazzler.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mikeco.readazzler.models.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
	Logger log = LoggerFactory.getLogger(CategoryRepository.class);

	@Transactional
	public default Category findOrCreate(String name, String taxonomyUri) {
		List<Category> result = findByNameAndTaxonomyUri(name, taxonomyUri);
		switch (result.size()) {
		case 0:
			Category category = new Category(name, taxonomyUri);
			this.save(category);
			return category;
		case 1:
			return result.get(0);
		default:
			log.error(String.format("Duplicates for Category name: %s taxonomyUri: %s", name, taxonomyUri));
			return result.get(0);
		}
	}

	public List<Category> findByNameAndTaxonomyUri(String name, String taxonomyUri);
}