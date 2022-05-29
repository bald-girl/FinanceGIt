package com.example.familyfinance.controller;

import com.example.familyfinance.entity.MembersEntity;
import com.example.familyfinance.entity.UsersEntity;
import com.example.familyfinance.service.MembersEntityService;
import com.example.familyfinance.service.UsersEntityService;
import com.example.familyfinance.util.PopupPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Controller
public class MembersEntityController {

    @Autowired
    private MembersEntityService membersEntityService;

    @Autowired
    private UsersEntityService usersEntityService;

    /**
     * 家主 添加家庭成员 ：回先判断家庭成员编号和昵称是否匹配
     * @param httpServletRequest 获取家主填写的家庭成员的信息
     * @return
     */
    @PreAuthorize("hasAnyAuthority('homeowner')")
    @RequestMapping(value = "/createMember")
    public ModelAndView createMember(HttpServletRequest httpServletRequest) {

        UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        String membersName = httpServletRequest.getParameter("membersName");
        String membersRelation = httpServletRequest.getParameter("membersRelation");
        //所填写的成员编号
        Integer membersId = Integer.valueOf(httpServletRequest.getParameter("membersId"));
        //当前用户编号
        Integer homeownerId = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        //当前用户名称
        String homeownerName = httpServletRequest.getParameter("usersName");

        MembersEntity membersEntity = new MembersEntity(membersId,homeownerId,membersName,membersRelation);

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //当前操作的表的类别
        modelList.addObject("type","family");

//        ModelAndView modelForm = new ModelAndView("redirect:/pre_family");
        ModelAndView go_back = new ModelAndView("redirect:/go_back");

        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",homeownerId);

        PopupPrompt pop = new PopupPrompt();
        //如果 用户表中该成员的编号、名称与当前用户输入的成员编号、名称都匹配 ---> 将该成员添加到家庭成员表中
        if(usersEntityService.getById(membersId)!=null&&usersEntityService.getById(membersId).getUsersName().equals(membersName)){
            if(membersEntityService.saveOrUpdate(membersEntity)){
                //如果当前用户还不在成员表中--->将当前用户添加进去 使用saveOrUpdate防止数据库中已经有
                MembersEntity homeownerEntity = new MembersEntity(homeownerId,homeownerId,homeownerName,"本人");
                membersEntityService.saveOrUpdate(homeownerEntity);
                //添加成功 -->跳转到 incomeEntityController : ‘/pre_list’
                pop.new_style("添加家庭成员成功！");
                return modelList;
            }else {
                pop.new_style("添加家庭成员失败！");
                return  go_back;
            }
        }else {
            pop.new_style("您输入的家庭成员信息不匹配！");
            return  go_back;
        }
    }

    @RequestMapping(value = "/pre_family")
    public String toForm2(){

        return "family";
    }

    /**
     * 家主 删除家庭成员
     * @param httpServletRequest 获取要被删除的家庭成员的编号
     * @return
     */
    @PreAuthorize("hasAnyAuthority('homeowner')")
    @RequestMapping(value = "/deleteMember")
    public ModelAndView deleteMember(HttpServletRequest httpServletRequest){

        //当前用户是家主 所有获取的id即家主id
        Integer homeownerIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("usersId"));

        //从table.jsp页面传过来的 要被删除的 家庭成员id
        Integer memberIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("memberId"));

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",homeownerIdFromJSP);
        //当前操作的表的类别
        modelList.addObject("type","family");

        ModelAndView go_back = new ModelAndView("redirect:/go_back");

        PopupPrompt pop = new PopupPrompt();
        if(membersEntityService.removeById(memberIdFromJSP)){
            //删除收入记录失败
            pop.new_style("删除家庭成员成功！");
            return modelList;
        }else {
            pop.new_style("删除家庭成员失败！");
            return go_back;
        }
    }

//    restFul风格 但是转发到familyUpdate.jsp页面会没有样式  ---------未解决
//    @RequestMapping(value = "/pre_updateMember/{membersId}",method = RequestMethod.GET)
//    public String pre_updateMember(@PathVariable(value = "membersId") Integer membersId, Model model){
//
//        //从table.jsp页面传过来的 要被修改的 家庭成员id
//        MembersEntity updateMember = membersEntityService.getById(membersId);
//        model.addAttribute("updateMember",updateMember);
//
//        return "familyUpdate";
//    }

    @PreAuthorize("hasAnyAuthority('homeowner')")
    @RequestMapping(value = "/pre_updateMember")
    public String pre_updateMember(HttpServletRequest httpServletRequest, Model model){

        UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        String memberId = httpServletRequest.getParameter("memberId");
        //从table.jsp页面传过来的 要被修改的 家庭成员id
        MembersEntity updateMember = membersEntityService.getById(memberId);
        model.addAttribute("updateMember",updateMember);

        return "familyUpdate";
    }

    /**
     * 家主 修改家庭成员的信息
     * @param httpServletRequest 获取家主填写的信息
     * @return
     */
    @PreAuthorize("hasAnyAuthority('homeowner')")
    @RequestMapping(value = "/updateMember")
    public ModelAndView updateMember(HttpServletRequest httpServletRequest){

        Integer membersIdFromJsp = Integer.valueOf(httpServletRequest.getParameter("membersId"));
        String membersNameFromJsp = httpServletRequest.getParameter("membersName");
        String membersRelationFromJsp = httpServletRequest.getParameter("membersRelation");
        MembersEntity member = new MembersEntity(membersIdFromJsp,membersNameFromJsp,membersRelationFromJsp);

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        modelList.addObject("membersId",membersIdFromJsp);
        //当前操作的表的类别
        modelList.addObject("type","family");

        ModelAndView go_back2 = new ModelAndView("redirect:/go_back2");

        PopupPrompt pop = new PopupPrompt();
        if(usersEntityService.getById(membersIdFromJsp)!=null&&usersEntityService.getById(membersIdFromJsp).getUsersName().equals(membersNameFromJsp)) {
            if(membersEntityService.saveOrUpdate(member)){
                pop.new_style("更新家庭成员信息成功！");
                return modelList;
            }else {
                pop.new_style("更新家庭成员信息失败！");
                return go_back2;
            }
        }else {
            pop.new_style("您输入的家庭成员信息不匹配！");
            return go_back2;
        }


    }
    /**
     * @return 账号或密码错误 打印提示信息
     */
//    @RequestMapping(value = "/member_error" ,produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    public String member_error(String type) {
//
//        switch (type){
//            case "add":
//                return "<script>alert('成员编号与昵称不匹配，请检查后重新添加！');history.go(-1);</script>";
//            case "delete":
//                return "<script>alert('删除成员失败，请重试！');history.go(-1);</script>";
//        }
//
//        return "<script>alert('操作失败，请重试！');history.go(-1);</script>";
//
//    }
}
