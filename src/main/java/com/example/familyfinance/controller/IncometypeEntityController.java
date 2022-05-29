package com.example.familyfinance.controller;


import com.example.familyfinance.entity.IncometypeEntity;
import com.example.familyfinance.entity.OutlaytypeEntity;
import com.example.familyfinance.service.IncometypeEntityService;
import com.example.familyfinance.service.OutlaytypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
public class IncometypeEntityController {

    @Autowired
    IncometypeEntityService incometypeEntityService ;

    @Autowired
    OutlaytypeEntityService outlaytypeEntityService;


    /**
     * 在用户登录成功加载页面之前 获取收支类型 存到列表中
     * 或者说 在任何一个页面要跳转到 收支管理 界面时都要先经过此操作
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/pre_type")
    //to form
    public String incomeType(ModelMap modelMap){
        //查询收入、支出类型
        List<IncometypeEntity> incomeTypeList = incometypeEntityService.list();
        List<OutlaytypeEntity> outlayTypeList = outlaytypeEntityService.list();

        modelMap.addAttribute("incomeType",incomeTypeList);
        modelMap.addAttribute("outlayType",outlayTypeList);

        return "form";
    }

    /**
     * 当用户前往 债权债务页面时 先经过此处controller
     * @return form2.jsp页面
     */
    @RequestMapping(value = "/pre_type2")
    public String toForm2(){
        ModelAndView modelList = new ModelAndView("redirect:/pre_list");

        return "form2";
    }

    /**
     * 管理员添加收入类型
     * @param httpServletRequest 获取表单数据
     * @return modelview
     */
    @RequestMapping(value = "/createIncomeType")
    public ModelAndView createIncomeType(HttpServletRequest httpServletRequest){

        String incomeTypeName = httpServletRequest.getParameter("incomeTypeName");

        IncometypeEntity incometypeEntity = new IncometypeEntity(incomeTypeName);

        ModelAndView modelTypeList = new ModelAndView("redirect:/adminLogin");
        ModelAndView modelTypeAdd = new ModelAndView("redirect:/createIncomeType");

        if(incometypeEntityService.save(incometypeEntity)){
            return modelTypeList;
        }
        return modelTypeAdd;
    }



    @RequestMapping(value = "/delIncomeType")
    public ModelAndView delIncomeType(HttpServletRequest httpServletRequest, HttpSession httpSession){

        ModelAndView modelTypeList = new ModelAndView("redirect:/adminLogin");
        //拼接参数-->当前登录用户的ID
        modelTypeList.addObject("adminPwd",httpSession.getAttribute("adminPwd"));
        modelTypeList.addObject("adminId",httpSession.getAttribute("adminId"));

        //从jsp页面传过来的 要被删除的 收入类型id
        Integer incomeTypeIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("incometypeId"));
        incometypeEntityService.removeById(incomeTypeIdFromJSP);
        return modelTypeList;
    }






}
