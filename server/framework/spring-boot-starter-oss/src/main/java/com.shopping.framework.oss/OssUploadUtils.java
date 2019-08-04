package com.shopping.framework.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.oss.bean.ImageSize;
import com.shopping.framework.oss.bean.OssConfig;
import com.shopping.framework.oss.bean.OssFile;
import com.shopping.framework.oss.bean.OssStreamPath;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wpl
 * @Date 2018/6/28
 * @Description
 */
@Log4j
public class OssUploadUtils {

    private String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String bucketName = "rblc";
    private static OssConfig ossConfig;

    static {
        ossConfig = new OssConfig(
                "upload",
                160,
                160,
                300,
                300,
                1024,
                1024,
                "gif|jpg|jpeg|bmp|png|tbi");
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    private static Properties prop = new Properties();
    static {
        //InputStream in = OssUploadUtils.class.getResourceAsStream("/config/oss.properties");
        try {
            //prop.load(in);

        } catch (Exception e) {
            System.out.println("oss异常"+e);
            e.printStackTrace();
        }
    }

    /**
     * 获取配置文件config里的值
     *
     * @param proKey
     * @return
     */
    public String getProperty(String proKey) {
        return prop.getProperty(proKey);
    }

    public String getConfig(String proKey) {
        return prop.getProperty(proKey);
    }

/*	public OssUploadUtils(){

	}*/

    public OssUploadUtils(OssConfig ossConfig){
        this.ossConfig = ossConfig;
    }

    /**
     * soo文件上传
     * @param files 文件组
     * @param ossPathName 文件模块名  如：store/10000   user   goods
     * @return
     */
    public List<OssFile>  ossMultipartUpload(Map<String, MultipartFile> files, String ossPathName) {
        List<OssFile> fileList = new ArrayList<OssFile>();
        ByteArrayOutputStream bs = null;
        ossPathName = ossPathName.startsWith("/") ? ossPathName.substring(1,ossPathName.length()):ossPathName;
        InputStream bis = null;
        InputStream sis = null;
        ImageOutputStream ios = null;
        try {
            byte[] fileByte = new byte[0];
            for (Map.Entry<String, MultipartFile> entity : files.entrySet()) {
                MultipartFile file = entity.getValue();

                fileByte = file.getBytes();//获取文件字节数组
                InputStream in = new ByteArrayInputStream(fileByte);
                List<ImageSize> whs = new ArrayList<ImageSize>();//图片大小
                if (fileByte == null) {
                    return null;
                }
                String fileName = entity.getKey();
                //String path = ossConfig.getUploadFilePath() + "/" + ossPathName + "/" + getDatePath("yyyy/MM/dd") + "/";
                String path = ossConfig.getUploadFilePath() + "/" + ossPathName + "/";
                String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase() : "";
                if (this.isImg(suffix)) {
                    BufferedImage bi = ImageIO.read(in);
                    //原图的大小
                    int width = bi.getWidth();
                    int height = bi.getHeight();
                    bs = new ByteArrayOutputStream();
                    ios = ImageIO.createImageOutputStream(bs);
                    ImageIO.write(bi, suffix.replace(".", ""), ios);
                    bis = new ByteArrayInputStream(bs.toByteArray());
                    ImageSize is = new ImageSize();
                    is.setHeight(height);
                    is.setWidth(width);
                    String uid = getUUID();
                    OssStreamPath p = new OssStreamPath(fileByte, path, fileName, uid, suffix);
                    OssFile ossFile = uploadOss(p,is,"");
					/*生成缩略图*/
                    ossFile.setSmallPath( this.createThumbnail(ossFile,bi,uid,ossConfig.getSmallWidth(),ossConfig.getSmallHeight(),"small").getAddress());
                    ossFile.setMiddlePath( this.createThumbnail(ossFile,bi,uid,ossConfig.getMiddleWidth(),ossConfig.getMiddleHeight(),"middle").getAddress());
                    ossFile.setBigPath( this.createThumbnail(ossFile,bi,uid,ossConfig.getBigWidth(),ossConfig.getBigHeight(),"big").getAddress());
                    fileList.add(ossFile);
                }else{
                    OssStreamPath p = new OssStreamPath(fileByte, path, fileName, getUUID(), suffix);
                    fileList.add(uploadOss(p));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public OssFile createThumbnail(OssFile ossFile,BufferedImage bi,String newName,int w,int h,String sizeType) throws Exception{
        InputStream bis = null;
        ImageOutputStream ios = null;
        ByteArrayOutputStream bs = null;
        //small  ---- middle -----big
        ImageSize is = new ImageSize();
        int width = ossFile.getImageSize().getWidth();
        int height = ossFile.getImageSize().getHeight();
        if (w >= width) {
            w = width;
        }
        if (h >= height) {
            h = height;
        }
        Double d = ArithmeticUtil.div(w, h, 10) * h;
        //获取压缩后的数据流
        BufferedImage sbi = Thumbnails.of(bi).size(w, d.intValue()).outputQuality(1.0f).asBufferedImage();
        bs = new ByteArrayOutputStream();
        ios = ImageIO.createImageOutputStream(bs);
        ImageIO.write(sbi, ossFile.getSuffix().replace(".", ""), ios);
        bis = new ByteArrayInputStream(bs.toByteArray());
        OssStreamPath p = new OssStreamPath(bs.toByteArray(), ossFile.getSavePath(), ossFile.getFileName(), newName, ossFile.getSuffix());
        return uploadOss(p,is,sizeType);
    }

    public String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    private static final String getDatePath(String dateFormat) {
        SimpleDateFormat date = new SimpleDateFormat(dateFormat);
        return date.format(new Date());
    }


    public OssFile uploadOss(OssStreamPath ossStreamPath,ImageSize is,String sizeType) {

        OssFile file = new OssFile();
        try {
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            String path = ossStreamPath.getRemotePath() + ossStreamPath.getFileNewName() + "." + ossStreamPath.getSuffix();
            if(null != sizeType && !"".equals(sizeType)){
                path = ossStreamPath.getRemotePath() + ossStreamPath.getFileNewName() + "." + ossStreamPath.getSuffix() + "_" + sizeType + "." + ossStreamPath.getSuffix();;
            }
            PutObjectResult result = client.putObject(bucketName, path, new ByteArrayInputStream(ossStreamPath.getStream()));
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(bucketName, path);
            Long fileSize = objectMeta.getSize();
            file =  new OssFile(ossStreamPath.getFileOldName(), path, fileSize,is,ossStreamPath.getSuffix());
            file.setNewName(ossStreamPath.getFileNewName() + "." + ossStreamPath.getSuffix());
            file.setSavePath(ossStreamPath.getRemotePath());
            // 关闭client
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;


    }


    public OssFile uploadOss(OssStreamPath ossStreamPath) {
        OssFile file = new OssFile();
        try {
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            String path = ossStreamPath.getRemotePath() + ossStreamPath.getFileNewName() + "." + ossStreamPath.getSuffix();
            PutObjectResult result = client.putObject(bucketName, path, new ByteArrayInputStream(ossStreamPath.getStream()));
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(bucketName, path);
            Long fileSize = objectMeta.getSize();
            file =  new OssFile(ossStreamPath.getFileOldName(), path, fileSize,ossStreamPath.getSuffix());
            file.setSavePath(ossStreamPath.getRemotePath());
            file.setNewName(ossStreamPath.getFileNewName() + "." + ossStreamPath.getSuffix());
            // 关闭client
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 判断是否为图片文件
     *
     * @param extend
     * @return
     */
    public boolean isImg(String extend) {
        if(extend.indexOf(".") >= 0) {
            extend = extend.replaceAll(".", "");
        }
        String imageSuffix = ossConfig.getImageSuffix();
        return imageSuffix.contains(extend);
    }

    /**
     * 将文件保存于服务器
     *
     * @param request          请求
     * @param filePath         文件路径
     * @param saveFilePathName 保存路径
     * @param saveFileName     保存文件名
     * @param extendes         扩展名
     * @return 保存后的文件信息
     * width
     * height
     * mime
     * fileName
     * fileSize
     * oldName
     * @throws IOException
     */
    public static Map saveFileToServerAppoint(HttpServletRequest request, String filePath, String saveFilePathName,
                                              String saveFileName, String[] extendes) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file =  multipartRequest.getFile(filePath);
        Map map = new HashMap();
        if ((file != null) && (!file.isEmpty())) {
            map = ossUpload(saveFilePathName,file);
        } else {
            map.put("width", Integer.valueOf(0));
            map.put("height", Integer.valueOf(0));
            map.put("mime", "");
            map.put("fileName", "");
            map.put("fileSize", Float.valueOf(0.0F));
            map.put("oldName", "");
        }
        return map;
    }

    public static Map ossUpload(String saveFilePathName,MultipartFile file ){
        Map map = new HashMap();
        OssUploadUtils ossUploadUtils = new OssUploadUtils(ossConfig);
        String ossPathName = "";
        if(null != saveFilePathName && saveFilePathName.indexOf(ossConfig.getUploadFilePath()) > -1){
            saveFilePathName = saveFilePathName.replaceAll("\\\\","/").replaceAll("//","/");
            String[] ps = saveFilePathName.split(ossConfig.getUploadFilePath());
            ossPathName = ps[1];
        }else{
            ossPathName = saveFilePathName;
        }
        Map uploadMap = new HashMap();
        uploadMap.put(file.getOriginalFilename(),file);
        List<OssFile> ossFiles = ossUploadUtils.ossMultipartUpload(uploadMap,ossPathName);
        OssFile ossFile = new OssFile();
        if(CommUtils.isNotNull(ossFiles) && ossFiles.size()>0){
            ossFile = ossFiles.get(0);
        }
        if(CommUtils.isNotNull(ossFile.getImageSize())){
            map.put("width",ossFile.getImageSize().getWidth());
            map.put("height",ossFile.getImageSize().getHeight());
        }
        map.put("mime", ossFile.getSuffix());
        map.put("fileName", ossFile.getNewName());
        map.put("savePath", ossFile.getSavePath());
        map.put("smallPath", ossFile.getSmallPath());
        map.put("middlePath", ossFile.getMiddlePath());
        map.put("bigPath", ossFile.getBigPath());
        map.put("oldName", ossFile.getFileName());
        map.put("fileSize", Float.valueOf(ossFile.getSize()));
        map.put("address", ossFile.getAddress());
        return map;
    }

    public static Map<String,Object> uploadImageByWH(String saveFilePathName,MultipartFile file,List<Map> WHList){
        Map map = new HashMap();
        try{
            //判断传入参数file是否为空
            if(CommUtils.isNotNull(file)){
                BufferedImage image = ImageIO.read(file.getInputStream());
                int width = image.getWidth();
                int height = image.getHeight();
                Boolean isUpload = false;
                //循环判断图片大小是否满足
                if(CommUtils.isNotNull(WHList)){
                    for (Map whMap:WHList) {
                        int setWidth = (int)whMap.get("width");
                        int setHeight = (int)whMap.get("height");
                        if(setWidth == width && setHeight == height ){
                            isUpload = true;
                            break;
                        }
                    }
                }else{
                    isUpload = true;
                }
                if(isUpload){
                    map = ossUpload(saveFilePathName,file);
                    map.put("status",1001);
                    map.put("message","上传成功");
                }else{
                    map.put("status",1002);
                    map.put("message","图片不满足规定宽高");
                }

            }else {
                map.put("status",-1);
                map.put("message","file为空");
            }
        }catch (Exception e){
            log.error("",e);
        }
        return map;
    }

    /**
     * 将文件保存于服务器
     *
     * @param request          请求
     * @param filePath         文件路径
     * @param saveFilePathName 保存路径
     * @param saveFileName     保存文件名
     * @param extendes         扩展名
     * @return 保存后的文件信息
     * width
     * height
     * mime
     * fileName
     * fileSize
     * oldName
     * @throws IOException
     */
    @SuppressWarnings({"unused", "unchecked", "rawtypes"})
    public static Map saveFileToServer(HttpServletRequest request, String filePath, String saveFilePathName,
                                       String saveFileName, String[] extendes) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;//多文件上传，读取文章内容
//		CommonsMultipartFile file1 = (CommonsMultipartFile) multipartRequest.getFile("testImg"); // 单上传 直接获取文件
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String fileName = null;
        Map map = new HashMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();
            if ((file != null) && (!file.isEmpty())) {
                map = ossUpload(saveFilePathName,file);
            } else {
                map.put("width", Integer.valueOf(0));
                map.put("height", Integer.valueOf(0));
                map.put("mime", "");
                map.put("fileName", "");
                map.put("fileSize", Float.valueOf(0.0F));
                map.put("oldName", "");
            }
        }
        return map;
    }

    /**
     * 多个文件上传
     *
     * @param request
     * @param saveFilePathName
     * @param extendes
     * @return Map key = 文件名称
     * @throws IOException
     */
    public static Map saveMultiFileToServer(HttpServletRequest request, String saveFilePathName, String[] extendes) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;//多文件上传，读取文章内容
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Map mapFile = new HashMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            String saveFileName = null;
            Map map = new HashMap();
            String key = entity.getKey();
            MultipartFile file = entity.getValue();
            if ((file != null) && (!file.isEmpty())) {
                map = ossUpload(saveFilePathName,file);
            } else {
                map.put("width", Integer.valueOf(0));
                map.put("height", Integer.valueOf(0));
                map.put("mime", "");
                map.put("fileName", "");
                map.put("fileSize", Float.valueOf(0.0F));
                map.put("oldName", "");
            }
            mapFile.put(key, map);
        }
        return mapFile;
    }

    public static void main(String[] args) throws Exception {
        /*File file = new File("/Users/wangpenglin/Desktop/c3a2aec4gy1fmjzum1pu0j20hs0hndg7.jpg");
        InputStream in = new FileInputStream(file);
        OssConfig ossConfig = new OssConfig("upload",160,160,300,300,1024,1024,"gif|jpg|jpeg|bmp|png|tbi");
        OssUploadUtils u = new OssUploadUtils(ossConfig);

        InputStream input = new FileInputStream(file);

        byte[] byt = new byte[input.available()];
        OssStreamPath p = new OssStreamPath(byt, "upload/", "code", u.getUUID(), "jpg");
        System.out.println(u.uploadOss(p).toString());*/
    }

}
