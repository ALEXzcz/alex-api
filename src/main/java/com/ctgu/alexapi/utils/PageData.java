package com.ctgu.alexapi.utils;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageData<T> {
    private Long totalNum;      //总条数

    private Long totalPage;     //总页数

    private List<T> data;       //数据
}