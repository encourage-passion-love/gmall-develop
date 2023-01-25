package com.xfp.gmall.order.manager.config;

import com.xfp.gmall.order.manager.controller.SpuController;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
@Configuration
public class FdfsClient {

    @Bean
    public StorageClient getStorageClient() throws IOException, MyException {
        String path = SpuController.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }
}
