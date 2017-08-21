package cn.blingfeng.disk.pojo;

public class TbLastUpload {
    private Long id;

    private String lastUpload;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastUpload() {
        return lastUpload;
    }

    public void setLastUpload(String lastUpload) {
        this.lastUpload = lastUpload == null ? null : lastUpload.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}