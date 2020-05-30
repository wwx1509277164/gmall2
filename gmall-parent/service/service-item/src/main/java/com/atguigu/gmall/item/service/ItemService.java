package com.atguigu.gmall.item.service;

import java.util.Map;

/**
 * @author Administrator
 * @create 2020-05-29 21:00
 */
public interface ItemService {
    Map<String, Object> getBySkuId(Long skuId);
}
