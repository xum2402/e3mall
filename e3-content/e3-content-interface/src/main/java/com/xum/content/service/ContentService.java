package com.xum.content.service;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.pojo.Content;

public interface ContentService {
    EasyUIDataGridResult getContentList(Long catId,Integer pageNum,Integer pageSize);

    E3Result addContent(Content content);
}
