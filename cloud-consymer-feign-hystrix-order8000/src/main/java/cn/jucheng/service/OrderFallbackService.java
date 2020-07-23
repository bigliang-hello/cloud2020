package cn.jucheng.service;

import org.springframework.stereotype.Component;

@Component
public class OrderFallbackService implements OrderService {

    @Override
    public String paymentInfo(Integer id) {
        return "paymentInfo fallback";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "paymentInfo_timeout fallback";
    }
}
