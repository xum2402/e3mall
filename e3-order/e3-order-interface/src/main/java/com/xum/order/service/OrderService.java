package com.xum.order.service;

import com.xum.common.util.E3Result;
import com.xum.order.pojo.OrderInfo;

public interface OrderService {
    /**
     * 创建一个订单
     * @param orderInfo
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);
}
