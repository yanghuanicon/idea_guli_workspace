package com.guli.educenter.mapper;

import com.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer selectRegisterCount(String day);
}
