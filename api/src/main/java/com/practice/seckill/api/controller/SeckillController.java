package com.practice.seckill.api.controller;

import com.practice.seckill.admin.constant.ReturnResponse;
import com.practice.seckill.api.bo.SeckillGoodsBO;
import com.practice.seckill.api.dto.SeckillOrderDTO;
import com.practice.seckill.api.dto.SeckillParameterDTO;
import com.practice.seckill.api.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
@Api(value = "秒杀", tags = "秒杀")
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    //权限验证未完成，只有管理员可以操作/做成定时任务，最好做成定时任务，比如在秒杀半小时前上架商品，秒杀在整点开始，定时任务在半小时执行
    @PutMapping("/upload")
    @ApiOperation(value = "上架秒杀活动及商品", notes = "上架秒杀活动及商品")
    public ReturnResponse uploadSeckillGoods(HttpSession session) throws Exception {
//        if(session.getAttribute("loginUser") == null) throw Exception
        seckillService.uploadLatest3DaysSeckillGoods();
        return ReturnResponse.ok();
    }

    @GetMapping("/seckillGoods")
    @ApiOperation(value = "秒杀活动商品列表", notes = "秒杀活动商品列表")
    public ReturnResponse getSeckillGoodsList() throws Exception {
        seckillService.uploadLatest3DaysSeckillGoods();
        return ReturnResponse.ok();
    }

    /**
     * 秒杀下单
     *
     * @param parameterDTO
     * @return
     */
    @PostMapping("/item/{seckillId}")
    @ApiOperation(value = "秒杀下单", notes = "秒杀下单")
    public ReturnResponse seckillItem(@PathVariable Long seckillId, @RequestBody SeckillParameterDTO parameterDTO) throws Exception {
        // 秒杀成功，返回订单
        SeckillOrderDTO orderDTO = seckillService.seckillItem(parameterDTO);
        return ReturnResponse.ok(orderDTO);
    }

}

