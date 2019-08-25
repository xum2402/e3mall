package com.xum.content.service.impl;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUITreeNode;
import com.xum.content.service.ContentCategoryService;
import com.xum.mapper.ContentCategoryMapper;
import com.xum.pojo.ContentCategory;
import com.xum.pojo.ContentCategoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Resource
    private ContentCategoryMapper categoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {

        ContentCategoryExample example = new ContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);

        List<ContentCategory> categoryList = categoryMapper.selectByExample(example);
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (ContentCategory contentCategory : categoryList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");

            nodeList.add(node);
        }


        return nodeList;
    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        //插入数据到数据库
        ContentCategory category = new ContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        //1 正常,2删除
        category.setStatus(1);
        //新添加的节点一定为叶子节点
        category.setIsParent(false);
        //默认排序
        category.setSortOrder(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        categoryMapper.insert(category);
        //判断父节点的isparent属性,如果不是true改为true

        ContentCategory parent = categoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parent);
        }

        //返回结果,包含新添加的分类
        return E3Result.ok(category);
    }
}
