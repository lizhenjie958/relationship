package com.mcf.relationship.controller.relation;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageRequest;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.relation.vo.ProtagonistVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:21
 */
@RestController
@RequestMapping("/protagonist")
public class ProtagonistController {

    @PostMapping("queryPage")
    public McfResult<PageResponse<ProtagonistVO>> queryPage(@RequestBody PageRequest request){
        List<ProtagonistVO> list = new ArrayList<>();
        ProtagonistVO protagonistVO = new ProtagonistVO();
        protagonistVO.setId(1L);
        protagonistVO.setName("张三");
        protagonistVO.setSmallPicUrl("https://www.baidu.com");
        protagonistVO.setPicUrl("https://www.baidu.com");
        list.add(protagonistVO);

        for (long i = 2; i <= 35; i++) {
            ProtagonistVO protagonistVO1 = new ProtagonistVO();
            protagonistVO1.setId( i);
            BeanUtils.copyProperties(protagonistVO, protagonistVO1);
            list.add(protagonistVO1);
        }
        int i = request.getPageNo() * request.getPageSize();
        if (i > list.size()) {
            i = list.size();
        }
        PageResponse<ProtagonistVO> pageResponse = new PageResponse<>(list.size(), list.subList(request.getPageNo() - 1, i));
        return McfResult.success(pageResponse);
    }

}
