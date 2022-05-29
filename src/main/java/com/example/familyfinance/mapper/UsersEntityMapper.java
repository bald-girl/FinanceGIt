package com.example.familyfinance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.familyfinance.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Mapper
@Repository

public interface UsersEntityMapper extends BaseMapper<UsersEntity> {

}
