package cn.jucheng.controller;

import cn.jucheng.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo(@PathVariable("id") Integer id){
        return orderService.paymentInfo(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    @HystrixCommand
    public String paymentInfo_timeout(@PathVariable("id") Integer id){
        int age = 10/0;
        return orderService.paymentInfo_timeout(id);
    }

    public String paymentInfo_timeoutHandler(Integer id){
        return "我是80消费者";
    }

    public String payment_global_fallbackMethod(){
        return "Global ***";
    }
}
