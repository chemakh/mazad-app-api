package ch.com.mazad.service;

import ch.com.mazad.config.notification.HeaderRequestInterceptor;
import ch.com.mazad.repository.SaleRepository;
import ch.com.mazad.repository.UserRepository;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.Notification;
import net.minidev.json.JSONObject;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chemakh on 05/09/2017.
 */
@Service
public class PushNotificationsService
{
    @Value("${mazad.firebase.api.key}")
    private String FIREBASE_SERVER_KEY;
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String BID_CREATION = "Bid Creation";
    public static final String SELLING_ARTICLE = "selling article";
    public static final String BUY_IT_NOW_ARTICLE = "buy it now operation";
    @Inject
    private UserRepository userRepository;
    @Inject
    private SaleRepository saleRepository;
    @Inject
    private DTOMapper mapper;

    private CompletableFuture<Object> send(Notification entity)
    {

        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        JSONObject json = new JSONObject();
        json.put("to", "/topics/mazad");
        json.put("notification", entity);

        Object firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, json, Object.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

    @Async
    public CompletableFuture<Object> notify(ArticleDTO articleDTO, String createdByUserRef, String operationType)
    {
        Notification notification = new Notification();

        notification.setArticle(articleDTO);
        notification.setCreationDate(LocalDateTime.now());
        notification.setCreatedByUser(mapper.fromUserToDTO(userRepository.findOneByReference(createdByUserRef).orElse(null)));
        notification.setOperation(operationType);

        if(operationType.compareTo(BID_CREATION) == 0 || operationType.compareTo(BUY_IT_NOW_ARTICLE) == 0)
            notification.getConsernedUsers().add(mapper.fromUserToDTO(userRepository.findOneByReference(articleDTO.getCreatedByUserReference()).orElse(null)));
        else if(operationType.compareTo(SELLING_ARTICLE) == 0)
        {
            notification.getConsernedUsers().add
                    (mapper.fromUserToDTO(saleRepository.findTopByArticleReferenceOrderBySoldDateDesc(articleDTO.getReference()).getBid().getUser()));
        }

        return send(notification);
    }
}
