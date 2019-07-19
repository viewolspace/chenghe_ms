package com.chenghe.sys.service.impl;

import com.youguu.base.BaseTest;
import com.youguu.core.util.PageHolder;
import com.chenghe.sys.pojo.SysRole;
import com.chenghe.sys.service.SysRoleService;
import org.junit.Test;

public class SysRoleServiceImplTest extends BaseTest {

	SysRoleService sysRoleService = (SysRoleService) getBean("sysRoleService");


	@Test
	public void testQuerySysRoleByPage() throws Exception {
		PageHolder<SysRole> pageHolder = sysRoleService.querySysRoleByPage(1,"", 1, 50);

		System.out.println(pageHolder.size());
	}
}