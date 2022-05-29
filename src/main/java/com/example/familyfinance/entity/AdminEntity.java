package com.example.familyfinance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

public class AdminEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private String adminName;

    private String adminPwd;


}
