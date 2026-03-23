package com.fq.modules.feedback.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.feedback.dto.FeedbackDTO;
import com.fq.modules.feedback.entity.Feedback;
import com.fq.modules.feedback.mapper.FeedbackMapper;
import com.fq.modules.feedback.service.IFeedbackService;
import com.fq.utils.BeanMapper;
import com.fq.utils.user.UserUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-27
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    @Override
    public void save(FeedbackDTO reqDTO) {
        Feedback feedback = new Feedback();
        BeanMapper.copy(reqDTO, feedback);
        feedback.setId(IdWorker.getId());
        feedback.setUserId(UserUtils.getUserId());
        this.save(feedback);
    }
}
