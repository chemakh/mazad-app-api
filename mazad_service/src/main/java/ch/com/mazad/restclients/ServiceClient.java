package ch.com.mazad.restclients;

import ch.com.mazad.web.dto.ArticleDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Chemakh on 28/08/2017.
 */
@FeignClient(name = "mazad-service-server", url = "${mazad.feign.service}")
public interface ServiceClient
{
    @PutMapping(value = "articles/validate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ArticleDTO> validateArticle(@RequestParam("reference") String reference);
}
