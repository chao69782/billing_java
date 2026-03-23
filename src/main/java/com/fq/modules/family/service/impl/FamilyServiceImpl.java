package com.fq.modules.family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.family.entity.Family;
import com.fq.modules.family.mapper.FamilyMapper;
import com.fq.modules.family.service.IFamilyService;
import com.fq.modules.family.vo.MemberListVO;
import com.fq.modules.sys.user.entity.SysUser;
import com.fq.modules.sys.user.service.SysUserService;
import com.fq.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-25
 */
@Service
public class FamilyServiceImpl extends ServiceImpl<FamilyMapper, Family> implements IFamilyService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<MemberListVO> members() {
        return baseMapper.members(UserUtils.getUserId());
    }

    @Override
    public void join(String code) {
        // 校验是否已有家庭
        Long userId = UserUtils.getUserId();
        Long groupId = findGroupId(userId);
        if (groupId != null){
            throw new ServiceException(ApiError.ERROR_10070002);
        }

        SysUser sysUser = sysUserService.checkInviteCode(code);
        Long adminUserId = sysUser.getId();
        if (adminUserId.equals(userId)){
            throw new ServiceException(ApiError.ERROR_10070003);
        }
        Long adminGroupId = findGroupId(adminUserId);
        if (adminGroupId != null){
            Family family = new Family();
            family.setId(IdWorker.getId());
            family.setUserId(userId);
            family.setRole(0);
            family.setGroupId(adminGroupId);
            this.save(family);
        } else {
            adminGroupId = IdWorker.getId();
            List<Family> list = getFamilies(adminUserId, adminGroupId, userId);
            this.saveBatch(list);

        }
    }

    private static List<Family> getFamilies(Long adminUserId, Long adminGroupId, Long userId) {
        List<Family> list = new ArrayList<>();
        Family familyAdmin = new Family();
        familyAdmin.setId(IdWorker.getId());
        familyAdmin.setUserId(adminUserId);
        familyAdmin.setRole(0);
        familyAdmin.setGroupId(adminGroupId);
        list.add(familyAdmin);

        Family familyUser = new Family();
        familyUser.setId(IdWorker.getId());
        familyUser.setUserId(userId);
        familyUser.setRole(1);
        familyUser.setGroupId(adminGroupId);
        list.add(familyUser);
        return list;
    }

    @Override
    public Long findGroupId(Long userId) {
        return baseMapper.findGroupId(userId);
    }

    @Override
    public void editRemark(Long id, String remark) {
        UpdateWrapper<Family> wrapper = new UpdateWrapper<>();
        wrapper.set("remark",remark).eq("id",id);
        this.update(wrapper);
    }

    @Override
    public void remove(Long id) {
        Family family = this.getById(id);
        if (family == null){
            return;
        }
        Long groupId = family.getGroupId();
        Long adminUserId = findAdminUserId(groupId);
        if (adminUserId == null){
            return;
        }
        Long count = countMember(groupId);

        if (adminUserId.equals(UserUtils.getUserId())){
            // 小于等于2时，删除所有成员
            if (count <= 2){
                removeAllMember(groupId);
            } else {
                this.removeById(id);
            }
        } else {
            throw new ServiceException(ApiError.ERROR_10070004);
        }

    }

    @Override
    public Long findAdminUserId(Long groupId) {

        QueryWrapper<Family> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Family::getGroupId,groupId).eq(Family::getRole,0);
        Family family = this.getOne(wrapper);
        return family == null ? null : family.getUserId();
    }

    @Override
    public Long countMember(Long groupId) {
        return baseMapper.countMember(groupId);
    }

    @Override
    public void removeAllMember(Long groupId) {
        baseMapper.removeAllMember(groupId);
    }

    @Override
    public void quit() {
        Long userId = UserUtils.getUserId();
        Long groupId = findGroupId(userId);
        if (groupId == null){
            return;
        }
        Long count = countMember(groupId);
        // 小于等于2时，删除所有成员
        if (count <= 2){
            removeAllMember(groupId);
        } else {
            removeByUserId(userId);
        }
    }

    @Override
    public void removeByUserId(Long userId) {
        baseMapper.removeByUserId(userId);
    }
}
