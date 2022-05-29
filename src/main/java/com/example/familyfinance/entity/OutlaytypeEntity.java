package com.example.familyfinance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


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

public class OutlaytypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支出类型编号
     */
    @TableId(value = "outlayType_id", type = IdType.AUTO)
    private Integer outlaytypeId;

    /**
     * 支出类型名称
     */
    @TableField("outlayType_name")
    private String outlaytypeName;

    public OutlaytypeEntity(String outlaytypeName) {
        this.outlaytypeName = outlaytypeName;
    }
}
