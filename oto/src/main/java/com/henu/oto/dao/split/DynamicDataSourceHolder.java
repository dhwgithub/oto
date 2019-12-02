package com.henu.oto.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>(); //保证线程安全
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    /**
     * 获取线程的DBType
     */
    public static String getDbType(){
        String db = contextHolder.get();
        if(db == null){
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 设置线程的DBType
     * @param str
     */
    public static void setDbType(String str){
        logger.debug("所使用的数据源为：" + str);
        contextHolder.set(str);
    }

    /**
     * 清理连接类型
     */
    public static void clearDBType(){
        contextHolder.remove();
    }
}
