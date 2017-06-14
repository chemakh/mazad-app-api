package ch.com.mazad.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class JwtConfiguration
{
    @Inject
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Inject
    private DataSource dataSource;


    @Bean
    @Qualifier("JWTtokenStore")
    public TokenStore tokenStore()
    {

        return new JwtTokenStore(jwtAccessTokenConverter);
    }


    @Bean
    @Qualifier("jdbcTokenStore")
    public TokenStore jdbcTokenStore()
    {

        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer()
    {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.cert");
        String publicKey = null;
        try
        {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public DefaultTokenServices tokenServices()
    {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter);
        defaultTokenServices.setTokenStore(tokenStore);
        return defaultTokenServices;
    }
}
