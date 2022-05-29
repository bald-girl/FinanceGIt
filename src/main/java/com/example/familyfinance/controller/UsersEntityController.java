package com.example.familyfinance.controller;


import com.example.familyfinance.entity.UsersEntity;
import com.example.familyfinance.service.IncometypeEntityService;
import com.example.familyfinance.service.MembersEntityService;
import com.example.familyfinance.service.UsersEntityService;
import com.example.familyfinance.service.impl.UserDetailsServiceImpl;
import com.example.familyfinance.util.PopupPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
public class UsersEntityController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    IncometypeEntityService incometypeEntityService ;

    @Autowired
    MembersEntityService membersEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/jump", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request) {
        String url = request.getParameter("url");
        return url;
    }

    @RequestMapping("/pre_login" )
    public String pre_login(){
        return "user/login";
    }

//    @RequestMapping("/login" )
//    public String login(){
//        return "user/login";
//    }

    /**
     * @return 账号或密码错误 打印提示信息
     */
    @RequestMapping(value = "/go_back" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String go_back() {
        return "<script>history.go(-1);</script>";
    }

    @RequestMapping(value = "/go_back2" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String go_back2() {
        return "<script>history.go(-2);</script>";
    }


    @RequestMapping("/index")
    public String toLogin(){
        return "index";
    }

    @RequestMapping(value = "/pre_register")
    public String pre_addEmp() {
        return "user/register";
    }

//    @Autowired
//    protected AuthenticationManager authenticationManager;

    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest httpServletRequest) {

        UsersEntity user = new UsersEntity(httpServletRequest.getParameter("username"),httpServletRequest.getParameter("password"));
        PopupPrompt style = new PopupPrompt();
        //调用register方法
        if(userDetailsService.register(user)){
            //給予用戶user角色
            user.setUsersRole("user");

            style.new_style("注册成功，将为您跳转到登录页！");

//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(user.getUsersRole()));
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsersId(),user.getUsersPwd(),authorities);
//            try{
//                token.setDetails(new WebAuthenticationDetails(httpServletRequest));
//                Authentication authenticationUser = authenticationManager.authenticate(token);
//                SecurityContext context = SecurityContextHolder.getContext();
//                System.out.println("1:"+context);
//                context.setAuthentication(authenticationUser);
//                System.out.println("2:"+context);
//                httpServletRequest.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//                System.out.println("11111111111111111111111111111");
//                return new ModelAndView("redirect:/403");
//            }catch (AuthenticationException e){
//                System.out.println("认证失败"+e.getMessage());
//                return new ModelAndView("redirect:/pre_login");
//            }

//            UsersEntity userDetails = (UsersEntity) userDetailsService.loadUserByUsername(user.getUsersId().toString());
//            System.out.println("当前用户权限："+userDetails.getAuthorities());
//            //会定位到默认登录页  预估是因为pre_list没获取到对应的Authorities
//            return new ModelAndView("redirect:/pre_list");

            return new ModelAndView("redirect:/pre_login");

        }else {
            style.new_style("用户名已存在，请重新注册！");
            return new ModelAndView("redirect:/pre_register");
        }
    }



    @RequestMapping(value = "/pre_update")
    public String pre_update() {

        return "user/update";
    }

    @RequestMapping(value = "/pre_adminLogin")
    public String pre_adminLogin(){
        return "user/adminLogin";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
    /**
     * 用户修改密码
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/userUpdate")
    public ModelAndView userUpdate(HttpServletRequest httpServletRequest, HttpSession httpSession) {

        //获取用户Id、输入的旧密码、新密码
        Integer usersId =  Integer.valueOf(httpServletRequest.getParameter("usersId")) ;
        String originalPwd = httpServletRequest.getParameter("originalPwd");
        String newPwd = httpServletRequest.getParameter("newUsersPwd");

        //操作成功-->跳转到主界面
        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //操作失败-->重新进入修改信息界面
        ModelAndView modelUserUpdate = new ModelAndView("redirect:/pre_update");

        PopupPrompt pop = new PopupPrompt();

        if (userDetailsService.getById(usersId) != null) {
            UsersEntity usersEntity = userDetailsService.getById(usersId);
            //判断数据库中该账号对应的密码与用户输入是否相同
            //BCryptPasswordEncoder是强哈希方法 每次加密的结果都不一样 且不可逆
            if (passwordEncoder.matches(originalPwd,usersEntity.getPassword())) {
                usersEntity.setUsersPwd(passwordEncoder.encode(newPwd));
                userDetailsService.saveOrUpdate(usersEntity);
                httpSession.setAttribute("userId",usersId);
                return modelList;
            }else {
                pop.new_style("原密码输入错误！");
                return modelUserUpdate;
            }
        }else {
            return modelUserUpdate;
        }
    }


}
