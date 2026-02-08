package com.mcf.relationship.common.consts;

/**
 * @Author ZhuPo
 * @date 2026/2/8 10:30
 */
public interface RandomStrConst {
    /**
     * 定义易混淆的字符 ( l, o, 0, 1 )
     */
    char[] CONFUSING_CHARS = {
            'l','o','0','1'
    };

    /**
     * 定义易读的小写字母和数字（去掉了 l, o, 0, 1）
     */
    char[] READABLE_CHARS = {
            // 小写字母（去掉了 l, o）
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            // 数字（去掉了 0, 1）
            '2', '3', '4', '5', '6', '7', '8', '9'
    };
}
