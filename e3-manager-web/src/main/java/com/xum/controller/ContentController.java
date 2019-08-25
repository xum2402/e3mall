package com.xum.controller;


import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.content.service.ContentService;
import com.xum.pojo.Content;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    private ContentService contentService;

    @RequestMapping("/query/list")
    public EasyUIDataGridResult getContentListByCatId(Long categoryId,Integer page,Integer rows){

        return contentService.getContentList(categoryId,page,rows);
    }

    /**
     * 添加内容
     * @param content
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public E3Result addContent(Content content){
        return contentService.addContent(content);
    }
}
