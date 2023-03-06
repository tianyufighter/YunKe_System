package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.PageResult;
import com.titos.info.global.constant.CommonConst;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.shareplatform.entity.News;
import com.titos.info.shareplatform.entity.NewsTag;
import com.titos.info.shareplatform.entity.NewsTagMap;
import com.titos.info.shareplatform.vo.*;
import com.titos.shareplatform.dao.NewsDao;
import com.titos.shareplatform.dao.NewsTagDao;
import com.titos.shareplatform.dao.NewsTagMapDao;
import com.titos.shareplatform.service.NewsService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName NewsServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 21:50
 **/
@Service
@Slf4j
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {

    @Resource
    private NewsDao newsDao;

    @Resource
    private NewsTagDao newsTagDao;

    @Resource
    private NewsTagMapDao newsTagMapDao;

    @Override
    public CommonResult<PageResult<NewsVO>> listNews(ConditionVO conditionVO, Long pageNum, Long pageSize) {
        List<Integer> newsIdList = null;
        if (Objects.nonNull(conditionVO.getTagId())) {
            newsIdList = newsTagMapDao.selectList(new LambdaQueryWrapper<NewsTagMap>()
                    .select(NewsTagMap::getNewsId)
                    .eq(NewsTagMap::getTagId, conditionVO.getTagId())).stream().map(NewsTagMap::getNewsId).collect(Collectors.toList());
            if (newsIdList.size() == 0) {
                return CommonResult.success(new PageResult<>(Collections.emptyList(), 0));
            }
        }
        Page<News> page = new Page<>(pageNum, pageSize);
        Page<News> newsPage = newsDao.selectPage(page, new LambdaQueryWrapper<News>()
                .select(News::getId, News::getNewsCover, News::getNewsTitle, News::getNewsContent)
                .in(newsIdList != null, News::getId, newsIdList)
                .gt(conditionVO.getStartTime() != null, News::getCreateTime, conditionVO.getStartTime())
                .le(conditionVO.getEndTime() != null, News::getCreateTime, conditionVO.getEndTime())
                .orderByDesc(News::getCreateTime));

        List<NewsVO> newsVOList = newsPage.getRecords().stream().map(
                item -> NewsVO.builder()
                        .id(item.getId())
                        .newsCover(item.getNewsCover())
                        .newsTitle(StringUtils.substring(item.getNewsTitle(), 0, 10))
                        .newsContent(StringUtils.substring(item.getNewsContent(), 0, 50))
                        .build()).collect(Collectors.toList());
        newsVOList.forEach(item -> {
            item.setTagName(newsTagDao.listTagNameByNewsId(item.getId()));
        });
        return CommonResult.success(new PageResult<>(newsVOList, (int) newsPage.getPages()));
    }

    @Override
    public CommonResult<NewsDetailVO> listNewsById(Integer newsId) {
        NewsDetailVO newsDetailVO = newsDao.listNewsById(newsId);
        newsDetailVO.setTagName(newsTagDao.listTagNameByNewsId(newsId));
        return CommonResult.success(newsDetailVO);
    }

    @Override
    public CommonResult<List<NewsVO>> searchNews(String keywords) {
        List<News> newsList = newsDao.selectList(new LambdaQueryWrapper<News>()
                .like(News::getNewsTitle, keywords)
                .or()
                .like(News::getNewsContent, keywords));
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

                newsContent = (preText + postText).replaceAll(keywords,
                        CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
            }
            String newsTitle = item.getNewsTitle().replaceAll(keywords,
                    CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
            return NewsVO.builder()
                    .id(item.getId())
                    .newsCover(item.getNewsCover())
                    .newsTitle(newsTitle)
                    .newsContent(newsContent)
                    .build();
        }).collect(Collectors.toList());
        return CommonResult.success(newsVOList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteNews(CustomStatement customStatement, IdListVO idListVO) {
        log.info(customStatement.getRole().toString());
        if (!customStatement.getRole().equals(1)) {
            return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
        }
        newsDao.deleteBatchIds(idListVO.getIdList());
        newsTagMapDao.delete(new LambdaQueryWrapper<NewsTagMap>()
                .in(NewsTagMap::getNewsId, idListVO.getIdList()));
        return CommonResult.success(Boolean.TRUE);
    }

    @Override
    public CommonResult<List<NewsTagVO>> listNewsTag() {
        List<NewsTag> newsTagList = newsTagDao.selectList(new LambdaQueryWrapper<NewsTag>()
                .select(NewsTag::getId, NewsTag::getTagName));
        return CommonResult.success(BeanCopyUtils.copyList(newsTagList, NewsTagVO.class));
    }
}
