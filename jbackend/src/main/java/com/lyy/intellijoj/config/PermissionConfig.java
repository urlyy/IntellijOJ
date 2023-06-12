package com.lyy.intellijoj.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionConfig implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }

    public static void main(String[] args) {
        // 获取：当前账号所拥有的权限集合
        StpUtil.getPermissionList();

        // 判断：当前账号是否含有指定权限, 返回 true 或 false
        StpUtil.hasPermission("user.add");

        // 校验：当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException
        StpUtil.checkPermission("user.add");

        // 校验：当前账号是否含有指定权限 [指定多个，必须全部验证通过]
        StpUtil.checkPermissionAnd("user.add", "user.delete", "user.get");

        // 校验：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]


        // 获取：当前账号所拥有的角色集合
        StpUtil.getRoleList();

        // 判断：当前账号是否拥有指定角色, 返回 true 或 false
        StpUtil.hasRole("super-admin");

        // 校验：当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
        StpUtil.checkRole("super-admin");

        // 校验：当前账号是否含有指定角色标识 [指定多个，必须全部验证通过]
        StpUtil.checkRoleAnd("super-admin", "shop-admin");

        // 校验：当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可]
        StpUtil.checkRoleOr("super-admin", "shop-admin");

        // 当拥有 *.delete 权限时
        StpUtil.hasPermission("art.delete");      // true
        StpUtil.hasPermission("user.delete");     // true
        StpUtil.hasPermission("user.update");     // false
//按钮级权限
//        如果是前后端分离项目，则：
//
//        在登录时，把当前账号拥有的所有权限码一次性返回给前端。
//        前端将权限码集合保存在localStorage或其它全局状态管理对象中。
//        在需要权限控制的按钮上，使用 js 进行逻辑判断，例如在Vue框架中我们可以使用如下写法：
//          <button v-if="arr.indexOf('user.delete') > -1">删除按钮</button>
//                复制到剪贴板错误复制成功
//        其中：arr是当前用户拥有的权限码数组，user.delete是显示按钮需要拥有的权限码，删除按钮是用户拥有权限码才可以看到的内容。

        //主动下线
//        StpUtil.logout(10001);                    // 强制指定账号注销下线
//        StpUtil.logout(10001, "PC");              // 强制指定账号指定端注销下线
//        StpUtil.logoutByTokenValue("token");      // 强制指定 Token 注销下线
        //被动下线
//        StpUtil.kickout(10001);                    // 将指定账号踢下线
//        StpUtil.kickout(10001, "PC");              // 将指定账号指定端踢下线
//        StpUtil.kickoutByTokenValue("token");      // 将指定 Token 踢下线

//        @SaCheckLogin: 登录校验 —— 只有登录之后才能进入该方法。
//        @SaCheckRole("admin"): 角色校验 —— 必须具有指定角色标识才能进入该方法。
//        @SaCheckPermission("user:add"): 权限校验 —— 必须具有指定权限才能进入该方法。
//        @SaCheckSafe: 二级认证校验 —— 必须二级认证之后才能进入该方法。
//        @SaCheckBasic: HttpBasic校验 —— 只有通过 Basic 认证后才能进入该方法。
//        @SaIgnore：忽略校验 —— 表示被修饰的方法或类无需进行注解鉴权和路由拦截器鉴权。
//        @SaCheckDisable("comment")：账号服务封禁校验 —— 校验当前账号指定服务是否被封禁。



        //管理员操作要有二级认证，即登录完后再登录

        //封号
        //// 先踢下线
        //StpUtil.kickout(10001);
        //// 再封禁账号
        //StpUtil.disable(10001, 86400);


        //前后端单点登录


        //使用SaTokenDao接口


//        Q：我使用过滤器鉴权 or 全局拦截器鉴权，结果 Swagger 不能访问了，我应该排除哪些地址？
//        尝试加上排除 "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**" ,"/doc.html/**","/error","/favicon.ico"
//
//        不同版本可能会有所不同，其实在前端摁一下 F12 看看哪个 url 报错排除哪个就行了（另附：注解鉴权是不需要排除的，因为 Swagger 本身也没有使用 Sa-Token 的注解）

    }

}