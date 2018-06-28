package com.joe.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {


    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String filename, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(filename);
        makeDirPath(targetAddr);
        String relativeAddr  =targetAddr +realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailInputStream).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/rr.png")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/o2o/xxx.jpg,
     * 那么 home work o2o 这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        // TODO Auto-generated method stub
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }


    /**
     * 生成随机文件名，当前年月日小时分钟秒+五位随机数
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+rannum;
    }
    /**
     * 获取输入文件流的扩展名
     * @throws IOException
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static void main(String[] args) throws IOException {

        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Thumbnails.of(new File("C:\\Users\\MaQiao\\Desktop\\huangren.png")).size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/rr.png")), 0.25f)
                .outputQuality(0.8f).toFile("C:\\Users\\MaQiao\\Desktop\\rrnew.png");
    }
}
