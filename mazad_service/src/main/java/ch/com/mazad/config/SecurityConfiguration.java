package ch.com.mazad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")

                .antMatchers("/ws/users/authenticate")
                .antMatchers("/ws/users/get")
                .antMatchers(HttpMethod.POST,"/ws/users")
                .antMatchers(HttpMethod.GET, "/ws/categories")
                .antMatchers(HttpMethod.GET, "/ws/regions")
                .antMatchers(HttpMethod.GET, "/ws/articles")
                .antMatchers(HttpMethod.GET, "/ws/articles/bylabel")
                .antMatchers("/ws/users/password/reset")
                .antMatchers("/ws/users/password/reset/finish")
                .antMatchers("/ws/users/email/request/code")
                .antMatchers("/ws/users/email/activate")
                .antMatchers("/ws/users/token/refresh");


//                .antMatchers("/v2/api-docs",
//                        "/swagger-resources/configuration/ui",
//                        "/swagger-resources",
//                        "/swagger-resources/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**")

//                .antMatchers("/angular/**")

                        //actuator endPoints
//                .antMatchers("/actuator")
//                .antMatchers("/auditevents")
//                .antMatchers("/autoconfig")
//                .antMatchers("/beans")
//                .antMatchers("/configprops")
//                .antMatchers("/trace")
//                .antMatchers("/dump")
//                .antMatchers("/env")
//                .antMatchers("/flyway")
//                .antMatchers("/health")
//                .antMatchers("/info")
//                .antMatchers("/loggers")
//                .antMatchers("/liquibase")
//                .antMatchers("/metrics")
//                .antMatchers("/mappings")
//                .antMatchers("/shutdown")
//                .antMatchers("/trace");


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().realmName("mazad_service")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers().antMatchers("/oauth/authorize")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/authorize").authenticated()
                .and()
                .headers().disable();
    }
}
