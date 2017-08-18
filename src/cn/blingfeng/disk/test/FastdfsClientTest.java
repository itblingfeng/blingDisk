package cn.blingfeng.disk.test;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FastdfsClientTest {

    //客户端配置文件
    public String conf_filename = "E:\\e3项目\\cn.blingfeng.disk\\src\\fdfs_client.conf";
    //本地文件，要上传的文件
    public String local_filename = "F:\\项目二：宜立方商城（80-93天）\\项目二：宜立方商城（80-93天）\\e3商城_day01\\黑马32期\\01.教案-3.0\\01.参考资料\\FastDFS\\bin\\fastdfs_client\\src\\main\\resources\\fdfs_client.conf";

    //上传文件
    @Test
    public void testUpload() {

        for(int i=0;i<100;i++){

            try {
                ClientGlobal.init(conf_filename);

                TrackerClient tracker = new TrackerClient();
                TrackerServer trackerServer = tracker.getConnection();
                StorageServer storageServer = null;

                StorageClient storageClient = new StorageClient(trackerServer,
                        storageServer);
                NameValuePair nvp [] = new NameValuePair[]{
                        new NameValuePair("item_id", "100010"),
                        new NameValuePair("width", "80"),
                        new NameValuePair("height", "90")
                };
                String fileIds[] = storageClient.upload_file(local_filename, null,
                        nvp);

                System.out.println(fileIds.length);
                System.out.println("组名：" + fileIds[0]);
                System.out.println("路径: " + fileIds[1]);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
