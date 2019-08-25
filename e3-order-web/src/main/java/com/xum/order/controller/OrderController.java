package com.xum.order.controller;

import com.xum.cart.service.CartService;
import com.xum.common.util.E3Result;
import com.xum.order.pojo.OrderInfo;
import com.xum.order.service.OrderService;
import com.xum.pojo.Item;
import com.xum.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {

    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(@RequestAttribute("user") User user, Model model){//注解方式取request中的attribute属性值
        //根据用户id取收货地址列表,静态数据模拟

        //获取支付方式,静态数据


        //通过用户id获取购物车列表
        List<Item> cartList = cartService.getCartList(user.getId());
        model.addAttribute("cartList",cartList);
        return "order-cart";
    }

    /**
     * 创建订单
     * @param orderInfo
     * @param request
     * @return
     */
    @PostMapping("/order/create")
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request){

        //取用户信息,添加到orderInfo中
        User user = (User) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());

        E3Result order = orderService.createOrder(orderInfo);

        if (order.getStatus() == 200){
            //清空购物车
        cartService.clearCart(user.getId());
        }
        request.setAttribute("orderId",order.getData());
        request.setAttribute("payment",orderInfo.getPayment());

        return "success";
    }
}
