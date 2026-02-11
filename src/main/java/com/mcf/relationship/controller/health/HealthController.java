package com.mcf.relationship.controller.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ZhuPo
 * @date 2026/2/11 22:02
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("status")
    public String health(){
        return "ok";
    }
}
