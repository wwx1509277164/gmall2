package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.service.ManagerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @create 2020-05-26 8:20
 */
@RestController
@RequestMapping("/admin/product")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    //获取一级分类
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> list = managerService.getCategory1();
        return Result.ok(list);
    }
    //获取二级分类
    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable("category1Id") Long category1Id){
        List<BaseCategory2> category2 = managerService.getCategory2(category1Id);
        return Result.ok(category2);
    }
    //获取三级分类
    @GetMapping("/getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable("category2Id") Long category2Id){
        List<BaseCategory3> category3 = managerService.getCategory3(category2Id);
        return Result.ok(category3);
    }
    //根据分类id获取平台属性
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable("category1Id") Long category1Id, @PathVariable("category2Id") Long category2Id, @PathVariable("category3Id") Long category3Id){

        List<BaseAttrInfo> list = managerService.attrInfoList(category1Id,category2Id,category3Id);
        return Result.ok(list);
    }
    //添加或修改平台属性和平台属性值
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        System.out.println(baseAttrInfo);
        managerService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }
    //根据平台属性id获取平台属性
    @GetMapping("getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId") Long attrId){
        BaseAttrInfo baseAttrInfo = managerService.getAttrValueList(attrId);
        return Result.ok(baseAttrInfo.getAttrValueList());
    }

    @GetMapping("/{page}/{limit}")
    public Result getSpuByPage(@PathVariable("page") Long page,
                               @PathVariable("limit") Long limit,
                               Long category3Id){
       IPage<SpuInfo> p  =managerService.getSpuByPage(page,limit,category3Id);
       return Result.ok(p);
    }

    //添加spu
    @GetMapping("/baseSaleAttrList")
    public Result baseSaleAttrList(){
        List<BaseSaleAttr> list = managerService.baseSaleAttrList();
        return Result.ok(list);
    }
    @GetMapping("/baseTrademark/getTrademarkList")
    public Result getTrademarkList(){
        List<BaseTrademark> list = managerService.getTrademarkList();
        return Result.ok(list);
    }
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        managerService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

    //sku的添加
    //获取照片
    @GetMapping("/spuImageList/{spuId}")
    public Result spuImageList(@PathVariable("spuId") Long spuId){
        List<SpuImage> list = managerService.spuImageList(spuId);
        return Result.ok(list);
    }
    //获取销售属性和属性值
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable("spuId") Long spuId){
        List<SpuSaleAttr> spuSaleAttrList = managerService.spuSaleAttrList(spuId);
        return Result.ok(spuSaleAttrList);
    }
}



