package com.shopping.framework.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 阿里云oss对象存储工具类
 */
@Log4j2
@Component
public class AliOSSUtil {

    /**
     * @param ossClient  oss客户端
     * @param url        URL
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名）例如“test/index.html”
     * @return void        返回类型
     * @throws
     * @Title: uploadByNetworkStream
     * @Description: 通过网络流上传文件
     */
    public void uploadByNetworkStream(OSSClient ossClient, URL url, String bucketName, String objectName) {
        try {
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @param ossClient   OSS
     * @param inputStream 输入流
     * @param bucketName  bucket名称
     * @param objectName  上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void        返回类型
     * @throws
     * @Title: uploadByInputStream
     * @Description: 通过输入流上传文件
     */
    public void uploadByInputStream(OSSClient ossClient, InputStream inputStream, String bucketName, String objectName) {
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ossClient  oss客户端
     * @param file       上传的文件
     * @param bucketName bucket名称
     * @param objectName 上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void        返回类型
     * @throws
     * @Title: uploadByFile
     * @Description: 通过file上传文件
     */
    public static void uploadByFile(OSSClient ossClient, File file, String bucketName, String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ossClient  oss客户端
     * @param bucketName bucket名称
     * @param key        文件路径/名称，例如“test/a.txt”
     * @return void            返回类型
     * @throws
     * @Title: deleteFile
     * @Description: 根据key删除oss服务器上的文件
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     * @param ossClient  oss客户端
     * @param bucketName bucket名称
     * @param key        文件路径和名称
     * @return InputStream    文件输入流
     * @throws
     * @Title: getInputStreamByOSS
     * @Description:根据key获取服务器上的文件的输入流
     */
    public static InputStream getInputStreamByOSS(OSSClient ossClient, String bucketName, String key) {
        InputStream content = null;
        try {
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * @param ossClient  oss客户端
     * @param bucketName bucket名称
     * @return List<String>  文件路径和大小集合
     * @throws
     * @Title: queryAllObject
     * @Description: 查询某个bucket里面的所有文件
     */
    public static List<String> queryAllObject(OSSClient ossClient, String bucketName) {
        List<String> results = new ArrayList<String>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                // objectSummary.getSize();
                results.add(objectSummary.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 从OSS服务中下载所需文件到本地临时文件
     *
     * @param bucketName 要访问的存储空间
     * @param objectName 要下载的对象/文件
     * @return
     */
    public String downloadOSS(OSSClient ossClient, String bucketName, String objectName) {
        String basePath = System.getProperty("user.dir") + "/files";
        try {
            //指定文件保存路径
            String filePath = basePath + "/" + objectName.substring(0, objectName.lastIndexOf("/"));
            //判断文件目录是否存在，不存在则创建
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断保存文件名是否加后缀
            if (objectName.contains(".")) {
                //指定文件保存名称
                filePath = filePath + "/" + objectName.substring(objectName.lastIndexOf("/") + 1);
            }

            //获取OSS文件并保存到本地指定路径中，此文件路径一定要存在，若保存目录不存在则报错，若保存文件名已存在则覆盖本地文件
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(filePath));
            return filePath;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭oss连接
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
