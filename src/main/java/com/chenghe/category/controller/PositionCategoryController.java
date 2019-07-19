package com.chenghe.category.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.pojo.SysUser;
import com.chenghe.sys.pojo.SysUserRole;
import com.chenghe.sys.service.SysUserRoleService;
import com.chenghe.sys.service.SysUserService;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.util.MD5;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("positionCategory")
public class PositionCategoryController {

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserRoleService sysUserRoleService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	@MethodLog(module = Constants.SYS_USER, desc = "添加用户")
	@Repeat
	public BaseResponse addUser(String userName, String password, String email, String phone, String realName, Integer
			roleId, Integer userStatus, Integer expoId) {
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		sysUser.setRealName(realName);
		sysUser.setEmail(email);
		sysUser.setPhone(phone);
		sysUser.setPswd(new MD5().getMD5ofStr(password).toLowerCase());
		sysUser.setUserStatus(userStatus);
		sysUser.setCreateTime(new Date());
		sysUser.setExpoId(expoId);
		int result = sysUserService.saveSysUser(sysUser);

		BaseResponse rs = new BaseResponse();
		if(result>0){
			SysUserRole userRole = new SysUserRole();
			userRole.setUid(result);
			userRole.setRid(roleId);
			userRole.setCreateTime(new Date());
			sysUserRoleService.saveSysUserRole(userRole);

			rs.setStatus(true);
			rs.setMsg("添加成功");
		} else {
			rs.setStatus(false);
			rs.setMsg("添加失败");
		}

		return rs;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	@MethodLog(module = Constants.SYS_USER, desc = "修改用户")
	@Repeat
	public BaseResponse updateUser(Integer id, String userName, String password, String email, String phone,
								   String realName, Integer roleId, Integer userStatus, Integer expoId) {
		BaseResponse rs = new BaseResponse();
		if(TokenManager.getUserId() == id){
			rs.setStatus(false);
			rs.setMsg("无权限修改，请联系超级管理员");
			return rs;
		}
		SysUser sysUser = sysUserService.getSysUser(id);
		if(null==sysUser){
			rs.setStatus(false);
			rs.setMsg("用户不存在或系统异常");
		}

		sysUser.setUserName(userName);
		sysUser.setRealName(realName);
		sysUser.setEmail(email);
		sysUser.setPhone(phone);
		sysUser.setUserStatus(userStatus);
		sysUser.setExpoId(expoId);
		int result = sysUserService.updateSysUser(sysUser);

		if(result>0){
			SysUserRole userRole = new SysUserRole();
			userRole.setUid(id);
			userRole.setRid(roleId);
			sysUserRoleService.updateSysUserRole(userRole);

			rs.setStatus(true);
			rs.setMsg("修改成功");
		} else {
			rs.setStatus(false);
			rs.setMsg("修改失败");
		}

		return rs;
	}

	@RequestMapping(value = "/deleteUser")
	@ResponseBody
	@MethodLog(module = Constants.SYS_USER, desc = "删除用户")
	@Repeat
	public BaseResponse deleteUser(int id) {
		BaseResponse rs = new BaseResponse();
		if(TokenManager.getUserId() == id){
			rs.setStatus(false);
			rs.setMsg("无权限删除，请联系超级管理员");
			return rs;
		}

		int result = sysUserService.deleteSysUser(id);
		if(result>0){
			sysUserRoleService.deleteSysUserRoleByUid(id);
			rs.setStatus(true);
			rs.setMsg("删除成功");
		} else {
			rs.setStatus(false);
			rs.setMsg("删除失败");
		}

		return rs;
	}

	@RequestMapping(value = "/userlist", method = RequestMethod.POST)
	@ResponseBody
	public GridBaseResponse userList(@RequestParam(value="userId", defaultValue="0") int userId,
									 String realName,
									 @RequestParam(value = "page", defaultValue = "1") int page,
									 @RequestParam(value = "limit", defaultValue = "10") int limit) {

		GridBaseResponse rs = new GridBaseResponse();
		rs.setCode(0);
		rs.setMsg("ok");

		PageHolder<SysUser> pageHolder = sysUserService.querySysUserByPage(TokenManager.getAppId(), userId, realName, page, limit);
		if(null != pageHolder.getList()){
			rs.setData(pageHolder.getList());
			rs.setCount(pageHolder.getTotalCount());
		}

		return rs;
	}
}
