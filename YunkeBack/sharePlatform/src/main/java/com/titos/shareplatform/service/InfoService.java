package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.global.PageResult;
import com.titos.info.shareplatform.entity.Info;
import com.titos.info.shareplatform.vo.*;
import com.titos.tool.token.CustomStatement;

import java.util.List;

/**
 * @ClassName InfoService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 0:38
 **/
public interface InfoService extends IService<Info> {

    /**
     * 按条件分页查询信息
     *
     * @param filterInfo 条件
     * @param pageNum    当前页
     * @param pageSize   每页的数量
     * @return 分页信息列表
     */
    PageResult<InfoVO> listInfo(FilterInfoVO filterInfo, Long pageNum, Long pageSize);

    /**
     * 获取当前用户的信息
     *
     * @param customStatement 用户信息
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 信息列表
     */
    CommonResult<List<MyInfoVO>> listMyInfo(CustomStatement customStatement, Integer pageNum, Integer pageSize);

    /**
     * 发布信息
     *
     * @param customStatement 用户信息
     * @param addInfoVO       需要发布的信息
     * @return 是否发布成功
     */
    CommonResult<Boolean> addInfo(CustomStatement customStatement, AddInfoVO addInfoVO);

    /**
     * 更新信息状态
     *
     * @param customStatement 用户信息
     * @param updateInfoVO    更新的消息
     * @return 是否更新成功
     */
    CommonResult<Boolean> updateInfo(CustomStatement customStatement, UpdateInfoVO updateInfoVO);

    /**
     * 批量删除消息
     *
     * @param customStatement 用户消息
     * @param idListVO        需要删除的消息的ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteInfo(CustomStatement customStatement, IdListVO idListVO);

    /**
     * 获取当前用户发表的信息总量
     * @param customStatement 用户信息
     * @return 当前用户发表的信息总量
     */
    CommonResult<Integer> getInfoTotal(CustomStatement customStatement);
}
