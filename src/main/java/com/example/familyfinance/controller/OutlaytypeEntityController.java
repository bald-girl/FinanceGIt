package com.example.familyfinance.controller;


import com.example.familyfinance.entity.OutlaytypeEntity;
import com.example.familyfinance.service.OutlaytypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Controller
public class OutlaytypeEntityController {

    @Autowired
    private OutlaytypeEntityService outlaytypeEntityService;

    /**
     * 管理员添加 创建新的支出类型
     * @param httpServletRequest 管理员在页面输入的信息
     * @return 添加成功-->跳转到支出类型页面   添加失败-->重新回到本页面
     */
    @RequestMapping(value = "/createOutlayType")
    public ModelAndView createOutlayType(HttpServletRequest httpServletRequest){

        String outlayTypeName = httpServletRequest.getParameter("outlayTypeName");

        OutlaytypeEntity outlaytypeEntity = new OutlaytypeEntity(outlayTypeName);

        ModelAndView modelTypeList = new ModelAndView("redirect:/adminLogin");
        ModelAndView modelTypeAdd = new ModelAndView("redirect:/createOutlayType");

        if(outlaytypeEntityService.save(outlaytypeEntity)){
            return modelTypeList;
        }
        return modelTypeAdd;
    }

    /**
     * 管理员删除支出类型
     * @param httpServletRequest 获取管理员在页面点击的支出类型的编号
     * @param httpSession 获取管理员的编号和密码
     * @return
     */
    @RequestMapping(value = "/delOutlayType")
    public ModelAndView delOutlayType(HttpServletRequest httpServletRequest, HttpSession httpSession){

        //由于将“获取当前数据库中收支类型”写在adminLogin的控制层中，所以当管理员删除收支类型时需要先跳转到adminLogin
        ModelAndView modelTypeList = new ModelAndView("redirect:/adminLogin");
        //拼接参数-->当前登录用户的ID
        modelTypeList.addObject("adminPwd",httpSession.getAttribute("adminPwd"));
        modelTypeList.addObject("adminId",httpSession.getAttribute("adminId"));

        //从jsp页面传过来的 要被删除的 收入类型id
        Integer outlayTypeIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("outlaytypeId"));
        outlaytypeEntityService.removeById(outlayTypeIdFromJSP);
        return modelTypeList;
    }
}
