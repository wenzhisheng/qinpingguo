package com.common.interceptor;

import com.alibaba.druid.util.JdbcUtils;
import com.common.model.base.PageResult;
import com.common.model.base.PageVO;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @Author A jun
 * @ClassName com.com.member.member.interceptor
 * @Description 分页拦截器
 * @Date 2018/3/26 0026 14:27
 * @Version 1.0
 */
@Component
@Intercepts({
        @Signature(method = "query", type = Executor.class, args =
                {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
            })
public class PageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement pageStatement = (MappedStatement) invocation.getArgs()[0];//当前查询对应的Mapper
        Object params = invocation.getArgs()[1];
        PageVO pageVO = null;//分页参数
        //校验是否是分页
        if (null != params) {
            Map<String, ?> paramsObject = (Map<String, ?>) params;
            Object pageVOtemp = null;
            Set<String> keys =  paramsObject.keySet();
            for(String key:keys){
                pageVOtemp = paramsObject.get(key);
                if (null != pageVOtemp && pageVOtemp instanceof PageVO) {
                    pageVO = (PageVO) pageVOtemp;
                }
            }
        }
        //检查是否是分页sql
        if (null == pageVO) {
            //不是分页sql不做分页执行
            return invocation.proceed();
        }
        //获取分页COUNT
        Integer count = findSqlCount(pageStatement, params);
        //分页结果集
        List<?> result = (List<?>) invocation.proceed();
        //构建分页返回对象
        PageResult<?> pageResult = new PageResult<>(pageVO.getPageNo(), pageVO.getPageSize(), count, result);
        List<PageResult> resultReturn = new ArrayList<PageResult>();
        resultReturn.add(pageResult);
        return resultReturn;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Integer findSqlCount(MappedStatement pageStatement, Object params) {
        String countId = pageStatement.getId() + "Count";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取COUNT SQL Mapped
            BoundSql countBoundSqlTemp = pageStatement.getConfiguration().getMappedStatement(countId).getBoundSql(params);
            //获取COUNT SQL 参数映射
            List<ParameterMapping> parameterMappings = countBoundSqlTemp.getParameterMappings();
            //获取分页sql
            String countSql = countBoundSqlTemp.getSql();
            //重构COUNT SQL语句对象
            BoundSql countBoundSql = new BoundSql(pageStatement.getConfiguration(), countSql, parameterMappings, params);
            //参数控制拦截器
            ParameterHandler parameterHandler = new DefaultParameterHandler(pageStatement, params,
                    countBoundSql);
            //从连接池获取链接
            connection = pageStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            //执行分页count Sql 查询
            preparedStatement = connection.prepareStatement(countSql);
            //设置参数
            parameterHandler.setParameters(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //清空链接
            JdbcUtils.close(resultSet);
            JdbcUtils.close(preparedStatement);
            JdbcUtils.close(connection);
        }
        return 0;
    }
}
