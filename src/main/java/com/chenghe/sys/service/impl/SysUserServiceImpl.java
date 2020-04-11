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
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserDAO sysUserDAO;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private ICompanyService companyService;

	@Override
	public int saveSysUser(SysUser sysUser) {
		return sysUserDAO.saveSysUser(sysUser);
	}

	@Override
	public int updateSysUser(SysUser sysUser) {
		return sysUserDAO.updateSysUser(sysUser);
	}

	@Override
	public int deleteSysUser(int id) {
		return sysUserDAO.deleteSysUser(id);
	}

	@Override
	public SysUser getSysUser(int id) {
		return sysUserDAO.getSysUser(id);
	}

	@Override
	public PageHolder<SysUser> querySysUserByPage(int userId, String realName, int pageIndex, int pageSize) {
		return sysUserDAO.querySysUserByPage(userId, realName, pageIndex, pageSize);
	}

	@Override
	public SysUser findSysUserByUserName(String username) {
		SysUser sysUser = sysUserDAO.findSysUserByUserName(username);
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
		return sysUserDAO.updateLastLoginTime(userName, lastLoginTime);
	}

	@Override
	public int updatePwd(String userName, String oldPwd, String newPwd) {
		return sysUserDAO.updatePwd(userName, oldPwd, newPwd);
	}

}
