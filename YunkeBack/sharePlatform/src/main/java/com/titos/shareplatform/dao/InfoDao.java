package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Info;
import com.titos.info.shareplatform.vo.FilterInfoVO;
import com.titos.info.shareplatform.vo.InfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName InfoDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 0:39
 **/
@Repository
public interface InfoDao extends BaseMapper<Info> {

    /**
     * 按条件分页查询信息
     *
     * @param filterInfo 条件
     * @param pageNum    当前页
     * @param pageSize   每页的数量
     * @return 信息列表
     */
    List<InfoVO> listInfo(FilterInfoVO filterInfo, Long pageNum, Long pageSize);

    /**
     * 查询 按条件分页查询信息 的分页总数
     *
     * @param filterInfo 条件
     * @param pageNum    当前页
     * @param pageSize   每页的数量
     * @return 分页总数
     */
    Integer listInfoTotal(FilterInfoVO filterInfo, Long pageNum, Long pageSize);
}
