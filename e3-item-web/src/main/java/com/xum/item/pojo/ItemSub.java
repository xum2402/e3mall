package com.xum.item.pojo;

import com.xum.pojo.Item;

public class ItemSub extends Item {


    public ItemSub(Item item){

        this.setId(item.getId());
        this.setTitle(item.getTitle());
        this.setSellPoint(item.getSellPoint());
        this.setPrice(item.getPrice());
        this.setNum(item.getNum());
        this.setBarcode(item.getBarcode());
        this.setImage(item.getImage());
        this.setCid(item.getCid());
        this.setStatus(item.getStatus());
        this.setCreated(item.getCreated());
        this.setUpdated(item.getUpdated());

    }



    public String[] getImages(){
        String image = this.getImage();

        if (image!=null &&image!=""){
            return image.split(",");
        }
        return null;
    }
}
