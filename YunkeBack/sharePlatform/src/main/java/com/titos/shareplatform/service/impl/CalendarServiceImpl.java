package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.shareplatform.entity.Calendar;
import com.titos.info.shareplatform.entity.CalendarTag;
import com.titos.info.shareplatform.entity.Tag;
import com.titos.info.shareplatform.entity.Task;
import com.titos.info.shareplatform.vo.CalendarVO;
import com.titos.info.shareplatform.vo.FilterTimeVO;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.shareplatform.dao.CalendarDao;
import com.titos.shareplatform.dao.CalendarTagDao;
import com.titos.shareplatform.dao.TagDao;
import com.titos.shareplatform.service.CalendarService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import com.titos.tool.sensitive.SensitiveFilter;
import com.titos.tool.token.CustomStatement;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName CalendarServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 22:49
 **/
@Service
public class CalendarServiceImpl extends ServiceImpl<CalendarDao, Calendar> implements CalendarService {

    @Resource
    private CalendarDao calendarDao;

    @Resource
    private TagDao tagDao;

    @Lazy
    @Resource
    private CalendarService calendarService;

    @Resource
    private CalendarTagDao calendarTagDao;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @Override
    public CommonResult<List<CalendarVO>> listCalendar(CustomStatement customStatement) {
        List<Calendar> calendarList = calendarDao.selectList(new LambdaQueryWrapper<Calendar>()
                .select(Calendar::getId, Calendar::getCalendarTitle, Calendar::getCalendarDesc,
                        Calendar::getStartTime, Calendar::getEndTime)
                .eq(Calendar::getUserId, customStatement.getId()));
        List<CalendarVO> calendarVOList = BeanCopyUtils.copyList(calendarList, CalendarVO.class);
        calendarVOList.forEach(item -> {
            item.setTagName(tagDao.listTagNameByCalendarId(item.getId()));
        });
        return CommonResult.success(calendarVOList);
    }

    @Override
    public void addOrUpdateCalendar(CustomStatement customStatement, CalendarVO calendarVO) {
        // 敏感词过滤
        calendarVO.setCalendarDesc(sensitiveFilter.filter(calendarVO.getCalendarDesc()));
        calendarVO.setCalendarTitle(sensitiveFilter.filter(calendarVO.getCalendarTitle()));
        Calendar calendar = BeanCopyUtils.copyObject(calendarVO, Calendar.class);
        calendar.setUserId(customStatement.getId());
        calendarService.saveOrUpdate(calendar);
        saveCalendarTag(calendarVO, calendar.getId());
    }

    private void saveCalendarTag(CalendarVO calendarVO, Integer calendarId) {
        if (Objects.nonNull(calendarVO.getId())) {
            calendarTagDao.delete(new LambdaQueryWrapper<CalendarTag>()
                    .eq(CalendarTag::getCalendarId, calendarVO.getId()));
        }
        Integer tagId = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, calendarVO.getTagName())).getId();
        calendarTagDao.insert(CalendarTag.builder()
                .calendarId(calendarId)
                .tagId(tagId)
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteCalendar(CustomStatement customStatement, IdListVO idListVO) {
        List<Integer> curCalendarIdList = calendarDao.selectList(new LambdaQueryWrapper<Calendar>()
                .select(Calendar::getId)
                .eq(Calendar::getUserId, customStatement.getId())).stream().map(Calendar::getId).collect(Collectors.toList());
        for (Integer calendarId : idListVO.getIdList()) {
            if (!curCalendarIdList.contains(calendarId)) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
        List<Integer> curCalendarTagIdList = calendarTagDao.selectList(new LambdaQueryWrapper<CalendarTag>()
                .select(CalendarTag::getId)
                .in(CalendarTag::getCalendarId, idListVO.getIdList())).stream().map(CalendarTag::getId).collect(Collectors.toList());
        calendarTagDao.deleteBatchIds(curCalendarTagIdList);
        calendarDao.deleteBatchIds(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }
}
