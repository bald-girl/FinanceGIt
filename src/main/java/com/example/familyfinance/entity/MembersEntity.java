package com.example.familyfinance.entity;

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

public class MembersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成员编号
     */
    @TableId(value = "members_id")
    private Integer membersId;

    /**
     * 所属家庭的家主编号
     */
    private Integer homeownerId;

    /**
     * 成员名称
     */
    private String membersName;

    /**
     * 成员与家主的关系
     */
    private String membersRelation;

    public MembersEntity(Integer membersId, String membersName, String membersRelation) {
        this.membersId = membersId;
        this.membersName = membersName;
        this.membersRelation = membersRelation;
    }
}
