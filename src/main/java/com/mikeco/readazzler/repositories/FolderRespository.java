package com.mikeco.readazzler.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.Folder;

public interface FolderRespository extends PagingAndSortingRepository<Folder, Long> {
	public List<Folder> findByLabel(String label);
}
