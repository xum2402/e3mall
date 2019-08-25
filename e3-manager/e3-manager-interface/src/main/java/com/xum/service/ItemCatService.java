package com.xum.service;

import com.xum.comon.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);
}
