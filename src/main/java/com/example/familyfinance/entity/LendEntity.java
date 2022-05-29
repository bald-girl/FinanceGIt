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

public class LendEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 债权编号
     */
    @TableId(value = "lend_id", type = IdType.AUTO)
    private Integer lendId;

    /**
     * 借给谁
     */
    private String lendName;

    /**
     * 借出的成员编号
     */
    @TableField("lend_memberId")
    private Integer lendMemberid;

    /**
     * 借出的日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lendTime;

    /**
     * 借出金额
     */
    private float lendAmount;

    @TableField(exist = false)
    private String lendMonth;

    @TableField(exist = false)
    private Float lendMonthAmount;

    @TableField(exist = false)
    private Float memberYearLend;

    public LendEntity( String lendName, Integer lendMemberid, Date lendTime, float lendAmount ) {
        this.lendName = lendName;
        this.lendMemberid = lendMemberid;
        this.lendTime = lendTime;
        this.lendAmount = lendAmount;
    }


}
