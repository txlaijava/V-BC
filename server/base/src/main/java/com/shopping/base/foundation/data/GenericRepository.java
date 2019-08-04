/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends Repository<T, ID> {

	List<T> findAll();

	List<T> findAll(Sort sort);

	long count();

	T findOne(ID id);

	T findById(ID id);

	<S extends T> S save(S entity);

	boolean exists(ID id);

	void delete(ID id);

	void delete(T entity);

	void delete(Iterable<? extends T> entities);

	void deleteAll();

	T findOne(Specification<T> spec);

	List<T> findAll(Specification<T> spec);

	Page<T> findAll(Specification<T> spec, Pageable pageable);

	List<T> findAll(Specification<T> spec, Sort sort);

	long count(Specification<T> spec);
}