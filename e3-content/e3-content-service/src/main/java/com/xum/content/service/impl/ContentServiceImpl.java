package com.xum.content.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.content.service.ContentService;
import com.xum.mapper.ContentMapper;
import com.xum.pojo.Content;
import com.xum.pojo.ContentExample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentMapper contentMapper;


    private String key = "E3MALL_CONTENTLIST_CATID_PAGE_PAGESIZE";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
        /**
         * @param catId 分类Id
         * @param pageNum 页数
         * @param pageSize 条数
         * @return
         */
        @Override
        public EasyUIDataGridResult getContentList(Long catId,Integer pageNum, Integer pageSize) {

            EasyUIDataGridResult result =null;
            //查询缓存

            try {
                //如果缓存有数据直接响应结果
                result = (EasyUIDataGridResult) redisTemplate.opsForValue().get(key+catId+pageNum+pageSize);
                if(result!=null){
                    return result;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //设置查询条件
            ContentExample contentExample = new ContentExample();
            contentExample.createCriteria().andCategoryIdEqualTo(catId);

            PageHelper.startPage(pageNum,pageSize);
            Page<Content> contents = (Page<Content>) contentMapper.selectByExampleWithBLOBs(contentExample);

             result = new EasyUIDataGridResult();
            result.setTotal(contents.getTotal());
            result.setRows(contents);

            try {
                //将查询结果添加到缓存
                redisTemplate.opsForValue().set(key+catId+pageNum+pageSize,result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

    @Override
    public E3Result addContent(Content content) {
            content.setCreated(new Date());
            content.setUpdated(new Date());

        int insert = contentMapper.insert(content);

        if (insert>0){
            Set<String> keys = redisTemplate.keys("E3MALL_CONTENT*");
            redisTemplate.delete(keys);
        }

        return E3Result.ok();
    }



}
