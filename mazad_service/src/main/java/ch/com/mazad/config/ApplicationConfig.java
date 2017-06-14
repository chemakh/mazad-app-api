package ch.com.mazad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

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
}
