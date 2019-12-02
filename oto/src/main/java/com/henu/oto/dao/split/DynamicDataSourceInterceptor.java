package com.henu.oto.dao.split;

import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.ResourceHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 拦截器，设置路由
 */
@Intercepts({@Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResourceHolder.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static final String REGEX = ".*insert\\u0020.*|.*.delete\\u0020.*|.*update\\u0020.*";
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    /**
     * 遇到什么去拦截
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizedActive = TransactionSynchronizationManager.isSynchronizationActive();
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        MappedStatement mappedStatement = null;
        if (synchronizedActive != true){ //非事务
            Object[] objects = invocation.getArgs();
            mappedStatement = (MappedStatement)objects[0];

            //读方法
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)){
                // selectKey为自增id查询主键(SELECT LAST_INSERT_ID())方法，使用主库
                if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                }else {
                    BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]]", " ");
                    if (sql.matches(REGEX)){
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    }else {
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE; //从库不能写数据
                    }
                }
            }
        }else {
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Stratery, SqlCommanType [{}]...", mappedStatement.getId(),
                lookupKey, mappedStatement.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    /**
     * 返回封装的（代理）对象
     * Executor支持所有的增删改查
     * @param target
     * @return1
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(target, this);
        }else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
