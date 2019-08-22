package com.guli.educenter.service;

import com.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    Integer countRegisterByDay(String day);

    UcenterMember getUserByOpenId(String openid);
}
