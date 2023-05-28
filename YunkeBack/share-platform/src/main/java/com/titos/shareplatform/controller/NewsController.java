package com.titos.shareplatform.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.shareplatform.service.NewsService;
import com.titos.shareplatform.vo.*;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sharePlatform/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    /**
     * 根据条件分页查询新闻信息
     * @param newsConditionVO 封装的条件
     * @return 返回符合条件的新闻信息
     */
    @PostMapping("/getNewsByCondition")
    public CommonResult<PageInfo<NewsVO>> queryNewsByCondition(@RequestBody NewsConditionVO newsConditionVO) {
        return newsService.queryNewsByNewsConditionVO(newsConditionVO);
    }
    /**
     * 按条件查询新闻
     *
     * @param conditionVO 查询条件
     * @param pageNum     当前页
     * @param pageSize    每页的数量
     * @return 新闻列表
     */
    @GetMapping(value = "/list")
    public CommonResult<PageInfo<NewsVO>> listNews(
            ConditionVO conditionVO,
            @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        return newsService.listNews(conditionVO, pageNum, pageSize);
    }

    /**
     * 查询新闻详情
     *
     * @param newsId 新闻主键ID
     * @return 新闻详情
     */
    @GetMapping(value = "/list/{id}")
    public CommonResult<NewsDetailVO> listNewsById(@PathVariable("id") Integer newsId) {
        return newsService.listNewsById(newsId);
    }

    /**
     * 搜索新闻
     *
     * @param keywords 关键词
     * @return 新闻列表
     */
    @GetMapping(value = "/search")
    public CommonResult<List<NewsVO>> searchNews(String keywords) {
        return newsService.searchNews(keywords);
    }

    /**
     * 删除新闻
     *
     * @param customStatement 用户信息
     * @param idListVO        新闻ID列表
     * @return 是否删除成功
     */
    @InjectToken
    @DeleteMapping(value = "/delete")
    public CommonResult<Boolean> deleteNews(
            CustomStatement customStatement,
            @RequestBody IdListVO idListVO) {
        return newsService.deleteNews(customStatement, idListVO);
    }

    /**
     * 查询所有的新闻的标签
     *
     * @return 标签列表
     */
    @GetMapping(value = "/tags")
    public CommonResult<List<NewsTagVO>> listNewsTag() {
        return newsService.listNewsTag();
    }

    /**
     * 批量删除新闻信息
     * @param idListVO
     * @return
     */
    @PostMapping("/deleteNewsBatch")
    public CommonResult deleteNewsBatch(@RequestBody IdListVO idListVO) {
        return newsService.deleteNewsBatch(idListVO);
    }

    /**
     * 根据新闻的id更新对应的新闻信息
     * @param newsVO
     * @return
     */
    @PostMapping("/updateNews")
    public CommonResult updateNews(@RequestBody NewsVO newsVO) {
        return newsService.updateNews(newsVO);
    }

    /**
     * 添加新闻信息
     * @param newsVO
     * @return
     */
    @PostMapping("/addNews")
    public CommonResult addNews(@RequestBody NewsVO newsVO) {
        return newsService.addNews(newsVO);
    }
}