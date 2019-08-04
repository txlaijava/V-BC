package com.shopping.base.utils;


import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面处理公共类
 *
 * @author daoxing
 */
@Log4j2
public class CommUtils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    static int totalFolder;
    static int totalFile;

    /**
     * 取中间随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal random(BigDecimal min, BigDecimal max) {
        Random r = new Random();
        double b = r.nextDouble() * (max.floatValue() - min.floatValue()) + min.floatValue();
        BigDecimal result = new BigDecimal(b);
        if (result.compareTo(min) == -1 || result.compareTo(max) == 1) {
            result = random(min, max);
        }
        return result.setScale(2, 4);
    }

    /**
     * 前两字母小写
     *
     * @param str
     * @return
     */
    public static String first2low(String str) {
        String s = "";
        s = str.substring(0, 1).toLowerCase() + str.substring(1);
        return s;
    }

    /**
     * 前两字母大写
     *
     * @param str
     * @return
     */
    public static String first2upper(String str) {
        String s = "";
        s = str.substring(0, 1).toUpperCase() + str.substring(1);
        return s;
    }

    /**
     * 将多行的字符串，转换成List 一行一个
     *
     * @param s
     * @return
     * @throws IOException
     */
    public static List<String> str2list(String s) throws IOException {
        List list = new ArrayList();
        if ((s != null) && (!s.equals(""))) {
            StringReader fr = new StringReader(s);
            BufferedReader br = new BufferedReader(fr);
            String aline = "";
            while ((aline = br.readLine()) != null) {
                list.add(aline);
            }
        }
        return list;
    }

    /**
     * 将日期yyyy-MM-dd转成Date对象
     *
     * @param s
     * @return
     */
    public static Date formatDate(String s) {
        Date d = null;
        try {
            d = dateFormat.parse(s);
        } catch (Exception localException) {
            log.error(localException);
        }
        return d;
    }

    /**
     * 将特定的格式的时间转换Date
     *
     * @param s      时间字符串
     * @param format 时间格式
     * @return
     */
    public static Date formatDate(String s, String format) {
        Date d = null;
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            d = dFormat.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    /**
     * 将时间转成特定的字符串
     *
     * @param format
     * @param v
     * @return
     */
    public static String formatTime(String format, Object v) {
        if (v == null)
            v = new Date();
        if (v.equals(""))
            return "";
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(v);
    }

    /**
     * 将时间转成yyyy-MM-dd HH:mm:ss字符窜
     *
     * @param v
     * @return
     */
    public static String formatLongDate(Object v) {
        if ((v == null) || (v.equals("")))
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(v);
    }

    /**
     * 将时间转成yyyy-MM-dd HH:mm:ss字符窜
     *
     * @param v
     * @return
     */
    public static String formatUnixTime(Long v) {
        if ((v == null) || (v.equals("")))
            return "";
        Date d = new Date(v * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(d);
    }

    /**
     * 将时间转成yyyy-MM-dd字符窜
     *
     * @param v
     * @return
     */
    public static String formatShortDate(Object v) {
        if (v == null)
            return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(v);
    }

    /**
     * 对URL进行解码
     *
     * @param s
     * @return
     */
    public static String decode(String s) {
        String ret = s;
        try {
            ret = URLDecoder.decode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    /**
     * 对URL进行转码
     *
     * @param s
     * @return
     */
    public static String encode(String s) {
        String ret = s;
        try {
            ret = URLEncoder.encode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    /**
     * 对ISO-8859-1字符串转成特定编码，如UTF-8
     */
    public static String convert(String str, String coding) {
        String newStr = "";
        if (str != null)
            try {
                newStr = new String(str.getBytes("ISO-8859-1"), coding);
            } catch (Exception e) {
                return newStr;
            }
        return newStr;
    }


    public static String getEachDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal
                .getTime());
        String[] dates = yesterday.split("-");
        String realDate = dates[0].substring(2, 4) + "-" + dates[1] + "-"
                + dates[2];
        return realDate.trim();
    }

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal
                .getTime());
        String[] dates = yesterday.split("-");
        String realDate = dates[0] + "-" + dates[1] + "-"
                + dates[2];
        return realDate.trim();
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
                //使用UUID重命名文件
                String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                        .toLowerCase();
                if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
                    saveFileName = UUID.randomUUID().toString() + "." + extend;
                }
                if (saveFileName.lastIndexOf(".") < 0) {
                    saveFileName = saveFileName + "." + extend;
                }
                float fileSize = Float.valueOf((float) file.getSize()).floatValue();
                List errors = new ArrayList();
                boolean flag = true;
                if (extendes != null) {
                    for (String s : extendes) {
                        if (extend.toLowerCase().equals(s))
                            flag = true;
                    }
                }
                if (flag) {
                    File path = new File(saveFilePathName);
                    if (!path.exists()) {
                        path.mkdir();
                    }
                    //保存路径以当前时间新建文件夹
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFilePathName + File.separator
                            + saveFileName));
                    InputStream is = null;
                    try {
                        is = file.getInputStream();
                        int size = (int) fileSize;
                        byte[] buffer = new byte[size];
                        while (is.read(buffer) > 0)
                            out.write(buffer);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                    if (isImg(extend)) {//如果是图片，计算图片高宽信息
                        File img = new File(saveFilePathName + File.separator + saveFileName);
                        try {
                            BufferedImage bis = ImageIO.read(img);
                            int w = bis.getWidth();
                            int h = bis.getHeight();
                            map.put("width", Integer.valueOf(w));
                            map.put("height", Integer.valueOf(h));
                        } catch (Exception localException) {
                        }
                    }
                    map.put("mime", extend);
                    map.put("fileName", saveFileName);
                    map.put("fileSize", Float.valueOf(fileSize));
                    map.put("error", errors);
                    map.put("oldName", file.getOriginalFilename());
                } else {
                    errors.add("不允许的扩展名");
                }
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
                //使用UUID重命名文件
                String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
                if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
                    saveFileName = UUID.randomUUID().toString() + "." + extend;
                }
                if (saveFileName.lastIndexOf(".") < 0) {
                    saveFileName = saveFileName + "." + extend;
                }
                float fileSize = Float.valueOf((float) file.getSize()).floatValue();
                List errors = new ArrayList();
                boolean flag = true;
                if (extendes != null) {
                    for (String s : extendes) {
                        if (extend.toLowerCase().equals(s))
                            flag = true;
                    }
                }
                if (flag) {
                    File path = new File(saveFilePathName);
                    if (!path.exists()) {
                        path.mkdir();
                    }
                    //保存路径以当前时间新建文件夹
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFilePathName + File.separator + saveFileName));
                    InputStream is = null;
                    try {
                        is = file.getInputStream();
                        int size = (int) fileSize;
                        byte[] buffer = new byte[size];
                        while (is.read(buffer) > 0)
                            out.write(buffer);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                    if (isImg(extend)) {//如果是图片，计算图片高宽信息
                        File img = new File(saveFilePathName + File.separator + saveFileName);
                        try {
                            BufferedImage bis = ImageIO.read(img);
                            int w = bis.getWidth();
                            int h = bis.getHeight();
                            map.put("width", Integer.valueOf(w));
                            map.put("height", Integer.valueOf(h));
                        } catch (Exception localException) {
                        }
                    }
                    map.put("mime", extend);
                    map.put("fileName", saveFileName);
                    map.put("fileSize", Float.valueOf(fileSize));
                    map.put("error", errors);
                    map.put("oldName", file.getOriginalFilename());
                    mapFile.put(key, map);
                } else {
                    errors.add("不允许的扩展名");
                }
            } else {
                map.put("width", Integer.valueOf(0));
                map.put("height", Integer.valueOf(0));
                map.put("mime", "");
                map.put("fileName", "");
                map.put("fileSize", Float.valueOf(0.0F));
                map.put("oldName", "");
                mapFile.put(key, map);
            }
        }
        return mapFile;
    }

    /**
     * 判断是否为图片文件
     *
     * @param extend
     * @return
     */
    public static boolean isImg(String extend) {
        boolean ret = false;
        List<String> list = new ArrayList<String>();
        list.add("jpg");
        list.add("jpeg");
        list.add("bmp");
        list.add("gif");
        list.add("png");
        list.add("tif");
        for (String s : list) {
            if (s.equals(extend))
                ret = true;
        }
        return ret;
    }

    public boolean contains(String sourceStr, Long str) {
        if (Utils.isNotEmpty(sourceStr)) {
            return sourceStr.contains(str.toString());
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param folderPath
     * @return
     */
    public static boolean createFolder(String folderPath) {
        boolean ret = true;
        try {
            File myFilePath = new File(folderPath);
            if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
                ret = myFilePath.mkdirs();
                if (!ret)
                    System.out.println("创建文件夹出错");
            }
        } catch (Exception e) {
            System.out.println("创建文件夹出错");
            ret = false;
        }
        return ret;
    }

    public static List toRowChildList(List list, int perNum) {
        List l = new ArrayList();
        if (list == null) {
            return l;
        }

        for (int i = 0; i < list.size(); i += perNum) {
            List cList = new ArrayList();
            for (int j = 0; j < perNum; j++)
                if (i + j < list.size())
                    cList.add(list.get(i + j));
            l.add(cList);
        }
        return l;
    }

    public static List copyList(List list, int begin, int end) {
        List l = new ArrayList();
        if (list == null)
            return l;
        if (end > list.size())
            end = list.size();
        for (int i = begin; i < end; i++) {
            l.add(list.get(i));
        }
        return l;
    }

    public static boolean isNotNull(Object obj) {
        try {
            return (obj != null) && (!obj.toString().equals(""));
        } catch (Exception ex) {
            return false;
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];

                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错 ");
            e.printStackTrace();
        }
    }

    public static boolean deleteFolder(String path) {
        boolean flag = false;
        File file = new File(path);

        if (!file.exists()) {
            return flag;
        }

        if (file.isFile()) {
            return deleteFile(path);
        }
        return deleteDirectory(path);
    }

    public static boolean deleteFile(String path) {
        boolean flag = false;
        File file = new File(path);

        if ((file.isFile()) && (file.exists())) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteDirectory(String path) {
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);

        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;

        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }

        return dirFile.delete();
    }

    /**
     * 分页静态导航条HTML代码
     */
    public static String showPageStaticHtml(String url, int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='" + url + "_1.htm'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='" + url + "_" + (currentPage - 1) + ".htm'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage)
                        s = s + "<a class='this' href='" + url + "_" + i + ".htm'>" + i + "</a> ";
                    else
                        s = s + "<a href='" + url + "_" + i + ".htm'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='" + url + "_" + (currentPage + 1) + ".htm'>下一页</a> ";
                }
                s = s + "<a href='" + url + "_" + pages + ".htm'>末页</a> ";
            }
        }
        return s;
    }


    public static String showPageMobileHtml(String url, String params, int currentPage, int pages) {
        String s = "<ul>";
        if (pages > 0) {
            if (currentPage >= 1) {
                //s = s + "<li><a href='" + url + "?currentPage=1" + params + "'>首页</a></li>";
                if (currentPage > 1) {
                    s = s + "<li><a href='" + url + "?currentPage=" + (currentPage - 1) + params + "'>上一页</a></li>";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage)
                        s = s + "<li class='active'><a href='" + url + "?currentPage=" + i + params + "'>" + i + "</a></li>";
                    else
                        s = s + "<li><a href='" + url + "?currentPage=" + i + params + "'>" + i + "</a></li>";
                    i++;
                }
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<li><a href='" + url + "?currentPage=" + (currentPage + 1) + params + "'>下一页</a></li>";
                }
                //s = s + "<li><a href='" + url + "?currentPage=" + pages + params + "'>末页</a></li>";
            }
        }
        s += "</ul>";
        return s;
    }

    /**
     * 分页动态导航条HTML代码，参参数
     */
    public static String showPageHtml(String url, String params, int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='" + url + "?currentPage=1" + params + "'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage - 1) + params + "'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage)
                        s = s + "<a class='this' href='" + url + "?currentPage=" + i + params + "'>" + i + "</a> ";
                    else
                        s = s + "<a href='" + url + "?currentPage=" + i + params + "'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage + 1) + params + "'>下一页</a> ";
                }
                s = s + "<a href='" + url + "?currentPage=" + pages + params + "'>末页</a> ";
            }
        }

        return s;
    }

    /**
     * 新版分页
     *
     * @param currentPage
     * @param pages
     * @return
     */
    public static String showPageFormHtml(int currentPage, int pages) {
        StringBuffer s = new StringBuffer();
        s.append("<ul>");
        if (pages > 0) {
            if (currentPage >= 1) {
                if (currentPage > 1) {
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage(1)'><span>首页</span></a></li>");
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage(" + (currentPage - 1) + ")'><span>上一页</span></a></li>");
                } else {
                    s.append("<li><span>首页</span></li>");
                    s.append("<li><span>上一页</span></li>");
                }
            }
            if (currentPage > 7) { //大于第七页 加省略号
                s.append("<li><span>...</span></li>");
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    //log.info(i+"  currentPage "+currentPage);
                    if (i == currentPage)
                        s.append("<li><span class=\"currentpage\">" + i + "</span></li>");
                    else
                        s.append("<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage(" + i + ")'><span>" + i + "</span></a></li>");
                    i++;
                }
            }
            int end = (currentPage + 5) < pages ? (currentPage + 5) : pages;
            if (end < pages) {
                s.append("<li><span>...</span></li>");
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage(" + (currentPage + 1) + ")'><span>下一页</span></a></li>");
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage(" + pages + ")'><span>末页</span></a></li>");
                } else {
                    s.append("<li><span>下一页</span></li>");
                    s.append("<li><span>末页</span></li>");
                }
            }
            //大于第二页出现跳转框
            if (pages > 2) {
                s.append("<li><span style='padding: 0;'><input type='text' name='inputPage' class='currentPage' id='inputPage' value='" + currentPage + "'></span></li> " +
                        "<li><a class='demo' href='javascript:void(0);' onclick='return gotoPage($(\"#inputPage\").val())'><span>跳转</span></a></li>");
            }
        }
        s.append("</ul>");
        return s.toString();
    }

    public static String showPageFormHtmlOld(int currentPage, int pages) {
        String s = "";
        //log.info("my this  currentPage "+currentPage);
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='javascript:void(0);' onclick='return gotoPage(1)'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + (currentPage - 1)
                            + ")'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    //log.info(i+"  currentPage "+currentPage);
                    if (i == currentPage)
                        s = s + "<a class='this' href='javascript:void(0);' onclick='return gotoPage(" + i + ")'>" + i
                                + "</a> ";
                    else
                        s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + i + ")'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + (currentPage + 1)
                            + ")'>下一页</a> ";
                }
                s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + pages + ")'>末页</a> " +
                        " <input type='text' name='inputPage' class='currentPage' id='inputPage'> <a href='javascript:void(0);'  onclick='return gotoPage($(\"#inputPage\").val())'>跳转</a>";
            }
        }
        return s;
    }


    public static String showPageAjax(int currentPage, int pages) {
        String s = "";
        //log.info("my this  currentPage "+currentPage);
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='javascript:void(0);' cur_page = '1'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='javascript:void(0);' cur_page = '" + (currentPage - 1) + "'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    //log.info(i+"  currentPage "+currentPage);
                    if (i == currentPage)
                        s = s + "<a class='this' href='javascript:void(0);' cur_page = '" + i + "'>" + i
                                + "</a> ";
                    else
                        s = s + "<a href='javascript:void(0);' cur_page='" + i + "'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='javascript:void(0);' cur_page='" + (currentPage + 1) + "'>下一页</a> ";
                }
                s = s + "<a href='javascript:void(0);' cur_page='" + pages + "'>末页</a> ";
            }
        }
        return s;
    }

    public static String showPageAjaxHtml(String url, String params, int currentPage, int pages) {
        StringBuffer s = new StringBuffer();
        s.append("<ul>");
        if (pages > 0) {
            String address = url + "?1=1" + params;
            if (currentPage >= 1) {
                if (currentPage > 1) {
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\",1,this)'><span>首页</span></a></li>");
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\","
                            + (currentPage - 1) + ",this)'><span>上一页</span></a></li>");
                } else {
                    s.append("<li><span>首页</span></li>");
                    s.append("<li><span>上一页</span></li>");
                }
            }
            if (currentPage > 7) { //大于第七页 加省略号
                s.append("<li><span>...</span></li>");
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage)
                        s.append("<li><span class=\"currentpage\">" + i + "</span></li>");
                    else
                        s.append("<li><a class='demo' href='javascript:void(0);'  onclick='return ajaxPage(\"" + address + "\"," + i + ",this)'><span>" + i + "</span></a></li>");
                    i++;
                }
            }
            int end = (currentPage + 5) < pages ? (currentPage + 5) : pages;
            if (end < pages) {
                s.append("<li><span>...</span></li>");
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + (currentPage + 1) + ",this)'><span>下一页</span></a></li>");
                    s.append("<li><a class='demo' href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + pages + ",this)'><span>末页</span></a></li>");
                } else {
                    s.append("<li><span>下一页</span></li>");
                    s.append("<li><span>末页</span></li>");
                }
            }
        }
        s.append("</ul>");
        return s.toString();
    }


    public static char randomChar() {
        char[] chars = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j',
                'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't',
                'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z'};
        int index = (int) (Math.random() * 52.0D) - 1;
        if (index < 0) {
            index = 0;
        }
        return chars[index];
    }

    public static String[] splitByChar(String s, String c) {
        if (Utils.isEmpty(s)) {
            return null;
        }
        String[] list = s.split(c);
        return list;
    }

    public static List splitByCharOnList(String s, String c) {
        if (Utils.isEmpty(s)) {
            return null;
        }
        return Arrays.asList(s.split(c));
    }

    public static Object requestByParam(HttpServletRequest request, String param) {
        if (!request.getParameter(param).equals("")) {
            return request.getParameter(param);
        }
        return null;
    }

    /**
     * 截取字符，截取出默认填充省略号
     *
     * @param s
     * @param maxLength
     * @return
     */
    public static String substring(String s, int maxLength) {
        if (!StringUtils.hasLength(s))
            return s;
        if (s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength) + "...";
    }

    public static String subStringOnEncode(String s, int maxLength) {
        if (!StringUtils.hasLength(s))
            return s;
        if (s.length() <= maxLength) {
            return s;
        }
        return Utils.stringToEncode(s.substring(0, maxLength) + "...");
    }

    /**
     * 从指定字符开始截取至最后
     * s = abcd  from = b  return = cd
     *
     * @param s
     * @param from
     * @return
     */
    public static String substringfrom(String s, String from) {
        if (s.indexOf(from) < 0)
            return "";
        return s.substring(s.indexOf(from) + from.length());
    }

    public static int null2Int(Object s) {
        int v = 0;
        if (s != null)
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException) {
            }
        return v;
    }

    public static float null2Float(Object s) {
        float v = 0.0F;
        if (s != null)
            try {
                v = Float.parseFloat(s.toString());
            } catch (Exception localException) {
            }
        return v;
    }

    public static double null2Double(Object s) {
        double v = 0.0D;
        if (s != null)
            try {
                v = Double.parseDouble(null2String(s));
            } catch (Exception localException) {
            }
        return v;
    }

    public static boolean null2Boolean(Object s) {
        boolean v = false;
        if (s != null)
            try {
                v = Boolean.parseBoolean(s.toString());
            } catch (Exception localException) {
            }
        return v;
    }

    /**
     * 转换ID编号
     *
     * @param s
     * @param num 要加几个0
     * @return
     */
    public static int changeIdStr(Object s, int num) {
        String i_s = "1";
        for (int j = 0; j < num; j++) {
            i_s = i_s + "0";
        }
        int i = Integer.parseInt(i_s);
        if (s != null) {
            if (Integer.parseInt(s.toString()) > i) {
                return Integer.parseInt(s.toString());
            } else {
                return i + Integer.parseInt(s.toString());
            }
        } else {
            return 0;
        }
    }

    public static String null2String(Object s) {
        return s == null ? "" : s.toString().trim();
    }

    public static BigDecimal null2BigDecimal(BigDecimal s) {
        if (s != null)
            return s;
        else {
            return new BigDecimal(0);
        }
    }

    public static BigDecimal null2BigDecimal(Object s) {
        if (s != null)
            return new BigDecimal(s.toString());
        else {
            return new BigDecimal(0);
        }
    }

    public static double null2DoubleBigDecimal(BigDecimal s) {
        double v = 0;
        if (s != null) {
            try {
                v = s.doubleValue();
            } catch (Exception localException) {
            }
        } else {
            v = 0;
        }
        return v;
    }

    public static Long null2Long(Object s) {
        Long v = Long.valueOf(-1L);
        if (s != null)
            try {
                v = Long.valueOf(Long.parseLong(s.toString()));
            } catch (Exception localException) {
            }
        return v;
    }

    public static String getTimeInfo(long time) {
        int hour = (int) time / 3600000;
        long balance = time - hour * 1000 * 60 * 60;
        int minute = (int) balance / 60000;
        balance -= minute * 1000 * 60;
        int seconds = (int) balance / 1000;
        String ret = "";
        if (hour > 0)
            ret = ret + hour + "小时";
        if (minute > 0)
            ret = ret + minute + "分";
        else if ((minute <= 0) && (seconds > 0))
            ret = ret + "零";
        if (seconds > 0)
            ret = ret + seconds + "秒";
        return ret;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        //对于双网卡IP的处理
        if (ip.indexOf(",") > 0) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        // 判断ip地址是否与正则表达式匹配
        if (ip.matches(regex)) {
            return ip;
        } else {
            return "127.0.0.1";
        }
    }

    public static int indexOf(String s, String sub) {
        return s.trim().indexOf(sub.trim());
    }

    /**
     * 计算时间差
     *
     * @param begin
     * @param end
     * @return
     */
    public static Map cal_time_space(Date begin, Date end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long l = end.getTime() - begin.getTime();
        long day = l / 86400000L;
        long hour = l / 3600000L - day * 24L;
        long min = l / 60000L - day * 24L * 60L - hour * 60L;
        long second = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        Map map = new HashMap();
        map.put("day", Long.valueOf(day));
        map.put("hour", Long.valueOf(hour));
        map.put("min", Long.valueOf(min));
        map.put("second", Long.valueOf(second));
        return map;
    }


    /**
     * 计算时间差
     *
     * @param begin
     * @return
     */
    public static long time_stamp(Date begin) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = 0;
        try {
            /*Date beg = df.parse(begin);*/
            Date end = new Date();
            l = (begin.getTime() / 1000) - (end.getTime() / 1000);
        } catch (Exception e) {
            log.error("计算时间戳错误！", e);
        }
        return l;
    }

    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return
     */
    public static final String randomString(int length) {
        char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                .toCharArray();
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static final String randomInt(int length) {
        if (length < 1) {
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters = "0123456789".toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }

    /**
     * 两个时间间隔天数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getDateDistance(String time1, String time2) {
        long quot = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000L / 60L / 60L / 24L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return quot;
    }

    /**
     * 获取相隔天数后的日期
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(d);
        c.add(Calendar.DATE, day);
        Date d2 = c.getTime();
        String s = df.format(d2);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取相隔分后的日期
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateMinute(Date d, int day) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.setTime(d);
        c.add(Calendar.MINUTE, day);
        Date d2 = c.getTime();
        String s = df.format(d2);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static double div(Object a, Object b) {
        double ret = 0.0D;
        if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
            BigDecimal e = new BigDecimal(null2String(a));
            BigDecimal f = new BigDecimal(null2String(b));
            if (null2Double(f) > 0.0D)
                ret = e.divide(f, 3, 1).doubleValue();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double subtract(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.subtract(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double add(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.add(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double mul(Object a, Object b) {
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        double ret = e.multiply(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double formatMoney(Object money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(money)).doubleValue();
    }

    public static int M2byte(float m) {
        float a = m * 1024.0F * 1024.0F;
        return (int) a;
    }

    public static boolean convertIntToBoolean(int intValue) {
        return intValue != 0;
    }

    /**
     * 获取URL
     *
     * @param request
     * @return
     */
    public static String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) {
            scheme = request.getHeader("x-forwarded-proto");
        }
        if (scheme == null) {
            scheme = request.getScheme().toLowerCase();
        }
        String url = (scheme.startsWith("https") ? "https://" : "http://") + request.getServerName();
        if (null2Int(Integer.valueOf(request.getServerPort())) != 80 && null2Int(Integer.valueOf(request.getServerPort())) != 443)
            url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
        else {
            url = url + contextPath;
        }
        return url;
    }

    public static int parseDate(String type, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (type.equals("y")) {
            return cal.get(1);
        }
        if (type.equals("M")) {
            return cal.get(2) + 1;
        }
        if (type.equals("d")) {
            return cal.get(5);
        }
        if (type.equals("H")) {
            return cal.get(11);
        }
        if (type.equals("m")) {
            return cal.get(12);
        }
        if (type.equals("s")) {
            return cal.get(13);
        }
        return 0;
    }

    public static int[] readImgWH(String imgurl) {
        boolean b = false;
        try {
            URL url = new URL(imgurl);

            BufferedInputStream bis = new BufferedInputStream(url.openStream());

            byte[] bytes = new byte[100];

            OutputStream bos = new FileOutputStream(new File("C:\\thetempimg.gif"));
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();

            b = true;
        } catch (Exception e) {
            b = false;
        }
        int[] a = new int[2];
        if (b) {
            File file = new File("C:\\thetempimg.gif");
            BufferedImage bi = null;
            boolean imgwrong = false;
            try {
                bi = ImageIO.read(file);
                try {
                    int i = bi.getType();
                    imgwrong = true;
                } catch (Exception e) {
                    imgwrong = false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (imgwrong) {
                a[0] = bi.getWidth();
                a[1] = bi.getHeight();
            } else {
                a = null;
            }

            file.delete();
        } else {
            a = null;
        }
        return a;
    }
    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static int splitLength(String s, String c) {
        int v = 0;
        if (!s.trim().equals("")) {
            v = s.split(c).length;
        }
        return v;
    }

    public static double fileSize(File folder) {
        totalFolder += 1;

        long foldersize = 0L;
        File[] filelist = folder.listFiles();

        if (filelist == null) {
            return 0.0;
        }
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
                foldersize = (long) (foldersize + fileSize(filelist[i]));
            } else {
                totalFile += 1;
                foldersize += filelist[i].length();
            }
        }
        return div(Long.valueOf(foldersize), Integer.valueOf(1024));
    }

    public static int fileCount(File file) {
        if (file == null) {
            return 0;
        }
        if (!file.isDirectory()) {
            return 1;
        }
        int fileCount = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                fileCount++;
            } else if (f.isDirectory()) {
                fileCount++;
                fileCount += fileCount(file);
            }
        }
        return fileCount;
    }

    public static String get_all_url(HttpServletRequest request) {
        String query_url = request.getRequestURI();
        if ((request.getQueryString() != null) && (!request.getQueryString().equals(""))) {
            query_url = query_url + "?" + request.getQueryString();
        }
        return query_url;
    }

    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6)
            return null;
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    public static Set<Integer> randomInt(int a, int length) {
        Set list = new TreeSet();
        int size = length;
        if (length > a) {
            size = a;
        }
        while (list.size() < size) {
            Random random = new Random();
            int b = random.nextInt(a);
            list.add(Integer.valueOf(b));
        }
        return list;
    }

    public static int formatDoubleToInt(Object obj) {
        if (Utils.isNotEmpty(obj)) {
            int i = new Double(obj.toString()).intValue();
            return i;
        } else {
            return 0;
        }
    }

    public static Double formatDouble(Object obj, int len) {
        Double ret = Double.valueOf(0.0D);
        String format = "0.0";
        for (int i = 1; i < len; i++) {
            format = format + "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return Double.valueOf(df.format(obj));
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
                || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
                || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
                || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
                || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
                || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0.0F;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (Character.isLetterOrDigit(c))
                continue;
            if (!isChinese(c)) {
                count += 1.0F;
                System.out.print(c);
            }
        }

        float result = count / chLength;

        return result > 0.4D;
    }

    public static String trimSpaces(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) && (Integer.parseInt(s[1]) < 255) && (Integer.parseInt(s[2]) < 255)
                    && (Integer.parseInt(s[3]) < 255))
                b = true;
        }
        return b;
    }

    /**
     * @param request
     * @return
     */
    public static String generic_domain(HttpServletRequest request) {
        String system_domain = "localhost";
        String serverName = request.getServerName();
        if (isIp(serverName))
            system_domain = serverName;
        else {
            system_domain = serverName.substring(serverName.indexOf(".") + 1);
        }

        return system_domain;
    }

    public static String getMbSpecialImageUrl(String web_path, String url) {
        if (isNotNull(url) && url.indexOf("http") >= 0) {
            return url;
        } else if (isNotNull(url)) {
            return web_path + File.separator + "upload" + url;
        } else {
            return web_path + "/upload/wap/default_goods_image_240.gif";
        }
    }

    /**
     * 字符转Long
     *
     * @param str
     * @return
     * @autor TanxiaoLong
     */
    public static Long StrToLong(String str) {
        try {
            if (str != null && !"".equals(str)) {
                return Long.parseLong(str);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 字符不为空
     *
     * @param str
     * @return
     * @autor Tanxiaolong
     */
    public static boolean StrIsNotEmpty(String str) {
        if (!"".equals(str) && str != null && !"null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证表单的有效性
     *
     * @param request
     * @param formToken
     * @return
     * @throws Exception
     */
    public static boolean isEffective(HttpServletRequest request, String formToken) throws Exception {
        String token = request.getParameter("from_token");
        if (null2String(token).equals("")) {
            return false;
        } else if (token.equals(formToken)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化日期
     *
     * @param date  java.utils.Date类型的时间
     * @param style DateStyleEnum 样式
     * @return 返回时间字符串
     */
    public static final String formatDate(Date date, String style) {
        SimpleDateFormat fmt = new SimpleDateFormat(style);
        return fmt.format(date);
    }

    /**
     * 和系统时间对比
     *
     * @param time
     */
    public static boolean compareNow(Date time) {
        if (Utils.isNotEmpty(time)) {
            return time.before(new Date());
        } else {
            return false;
        }
    }

    /*
     * 比较时间
     */
    public static int compareDate(Date time, int type) throws ParseException {
        SimpleDateFormat sdf = null;
        Date currDate = new Date();
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (type == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        String currDateStr = sdf.format(currDate);
        currDate = sdf.parse(currDateStr);
        String timeStr = sdf.format(time);
        time = sdf.parse(timeStr);

        return time.compareTo(currDate);
    }

    public static int compareDateStr(String timeStr, int type) throws ParseException {
        SimpleDateFormat sdf = null;
        Date currDate = new Date();
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (type == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 3) {
            sdf = new SimpleDateFormat("HH:mm:ss");
        }
        Date time = sdf.parse(timeStr);
        String currDateStr = sdf.format(currDate);
        currDate = sdf.parse(currDateStr);
        return time.compareTo(currDate);
    }


    /**
     * 读取流数据
     *
     * @param i
     * @return
     */
    public static String inputToStr(InputStream i) {
        String line = null;
        if (Utils.isNotEmpty(i)) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(i, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                line = buffer.toString();
                i.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是长整型
     */
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字(包括".")
     */
    public static boolean isNumber(String value) {

        return isInteger(value) || isDouble(value);

    }

    public static BigDecimal removePoint(String number) {
        if (Utils.isEmpty(number)) {
            return new BigDecimal(0);
        } else {
            return new BigDecimal(number).setScale(0, BigDecimal.ROUND_DOWN);
        }
    }

    /**
     * 对比参数
     *
     * @param comObj
     * @param params
     * @return
     */
    public static boolean isin(Object comObj, Object... params) {
        for (Object obj : params) {
            if (comObj.toString().equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取年龄
     *
     * @param dateOfBirth
     * @return
     */
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

    /**
     * 身份证*处理
     *
     * @param idCard
     * @return
     */
    public static String formatIdCard(String idCard) {
        if (CommUtils.isNotNull(idCard)) {
            if (idCard.length() == 15) {
                return idCard.substring(0, 1) + "*************" + idCard.substring(idCard.length() - 1);
            } else if (idCard.length() == 18) {
                return idCard.substring(0, 1) + "****************" + idCard.substring(idCard.length() - 1);
            }

        }
        return "";
    }

    /**
     * 身份证*处理
     *
     * @param bankCard
     * @return
     */
    public static String formatBankCard(String bankCard) {
        if (CommUtils.isNotNull(bankCard)) {
            return bankCard.substring(bankCard.length() - 4);

        }
        return "";
    }


    /**
     * 手机号码*处理
     *
     * @param tel
     * @return
     */
    public static String formatTel(String tel) {
        if (CommUtils.isNotNull(tel)) {
            return tel.replace(tel.substring(3, 7), "****");
        }
        return "";
    }

    /**
     * 账号类*号处理
     *
     * @param accId
     * @return
     */
    public static String formatAccId(String accId) {
        if (CommUtils.isNotNull(accId)) {
            String temp = accId.substring(1, accId.length() - 1);
            String _t = "";
            for (int i = 0; i < temp.length(); i++) {
                _t = _t + "*";
            }
            return accId.replace(temp, _t);
        }
        return "";
    }

    /**
     * 获取银行卡后四位
     *
     * @param bankno
     * @return
     */
    public static String getBankFour(String bankno) {
        if (CommUtils.isNotNull(bankno)) {
            String temp = bankno.substring(bankno.length() - 4);
            return temp;
        }
        return "";
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(
                Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 获取图片颜色
     *
     * @param logo_path
     * @return
     */
    public static String getPngColor(String logo_path) {
        if (CommUtils.isNotNull(logo_path)) {
            String color = "";
            int[] rgb = new int[3];
            File file = new File(logo_path);
            if (!file.exists()) {
                return "";
            }
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int width = bi.getWidth();
            int height = bi.getHeight();
            int minx = bi.getMinX();
            int miny = bi.getMinY();
            //System.out.println("width="+width+",height="+height+".");
            //System.out.println("minx="+minx+",miniy="+miny+".");
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    int pixel = bi.getRGB(i, j);
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    color = "#" + toBrowserHexValue(rgb[0]) + toBrowserHexValue(rgb[1]) + toBrowserHexValue(rgb[2]);

                    if (!color.equals("#FFFFFF")) {
                        break;
                    }
                }
                if (!color.equals("#FFFFFF")) {
                    break;
                }
            }
            System.out.println(color);
            return color;
        } else {
            return "";
        }
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static String encoding(String src) {
        if (src == null)
            return "";
        StringBuilder result = new StringBuilder();
        if (src != null) {
            src = src.trim();
            for (int pos = 0; pos < src.length(); pos++) {
                switch (src.charAt(pos)) {
                    case '&':
                        result.append("");
                        break;
                    case 'n':
                        result.append("");
                        break;
                    case 'b':
                        result.append("");
                        break;
                    case 's':
                        result.append("");
                        break;
                    case 'p':
                        result.append("");
                        break;
                    case ';':
                        result.append("");
                        break;
                    case '<':
                        result.append("");
                        break;
                    case '>':
                        result.append("");
                        break;
                    case 'r':
                        result.append("");
                        break;
                    case '/':
                        result.append("");
                        break;
                    default:
                        result.append(src.charAt(pos));
                        break;
                }
            }
        }
        return result.toString();
    }

    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     *
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * IpUtils工具类方法
     * 获取真实的ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddrV2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isNotNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (isNotNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * Gson 转换
     */
    private static Gson gson = null;

    static {

        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 将json格式转换成list对象    测试里面装的是一个Object对象  List<Map<String,Object>>
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> objList = null;
        if (gson != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
            }.getType();
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    public static String getPayChannelZh(String payChannel) {
        String channel = "未知";
        switch (payChannel) {
            case "alipay_wap" :
                channel = "支付宝网页在线支付";
                break;
            case "alipay" :
                channel = "支付宝APP支付";
                break;
            case "wx_pub" :
                channel = "微信公众号支付";
                break;
            case "wx" :
                channel = "微信APP支付";
                break;
            case "hb" :
                channel = "红宝支付";
                break;
            case "wxapp" :
                channel = "微信小程序支付";
                break;
            case "vip_card" :
                channel = "会员卡支付";
                break;
            default:
                break;
        }
        return channel;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        //赤道半径
        double EARTH_RADIUS = 6378.137;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    public static String getDateSx(){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "早上好，";
        } else if (hour >= 8 && hour < 11) {
            return "上午好，";
        } else if (hour >= 11 && hour < 13) {
            return "中午好，";
        } else if (hour >= 13 && hour < 18) {
            return "下午好，";
        } else {
            return "晚上好，";
        }
    }


    /**
     * 根据给的时间获取此月的最后一天
     * 传入参数为String，可传入：1、"201807"；2、"2018-07-01"
     * @param date
     * 			账期
     * @return String
     * 			当月的最后一天
     */
    public static String getLastDayByMonth(String date) {
        if (date.contains("-")) {
            date = date.replaceAll("-", "");
        } else if (date.length() < 6 || date.length() > 10) {
            System.out.println("日期错误！");
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());

    }
}
