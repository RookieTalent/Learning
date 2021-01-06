package run.zykj.app.subject14.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.zykj.app.subject14.entity.dto.RegisterInfo;
import run.zykj.app.subject14.service.UserService;

/**
 * @author xiaolin
 * @date 2021/1/6 9:33
 */
@RestController
@RequestMapping("/user")
@Api(tags = "subject14-邮件激活")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册接口")
    public void register(@RequestBody RegisterInfo info){
        userService.register(info);
    }

    @GetMapping("/activation/{code}/{userId}")
    @ApiOperation(value = "注册回调激活用户接口")
    public String activation(@PathVariable("code")String code, @PathVariable("userId")Long userId){
        userService.activationUser(code, userId);
        return "激活成功!";
    }

}
