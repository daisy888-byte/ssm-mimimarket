package com.bjpn.service.impl;

import com.bjpn.mapper.ProductInfoMapper;
import com.bjpn.pojo.ProductInfo;
import com.bjpn.pojo.ProductInfoExample;
import com.bjpn.service.ProductService;
import com.bjpn.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Override
    public PageInfo<ProductInfo> splitPage(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectByVo(vo);
        return new PageInfo<>(list);
    }

    @Override
    public int saveProduct(ProductInfo productInfo) {

        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo selectById(Integer pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateById(ProductInfo productInfo) {

        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteBatch(String[] ps) {
        return productInfoMapper.deleteByBatchKeys(ps);
    }

    @Override
    public int deleteByKey(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }


}
