package com.xum.comon.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品信息
 */
@Data
public class SearchItem implements Serializable {

    private String id;

    private String title;
    //卖点
    private String sell_point;

    private Long price;

    private String image;
    //商品分类
    private String category_name;

    public String[] getImages(){
        if(image!=null && image != ""){
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
