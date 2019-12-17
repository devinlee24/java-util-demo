package com.devinlee.javautildemo.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类(生成、识别)
 * 基于Google Zxing
 * @author devinlee
 */
public class QRCodeUtil {
    private static final int width = 300;// 默认二维码宽度
    private static final int height = 300;// 默认二维码高度
    private static final String format = "png";// 默认二维码文件格式
    private static final Map<EncodeHintType, Object> hints = new HashMap();// 二维码参数

    static {
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, 2);// 二维码与图片边距
    }

    /**
     * 返回一个 BufferedImage 对象
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     */
    public static BufferedImage toBufferedImage(String content, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 将二维码图片输出到一个流中
     *
     * @param content 二维码内容
     * @param stream  输出流
     * @param width   宽
     * @param height  高
     */
    public static void writeToStream(String content, OutputStream stream, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
    }

    /**
     * 生成二维码图片文件
     *
     * @param content 二维码内容
     * @param path    文件保存路径
     * @param width   宽
     * @param height  高
     */
    public static void createQRCode(String content, String path, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        //toPath() 方法由 jdk1.7 及以上提供
        MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());
    }

    /**
     * 读取二维码信息
     * @param filePath 二维码图片路径
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readQRCode(String filePath)
            throws FileNotFoundException, IOException, NotFoundException {
        return new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath))))),new HashMap<DecodeHintType, ErrorCorrectionLevel>()).getText();
    }

    public static void main(String[] args) throws Exception {

        //生成二维码图片并输出到一个流中
        OutputStream outputStream = new FileOutputStream(new File("/Users/devinlee/Pictures/qunar.png"));
        QRCodeUtil.writeToStream("https://www.baidu.com", outputStream, width, height);

        //生成二维码
        QRCodeUtil.createQRCode("https://www.jd.com", "/Users/devinlee/Pictures/qunar2.png", width, height);

        //读取二维码
        System.out.println(QRCodeUtil.readQRCode("/Users/devinlee/Pictures/qunar2.png"));
    }
}
