package com.chenghe.sys.service.impl;

import com.youguu.core.util.PageHolder;
import com.chenghe.sys.dao.SysRoleDAO;
import com.chenghe.sys.dao.SysRolePermissionDAO;
import com.chenghe.sys.pojo.SysRole;
import com.chenghe.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by leo on 2017/11/23.
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDAO sysRoleDAO;
	@Resource
	private SysRolePermissionDAO sysRolePermissionDAO;

	@Override
	public int saveSysRole(SysRole role) {
		return sysRoleDAO.saveSysRole(role);
	}

	@Override
	public int updateSysRole(SysRole role) {
		return sysRoleDAO.updateSysRole(role);
	}

	@Override
	public int deleteSysRole(int id) {
		sysRoleDAO.deleteSysRole(id);//删除角色
		return sysRolePermissionDAO.deleteSysRolePermissionByRole(id);//删除角色关联的权限
	}

	@Override
	public SysRole getSysRole(int id) {
		return sysRoleDAO.getSysRole(id);
	}

	@Override
	public List<SysRole> listALLSysRole(int appId) {
		return sysRoleDAO.listALLSysRole(appId);
	}

	@Override
	public PageHolder<SysRole> querySysRoleByPage(String name, int pageIndex, int pageSize) {
		return sysRoleDAO.querySysRoleByPage(name, pageIndex, pageSize);
	}

	@Override
	public int relateApp(int id, int appId) {
		return sysRoleDAO.relateApp(id, appId);
	}
}
