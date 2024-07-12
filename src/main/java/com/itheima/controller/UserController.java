package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;;

    @PostMapping("/register")
    public Result<User> register(@Pattern(regexp = "^\\S{5,16}$") final String username, @Pattern(regexp = "^\\S{5,16}$") final String password){
        final User existingUser = userService.findByUserName(username);
        if(existingUser == null){
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") final String username,@Pattern(regexp = "^\\S{5,16}$") final String password){
        //final User existingUser = userService.findByUserName(username);
        User loginUser = userService.findByUserName(username);

        if(loginUser == null){
            return Result.error("用户不存在");
        }

        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getUsername());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密码错误");

//        if(Md5Util.getMD5String(password).equals(existingUser.getPassword())){
//            final Map<String, Object> map = Map.of("id",existingUser.getId(),
//                    "username", existingUser.getUsername());
//            final String token = JwtUtil.genToken(map);
//        }

    }


}
