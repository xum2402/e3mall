package com.xum.order.service.impl;

import com.xum.common.util.E3Result;
import com.xum.mapper.OrderItemMapper;
import com.xum.mapper.OrderMapper;
import com.xum.mapper.OrderShippingMapper;
import com.xum.order.pojo.OrderInfo;
import com.xum.order.service.OrderService;
import com.xum.pojo.OrderItem;
import com.xum.pojo.OrderShipping;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderShippingMapper orderShippingMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {

//        生成订单号,使用redis的incr
        String orderId = getIdByRedisIncr("ORDER_ID_GEN","10544");
        //补全orderInfo的属性
        orderInfo.setOrderId(orderId);
        //新生成的订单一定为未付款:1,  已付款：2， 已发货：3， 交易成功：4
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());

        //插入数据库
        orderMapper.insert(orderInfo);

        //向订单明细表中插入数据
        List<OrderItem> orderItems = orderInfo.getOrderItems();
        for (OrderItem orderItem : orderItems) {
//            生成订单明细id
            String orderDetailId = getIdByRedisIncr("ORDER_DETAIL_ID_GEN","1");
            //补全订单明细
            orderItem.setId(orderDetailId);
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }

        //向物流表插入数据

        OrderShipping orderShipping = orderInfo.getOrderShipping();

        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);


        return E3Result.ok(orderId);
    }










    /**
     * 使用redis自增生成id
     * @param key
     * @return
     */

    private String getIdByRedisIncr(String key,String initNum){

        redisTemplate.setValueSerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().set(key,initNum);
        }

         return String.valueOf(redisTemplate.opsForValue().increment(key));
    }

}
