package com.henu.oto.util;

import com.henu.oto.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();


//	/**
//	 * demo
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception{
//		Thumbnails.of(new File("E:/test/t2.png"))
//				.size(200, 200)
//				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/t1.png")), 0.25f)
//				.outputQuality(0.8f)
//				.toFile("E:/test/t2_new.png");
//	}

	/**
	 * 缩略图处理
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName(); // 获取随机生成名
		String extension = getFileExtension(thumbnail.getImageName()); // 获取扩展名
		makeDirPath(targetAddr); // 创建目录文件
		String relativeAddr = targetAddr + realFileName + extension;

		logger.debug("current relativeAddr is: " + realFileName);

		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

		logger.debug("current complete adr is: " + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("basePath is: " + basePath);

		try {
			File waterMarkFile = new File(basePath + "/watermark.jpg");
			// 图片处理、加水印
			Thumbnails.of(thumbnail.getImage())
					.size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMarkFile), 0.25f)
					.outputQuality(0.8f)
					.toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("上传图片失败：" + e.toString());
		}
		return relativeAddr;
	}

	/**
	 * 缩略图处理
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;

		logger.debug("current relativeAddr is: " + realFileName);

		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

		logger.debug("current complete adr is: " + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("basePath is: " + basePath);

		try {
			File waterMarkFile = new File(basePath + "/watermark.jpg");
			Thumbnails.of(thumbnail.getImage())
					.size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMarkFile), 0.25f)
					.outputQuality(0.9f)
					.toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("上传图片失败：" + e.toString());
		}
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及的目录，即/home/work/dhw/xx.jpg
	 * 那么home work dhw 这三个文件夹都得自动创建
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if( !dirPath.exists()){
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日时分秒+5位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * storePath是文件的路径还是目录的路径
	 * 如果是文件路径则删除该文件
	 * 如果是目录的路径则删除该目录下所有的文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath){
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if(fileOrPath.exists()){
			if(fileOrPath.isDirectory()){
				File[] files = fileOrPath.listFiles();
				for (int i=0; i<files.length; i++){
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
