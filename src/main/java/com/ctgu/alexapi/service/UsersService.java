package com.ctgu.alexapi.service;

import com.ctgu.alexapi.entity.UsersEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctgu.alexapi.utils.ApiResult;

/**
* @author Alex2
* @description 针对表【t_users】的数据库操作Service
* @createDate 2025-07-02 14:37:56
*/
public interface UsersService extends IService<UsersEntity> {

    ApiResult getAllUser(Integer pageNum, Integer pageSize);

    ApiResult getUserById(Integer id);
}
