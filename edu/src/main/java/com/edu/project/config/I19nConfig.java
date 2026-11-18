package com.edu.project.config;

import java.time.Duration;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration 
public class I19nConfig implements WebMvcConfigurer{
  @Bean("messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/mge");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
  @Bean 
  public LocaleResolver localeResolver(){
    CookieLocaleResolver resolver = new CookieLocaleResolver("lang");
        resolver.setDefaultLocale(Locale.of("vi")); //Locale.of() là một phương thức tĩnh (static method) được gọi trực tiếp từ lớp Locale, không phải là hàm khởi tạo (constructor).
        resolver.setCookieMaxAge(Duration.ofDays(30));
        return resolver;
  }

  @Override 
  public void addInterceptors(InterceptorRegistry registry){
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang"); // bắt đầu tham số ?lang=vi or ?lang=en
    registry.addInterceptor(interceptor);
  }
}
