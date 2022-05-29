package com.example.familyfinance.controller;


import com.example.familyfinance.entity.OutlayEntity;
import com.example.familyfinance.service.OutlayEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Controller
public class OutlayEntityController {


    @Autowired
    private OutlayEntityService outlayEntityService;

    @RequestMapping("/createOutlay")
    public ModelAndView createOutlay(HttpServletRequest httpServletRequest) throws Exception {

        //获取用户输入的支出信息
        String date = httpServletRequest.getParameter("outlayDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date outlayDate = simpleDateFormat.parse(date);

        Float outlayAmount =  Float.valueOf(httpServletRequest.getParameter("outlayAmount"));
        String outlayType = httpServletRequest.getParameter("outlayType");

        Integer membersId = Integer.valueOf(httpServletRequest.getParameter("usersId"));

        OutlayEntity outlayEntity = new OutlayEntity(outlayType,outlayAmount,outlayDate,membersId);

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",membersId);
        //当前操作的表的类别
        modelList.addObject("type","outlay");

        ModelAndView modelForm = new ModelAndView("redirect:/pre_type");

        if(outlayEntityService.save(outlayEntity)){
            //添加支出记录成功 -->跳转到 IncomeEntityController ‘/pre_list’ controller 随后到outlayTable.jsp页面
            return modelList;
        }
        return  modelForm;
    }

    @RequestMapping("/delOutlay")
    public ModelAndView delOutlay(HttpServletRequest httpServletRequest){

        Integer userIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        //从table.jsp页面传过来的 要被删除的 支出id
        Integer outlayIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("outlayId"));
        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",userIdFromJSP);
        //当前操作的表的类别
        modelList.addObject("type","outlay");

        outlayEntityService.removeById(outlayIdFromJSP);
        return modelList;
    }
}
