package com.fq.modules.family.mapper;

import com.fq.modules.family.entity.Family;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.family.vo.MemberListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 超chao
 * @since 2026-02-25
 */
@Mapper
public interface FamilyMapper extends BaseMapper<Family> {
    /**
     * 获取家庭列表
     * @return
     */
    List<MemberListVO> members(@Param("userId") Long userId);

    /**
     * 查询家庭分组
     * @param userId
     */
    Long findGroupId(Long userId);

    /**
     * 查询家庭成员数
     * @param groupId
     * @return
     */
    Long countMember(Long groupId);

    /**
     * 移除所有家庭成员
     * @param groupId
     */
    void removeAllMember(Long groupId);

    /**
     * 根据用户ID删除
     * @param userId
     */
    void removeByUserId(Long userId);

}
