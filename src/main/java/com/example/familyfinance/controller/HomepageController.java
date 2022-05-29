package com.example.familyfinance.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.familyfinance.entity.BorrowEntity;
import com.example.familyfinance.entity.IncomeEntity;
import com.example.familyfinance.entity.LendEntity;
import com.example.familyfinance.entity.OutlayEntity;
import com.example.familyfinance.service.BorrowEntityService;
import com.example.familyfinance.service.IncomeEntityService;
import com.example.familyfinance.service.LendEntityService;
import com.example.familyfinance.service.OutlayEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首先获取从 /pre_list 中获取家庭成员所有 收支借贷 的详细信息
 */
@Controller
public class HomepageController {

    @Autowired
    private IncomeEntityService incomeEntityService;

    @Autowired
    private OutlayEntityService outlayEntityService;

    @Autowired
    private BorrowEntityService borrowEntityService;

    @Autowired
    private LendEntityService lendEntityService;

    @ResponseBody
    @RequestMapping("/pre_homepage")
    public Object pre_homepage(HttpSession httpSession){

        //从 /pre_list 获取获取本家庭所有家庭成员编号
        ArrayList<Integer> membersIdList = (ArrayList) httpSession.getAttribute("membersIdList");

        //将所有json数据存到json数组中
        JSONArray jsonArray = new JSONArray();

        //获取当前年月
        Date d = new Date();
        SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        String dateNowStr = sdfYearMonth.format(d);
        String yearNowStr = sdfYear.format(d);

        //查询本家庭所有家庭成员 本月收入类型 对应的收入金额 0
        QueryWrapper<IncomeEntity> incomeTypeQuery = new QueryWrapper<>();
        incomeTypeQuery.select("income_kind, sum(income_amount) as income_type_total");
        //ge表示>=
        incomeTypeQuery.eq("DATE_FORMAT(income_time, '%Y-%m')",dateNowStr);
        incomeTypeQuery.in("income_memberId",membersIdList);
        incomeTypeQuery.groupBy("income_kind");
        List<IncomeEntity> incomeTypeList = incomeEntityService.list(incomeTypeQuery);
        jsonArray.add(incomeTypeList);

        //查询本家庭本月所有家庭成员 本月支出类型 对应的支出金额 1
        QueryWrapper<OutlayEntity> outlayTypeQuery = new QueryWrapper<>();
        outlayTypeQuery.select("outlay_kind, sum(outlay_amount) as outlay_type_total");
        outlayTypeQuery.eq("DATE_FORMAT(outlay_time, '%Y-%m')",dateNowStr);
        outlayTypeQuery.in("outlay_memberId",membersIdList);
        outlayTypeQuery.groupBy("outlay_kind");
        List<OutlayEntity> outlayTypeList = outlayEntityService.list(outlayTypeQuery);
        jsonArray.add(outlayTypeList);

        //查询本家庭一年内每个月收入 2
        QueryWrapper<IncomeEntity> incomeMonthQuery = new QueryWrapper<>();
        incomeMonthQuery.select("DATE_FORMAT(income_time, '%Y-%m') as income_month","SUM(income_amount) as income_month_amount");
        incomeMonthQuery.eq("DATE_FORMAT(income_time, '%Y')",yearNowStr);
        incomeMonthQuery.in("income_memberId",membersIdList);
        incomeMonthQuery.groupBy("income_month");
        incomeMonthQuery.orderByDesc("income_month");
        List<IncomeEntity> incomeMonth = incomeEntityService.list(incomeMonthQuery);
        jsonArray.add(incomeMonth);

        //查询本家庭一年内每个月支出 3
        QueryWrapper<OutlayEntity> outlayMonthQuery = new QueryWrapper<>();
        outlayMonthQuery.select("DATE_FORMAT(outlay_time, '%Y-%m') as outlay_month","SUM(outlay_amount) as outlay_month_amount");
        outlayMonthQuery.eq("DATE_FORMAT(outlay_time, '%Y')",yearNowStr);
        outlayMonthQuery.in("outlay_memberId",membersIdList);
        outlayMonthQuery.groupBy("outlay_month");
        outlayMonthQuery.orderByDesc("outlay_month");
        List<OutlayEntity> outlayMonth = outlayEntityService.list(outlayMonthQuery);
        jsonArray.add(outlayMonth);

        //查询本家庭一年内每个月负债 4
        QueryWrapper<BorrowEntity> borrowMonthQuery = new QueryWrapper<>();
        borrowMonthQuery.select("DATE_FORMAT(borrow_time, '%Y-%m') as borrow_month","SUM(borrow_amount) as borrow_month_amount");
        borrowMonthQuery.eq("DATE_FORMAT(borrow_time, '%Y')","2022");
        borrowMonthQuery.in("borrow_memberId",membersIdList);
        borrowMonthQuery.groupBy("borrow_month");
        borrowMonthQuery.orderByDesc("borrow_month");
        List<BorrowEntity> borrowMonth = borrowEntityService.list(borrowMonthQuery);
        jsonArray.add(borrowMonth);

        //查询本家庭一年内每个月债权 5
        QueryWrapper<LendEntity> lendMonthQuery = new QueryWrapper<>();
        lendMonthQuery.select("DATE_FORMAT(lend_time, '%Y-%m') as lend_month","SUM(lend_amount) as lend_month_amount");
        lendMonthQuery.eq("DATE_FORMAT(lend_time, '%Y')","2022");
        lendMonthQuery.in("lend_memberId",membersIdList);
        lendMonthQuery.groupBy("lend_month");
        lendMonthQuery.orderByDesc("lend_month");
        List<LendEntity> lendMonth = lendEntityService.list(lendMonthQuery);
        jsonArray.add(lendMonth);

        //查询本家庭本月不同成员收入 6
        QueryWrapper<IncomeEntity> incomeMonthMemberQuery = new QueryWrapper<>();
        incomeMonthMemberQuery.select("income_memberId","SUM(income_amount) as member_month_income");
        incomeMonthMemberQuery.eq("DATE_FORMAT(income_time, '%Y-%m')",dateNowStr);
        incomeMonthMemberQuery.in("income_memberId",membersIdList);
        incomeMonthMemberQuery.groupBy("income_memberId");
        List<IncomeEntity> memberIncome = incomeEntityService.list(incomeMonthMemberQuery);
        jsonArray.add(memberIncome);

        //查询本家庭本月不同成员支出 7
        QueryWrapper<OutlayEntity> outlayMonthMemberQuery = new QueryWrapper<>();
        outlayMonthMemberQuery.select("outlay_memberId","SUM(outlay_amount) as member_month_outlay");
        outlayMonthMemberQuery.eq("DATE_FORMAT(outlay_time, '%Y-%m')",dateNowStr);
        outlayMonthMemberQuery.in("outlay_memberId",membersIdList);
        outlayMonthMemberQuery.groupBy("outlay_memberId");
        List<OutlayEntity> memberOutlay = outlayEntityService.list(outlayMonthMemberQuery);
        jsonArray.add(memberOutlay);

        //查询本家庭本年度总收入 8
        QueryWrapper<IncomeEntity> incomeAllYearQuery = new QueryWrapper<>();
        incomeAllYearQuery.select("SUM(income_amount) as member_year_income");
        incomeAllYearQuery.eq("DATE_FORMAT(income_time, '%Y')",yearNowStr);
        incomeAllYearQuery.in("income_memberId",membersIdList);
        List<IncomeEntity> incomeAllYear = incomeEntityService.list(incomeAllYearQuery);
        jsonArray.add(incomeAllYear);

        //查询本家庭本年度总支出 9
        QueryWrapper<OutlayEntity> outlayAllYearQuery = new QueryWrapper<>();
        outlayAllYearQuery.select("SUM(outlay_amount) as member_year_outlay");
        outlayAllYearQuery.eq("DATE_FORMAT(outlay_time, '%Y')",yearNowStr);
        outlayAllYearQuery.in("outlay_memberId",membersIdList);
        List<OutlayEntity> outlayAllYear = outlayEntityService.list(outlayAllYearQuery);
        jsonArray.add(outlayAllYear);

        //查询本家庭本年度总借入 10
        QueryWrapper<BorrowEntity> borrowAllYearQuery = new QueryWrapper<>();
        borrowAllYearQuery.select("SUM(borrow_amount) as member_year_borrow");
        borrowAllYearQuery.eq("DATE_FORMAT(borrow_time, '%Y')",yearNowStr);
        borrowAllYearQuery.in("borrow_memberId",membersIdList);
        List<BorrowEntity> borrowAllYear = borrowEntityService.list(borrowAllYearQuery);
        jsonArray.add(borrowAllYear);

        //查询本家庭本年度总借出 11
        QueryWrapper<LendEntity> lendAllYearQuery = new QueryWrapper<>();
        lendAllYearQuery.select("SUM(lend_amount) as member_year_lend");
        lendAllYearQuery.eq("DATE_FORMAT(lend_time, '%Y')",yearNowStr);
        lendAllYearQuery.in("lend_memberId",membersIdList);
        List<LendEntity> lendAllYear = lendEntityService.list(lendAllYearQuery);
        jsonArray.add(lendAllYear);

        return jsonArray;
    }
}
