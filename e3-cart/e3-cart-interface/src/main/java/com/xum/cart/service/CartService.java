package com.xum.cart.service;

import com.xum.common.util.E3Result;
import com.xum.pojo.Item;

import java.util.List;

/**
 * 购物车
 * @author 86182
 */
public interface CartService {
    /**
     * 添加商品
     * @param userId
     * @param itemId
     * @return
     */
    E3Result addCart(Long userId,Long itemId,Integer num);

    /**
     * 合并redis和cookie的购物车
     * @param userId
     * @param itemList
     * @return
     */
    E3Result mergeCart(Long userId, List<Item> itemList);

    /**
     * 获取购物车
     * @param userId
     * @return
     */
    List<Item> getCartList(Long userId);

    /**
     * 修改购物车商品数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result updateCartNum(Long userId,Long itemId,Integer num);

    /**
     * 删除购物车商品
     * @param userId
     * @param itemId
     * @return
     */
    E3Result deleteCartItem(Long userId,Long itemId);

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    E3Result clearCart(Long userId);
}
