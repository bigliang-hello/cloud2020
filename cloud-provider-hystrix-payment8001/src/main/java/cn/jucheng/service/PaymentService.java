package cn.jucheng.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    //ok
    public String paymentInfo(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo,id:" + id + " 哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_timeout,id:" + id + " 呵呵";
    }

    public String paymentInfo_timeoutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_timeout,id:" + id + " 呜呜";
    }

    //服务熔断

    @HystrixCommand(fallbackMethod = "paymentBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范文
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    })
    public String paymentBreaker(Integer id){
        if (id < 0){
            throw new RuntimeException("id 不能为负数");
        }
        String serialNum = IdUtil.simpleUUID();
        return "调用成功，流水号：" + serialNum;
    }

    public String paymentBreakerFallback(Integer id){
        return "id 不能负数****";
    }
}
