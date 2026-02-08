package com.mcf.relationship;

import com.mcf.relationship.domain.service.MemberRedeemService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/8 23:44
 */
@SpringBootTest
public class MemberRedeemTest {

    @Resource
    private MemberRedeemService memberRedeemService;

    @Test
    public void generateRedeemCode(){
        memberRedeemService.batchGenerateRedeem(3L,100);
    }
}
