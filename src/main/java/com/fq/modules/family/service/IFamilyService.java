package com.fq.modules.family.service;

import com.fq.modules.family.entity.Family;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.family.vo.MemberListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-25
 */
public interface IFamilyService extends IService<Family> {

    /**
     * 获取家庭列表
     * @return
     */
    List<MemberListVO> members();

    /**
     * 加入
     * @return
     */
    void join(String code);

    /**
     * 查询家庭分组
     * @param userId
     */
    Long findGroupId(Long userId);

    /**
     * 修改备注
     * @param id
     * @param remark
     */
    void editRemark(Long id, String remark);

    /**
     * 移除
     * @param id
     */
    void remove(Long id);

    /**
     * 根据分组ID查询管理员用户ID
     * @param groupId
     * @return
     */
    Long findAdminUserId(Long groupId);

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
     * 退出家庭
     */
    void quit();

    /**
     * 根据用户ID删除
     * @param userId
     */
    void removeByUserId(Long userId);

}
