package com.chenghe.shiro.token;

import com.chenghe.position.controller.PositionController;
import com.chenghe.sys.pojo.SysUser;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import org.apache.shiro.SecurityUtils;

/**
 * Created by leo on 2017/11/27.
 */
public class TokenManager {

    private static Log log = LogFactory.getLog(TokenManager.class);

    public static SysUser getToken() {
        SysUser token = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return token;
    }

    public static String getUserName() {
        return getToken() == null ? null : getToken().getUserName();
    }

    public static String getRealName() {
        return getToken() == null ? null : getToken().getRealName();
    }


    public static String getCompanyId() {
        return null == getToken() ? null : getToken().getCompanyId();
    }

    public static Integer getUserId() {
        return getToken() == null ? null : getToken().getId();
    }

    public static Integer getAppId() {
        if (getToken() == null) {
            log.debug("getToken() is null, return appId: 0");
            return 0;
        }
        log.debug("getToken() is not null, return appId: {}", getToken().getAppId());
        return getToken().getAppId();
    }

    /**
     * 用于管理员(admin)登录成功后选择APP时，把appId设置到token里，方便后续使用
     *
     * @param appId
     * @return
     */
    public static int setAppId(int appId) {
        if (getToken() == null) {
            return -1;
        }
        getToken().setAppId(appId);
        return 1;
    }

    public static Integer getRoleId() {
        return getToken() == null ? null : getToken().getRoleId();
    }


    public static SysUser login(SysUser user, Boolean rememberMe) {
        ShiroToken token = new ShiroToken(user.getUserName(), user.getPswd());
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);
        return getToken();
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
}
