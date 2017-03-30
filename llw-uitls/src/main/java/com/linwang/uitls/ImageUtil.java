package com.linwang.uitls;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageUtil {
    
    private static final Log log = LogFactory.getLog(ImageUtil.class);
    
    /**
     * 对大图片进行压缩，返回一个指定大小的全图
     * @param srcfile 原始图片File对象
     * @param parentPath 保存路径
     * @param widthdist 图片最大宽度  不考虑图片的高度
     * @return
     */
    public static File reuceImgOnlyWidth(File srcfile, int widthdist) {
        try {
            String filedir = srcfile.getParent();
            String fileNameOld = srcfile.getAbsolutePath();
            String fileName = System.currentTimeMillis() + fileNameOld.substring(fileNameOld.lastIndexOf("."));
            File dir = new File(filedir + File.separator + widthdist);

            if (!dir.exists())
                dir.mkdir();

            BufferedImage bi = javax.imageio.ImageIO.read(srcfile);

            Double width = new Double(bi.getWidth());
            Double height = new Double(bi.getHeight());
            if (width < widthdist) {
                BufferedImage tag = new BufferedImage(width.intValue(), height
                        .intValue(), BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(
                        bi.getScaledInstance(width.intValue(), height
                                .intValue(), Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName);
//              com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec
//                      .createJPEGEncoder(out);
//              encoder.encode(tag);
                ImageIO.write(tag, "jpg", out);
                out.close();
                return new File(dir.getAbsolutePath() + File.separator + fileName);
            } else {
                Double heightdist = new Double(height / width * widthdist);

                BufferedImage tag = new BufferedImage(widthdist, heightdist
                        .intValue(), BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(
                        bi.getScaledInstance(widthdist, heightdist.intValue(),
                                Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName);
//              com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec
//                      .createJPEGEncoder(out);
//              encoder.encode(tag);
                ImageIO.write(tag, "jpg", out);
                out.close();
                return new File(dir.getAbsolutePath() + File.separator + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return null;
        }
    }
    /**
     * 对大图片进行压缩，返回一个指定大小的全图
     * @param srcfile 原始图片File对象
     * @param parentPath 保存路径
     * @param widthdist 图片最大宽度 如果高度大于宽度，widthdist 为图片的高度
     * @return
     */
    public static String reuceImg(File srcfile,String parentPath, int widthdist) {
        try {
            String filedir = srcfile.getParent();
            String fileName = System.currentTimeMillis() + parentPath.substring(parentPath.lastIndexOf("."));
            File dir = new File(filedir + "/" + widthdist);
            
            if (!dir.exists())
                dir.mkdir();
            
            BufferedImage bi = javax.imageio.ImageIO.read(srcfile);
            
            Double width = new Double(bi.getWidth());
            Double height = new Double(bi.getHeight());
            if(width < height){
                width = widthdist*width/height;
                height = new Double(widthdist);
            }
            String path = null;
            if (width < widthdist) {
                BufferedImage tag = new BufferedImage(width.intValue(), height
                        .intValue(), BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(
                        bi.getScaledInstance(width.intValue(), height
                                .intValue(), Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(dir
                        .getAbsolutePath()
                        + "/" + fileName);
//              com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec
//                      .createJPEGEncoder(out);
//              encoder.encode(tag);
                ImageIO.write(tag, "jpg", out);
                path = parentPath.substring(0,parentPath.lastIndexOf("/")) + "/" + widthdist + "/" + fileName;
                out.close();
            } else {
                Double heightdist = new Double(height / width * widthdist);
                
                BufferedImage tag = new BufferedImage(widthdist, heightdist
                        .intValue(), BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(
                        bi.getScaledInstance(widthdist, heightdist.intValue(),
                                Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(dir
                        .getAbsolutePath()
                        + "/" + fileName);
//              com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec
//                      .createJPEGEncoder(out);
//              encoder.encode(tag);
                ImageIO.write(tag, "jpg", out);
                path = parentPath.substring(0,parentPath.lastIndexOf("/")) + "/" + widthdist + "/" + fileName;
                out.close();
            }
            return path;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    /**
     * 对图片进行截取指定大小区域（返回部分图片）
     * @param srcfile 原始图片File对象
     * @param parentPath 保存路径
     * @param x 起始坐标X
     * @param y 其实坐标Y
     * @param width 宽度
     * @param height 高度
     * @return
     */
    public static File reuceImgCutPart(File srcfile, int x, int y, int width, int height) {
        try {
            String filedir = srcfile.getParent();
            String fileNameOld = srcfile.getAbsolutePath();
            String fileName = System.currentTimeMillis() + fileNameOld.substring(fileNameOld.lastIndexOf("."));
            
            
            BufferedImage bi = javax.imageio.ImageIO.read(srcfile);
            int a = bi.getWidth();
            int b = bi.getHeight();
            if(a<(width+x)){
                width=a-x;
            }
            if(b<(height+y)){
                height = b-y;
            }
            BufferedImage tag = bi.getSubimage(x, y, width, height);
            File dir = new File(filedir + File.separator + width + "_" + height);
            if (!dir.exists())
                dir.mkdir();
            FileOutputStream out = new FileOutputStream(dir
                    .getAbsolutePath()
                    + "/" + fileName);
//          com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec
//          .createJPEGEncoder(out);
//          encoder.encode(tag);
            ImageIO.write(tag, "jpg", out);
            out.close();
            
            return new File(dir.getAbsolutePath() + File.separator + fileName);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
