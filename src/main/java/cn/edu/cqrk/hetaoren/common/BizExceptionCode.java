package cn.edu.cqrk.hetaoren.common;

public enum BizExceptionCode {

    ILLEGAL_PHONE(50001, "手机号不合法"),
    FAILED_LOGIN(50002, "登录失败"),
    ILLEGAL_ID(50003, "ID不合法"),
    FAILED_ADD(50004, "添加失败"),
    EXIST_PHONE(50005, "手机号已存在"),
    USER_NOT_FOUND(50006,"请求为空"),
    FAILED_UPDATE(50007, "更新失败"),
    FAILED_DELETE(50008,"删除失败"),
    ILLEGAL_USERNAME(50009,"用户名不能为空"),
    ILLEGAL_NAME(50010,"姓名不能为空"),
    CASE_NOT_FOUND(50011,"病例无法获取"),
    ILLEGAL_SYMPTOMS(50011,"症状不能为空"),
    ILLEGAL_PASSWORD(50011,"密码不正确")

            ;

    private  Integer code;
    private  String msg;


    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    BizExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
