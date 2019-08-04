package com.shopping.base.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shopping.base.foundation.util.ImageUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.UUID;


public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /**
     * 绘制二维码
     *
     * @param content      二维码内容
     * @param imgPath      Logo
     * @param needCompress 压缩Logo
     * @param qrcodeSize   二维码大小
     * @param uploadPath   上传路径
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String imgPath,
                                            boolean needCompress, Integer qrcodeSize,
                                            String uploadPath) throws Exception {
        if (Utils.isEmpty(qrcodeSize)) {
            qrcodeSize = QRCODE_SIZE;
        }
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress, qrcodeSize, uploadPath);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress, Integer qrcode_size, String uploadPath) throws Exception {
        if (Utils.isEmpty(qrcode_size)) {
            qrcode_size = QRCODE_SIZE;
        }
        File file = new File(imgPath);
        //如果是线上文件
        if (imgPath.startsWith("http")) {
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            String fileName = UUID.randomUUID().toString() + ".png";
            ImageUtils.download(imgPath, uploadPath + "/" + fileName);
            file = new File(uploadPath + "/" + fileName);
        } else {
            file = new File(imgPath);
        }
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(file);
        if(src == null){
            return;
        }
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcode_size - width) / 2;
        int y = (qrcode_size - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }


    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress, Integer qrcode_size) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress, qrcode_size, "");
        ImageIO.write(image, FORMAT_NAME, output);
    }


    public static void encode(String content, OutputStream output, Integer qrcode_size)
            throws Exception {
        QRCodeUtil.encode(content, null, output, false, qrcode_size);
    }

}