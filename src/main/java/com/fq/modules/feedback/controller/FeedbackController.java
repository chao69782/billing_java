package com.fq.modules.feedback.controller;


import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.feedback.dto.FeedbackDTO;
import com.fq.modules.feedback.service.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 超chao
 * @since 2026-02-27
 */
@Tag(name = "反馈")
@RestController
@RequestMapping("/api/front/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping("/save")
    @Operation(summary = "保存反馈")
    public ApiRest<Void> save(@Validated @RequestBody FeedbackDTO reqDTO) {
        feedbackService.save(reqDTO);
        return success();
    }

}
