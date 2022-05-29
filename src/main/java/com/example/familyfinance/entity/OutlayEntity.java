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

public class OutlayEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支出编号
     */
    @TableId(value = "outlay_id", type = IdType.AUTO)
    private Integer outlayId;

    /**
     * 支出金额
     */
    private Float outlayAmount;

    /**
     * 支出成员编号
     */
    @TableField("outlay_memberId")
    private Integer outlayMemberid;

    /**
     * 支出类型
     */
    private String outlayKind;

    /**
     * 支出时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outlayTime;

    /**
     * 某种类型支出总额
     */
    @TableField(exist = false)
    private Float outlayTypeTotal;


    @TableField(exist = false)
    private String outlayMonth;

    @TableField(exist = false)
    private Float outlayMonthAmount;

    @TableField(exist = false)
    private Float memberMonthOutlay;

    @TableField(exist = false)
    private Float memberYearOutlay;

    public OutlayEntity(String outlayKind,Float outlayAmount,   Date outlayTime,Integer outlayMemberid) {
        this.outlayAmount = outlayAmount;
        this.outlayMemberid = outlayMemberid;
        this.outlayKind = outlayKind;
        this.outlayTime = outlayTime;
    }


}
