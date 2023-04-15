package com.titos.shareplatform.service.impl;

import cn.hutool.core.convert.Convert;
import com.titos.info.global.CommonResult;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.shareplatform.dao.CalendarDao;
import com.titos.shareplatform.dao.CalendarTagDao;
import com.titos.shareplatform.dao.TagDao;
import com.titos.shareplatform.model.Calendar;
import com.titos.shareplatform.model.CalendarTag;
import com.titos.shareplatform.service.CalendarService;
import com.titos.shareplatform.vo.CalendarVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.tool.utils.BeanCopyUtils;
import com.titos.tool.token.CustomStatement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Resource
    private CalendarDao calendarDao;
    @Resource
    private TagDao tagDao;
    @Resource
    private CalendarTagDao calendarTagDao;
    @Resource
    private NormalServiceClient normalServiceClient;
    @Override
    public CommonResult<List<CalendarVO>> listCalendar(CustomStatement customStatement) {
        List<Calendar> calendarList = calendarDao.selectCalendarsByUserId(customStatement.getId());
        List<CalendarVO> calendarVOList = BeanCopyUtils.copyList(calendarList, CalendarVO.class);
        calendarVOList.forEach(item -> {
            Integer tagId = calendarTagDao.selectTagIdByCalendarId(item.getId());
            String tagName = tagDao.selectTagNameById(tagId);
            item.setTagName(tagName);
        });
        return CommonResult.success(calendarVOList);
    }

    @Override
    public void addCalendar(CustomStatement customStatement, CalendarVO calendarVO) {
        // 敏感词过滤
        calendarVO.setCalendarDesc(Convert.convert(String.class, normalServiceClient.replaceContent(calendarVO.getCalendarDesc()).getData()));
        calendarVO.setCalendarTitle(Convert.convert(String.class, normalServiceClient.replaceContent(calendarVO.getCalendarTitle()).getData()));
        Calendar calendar = BeanCopyUtils.copyObject(calendarVO, Calendar.class);
        calendar.setUserId(customStatement.getId());
        calendarDao.insertCalendar(calendar);
        // 获取tagId
        Integer tagId = tagDao.selectIdByTagName(calendarVO.getTagName());
        // 插入该calender所对应的事件id
        CalendarTag calendarTag = new CalendarTag();
        calendarTag.setCalendarId(calendar.getId());
        calendarTag.setTagId(tagId);
        calendarTagDao.insertCalendarTag(calendarTag);
    }

    @Override
    public void updateCalendar(CustomStatement customStatement, CalendarVO calendarVO) {
        // 敏感词过滤
        calendarVO.setCalendarDesc(Convert.convert(String.class, normalServiceClient.replaceContent(calendarVO.getCalendarDesc()).getData()));
        calendarVO.setCalendarTitle(Convert.convert(String.class, normalServiceClient.replaceContent(calendarVO.getCalendarTitle()).getData()));
        Calendar calendar = BeanCopyUtils.copyObject(calendarVO, Calendar.class);
        calendar.setUserId(customStatement.getId());
        calendarDao.updateCalendarById(calendar);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteCalendar(CustomStatement customStatement, IdListVO idListVO) {
        List<Integer> curCalendarTagIdList = calendarTagDao.selectIdsByCalendarId(idListVO.getIdList());
        calendarTagDao.deleteCalendarTagByIdBatch(curCalendarTagIdList);
        calendarDao.deleteCalendarByIdBatch(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }
}
