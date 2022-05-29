package com.example.familyfinance.controller;

import com.example.familyfinance.entity.*;
import com.example.familyfinance.service.*;
import com.example.familyfinance.util.PopupPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Controller
public class IncomeEntityController {

    @Autowired
    private IncomeEntityService incomeEntityService;

    @Autowired
    private OutlayEntityService outlayEntityService;

    @Autowired
    private BorrowEntityService borrowEntityService;

    @Autowired
    private LendEntityService lendEntityService;

    @Autowired
    private MembersEntityService membersEntityService;

    /**
     * 添加收入记录
     * @param httpServletRequest  form.jsp传递的参数
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/createIncome")
//    @ResponseBody
//    若需要弹窗 需添加ResponseBody注解 并在@RequestMapping中添加 produces = "text/html;charset=UTF-8"
    public ModelAndView createIncome(HttpServletRequest httpServletRequest) throws Exception {
        //获取日期
        String date = httpServletRequest.getParameter("incomeDate");
        //日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date incomeDate = simpleDateFormat.parse(date);

        Float incomeAmount =  Float.valueOf(httpServletRequest.getParameter("incomeAmount"));
        String incomeType = httpServletRequest.getParameter("incomeType");

        //当前用户id
        Integer membersId = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        IncomeEntity incomeEntity = new IncomeEntity(incomeAmount,membersId,incomeType,incomeDate);

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",membersId);

        ModelAndView modelForm = new ModelAndView("redirect:/pre_type");

        PopupPrompt pop = new PopupPrompt();
        if(incomeEntityService.save(incomeEntity)){
            //添加收入记录成功 --> 跳转到 incomeEntityController : ‘/pre_list’
            pop.new_style("添加收入记录成功！");
            return modelList;
        }else {
            pop.new_style("添加收入记录失败！");
            return  modelForm;
        }

    }

    /**
     * 在访问收、支、借、贷表时 ：先做预处理-->在数据库中先查询出来，再跳转到该页面
     * @param modelMap 给前端传数据--->收、支、借、贷、家庭成员表
     * @param membersId 从 controller 的 ModelView 中参数中传过来    获取当前用户id  查询他的所有家庭成员、资金流记录等
     * @param httpSession 从 controller /login中传过来    获取当前用户id  查询他的所有家庭成员、资金流记录等
     * @return table.jsp页面
     */
    @Autowired
    private RedisTemplate redisTemplate;


