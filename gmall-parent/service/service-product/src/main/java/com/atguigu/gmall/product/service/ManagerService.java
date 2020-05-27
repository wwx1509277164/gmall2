package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;

import java.util.List;

/**
 * @author Administrator
 * @create 2020-05-26 8:23
 */
public interface ManagerService {
    //三级分类的查询
    List<BaseCategory1> getCategory1();
    List<BaseCategory2> getCategory2(Long category1Id);
    List<BaseCategory3> getCategory3(Long category2Id);

}
