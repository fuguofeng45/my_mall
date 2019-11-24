package run.fgf45.javaer.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan({"run.fgf45.javaer.mbg.mapper", "run.fgf45.javaer.dao"})
public class MybatisConfig {
}
