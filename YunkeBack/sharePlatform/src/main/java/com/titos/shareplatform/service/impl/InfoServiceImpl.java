package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.PageResult;
import com.titos.info.global.constant.CommonConst;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.shareplatform.entity.Info;
import com.titos.info.shareplatform.vo.*;
import com.titos.shareplatform.dao.InfoDao;
import com.titos.shareplatform.service.InfoService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import com.titos.tool.sensitive.SensitiveFilter;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName InfoServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 0:39
 **/
@Service
@Slf4j
public class InfoServiceImpl extends ServiceImpl<InfoDao, Info> implements InfoService {

    @Resource
    private InfoDao infoDao;

    @Resource
    private SensitiveFilter sensitiveFilter;


    @Override
    public PageResult<InfoVO> listInfo(FilterInfoVO filterInfo, Long pageNum, Long pageSize) {
        List<InfoVO> infoList = infoDao.listInfo(filterInfo, (pageNum - 1) * pageSize, pageSize);
        Integer totalCount = infoDao.listInfoTotal(filterInfo, (pageNum - 1) * pageSize, pageSize);
        return new PageResult<>(infoList, (int) ((int) (totalCount + pageSize - 1) / pageSize));
    }

    @Override
    public CommonResult<List<MyInfoVO>> listMyInfo(CustomStatement customStatement, Integer pageNum, Integer pageSize) {
        Page<Info> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Info> queryWrapper = new LambdaQueryWrapper<Info>()
                .select(Info::getId, Info::getInfoTitle, Info::getType, Info::getStatus, Info::getCreateTime)
                .orderByDesc(Info::getCreateTime)
                .eq(Info::getUserId, customStatement.getId());
        //Page<Info> infoPage = infoDao.selectPage(page, queryWrapper);
        return CommonResult.success(BeanCopyUtils.copyList(infoDao.selectList(queryWrapper), MyInfoVO.class));
    }

    @Override
    public CommonResult<Boolean> addInfo(CustomStatement customStatement, AddInfoVO addInfoVO) {
        // 敏感词过滤
        addInfoVO.setInfoContent(sensitiveFilter.filter(addInfoVO.getInfoContent()));
        addInfoVO.setInfoTitle(sensitiveFilter.filter(addInfoVO.getInfoTitle()));
        Info info = BeanCopyUtils.copyObject(addInfoVO, Info.class);
        info.setUserId(customStatement.getId());
        infoDao.insert(info);
        return CommonResult.success(Boolean.TRUE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> updateInfo(CustomStatement customStatement, UpdateInfoVO updateInfoVO) {
        List<Integer> infoIdList = updateInfoVO.getIdList();
        for (Integer infoId : infoIdList) {
            if (!infoDao.selectById(infoId).getUserId().equals(customStatement.getId())) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
        updateInfoVO.getIdList().forEach(infoId -> {
            Info info = Info.builder()
                    .id(infoId)
                    .status(updateInfoVO.getInfoStatus())
                    .build();
            infoDao.updateById(info);
        });
        return CommonResult.success(Boolean.TRUE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteInfo(CustomStatement customStatement, IdListVO idListVO) {
        List<Integer> curInfoIdList = infoDao.selectList(new LambdaQueryWrapper<Info>()
                .select(Info::getId)
                .eq(Info::getUserId, customStatement.getId())).stream().map(Info::getId).collect(Collectors.toList());
        for (Integer infoId : idListVO.getIdList()) {
            if (!curInfoIdList.contains(infoId)) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
        infoDao.deleteBatchIds(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }

    @Override
    public CommonResult<Integer> getInfoTotal(CustomStatement customStatement) {
        Integer infoTotal = infoDao.selectList(new LambdaQueryWrapper<Info>()
                .select(Info::getId)
                .eq(Info::getUserId, customStatement.getId())).size();
        return CommonResult.success(infoTotal);
    }
}
