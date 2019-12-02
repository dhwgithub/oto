package com.henu.oto.util;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    /**
     * 返回项目图片根路径
     * @return
     */
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "E:/projectOto/image";
        }else{
            basePath = "/home/dhw/image";
        }

        basePath = basePath.replace("/", separator);
        return basePath;
    }

    /**
     * 返回项目图片子路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/" +  shopId + "/";
        return imagePath.replace("/", separator);
    }
}
