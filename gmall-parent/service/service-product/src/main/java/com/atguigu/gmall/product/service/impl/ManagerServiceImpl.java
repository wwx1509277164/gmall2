package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2020-05-26 8:24
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    BaseCategory1Mapper baseCategory1Mapper;
    @Autowired
    BaseCategory2Mapper baseCategory2Mapper;
    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    SpuInfoMapper spuInfoMapper;
    //查询三级分类的
    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }
    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        return baseCategory2Mapper.selectList(new QueryWrapper<BaseCategory2>().eq("category1_id",category1Id));
    }
    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        return baseCategory3Mapper.selectList(new QueryWrapper<BaseCategory3>().eq("category2_id",category2Id));

    }
    //查询属性值
    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        QueryWrapper<BaseAttrInfo> wrapper = new QueryWrapper<>();
        if (category3Id==null){
            if (category2Id==null){
                wrapper.eq("category_id",category1Id);
            }else {
                wrapper.eq("category_id",category2Id);
            }
        }else {
            wrapper.eq("category_id",category3Id);
        }
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.selectList(wrapper);
        return baseAttrInfoList;
    }
    //添加或者修改属性和属性值
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        if (StringUtils.isEmpty(baseAttrInfo.getId())) {
            baseAttrInfoMapper.insert(baseAttrInfo);
        }else {
            baseAttrInfoMapper.updateById(baseAttrInfo);
        }
        baseAttrValueMapper.delete(new QueryWrapper<BaseAttrValue>().eq("attr_id",baseAttrInfo.getId()));
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(baseAttrInfo.getId());
            baseAttrValueMapper.insert(baseAttrValue);
        }
    }
    //根据id获取属性值
    @Override
    public BaseAttrInfo getAttrValueList(Long attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.getAttrValueList(attrId);
        System.out.println(baseAttrInfo);
        return baseAttrInfo;
    }

    @Override
    public IPage<SpuInfo> getSpuByPage(Long page, Long limit,Long id) {
        Page<SpuInfo> p = new Page<>(page,limit);
        IPage<SpuInfo> iPage = spuInfoMapper.selectPage(p, new QueryWrapper<SpuInfo>().eq("category3_id",id));
        return iPage;
    }
    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    BaseTrademarkMapper baseTrademarkMapper;
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
      return baseSaleAttrMapper.selectList(null);
    }

    @Override
    public List<BaseTrademark> getTrademarkList() {
        return baseTrademarkMapper.selectList(null);
    }

    @Autowired
    SpuImageMapper spuImageMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存spu
        spuInfoMapper.insert(spuInfo);
        //保存照片
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuInfo.getId());
            spuImageMapper.insert(spuImage);
        }
        //保存属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(spuSaleAttr);
            //保存属性值
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
        }
    }

    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        return spuImageMapper.selectList(new QueryWrapper<SpuImage>().eq("spu_id",spuId));
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        return spuSaleAttrMapper.spuSaleAttrList(spuId);
    }
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //保存skuInfo
        skuInfoMapper.insert(skuInfo);
        //保存sku照片
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuInfo.getId());
            skuImageMapper.insert(skuImage);
        }
        //保存平台属性关联表
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuInfo.getId());
            skuAttrValueMapper.insert(skuAttrValue);
        }
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        skuSaleAttrValueList.forEach(skuSaleAttrValue -> {
            //外键 skuID
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            //外键 spuId
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
            //保存
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        });
    }

    @Override
    public IPage<SkuInfo> getSkuByPage(Long page, Long limit) {
        Page<SkuInfo> skuInfoPage = new Page<>(page, limit);
        IPage<SkuInfo> p = skuInfoMapper.selectPage(skuInfoPage, null);
        return p;
    }

    @Override
    public void onSale(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        skuInfo.setIsSale(1);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void cancelSale(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        skuInfo.setIsSale(0);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        List<SkuImage> skuImageList = skuImageMapper.selectList(new QueryWrapper<SkuImage>().eq("sku_id", skuId));
        skuInfo.setSkuImageList(skuImageList);
        return skuInfo;
    }

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Override
    public BaseCategoryView getCategoryViewByCategory3Id(Long category3Id) {
        return baseCategoryViewMapper.selectOne(new QueryWrapper<BaseCategoryView>().eq("category3_id",category3Id));
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return skuInfoMapper.selectById(skuId).getPrice();
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.getSpuSaleAttrListCheckBySku(skuId,spuId);
        return spuSaleAttrList;
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        Map result = new HashMap();
        List<Map> skuValueIdsMap = skuSaleAttrValueMapper.getSkuValueIdsMap(spuId);
        for (Map map : skuValueIdsMap) {
            result.put(map.get("value_ids"), map.get("sku_id"));
        }
        return result;
    }
}
