package com.bjpn.service.impl;

import com.bjpn.mapper.ProductTypeMapper;
import com.bjpn.pojo.ProductType;
import com.bjpn.pojo.ProductTypeExample;
import com.bjpn.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> selectTypeAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
