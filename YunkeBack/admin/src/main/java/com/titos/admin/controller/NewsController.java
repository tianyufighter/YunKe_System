package com.titos.admin.controller;

import com.github.pagehelper.PageInfo;
import com.titos.admin.service.NewsService;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/news")
public class NewsController {
    @Resource
    private NewsService newsService;
    @PostMapping("/newsList")
    public CommonResult<PageInfo<NewsVO>> queryNewsByCondition(@RequestBody NewsConditionVO newsConditionVO) {
        return CommonResult.success(newsService.queryNewsByPage(newsConditionVO));
    }
    @PostMapping("/updateNews")
    public CommonResult updateNews(@RequestBody NewsVO newsVO) {
        return newsService.updateNews(newsVO);
    }
    @PostMapping("/deleteNews")
    public CommonResult deleteNews(@RequestBody IdListVO idListVO) {
        return newsService.deleteNews(idListVO);
    }
    @InjectToken
    @PostMapping("/addNews")
    public CommonResult addNews(CustomStatement customStatement, @RequestBody NewsVO newsVO) {
        newsVO.setUserId(customStatement.getId());
        return newsService.addNews(newsVO);
    }
}
