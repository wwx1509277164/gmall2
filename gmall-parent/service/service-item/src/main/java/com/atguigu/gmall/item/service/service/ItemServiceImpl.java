package com.atguigu.gmall.item.service.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2020-05-29 21:01
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductFeignClient productFeignClient;
    @Override
    public Map<String, Object> getBySkuId(Long skuId) {
        Map result = new HashMap<>();

        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        result.put("skuInfo", skuInfo);
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        result.put("price", skuPrice);
        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
        result.put("categoryView", categoryView);
        List<SpuSaleAttr> spuSaleAttrListCheckBySku = productFeignClient.getSpuSaleAttrListCheckBySku(skuId, skuInfo.getSpuId());
        result.put("spuSaleAttrList", spuSaleAttrListCheckBySku);
        Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());
        result.put("valuesSkuJson", JSON.toJSONString(skuValueIdsMap));
        return result;
    }
}
