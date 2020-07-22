package cn.jucheng.controller;

import cn.jucheng.entities.CommonResult;
import cn.jucheng.entities.Payment;
import cn.jucheng.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果：" + result);

        if (result > 0){
            return new CommonResult(200, "插入数据库成功" + serverPort, payment);
        }else{
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询成功1");
        if (payment != null){
            return new CommonResult(200, "查询成功" + serverPort, payment);
        }else{
            return new CommonResult(444, "查询失败", null);
        }
    }
}
