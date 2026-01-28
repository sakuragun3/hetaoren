package cn.edu.cqrk.hetaoren.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(0);
        r.setMsg("success");
        r.setData(data);

        return r;
    }

    public static R success() {
        R r = new R();
        r.setCode(0);
        r.setMsg("success");
        r.setData(null);

        return r;
    }

    public static <T> R<T> failed(String msg) {
        R<T> r = new R();
        r.setCode(-1);
        r.setMsg(msg);
        r.setData(null);

        return r;
    }

    public static R failed() {
        R r = new R();
        r.setCode(-1);
        r.setMsg("failed");
        r.setData(null);

        return r;
    }

    public static R failed(Integer code,String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(null);

        return r;
    }

}
