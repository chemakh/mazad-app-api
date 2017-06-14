package ch.com.mazad.config.apidoc;

import ch.com.mazad.config.MazadProperties;
import ch.com.mazad.exception.ErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{

    private final static Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    @Inject
    private MazadProperties mazadProperties;


    @Bean
    public Docket swaggerSpringfoxDocket()
    {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                mazadProperties.getSwagger().getContactName(),
                mazadProperties.getSwagger().getContactUrl(),
                mazadProperties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                mazadProperties.getSwagger().getTitle(),
                mazadProperties.getSwagger().getDescription(),
                mazadProperties.getSwagger().getVersion(),
                mazadProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                mazadProperties.getSwagger().getLicense(),
                mazadProperties.getSwagger().getLicenseUrl());

        List<ResponseMessage> globalMessages = new ArrayList<>();
        globalMessages.add(new ResponseMessageBuilder().code(500).message(ErrorConstants.ERR_INTERNAL_SERVER_ERROR).build());

        List<Parameter> parameters = new LinkedList<>();
        parameters.add(new ParameterBuilder()
                .parameterType("header")
                .name("Authorization")
                .modelRef(new ModelRef("string"))
                .description("Authentication token (see /ws/user/authenticate)")
                .allowMultiple(false)
                .required(false)
                .defaultValue("Bearer")
                .build());

        parameters.add(new ParameterBuilder()
                .parameterType("header")
                .name("Accept-Language")
                .modelRef(new ModelRef("string"))
                .description("Accept-Language")
                .allowMultiple(false)
                .required(false)
                .defaultValue("fr")
                .build());

        //or use this annotation for each operation
//        @ApiImplicitParams({
//                @ApiImplicitParam(name = "Authorization", defaultValue = "Bearer",allowMultiple = false,
//                        required = false, dataType = "string", paramType = "header")
//        })


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalMessages)
                .globalResponseMessage(RequestMethod.POST, globalMessages)
                .globalResponseMessage(RequestMethod.PUT, globalMessages)
                .globalResponseMessage(RequestMethod.DELETE, globalMessages)
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .select().apis(RequestHandlerSelectors.basePackage("ch.com.mazad.web.controller"))
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
