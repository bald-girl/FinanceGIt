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

public class IncometypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收入类型编号
     */
    @TableId(value = "incomeType_id", type = IdType.AUTO)
    private Integer incometypeId;

    /**
     * 收入类型名称
     */
    @TableField("incomeType_name")
    private String incometypeName;

    public IncometypeEntity(String incometypeName) {
        this.incometypeName = incometypeName;
    }
}
