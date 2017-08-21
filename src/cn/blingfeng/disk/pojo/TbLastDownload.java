package cn.blingfeng.disk.pojo;

public class TbLastDownload {
    private Long id;

    private String lastDownload;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastDownload() {
        return lastDownload;
    }

    public void setLastDownload(String lastDownload) {
        this.lastDownload = lastDownload == null ? null : lastDownload.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}