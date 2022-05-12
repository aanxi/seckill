package com.practice.seckill.admin.controller;


import com.practice.seckill.admin.dto.GoodsDTO;
import com.practice.seckill.admin.dto.QueryConditionDTO;
import com.practice.seckill.admin.service.GoodsService;
import com.practice.seckill.admin.vo.GoodsRowVO;
import com.practice.seckill.admin.vo.PageVO;
import com.practice.seckill.common.constant.ReturnResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
@Api(value = "商品", tags = "商品")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/goods")
    @ApiOperation(value = "新建商品", notes = "新建商品")
    public ReturnResponse<GoodsDTO> createGoods(@RequestBody GoodsDTO dto) {
        GoodsDTO result = goodsService.createGoods(dto);
        return ReturnResponse.ok(result);
    }
    @DeleteMapping("/goods/{goodsId}")
    @ApiOperation(value = "移除商品", notes = "移除商品")
    public ReturnResponse<String> deleteGoods(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId) {
        return goodsService.deleteGoodsById(goodsId)? ReturnResponse.ok("删除成功"):ReturnResponse.makeFailResponse("删除失败");
    }

    @PutMapping("/goods/{goodsId}/info")
    @ApiOperation(value = "修改信息", notes = "商品信息")
    public ReturnResponse<GoodsDTO> info(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId, @RequestBody GoodsDTO dto) {
        GoodsDTO result = goodsService.modifyGoods(goodsId, dto);
        return ReturnResponse.ok(result);
    }

    @GetMapping("/goods")
    @ApiOperation(value = "商品列表", notes = "商品列表")
    public ReturnResponse<PageVO<GoodsRowVO>> goodsList(@RequestBody QueryConditionDTO dto) {
        return ReturnResponse.ok(goodsService.pageGoods(dto));
    }


}
