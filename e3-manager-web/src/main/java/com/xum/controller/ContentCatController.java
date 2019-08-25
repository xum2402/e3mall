package com.xum.controller;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUITreeNode;
import com.xum.content.service.ContentCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class ContentCatController {

    @Resource
    private ContentCategoryService categoryService;

    @RequestMapping("/list")
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){

        return categoryService.getContentCatList(parentId);
    }

    /**
     * 添加分类节点
     */
    @RequestMapping("/create")
    public E3Result createContentCategory(Long parentId,String name){

        return categoryService.addContentCategory(parentId,name);
    }
}