    @PreAuthorize("hasAnyAuthority('user','homeowner')")
    @RequestMapping(value = "/pre_list")
    public String pre_incomeList(ModelMap modelMap, String type, HttpSession httpSession, HttpServletRequest httpServletRequest){

//        Object user = redisTemplate.opsForValue().get("1111");
//        System.out.println(user.toString());
        //用户实体
        UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userIdNow = principal.getUsersId();

//        //从controller层 /login 取到
//        Integer userIdFromLogin = (Integer) httpSession.getAttribute("userId");
//        //当前用户id
//        Integer userIdNow = membersId == null ? userIdFromLogin: membersId;

        Integer homeownerId;
        Map<String,Object> membersMap = new HashMap<>();
        //用于存放 查询出来的所有家庭成员
        List<MembersEntity> membersEntityList;
        //用于存放所有家庭成员的id
        List<Integer> membersIdList = new ArrayList<>() ;

        //用当前用户的ID来查询家庭成员表 ---> 查出是否有其他家庭成员
        if(membersEntityService.getById(userIdNow)!=null){
            //如果有 ---> 查出所属家庭的家主id
            homeownerId = membersEntityService.getById(userIdNow).getHomeownerId();
            httpSession.setAttribute("homeownerId",homeownerId);
            membersMap.put("homeowner_id",homeownerId);
            //查出家主为 homeownerId 的所有家庭成员
            membersEntityList = (List<MembersEntity>) membersEntityService.listByMap(membersMap);
            //将所有家庭成员 放入ModelMap
            modelMap.addAttribute("membersEntityList",membersEntityList);
            for(MembersEntity membersEntity:membersEntityList){
                //将查出的家庭成员的ID 存放到list中
                membersIdList.add(membersEntity.getMembersId());
            }
        }else {
            //如果没有其他家庭成员 就将当前用户的id放进去
            membersIdList.add(userIdNow);
        }

        //存储for循环中根据每位成员编号查询到的数据
        List<IncomeEntity> incomeEntityList ;
        //拼接 根据多名成员编号查询到的数据
        List<IncomeEntity> incomeList = new ArrayList<>();

        List<OutlayEntity> outlayEntityList;
        List<OutlayEntity> outlayList = new ArrayList<>();
        List<BorrowEntity> borrowEntityList;
        List<BorrowEntity> borrowList = new ArrayList<>();
        List<LendEntity> lendEntityList;
        List<LendEntity> lendList = new ArrayList<>();

        //用于查询
        HashMap<String,Object> incomeHashMap = new HashMap<>();
        HashMap<String,Object> outlayHashMap = new HashMap<>();
        HashMap<String,Object> borrowHashMap = new HashMap<>();
        HashMap<String,Object> lendHashMap = new HashMap<>();

        //查询每个家庭成员的 收、支、借、贷
        for(Object memberId:membersIdList){
            incomeHashMap.put("income_memberId",memberId);
            incomeEntityList = (List<IncomeEntity>) incomeEntityService.listByMap(incomeHashMap);
            //拼接上一步查询到的数据
            incomeList.addAll(incomeEntityList);

            outlayHashMap.put("outlay_memberId",memberId);
            outlayEntityList = (List<OutlayEntity>) outlayEntityService.listByMap(outlayHashMap);
            outlayList.addAll(outlayEntityList);

            //查询借入信息
            borrowHashMap.put("borrow_memberId",memberId);
            borrowEntityList = (List<BorrowEntity>) borrowEntityService.listByMap(borrowHashMap);
            borrowList.addAll(borrowEntityList);
            //查询借出信息
            lendHashMap.put("lend_memberId",memberId);
            lendEntityList = (List<LendEntity>) lendEntityService.listByMap(lendHashMap);
            lendList.addAll(lendEntityList);
        }


        modelMap.addAttribute("incomeEntityList",incomeList);
        modelMap.addAttribute("outlayEntityList",outlayList);
        modelMap.addAttribute("borrowEntityList",borrowList);
        modelMap.addAttribute("lendEntityList",lendList);

        httpSession.setAttribute("membersIdList",membersIdList);
        httpSession.setAttribute("incomeEntityList",incomeList);
        httpSession.setAttribute("outlayEntityList",outlayList);
        httpSession.setAttribute("borrowEntityList",borrowList);
        httpSession.setAttribute("lendEntityList",lendList);

        if(httpServletRequest.getParameter("type")!=null ){
            switch (httpServletRequest.getParameter("type")) {
                case "outlay":
                    return "outlayTable";
                case "borrow":
                    return "borrowTable";
                case "lend":
                    return "lendTable";
                case "family":
                    return "familyTable";
            }
        } else if(type != null ){
            switch (type){
                case "outlay":
                    return "outlayTable";
                case "borrow":
                    return "borrowTable";
                case "lend":
                    return "lendTable";
                case "family":
                    return "familyTable";
            }
        }
        return "incomeTable";
    }

    /**
     * 删除收入信息
     * @param httpServletRequest 获取要被删除的收入信息的编号
     * @return
     */
    @RequestMapping("/delIncome")
    public ModelAndView delIncome(HttpServletRequest httpServletRequest){

        //当前用户id
        Integer userIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("usersId"));
        //从table.jsp页面传过来的 要被删除的 支出id
        Integer incomeIdFromJSP = Integer.valueOf(httpServletRequest.getParameter("incomeId"));

        ModelAndView modelList = new ModelAndView("redirect:/pre_list");
        //拼接参数-->当前登录用户的ID
        modelList.addObject("membersId",userIdFromJSP);

        PopupPrompt pop = new PopupPrompt();

        if(incomeEntityService.removeById(incomeIdFromJSP)){
           //删除收入记录失败
            pop.new_style("删除收入记录失败!");
        }else {
            pop.new_style("删除收入记录成功!");
        }
        return modelList;
    }


    /**
     * @return 账号或密码错误 打印提示信息
     */
    @RequestMapping(value = "/income_error" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String login_error() {

        return "<script>alert('账号或密码错误！');history.go(-1);</script>";

    }
}
