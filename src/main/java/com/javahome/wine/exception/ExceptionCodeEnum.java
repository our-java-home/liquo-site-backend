package com.javahome.wine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 勿忘初心
 * @since 2023-06-19-23:21
 * 全局异常错误码
 */
@AllArgsConstructor
@Getter
public enum ExceptionCodeEnum {
    // 操作成功
    EC0(0,"操作成功"),
    // 通用模块错误
    EC10000(10000,"系统内部错误"),
    EC10001(10001,"参数错误"),
    EC10002(10002,"资源不存在"),
    EC10003(10003,"token不合法或已过期"),
    EC10004(10004,"未授权用户"),
    EC10005(10005,"登录失败"),
    EC10006(10006,"请求的方法不被支持"),

    // 用户模块错误
    EC20000(20000,"用户已存在"),
    EC20001(20001,"用户不存在"),
    EC20002(20002,"用户名或密码错误"),

    // 商品模块错误
    EC30000(30000,"商品信息不存在"),
    EC30001(30001,"分类信息不存在"),
    EC30003(30002,"没有更多的商品信息了"),
    EC30004(30003,"请求的商品列表为空"),

    // 优惠券模块
    EC40001(40000,"未找到首页优惠券活动"),
    EC40002(40001,"未找到商品可用优惠券"),
    EC40003(40002,"未找到优惠券"),
    EC40004(40003,"优惠券已过期"),
    EC40005(40004,"你已经领取了优惠券"),
    EC40006(40005,"优惠券不符合使用的条件"),

    // 订单模块
    EC50000(50000,"订单中商品已经售罄"),
    EC50001(50001,"订单中存在已下架商品"),
    EC50002(50002,"订单中商品库存不足"),
    EC50003(50003,"订单中商品数量超过购买上限"),
    EC50004(50004,"订单中商品不能是0件"),
    EC50005(50005,"订单中优惠金额与实际金额不匹配"),
    EC50006(50006,"订单不存在"),
    EC50007(50007,"订单已过期"),
    EC50008(50008,"订单中的商品并不免费~");

    /**
     * 异常代码
     */
    private Integer code;
    /**
     * 描述信息
     */
    private String message;
}
