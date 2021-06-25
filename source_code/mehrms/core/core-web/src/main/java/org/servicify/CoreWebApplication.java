package org.servicify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/

@EnableCaching
@SpringBootApplication
@MapperScan(basePackages ="org.servicify.mehrms.mapper")
public class CoreWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreWebApplication.class, args);
	}

}
