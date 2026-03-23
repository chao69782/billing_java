package com.fq.modules.feedback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.feedback.dto.FeedbackDTO;
import com.fq.modules.feedback.entity.Feedback;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-27
 */
public interface IFeedbackService extends IService<Feedback> {

    /**
     * 保存反馈信息
     * @param reqDTO
     */
    void save(FeedbackDTO reqDTO);

}
