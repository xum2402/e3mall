package com.xum.portal.controller;

import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.content.service.ContentService;
import com.xum.pojo.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    /**获取配置文件内容*/
    @Value("${CONTENT_CAROUSEL_ID}")
    private Long CONTENT_CAROUSEL_ID;

    @Resource
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        EasyUIDataGridResult contentList = contentService.getContentList(CONTENT_CAROUSEL_ID,1,8);

        List<Content> ad1list = contentList.getRows();

        model.addAttribute("ad1List",ad1list);
        return "index";
    }
}
