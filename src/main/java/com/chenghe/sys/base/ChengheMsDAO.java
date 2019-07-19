package com.chenghe.sys.base;

import com.youguu.core.dao.SqlDAO;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.Resource;

public class ChengheMsDAO<T> extends SqlDAO<T> {
	public ChengheMsDAO() {
		super();
		setUseSimpleName(true);
	}

	@Resource(name = "chengheMsSessionFactory")
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

}
