package org.live.common.utils;

import java.util.UUID;

/**
 * uuid生成器
 * @author Mr.wang
 *
 */
public class UUIDGenerator {
    /**
     * 获得一个UUID字符串
     * @return String UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成盐
     * 密码加盐的方式，一定要盐+原始密码，shiro验证才能通过。否则报错。
     *
     * @return salt
     *
     */
    public static String createSalt() {

        return getUUID() ;
    }

}
