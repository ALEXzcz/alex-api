package com.ctgu.alexapi.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;

@Log4j2
public class Tools {
    public static <T> ApiResult pageResult(IPage<T> page, String TAG) {
        if (page.getRecords().isEmpty()) {
            log.info("{} : 获取列表为空 = {}", TAG, Collections.emptyList());
            return ApiResult.success("获取列表为空", Collections.emptyList());
        }

        PageData.PageDataBuilder<T> builder = PageData.builder();
        log.info("{} : 获取列表成功 = {}", TAG, page.getRecords());

        return ApiResult.success("获取列表成功", builder
                .totalNum(page.getTotal())
                .totalPage(page.getPages())
                .data(page.getRecords())
                .build());
    }
}