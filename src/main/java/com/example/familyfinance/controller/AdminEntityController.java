package com.example.familyfinance.controller;


import com.example.familyfinance.entity.AdminEntity;
import com.example.familyfinance.entity.IncometypeEntity;
import com.example.familyfinance.entity.OutlaytypeEntity;
import com.example.familyfinance.service.AdminEntityService;
import com.example.familyfinance.service.IncometypeEntityService;
import com.example.familyfinance.service.OutlaytypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Controller
public class AdminEntityController {

    @Autowired
    private AdminEntityService adminEntityService;

    @Autowired
    private IncometypeEntityService incometypeEntityService;

    @Autowired
    private OutlaytypeEntityService outlaytypeEntityService;

    @RequestMapping(value = "/admin_pre_login")
    public String admin_pre_login() {

        return "user/adminLogin";
    }

    @RequestMapping("/adminLogin")
    public String adminLogin(HttpServletRequest httpServletRequest,
                             HttpSession session,
                             ModelMap modelMap){
        Integer adminId;
        String adminPwd;
        if(httpServletRequest.getParameter("adminId") == null){
            adminId = (Integer) session.getAttribute("adminId");
        }
        else {
            adminId = Integer.valueOf(httpServletRequest.getParameter("adminId"));
        }
        if (httpServletRequest.getParameter("adminPwd") == null){
            adminPwd = (String) session.getAttribute("adminPwd");
        }
        else {
            adminPwd = httpServletRequest.getParameter("adminPwd");
        }


        //通过账号查询数据库 结果不为null则存在
        if (adminEntityService.getById(adminId) != null) {
            AdminEntity adminById = adminEntityService.getById(adminId);

            //判断数据库中该账号对应的密码与用户输入是否相同
            if (adminById.getAdminPwd().equals(adminPwd)) {
                //将成功登录的管理员设置到session中
                session.setAttribute("adminName", adminById.getAdminName());
                session.setAttribute("adminId", adminById.getAdminId());
                session.setAttribute("adminPwd",adminPwd);

                //查询所有的收支类型
                List<IncometypeEntity> incomeTypeList = incometypeEntityService.list();
                List<OutlaytypeEntity> outlayTypeList = outlaytypeEntityService.list();

                //将收支类型列表添加到ModelMap中给前端使用
                modelMap.addAttribute("incomeTypeList",incomeTypeList);
                modelMap.addAttribute("outlayTypeList",outlayTypeList);

                return "backstage";
            } else {
                httpServletRequest.setAttribute("error","账号或密码错误！");
                return "user/adminLogin";
            }
        }else {
            httpServletRequest.setAttribute("error","账号或密码错误！");
            return "user/adminLogin";
        }

    }
}
