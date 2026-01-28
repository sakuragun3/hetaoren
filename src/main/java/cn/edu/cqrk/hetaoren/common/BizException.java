package cn.edu.cqrk.hetaoren.common;


import lombok.Data;

@Data
public class BizException extends Exception {

    private Integer code;

    public BizException(BizExceptionCode bizExceptionCode){
        super(bizExceptionCode.getMsg());
        this.code = bizExceptionCode.getCode();
    }
}
