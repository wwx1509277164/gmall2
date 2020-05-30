package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2020-05-29 21:03
 */
//对外暴露的接口，供其他微服务进行调用
@RestController
@RequestMapping("api/product")
public class ProductApiController {
    //获取基本的sku信息和照片
    @Autowired
    private ManagerService managerService;
    @GetMapping("/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId){
        return managerService.getSkuInfo(skuId);
    }

    //获取三级分类
    @GetMapping("/inner/getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryView(@PathVariable("category3Id")Long category3Id){
        return managerService.getCategoryViewByCategory3Id(category3Id);
    }
    //获取价格
    @GetMapping("/inner/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable Long skuId){
        return managerService.getSkuPrice(skuId);
    }

    //获取销售属性集合
    @GetMapping("/inner/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}")
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable("skuId") Long skuId, @PathVariable("spuId") Long spuId){
        return managerService.getSpuSaleAttrListCheckBySku(skuId, spuId);
    }

    //获取
    @GetMapping("/inner/getSkuValueIdsMap/{spuId}")
    public Map getSkuValueIdsMap(@PathVariable("spuId") Long spuId){
        return managerService.getSkuValueIdsMap(spuId);
    }

}
