package com.bjpn.service;

import com.bjpn.pojo.ProductInfo;
import com.bjpn.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo<ProductInfo> splitPage(int pageNum,int pageSize);

    PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize);

    int saveProduct(ProductInfo productInfo);

    ProductInfo selectById(Integer pid);

    int updateById(ProductInfo productInfo);

    int deleteBatch(String[] ps);

    int deleteByKey(int pid);
}
