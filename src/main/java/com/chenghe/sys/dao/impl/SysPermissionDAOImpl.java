package com.chenghe.sys.dao.impl;

import com.chenghe.sys.base.ChengheMsDAO;
import com.chenghe.sys.dao.SysPermissionDAO;
import com.chenghe.sys.pojo.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2017/11/23.
 */
@Repository
public class SysPermissionDAOImpl extends ChengheMsDAO<SysPermission> implements SysPermissionDAO {
    @Override
    public int saveSysPermission(SysPermission permission) {
        return this.insert(permission);
    }

    @Override
    public int updateSysPermission(SysPermission permission) {
        return this.update(permission);
    }

    @Override
    public int deleteSysPermission(int id) {
        return this.delete(id);
    }

    @Override
    public SysPermission getSysPermission(int id) {
        return this.get(id);
    }

    @Override
    public List<SysPermission> queryAllSysPermission() {
        return this.getAll();
    }

    @Override
    public List<SysPermission> findSysPermissionByRoleidAndPermissionId(int roleId, int parentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        map.put("roleId", roleId);
        return this.findBy("select_user_permission", map);
    }

    @Override
    public List<SysPermission> findSysPermissionByRoleid(int roleId) {
        return this.findBy("select_user_permission_by_rid", roleId);
    }

    @Override
    public List<SysPermission> findSysPermissionByAppid(int roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        return this.findBy("select_user_permission_by_appid", map);
    }
}
