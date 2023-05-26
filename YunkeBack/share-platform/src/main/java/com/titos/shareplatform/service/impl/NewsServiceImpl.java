package com.titos.shareplatform.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.constant.CommonConst;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.user.model.User;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.shareplatform.convert.NewsConvert;
import com.titos.shareplatform.dao.NewsDao;
import com.titos.shareplatform.dao.NewsTagDao;
import com.titos.shareplatform.dao.NewsTagMapDao;
import com.titos.info.news.model.News;
import com.titos.shareplatform.model.NewsTag;
import com.titos.shareplatform.model.NewsTagMap;
import com.titos.shareplatform.query.ConditionQuery;
import com.titos.shareplatform.service.NewsService;
import com.titos.shareplatform.vo.*;
import com.titos.tool.utils.BeanCopyUtils;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsDao newsDao;
    @Resource
    private NewsTagDao newsTagDao;
    @Resource
    private NewsTagMapDao newsTagMapDao;
    @Resource
    private UserServiceClient userServiceClient;
    @Override
    public CommonResult<PageInfo<NewsVO>> listNews(ConditionVO conditionVO, Integer pageNum, Integer pageSize) {
        List<Integer> newsIdList = null;
        if (Objects.nonNull(conditionVO.getTagId())) {
            newsIdList = newsTagMapDao.selectNewsIdByTagId(conditionVO.getTagId());
            if (newsIdList.size() == 0) {
                return CommonResult.success(new PageInfo<>(Collections.emptyList()));
            }
        }
        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
        PageHelper.startPage(pageNum, pageSize);
        List<News> newsList = newsDao.selectNewsByCondition(new ConditionQuery(newsIdList, conditionVO.getStartTime(), conditionVO.getEndTime()));
        PageInfo pageInfo = new PageInfo(newsList);
        List<NewsVO> newsVOList = BeanCopyUtils.copyList(newsList, NewsVO.class);
        for (NewsVO newsVO : newsVOList) {
            newsVO.setNewsTitle(StringUtils.substring(newsVO.getNewsTitle(), 0, 10));
            newsVO.setNewsContent(StringUtils.substring(newsVO.getNewsContent(), 0, 50));
            // 根据newsId获取tagId
            Integer tagId = newsTagMapDao.selectTagIdByNewsId(newsVO.getId());
            String tagName = newsTagDao.selectTagNameById(tagId);
            newsVO.setTagName(tagName);
        }
        pageInfo.setList(newsVOList);
        return CommonResult.success(pageInfo);
    }

    @Override
    public CommonResult<PageInfo<NewsVO>> queryNewsByNewsConditionVO(NewsConditionVO newsConditionVO) {
        PageHelper.startPage(newsConditionVO.getPageNum(), newsConditionVO.getPageSize());
        List<News> newsList = newsDao.selectNewsByNewsConditionVO(newsConditionVO);
        PageInfo<News> pageInfo = new PageInfo<>(newsList);
        List<NewsVO> newsVOList = new ArrayList<>();
        newsList.forEach(news -> {
            // 根据用户id查询用户信息
            User user = Convert.convert(User.class, userServiceClient.queryUserById(news.getUserId()).getData());
            NewsVO newsVO = NewsVO.builder()
                    .id(news.getId())
                    .newsTitle(news.getNewsTitle())
                    .newsContent(news.getNewsContent())
                    .newsCover(news.getNewsCover())
                    .createTime(news.getCreateTime())
                    .username(user.getUsername())
                    .build();
            //根据newsId获取tagId
            Integer tagId = newsTagMapDao.selectTagIdByNewsId(news.getId());
            String tagName = newsTagDao.selectTagNameById(tagId);
            newsVO.setTagName(tagName);
            newsVOList.add(newsVO);
        });
        PageInfo pageInfo1 = new PageInfo(newsVOList);
        pageInfo1.setTotal(pageInfo.getTotal());
        return CommonResult.success(pageInfo1);
    }

    @Override
    public CommonResult<NewsDetailVO> listNewsById(Integer newsId) {
        NewsDetailVO newsDetailVO = newsDao.listNewsById(newsId);
        Integer tagId = newsTagMapDao.selectTagIdByNewsId(newsId);
        String tagName = newsTagDao.selectTagNameById(tagId);
        newsDetailVO.setTagName(tagName);
        return CommonResult.success(newsDetailVO);
    }

    @Override
    public CommonResult<List<NewsVO>> searchNews(String keywords) {
        List<News> newsList = newsDao.selectNewsVague(keywords);
        List<NewsVO> newsVOList = newsList.stream().map(item -> {
           String newsContent = item.getNewsContent();
           int index = item.getNewsContent().indexOf(keywords);
           if (index != -1) {
               int preIndex = index > 25 ? index - 25 : 0;
               String preText = StringUtils.substring(item.getNewsContent(), preIndex, index);
               int lastIndex = index + keywords.length();
               int postLength = item.getNewsContent().length() - lastIndex;
               int postIndex = postLength > 50 ? lastIndex + 50 : lastIndex + postLength;
               String postText = StringUtils.substring(item.getNewsContent(), index, postIndex);
               newsContent = (preText + postText).replaceAll(keywords, CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
           }
           String newsTitle = item.getNewsTitle().replaceAll(keywords, CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
           return NewsVO.builder()
                   .id(item.getId())
                   .newsCover(item.getNewsCover())
                   .newsTitle(newsTitle)
                   .newsContent(newsContent)
                   .build();
        }).collect(Collectors.toList());
        return CommonResult.success(newsVOList);
    }

    @Override
    public CommonResult<Boolean> deleteNews(CustomStatement customStatement, IdListVO idListVO) {
        log.info(customStatement.getRole().toString());
        if (customStatement.getRole().equals(1)) {
            return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
        }
        newsDao.deleteNewsByIdBatch(idListVO.getIdList());
        newsTagMapDao.deleteNewsTagMapById(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }

    @Override
    public CommonResult<List<NewsTagVO>> listNewsTag() {
        List<NewsTag> newsTagList = newsTagDao.selectAllNewsTag();
        return CommonResult.success(BeanCopyUtils.copyList(newsTagList, NewsTagVO.class));
    }

    @Override
    public CommonResult deleteNewsBatch(IdListVO idListVO) {
        newsDao.deleteNewsByIdBatch(idListVO.getIdList());
        newsTagMapDao.deleteNewsTagMapById(idListVO.getIdList());
        return CommonResult.success();
    }

    @Override
    public CommonResult updateNews(NewsVO newsVO) {
        // 获取该新闻的标签名
        String tagName = newsVO.getTagName();
        // 根据标签名，查询数据库中是否含有
        NewsTag newsTag = newsTagDao.selectTagByTagName(tagName);
        Integer newsTagId = null;
        if (newsTag == null) { // 表示该标签名是新添加的标签名
            NewsTag newsTag1 = new NewsTag();
            newsTag1.setTagName(tagName);
            Integer cnt = newsTagDao.insertTag(newsTag1);
            newsTagId = newsTag1.getId();
        } else {
            newsTagId = newsTag.getId();
        }
        // 删除原标签与新闻的关系
        newsTagMapDao.deleteNewsTagMapById(new ArrayList<>(Arrays.asList(newsVO.getId())));
        // 添加新的标签与该新闻的关系
        NewsTagMap newsTagMap = new NewsTagMap();
        newsTagMap.setTagId(newsTagId);
        newsTagMap.setNewsId(newsVO.getId());
        newsTagMapDao.insertNewsTagMap(newsTagMap);
        News news = NewsConvert.toNewsByNewsVO(newsVO);
        Integer res = newsDao.updateNewsById(news);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    @Override
    public CommonResult addNews(NewsVO newsVO) {
        News news = NewsConvert.toNewsByNewsVO(newsVO);
        Integer res = newsDao.addNews(news);
        // 删除原标签与新闻的关系
        newsTagMapDao.deleteNewsTagMapById(new ArrayList<>(Arrays.asList(news.getId())));
        // 根据标签名，查询数据库中是否含有
        NewsTag newsTag = newsTagDao.selectTagByTagName(newsVO.getTagName());
        Integer newsTagId = null;
        if (newsTag == null) { // 表示该标签名是新添加的标签名
            NewsTag newsTag1 = new NewsTag();
            newsTag1.setTagName(newsVO.getTagName());
            Integer cnt = newsTagDao.insertTag(newsTag1);
            newsTagId = newsTag1.getId();
        } else {
            newsTagId = newsTag.getId();
        }
        // 添加新的标签与该新闻的关系
        NewsTagMap newsTagMap = new NewsTagMap();
        newsTagMap.setTagId(newsTagId);
        newsTagMap.setNewsId(news.getId());
        newsTagMapDao.insertNewsTagMap(newsTagMap);
        return CommonResult.success();
    }
}
