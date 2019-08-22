package com.guli.educenter.controller;


import com.guli.common.result.Result;
import com.guli.educenter.entity.UcenterMember;
import com.guli.educenter.service.UcenterMemberService;
import com.guli.educenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService ucenterMemberService;

    //将token字符串转化成具体的对象
    @PostMapping("getLoginInfo/{token}")
    public Result getLoginInfo(@PathVariable String token){
        Claims claims = JwtUtils.checkJWT(token);
        String id  = (String)claims.get("id");
        String nickname  = (String)claims.get("nickname");
        String avatar  = (String)claims.get("avatar");

        UcenterMember member = new UcenterMember();
        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);

        return Result.ok().data("userInfo",member);
    }

    @ApiOperation("今日注册数")
    @GetMapping("getCount/{day}")
    public Result registerCount(@PathVariable("day") String day){
        Integer count = ucenterMemberService.countRegisterByDay(day);

        return Result.ok().data("count",count);
    }

}

