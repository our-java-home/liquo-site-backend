package com.javahome.wine.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:57
 * 数据分页实现
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {
    private T result;
    private Integer total;
}
