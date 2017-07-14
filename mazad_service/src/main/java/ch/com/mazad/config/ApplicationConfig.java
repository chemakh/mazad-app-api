package ch.com.mazad.config;

import org.apache.commons.lang.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by Chemakh on 14/04/2017.
 */
@Configuration
public class ApplicationConfig {

    @Bean(name = "taskExecutor")
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor()
    {
            return new SimpleAsyncTaskExecutor();
    }


    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver()
    {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("mails/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }

}
