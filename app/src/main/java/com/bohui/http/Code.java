package com.bohui.http;

public interface Code {
    int Code0 = 0;//请求成功，返回成功
    int Code_1 = -1;//未知异常，一般为系统异常
    int Code101 = 101;//登录失败
    int Code102 = 102;//登录超时
    int Code103 = 103;//用户名或密码错误
    int Code104 = 104;//未登录
    int Code105= 105;//当前用户已注册
    int Code106 = 106;//验证码不正确
    int Code107 = 107;//注册失败
    int Code108 = 108;//请输入正确的邀请码
    int Code201 = 201;//请上传图片，图片流参数为空，或者上传图片失败
    int Code202 = 301;//参数校验失败
    int Code401 = 401;//没有操作权限，无法观看视频，或者无法修改宝宝信息或者无法解绑设备
    int Code402 = 402;//您的申请还没有通过
    int Code403 = 403;//您的超时了
    int Code404 = 501;//无法重复签到
    int Code502 = 502;//重复分享无奖励
}
