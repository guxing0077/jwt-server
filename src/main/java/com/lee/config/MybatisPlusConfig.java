package com.lee.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mybatis config
 */
@Configuration
public class MybatisPlusConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);

    private static final String LOCATIONS = "classpath:mappers/*Mapper.xml";

    private static final String TYPE_ALIASES_PACKAGE = "com.lee.entity";
    @Autowired
    private DataSource dataSource;

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }


    private GlobalConfiguration globalConfiguration() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        //主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        globalConfiguration.setIdType(0);
        //字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        globalConfiguration.setFieldStrategy(2);
        //驼峰下划线转换
        globalConfiguration.setDbColumnUnderline(true);
        //刷新mapper 调试神器
        globalConfiguration.setRefresh(true);
        return globalConfiguration;
    }

    @Bean
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory()
            throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        //支持分页插件
        mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[]{
                performanceInterceptor()
        });
        // 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources(LOCATIONS);
        mybatisSqlSessionFactoryBean.setMapperLocations(resources);
        //枚举
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfiguration());
        return mybatisSqlSessionFactoryBean.getObject();
    }
}
