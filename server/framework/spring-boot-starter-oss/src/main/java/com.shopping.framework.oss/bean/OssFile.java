package com.shopping.framework.oss.bean;

import java.io.Serializable;

/**
 * Created by ZJ on 2017-08-18.
 */
public class OssFile implements Serializable {

    private String fileName;//文件名  xxx.xxx
    private String newName;  // xxxxxxxxxxxxxxxxx.xxx
    private String savePath; //  xx/xx/
    private String address;//储存地址  xx/xx/xxxxxxxxxxxxxxxxx.xxx
    private Long size;//文件大小
    private String smallPath; //  xx/xx/xxxxxxxxxxxxxxxxx.xxx
    private String middlePath; // xx/xx/xxxxxxxxxxxxxxxxx.xxx
    private String bigPath;   // xx/xx/xxxxxxxxxxxxxxxxx.xxx
    private ImageSize imageSize;
    private String suffix; // xxx

    public OssFile(){

    }

    public OssFile(String fileName, String address, Long size) {
        this.fileName = fileName;
        this.address = address;
        this.size = size;
    }

    public OssFile(String fileName, String address, Long size,ImageSize imageSize) {
        this.fileName = fileName;
        this.address = address;
        this.size = size;
        this.imageSize = imageSize;
    }


    public OssFile(String fileName, String address, Long size,String suffix) {
        this.fileName = fileName;
        this.address = address;
        this.size = size;
        this.suffix = suffix;
    }


    public OssFile(String fileName, Long size,String address, String suffix) {
        this.fileName = fileName;
        this.address = address;
        this.size = size;
        this.suffix = suffix;
    }

    public OssFile(String fileName, String address, Long size,ImageSize imageSize,String suffix) {
        this.fileName = fileName;
        this.address = address;
        this.size = size;
        this.imageSize = imageSize;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSmallPath() {
        return smallPath;
    }

    public void setSmallPath(String smallPath) {
        this.smallPath = smallPath;
    }

    public String getMiddlePath() {
        return middlePath;
    }

    public void setMiddlePath(String middlePath) {
        this.middlePath = middlePath;
    }

    public String getBigPath() {
        return bigPath;
    }

    public void setBigPath(String bigPath) {
        this.bigPath = bigPath;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
