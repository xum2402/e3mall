package com.xum.service.impl;

import com.xum.comon.pojo.EasyUITreeNode;
import com.xum.mapper.ItemCatMapper;
import com.xum.pojo.ItemCat;
import com.xum.pojo.ItemCatExample;
import com.xum.service.ItemCatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86182
 * 商品分类管理
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Resource
    private ItemCatMapper itemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        ItemCatExample catExample = new ItemCatExample();
        //添加查询条件
        catExample.createCriteria().andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = itemCatMapper.selectByExample(catExample);
        //转换为节点数列表
        List<EasyUITreeNode> treeNodes = new ArrayList<>();
        for (ItemCat itemCat : itemCats) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setText(itemCat.getName());
            treeNode.setState(itemCat.getIsParent()?"closed":"open");
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }
}
