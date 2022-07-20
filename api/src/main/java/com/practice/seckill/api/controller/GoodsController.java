package com.practice.seckill.api.controller;

import com.practice.seckill.admin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: seckill
 * @description: api商品控制器
 * @author: 张佳
 * @create: 2022-06-29 14:13
 **/
@RestController
@RequestMapping("/api")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

}

