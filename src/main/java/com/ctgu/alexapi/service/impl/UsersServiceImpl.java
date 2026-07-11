package com.ctgu.alexapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.alexapi.entity.UsersEntity;
import com.ctgu.alexapi.service.UsersService;
import com.ctgu.alexapi.mapper.UsersMapper;
import com.ctgu.alexapi.utils.ApiResult;
import com.ctgu.alexapi.utils.Tools;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
* @author Alex2
* @description 针对表【t_users】的数据库操作Service实现
* @createDate 2025-07-02 14:37:56
*/
@Log4j2
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, UsersEntity>
    implements UsersService {

    private static final String TAG = UsersServiceImpl.class.getSimpleName();

    @Autowired
    private RedisTemplate<String, UsersEntity> usersRedisTemplate;

    private static final String USER_CACHE_PREFIX = "user:";

    @Override
    public ApiResult getAllUser(Integer pageNum, Integer pageSize) {
        Page<UsersEntity> page = new Page<>(pageNum, pageSize);
        IPage<UsersEntity> result = lambdaQuery().page(page);
        return Tools.pageResult(result, TAG);
    }

    @Override
    public ApiResult getUserById(Integer userId) {
        String key = USER_CACHE_PREFIX + userId;

        // 1. 从 Redis 取缓存
        UsersEntity cachedUser = usersRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            log.info("{}: 从缓存获取用户成功 = {}", TAG, cachedUser);
            return ApiResult.success("获取用户成功（缓存）", cachedUser);
        }

        // 2. 缓存没命中，从数据库查
        UsersEntity usersEntity = lambdaQuery().eq(UsersEntity::getId, userId).one();
        if (usersEntity == null) {
            log.error("{}: 用户 Id = {} 不存在", TAG, userId);
            return ApiResult.error("用户不存在");
        }

        // 3. 查到后写入缓存，设置过期时间
        usersRedisTemplate.opsForValue().set(key, usersEntity, 10, TimeUnit.MINUTES);

        log.info("{}: 获取用户成功 = {}", TAG, usersEntity);
        return ApiResult.success("获取用户成功", usersEntity);
    }
}