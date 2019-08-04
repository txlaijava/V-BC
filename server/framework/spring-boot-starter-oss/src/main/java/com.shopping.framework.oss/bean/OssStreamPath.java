package com.shopping.framework.oss.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-08-18.
 */
public class OssStreamPath implements Serializable {

    private byte[] stream;
    private String remotePath;//储存地址
    private String fileOldName;//原本的文件名
    private String fileNewName;//新的文件名
    private String suffix;//文件戳

    public OssStreamPath(byte[] stream, String remotePath, String fileOldName, String fileNewName,String suffix) {
        this.stream = stream;
        this.remotePath = remotePath;
        this.fileOldName = fileOldName;
        this.fileNewName = fileNewName;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public byte[] getStream() {
        return stream;
    }

    public void setStream(byte[] stream) {
        this.stream = stream;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getFileOldName() {
        return fileOldName;
    }

    public void setFileOldName(String fileOldName) {
        this.fileOldName = fileOldName;
    }

    public String getFileNewName() {
        return fileNewName;
    }

    public void setFileNewName(String fileNewName) {
        this.fileNewName = fileNewName;
    }
}
