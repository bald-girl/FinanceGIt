package com.example.familyfinance.controller;



import com.example.familyfinance.entity.BorrowEntity;
import com.example.familyfinance.service.BorrowEntityService;
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
public class BorrowEntityController {

    @Autowired
    private BorrowEntityService borrowEntityService;

    @RequestMapping("/createBorrow")
    public ModelAndView createBorrow(HttpServletRequest httpServletRequest) throws Exception {

        String borrowName = httpServletRequest.getParameter("borrowName");
        Integer membersId = Integer.valueOf(httpServletRequest.getParameter("usersId"));

        String date = httpServletRequest.getParameter("borrowDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date borrowDate = simpleDateFormat.parse(date);

        Float borrowAmount =  Float.valueOf(httpServletRequest.getParameter("borrowAmount"));

        BorrowEntity borrowEntity = new BorrowEntity(borrowName,membersId,borrowDate,borrowAmount );

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",membersId);
        modelList.addObject("type","borrow");

        //to form2.jsp  IncomtypeEntityController类中的‘pre_type2’-->添加债权债务信息
        ModelAndView modelForm = new ModelAndView("redirect:/pre_type2");

        PopupPrompt pop = new PopupPrompt();
        if(borrowEntityService.save(borrowEntity)){
            //添加借入记录成功 -->跳转到 ‘/pre_list’ controller
            pop.new_style("添加借入信息成功！");
            return modelList;
        }else {
            pop.new_style("添加借入信息失败！");
            return  modelForm;
        }

    }


    @RequestMapping("/delBorrow")
    public ModelAndView delBorrow(HttpServletRequest httpServletRequest){
        PopupPrompt pop = new PopupPrompt();

        //当前用户id
        Integer userIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        //从table.jsp页面传过来的 要被删除的 支出id
        Integer borrowIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("borrowId"));
        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",userIdFromJSP);
        //当前操作的表的类别
        modelList.addObject("type","borrow");

        if(borrowEntityService.removeById(borrowIdFromJSP)){
            pop.new_style("删除借入信息成功！");
        }else {
            pop.new_style("删除借入信息失败！");
        }
        return modelList;
    }
}
