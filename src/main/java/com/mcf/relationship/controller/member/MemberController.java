package com.mcf.relationship.controller.member;

import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.member.request.AccessRecordQueryRequest;
import com.mcf.relationship.controller.member.request.RedeemMemberRequest;
import com.mcf.relationship.controller.member.response.MemberResponse;
import com.mcf.relationship.controller.member.vo.MemberAccessRecordVO;
import com.mcf.relationship.domain.service.MemberAccessRecordService;
import com.mcf.relationship.domain.service.MemberRedeemService;
import com.mcf.relationship.domain.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:54
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @Resource
    private MemberAccessRecordService memberAccessRecordService;

    @Resource
    private MemberRedeemService memberRedeemService;

    /**
     * 查询会员
     *
     * @return
     */
    @PostMapping("/queryMember")
    public McfResult<MemberResponse> queryMember(@RequestBody BaseRequest request){
        MemberResponse response = memberService.queryMember(request);
        return McfResult.success(response);
    }

    /**
     * 查询会员的获取记录
     *
     * @return
     */
    @PostMapping("/queryAccessRecordList")
    public McfResult<PageResponse<MemberAccessRecordVO>> queryAccessRecordList(@RequestBody AccessRecordQueryRequest request){
        PageResponse<MemberAccessRecordVO> response = memberAccessRecordService.queryAccessRecordList(request);
        return McfResult.success(response);
    }

    /**
     * 兑换会员
     *
     * @return
     */
    @PostMapping("/redeemMember")
    public McfResult<Void> redeemMember(@RequestBody RedeemMemberRequest request){
        memberRedeemService.redeemMember(request);
        return McfResult.success();
    }

}
