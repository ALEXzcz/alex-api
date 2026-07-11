package com.ctgu.alexapi.controller;

import com.ctgu.alexapi.service.UsersService;
import com.ctgu.alexapi.utils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Author Alex2
 * @Date 2025/7/2 14:43
 **/
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // http://localhost:8888/api/users/getAllUser?pageNum=1&pageSize=10
    @GetMapping("/getAllUser")
    public ApiResult getAllUser(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        return usersService.getAllUser(pageNum, pageSize);
    }

    // http://localhost:8888/api/users/1
    @GetMapping("/{id}")
    public ApiResult getUserById(@PathVariable("id") Integer id) {
        return usersService.getUserById(id);
    }
}