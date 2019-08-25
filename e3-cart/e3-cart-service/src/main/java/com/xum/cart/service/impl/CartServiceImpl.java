package com.xum.cart.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xum.cart.service.CartService;
import com.xum.common.util.E3Result;
import com.xum.mapper.ItemMapper;
import com.xum.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Item> redisTemplate;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public E3Result addCart(Long userId, Long itemId,Integer num) {

        Boolean hasKey = redisTemplate.hasKey("CART:" + userId + ":Item:" + itemId);
        if (hasKey){
            Item item = redisTemplate.opsForValue().get("CART:" + userId + ":Item:" + itemId);
            item.setNum(item.getNum() + num);

            //写回redis
            redisTemplate.opsForValue().set("CART:" + userId + ":Item:" + itemId,item);
            return E3Result.ok();
        }
        Item item = itemMapper.selectByPrimaryKey(itemId);
        //设置购物车中商品数量
        item.setNum(num);
        //取一张图片设置进去
        String images = item.getImage();
        if (!StringUtils.isBlank(images)){
            String image = images.split(",")[0];
        }
        item.setImage(images);
//        写进redis
        redisTemplate.opsForValue().set("CART:" + userId + ":Item:" + itemId,item);

        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(Long userId, List<Item> itemList) {
        //遍历商品列表
        for (Item item : itemList) {
            //将商品添加到redis购物车中
            addCart(userId,item.getId(),item.getNum());
        }


        return E3Result.ok();
    }

    /**
     * 查询购物车
     * @param userId
     * @return
     */
    @Override
    public List<Item> getCartList(Long userId) {
        Set<String> keys = redisTemplate.keys("CART:" + userId + "*");

        List<Item> cartList = redisTemplate.opsForValue().multiGet(keys);

        return cartList;
    }

    @Override
    public E3Result updateCartNum(Long userId, Long itemId, Integer num) {

//        取出商品，更新数量，放回
        Item item = redisTemplate.opsForValue().get("CART:" + userId + ":Item:" + itemId);

        item.setNum(num);

        redisTemplate.opsForValue().set("CART:" + userId + ":Item:" + itemId,item);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(Long userId, Long itemId) {

        redisTemplate.delete("CART:" + userId + ":Item:" + itemId);

        return E3Result.ok();
    }

    @Override
    public E3Result clearCart(Long userId) {

        Set<String> keys = redisTemplate.keys("CART:" + userId + "*");

        redisTemplate.delete(keys);
        return E3Result.ok();
    }
}
