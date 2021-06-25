package org.servicify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@EnableCaching
@MapperScan(basePackages ="org.servicify.mehrms.mapper")
@SpringBootApplication
public class MailApplication {
	//首先创建一个TopicExchange路由策略对象，TopicExchange路由策略会根据消息的routingkey将消息路由到一个或者多个Queue上
	public final static String TOPICNAME="Servicify-topic";

	//创建TopicExchange对象，三个参数分别为名字、重启后是否依然有效以及长期未用时是否删除
	@Bean
	TopicExchange topicExchange(){
		return new TopicExchange(TOPICNAME,true,false);
	}

//	创建一个员工入职消息
	@Bean
	Queue empQueue(){
		return new Queue("welcome");
	}

//	创建一个薪资消息
	@Bean
	Queue salaryQueue(){
		return new Queue("salary");
	}

//	将empQueue（）绑定至TopicExchange上，“welcome.#"表示消息凡是以”welcome"开头的，都将被路由到名称为"welcome”的Queue上；
//	而"#.welcome.#"表示消息的routingkey中凡是包含”welcome“字符的，都将被路由至名称为“welcome”的Queue上。
	@Bean
	Binding empBinding(){
		return BindingBuilder.bind(empQueue()).to(topicExchange()).with("#.welcome.#");
	}

//	同上
	@Bean
	Binding salaryBinding(){
		return BindingBuilder.bind(salaryQueue()).to(topicExchange()).with("#.salary.#");
	}

	//	创建启动类
	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}
}
