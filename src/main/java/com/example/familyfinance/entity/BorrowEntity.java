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

public class BorrowEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 债务编号
     */
    @TableId(value = "borrow_id", type = IdType.AUTO)
    private Integer borrowId;

    /**
     * 找谁借
     */
    private String borrowName;

    /**
     * 借入的成员编号
     */
    @TableField("borrow_memberId")
    private Integer borrowMemberid;

    /**
     * 借入的日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date borrowTime;

    /**
     * 借入金额
     */
    private Float borrowAmount;



    @TableField(exist = false)
    private String borrowMonth;

    @TableField(exist = false)
    private Float borrowMonthAmount;

    @TableField(exist = false)
    private Float memberYearBorrow;


    public BorrowEntity(String borrowName, Integer borrowMemberid, Date borrowTime, Float borrowAmount) {
        this.borrowName = borrowName;
        this.borrowMemberid = borrowMemberid;
        this.borrowTime = borrowTime;
        this.borrowAmount = borrowAmount;
    }
}
