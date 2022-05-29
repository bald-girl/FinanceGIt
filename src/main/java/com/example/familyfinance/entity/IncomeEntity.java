package com.example.familyfinance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收入编号
     */
    @TableId(value = "income_id", type = IdType.AUTO)
    private Integer incomeId;

    /**
     * 收入金额
     */
    private Float incomeAmount;

    /**
     * 收入成员编号
     */
    @TableField("income_memberId")
    private Integer incomeMemberid;

    /**
     * 收入类型
     */
    private String incomeKind;

    /**
     * 收入时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date incomeTime;

    @TableField(exist = false)
    private Float incomeTypeTotal;

    @TableField(exist = false)
    private String incomeMonth;

    @TableField(exist = false)
    private Float incomeMonthAmount;

    @TableField(exist = false)
    private Float memberMonthIncome;

    @TableField(exist = false)
    private Float memberYearIncome;

    public IncomeEntity(Float incomeAmount, Integer incomeMemberid, String incomeKind, Date incomeTime) {
        this.incomeAmount = incomeAmount;
        this.incomeMemberid = incomeMemberid;
        this.incomeKind = incomeKind;
        this.incomeTime = incomeTime;
    }
}
