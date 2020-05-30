package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2020-05-26 8:23
 */
public interface ManagerService {
    //三级分类的查询
    List<BaseCategory1> getCategory1();
    List<BaseCategory2> getCategory2(Long category1Id);
    List<BaseCategory3> getCategory3(Long category2Id);

    List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrValueList(Long attrId);

    IPage<SpuInfo> getSpuByPage(Long page, Long limit,Long id);

    List<BaseSaleAttr> baseSaleAttrList();

    List<BaseTrademark> getTrademarkList();

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuImage> spuImageList(Long spuId);

    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    IPage<SkuInfo> getSkuByPage(Long page,Long limit);

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    SkuInfo getSkuInfo(Long skuId);

    BaseCategoryView getCategoryViewByCategory3Id(Long category3Id);

    BigDecimal getSkuPrice(Long skuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId);

    Map getSkuValueIdsMap(Long spuId);
}
