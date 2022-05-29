package com.example.familyfinance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.familyfinance.entity.IncomeEntity;
import com.example.familyfinance.mapper.IncomeEntityMapper;
import com.example.familyfinance.service.IncomeEntityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Service
public class IncomeEntityServiceImpl extends ServiceImpl<IncomeEntityMapper, IncomeEntity> implements IncomeEntityService {

//    @Autowired(required=false)
//    private IncomeEntityMapper incomeEntityMapper;
//
//    @Autowired
//    public DifferentTypeIncome incomeType() {
//        return incomeEntityMapper.incomeTypeQuery();
//    }
}
