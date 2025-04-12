package com.example.feign_api.Message.Emit;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class PostMessage
{
    Integer code;//1：成功，0：失败
    String msg;

    public PostMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
