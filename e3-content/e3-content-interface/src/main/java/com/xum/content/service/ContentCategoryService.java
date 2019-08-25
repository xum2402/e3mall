package com.xum.content.service;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);

    E3Result addContentCategory(Long parentId,String name);
}
