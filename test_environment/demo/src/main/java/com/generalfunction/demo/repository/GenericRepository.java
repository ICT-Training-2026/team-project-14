package com.generalfunction.demo.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
	 	T findById(String getTableName,String getIdColumnName,ID id);
	    List<T> findAll(String getTableName);
	    int insert(T entity);
	    int update(T entity);
	    int deleteById(String getTableName,String getIdColumnName,ID id);

}
