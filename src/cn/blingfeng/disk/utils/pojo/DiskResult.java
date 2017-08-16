package cn.blingfeng.disk.utils.pojo;

import java.io.Serializable;

public class DiskResult implements Serializable{
    private Integer status;
    private  Object data;
    private  String msg;
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }
    public DiskResult(Object data){
        this.data=data;
        this.status=200;
    }
    public DiskResult(){
        this.status=200;
    }
    public static DiskResult ok(){
        return new DiskResult();
    }
    public static DiskResult ok(Object data){
      return new DiskResult(data);
    }
    public DiskResult(Integer status,Object data,String msg){
        this.status=status;
        this.data=data;
        this.msg=msg;
    }
    public static DiskResult build(Integer status,Object data,String msg){
        return new DiskResult(status,data,msg);
    }
}
