package com.example.familyfinance.controller;


import com.example.familyfinance.entity.LendEntity;
import com.example.familyfinance.service.LendEntityService;
import com.example.familyfinance.util.PopupPrompt;
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
public class LendEntityController {

    @Autowired
    private LendEntityService lendEntityService;

    @RequestMapping("/createLend")
    public ModelAndView createLend(HttpServletRequest httpServletRequest) throws Exception{

        String LendName = httpServletRequest.getParameter("lendName");
        Integer membersId = Integer.valueOf(httpServletRequest.getParameter("usersId"));

        String date = httpServletRequest.getParameter("lendDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date LendDate = simpleDateFormat.parse(date);

        Float LendAmount =  Float.valueOf(httpServletRequest.getParameter("lendAmount"));

        LendEntity LendEntity = new LendEntity(LendName,membersId,LendDate,LendAmount );

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",membersId);
        //当前操作的表的类别
        modelList.addObject("type","lend");

        //to form2.jsp  IncomtypeEntityController类中的‘pre_type2’
        ModelAndView modelForm = new ModelAndView("redirect:/pre_type2");

        PopupPrompt pop = new PopupPrompt();
        if(lendEntityService.save(LendEntity)){
            //添加借出记录成功 -->跳转到 ‘/pre_list’ controller
            pop.new_style("添加借出记录成功！");
            return modelList;
        }else {
            pop.new_style("添加借出记录失败！");
            return  modelForm;
        }

    }

    @RequestMapping("/delLend")
    public ModelAndView delLend(HttpServletRequest httpServletRequest){

        Integer userIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        //从table.jsp页面传过来的 要被删除的 债权id
        Integer lendIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("lendId"));
        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",userIdFromJSP);
        //当前操作的表的类别
        modelList.addObject("type","lend");

        PopupPrompt pop = new PopupPrompt();
        if(lendEntityService.removeById(lendIdFromJSP)){
            pop.new_style("删除借出记录成功！");
            return modelList;
        }else {
            pop.new_style("删除借出记录失败！");
            return modelList;
        }
    }

}
