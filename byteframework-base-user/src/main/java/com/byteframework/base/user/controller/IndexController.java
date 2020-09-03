package com.byteframework.base.user.controller;
import com.byteframework.commons.web.BaseAction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始页面
 *
 * @Author Sans
 * @CreateTime 2019/10/2 15:11
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseAction {


    /**
     * 首页
     *
     * @Author Sans
     * @CreateTime 2019/10/2 15:23
     * @Return Map<String, Object> 返回数据MAP
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public void userLogin(HttpServletRequest request, HttpServletResponse response) {
        this.responseSuccess(request, response, "这里是首页不需要权限和登录拦截");
    }

}
