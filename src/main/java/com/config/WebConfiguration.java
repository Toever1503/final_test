package com.config;

import com.config.filter.AdminFilter;
import com.config.filter.CartFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Properties;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    public static String ROOT_CONTENT_SYS;

    WebConfiguration(@Value("${web-content.root-content}") String ROOT_CONTENT_SYS) {
        ROOT_CONTENT_SYS = new File(ROOT_CONTENT_SYS).getAbsolutePath();
        System.out.println(ROOT_CONTENT_SYS);
        WebConfiguration.ROOT_CONTENT_SYS = ROOT_CONTENT_SYS;
        System.out.println(ROOT_CONTENT_SYS);
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://10.0.0.73:8080", "http://10.0.0.100:8080", "http://165.22.48.208")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS");
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:".concat(ROOT_CONTENT_SYS));
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(465);

        mailSender.setUsername("0845954f4600a5");
        mailSender.setPassword("8252158432ddf2");

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.smtp.auth", "true");

        return mailSender;
    }


//    @Bean
//    public FilterRegistrationBean<CartFilter> cartFilterFilterRegistrationBean() {
//        FilterRegistrationBean<CartFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new CartFilter());
//        registrationBean.addUrlPatterns("/user-cart/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<AdminFilter> adminFilterFilterRegistrationBean() {
//        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AdminFilter());
//        registrationBean.addUrlPatterns("/admin/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

}
