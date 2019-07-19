package com.chenghe.sys.dao.impl;

import com.chenghe.sys.base.ChengheMsDAO;
import com.chenghe.sys.dao.SysRolePermissionDAO;
import com.chenghe.sys.pojo.SysRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leo on 2017/11/23.
 */
@Repository
public class SysRolePermissionDAOImpl extends ChengheMsDAO<SysRolePermission> implements SysRolePermissionDAO {
    @Override
    public int batchSaveSysRolePermission(List<SysRolePermission> sysRolePermissionList) {
        return this.batchInsert(sysRolePermissionList, "batchSaveSysRolePermission");
    }

    @Override
    public int deleteSysRolePermissionByRole(int roleId) {
        return this.deleteBy("deleteSysRolePermissionByRole", roleId);
    }

    @Override
    public List<SysRolePermission> listSysRolePermissionByRole(Integer... roleId) {
        return this.findBy("listSysRolePermissionByRole", roleId);
    }
}