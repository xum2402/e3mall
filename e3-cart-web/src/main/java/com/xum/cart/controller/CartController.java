package com.xum.cart.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xum.cart.service.CartService;
import com.xum.common.util.CookieUtils;
import com.xum.common.util.E3Result;
import com.xum.pojo.Item;
import com.xum.pojo.User;
import com.xum.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Resource
    private ItemService itemService;

    @Resource
    private CartService cartService;


    private final ObjectMapper jacksonMapper = new ObjectMapper();


    @RequestMapping("cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

        //判断用户是否登录
        User user = (User) request.getAttribute("user");
        // 登录状态将购物车写入redis
        if (user!=null){

            cartService.addCart(user.getId(),itemId,num);

            return "cartSuccess";
        }

        List<Item> carList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (Item item : carList) {
            if (item.getId().equals(itemId)){
                flag = true;
                item.setNum(item.getNum()+num);
                break;
            }
        }

        //如果不存在
      if (!flag) {
//        根据商品查询商品信息
          Item item = itemService.getItemById(itemId);
          //设置商品数量
          item.setNum(num);
          //取一张图片
          String image = item.getImage();
          if (!StringUtils.isBlank(image)){
              item.setImage(image.split(",")[0]);
          }
          carList.add(item);
      }

      //写入cookie
        CookieUtils.setCookie(request,response,"cart",jacksonMapper.writeValueAsString(carList),5*24*60*60,true);

        return "cartSuccess";
    }


    /**
     * 显示购物车列表
     * @param request
     * @return
     */

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request,HttpServletResponse response){
        //从cookie中取购物车列表
        List<Item> cartList = getCartListFromCookie(request);
        //判断用户是否登录状态
        User user = (User) request.getAttribute("user");
        if(user!=null){
            //合并购物车
            cartService.mergeCart(user.getId(),cartList);
            //清空cookie购物车
            CookieUtils.deleteCookie(request,response,"cart");
            cartList = cartService.getCartList(user.getId());
        }

        request.setAttribute("cartList",cartList);

        return "cart";
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {

//        判断用户是否为登录状态
        User user = (User) request.getAttribute("user");
        if (user != null){
            return cartService.updateCartNum(user.getId(),itemId,num);
        }

        //从cookie中取购物车列表
        List<Item> cartList = getCartListFromCookie(request);
        for (Item item : cartList) {
            if (item.getId().equals(itemId)){
                item.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",jacksonMapper.writeValueAsString(cartList),5*24*60*60,true);

        return E3Result.ok();
    }

    /**
     * 删除购物车商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {

        //        判断用户是否为登录状态
        User user = (User) request.getAttribute("user");
        if (user != null){
            cartService.deleteCartItem(user.getId(),itemId);
            return "redirect:/cart/cart.html";
        }

        List<Item> cartList = getCartListFromCookie(request);
        for (Item item : cartList) {
            if (item.getId().equals(itemId)){
                cartList.remove(item);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",jacksonMapper.writeValueAsString(cartList),5*24*60*60,true);

        return "redirect:/cart/cart.html";
    }


    /**
     * 从cookie中取购物车列表
     * @param request
     * @return
     */
    private List<Item> getCartListFromCookie(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (!StringUtils.isBlank(json)){
            try {
                //返回带泛型的集合class
                JavaType javaType = jacksonMapper.getTypeFactory().constructParametricType(ArrayList.class, Item.class);

                List<Item> list = jacksonMapper.readValue(json, javaType);

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>();
    }
}
