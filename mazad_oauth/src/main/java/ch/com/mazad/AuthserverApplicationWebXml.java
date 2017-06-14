package ch.com.mazad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


public class AuthserverApplicationWebXml extends SpringBootServletInitializer
{

    private final Logger log = LoggerFactory.getLogger(AuthserverApplicationWebXml.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.profiles(addDefaultProfile())
                .sources(MazadOauthApplication.class);
    }

    private String addDefaultProfile()
    {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null && !profile.isEmpty())
        {
            log.info("Running with Spring profile(s) : {}", profile);
            return profile;
        }

        log.warn("No Spring profile configured, running with default configuration");
        return "dev";
    }
}
