package com.xum.order.pojo;

import com.xum.pojo.Order;
import com.xum.pojo.OrderItem;
import com.xum.pojo.OrderShipping;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInfo extends Order implements Serializable {

    private List<OrderItem> orderItems;

    private OrderShipping orderShipping;

}
