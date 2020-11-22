package com.chenghe.sys.service.impl;

import com.chenghe.parttime.pojo.Company;
import com.chenghe.parttime.service.ICompanyService;
import com.youguu.core.util.PageHolder;
import com.chenghe.sys.dao.SysUserDAO;
import com.chenghe.sys.pojo.SysRole;
import com.chenghe.sys.pojo.SysUser;
import com.chenghe.sys.service.SysRoleService;
import com.chenghe.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by leo on 2017/11/23.
 */
@Service("systemUserService")
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserDAO systemUserDAO;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private ICompanyService companyService;

	@Override
	public int saveSysUser(SysUser sysUser) {
		return systemUserDAO.saveSysUser(sysUser);
	}

	@Override
	public int updateSysUser(SysUser sysUser) {
		return systemUserDAO.updateSysUser(sysUser);
	}

	@Override
	public int deleteSysUser(int id) {
		return systemUserDAO.deleteSysUser(id);
	}

	@Override
	public SysUser getSysUser(int id) {
		return systemUserDAO.getSysUser(id);
	}

	@Override
	public PageHolder<SysUser> querySysUserByPage(int userId, String realName, int pageIndex, int pageSize) {
		return systemUserDAO.querySysUserByPage(userId, realName, pageIndex, pageSize);
	}

	@Override
	public SysUser findSysUserByUserName(String username) {
		SysUser sysUser = systemUserDAO.findSysUserByUserName(username);
		if(null != sysUser){
			try {
				Company company = companyService.getCompany(Integer.parseInt(sysUser.getCompanyId()));
				if (null != company) {
					sysUser.setAppId(company.getAppId());
				}
			} catch (NumberFormatException e) {
				// TODO 不处理
			}
		}

		return sysUser;
	}

	@Override
	public int updateLastLoginTime(String userName, Date lastLoginTime) {
		return systemUserDAO.updateLastLoginTime(userName, lastLoginTime);
	}

	@Override
	public int updatePwd(String userName, String oldPwd, String newPwd) {
		return systemUserDAO.updatePwd(userName, oldPwd, newPwd);
	}

}
