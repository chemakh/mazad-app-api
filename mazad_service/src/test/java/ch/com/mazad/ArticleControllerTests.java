package ch.com.mazad;

import ch.com.mazad.config.TestUtil;
import ch.com.mazad.web.dto.ArticleDTO;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Chemakh on 06/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ArticleControllerTests {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Value("${mazad.swagger.token}")
    private String token;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webContext).apply(springSecurity())
                .build();
        token = "Bearer" + token;
    }

    @Test
    public void testCreateArticle() throws Exception {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setCategory("bbb");
        articleDTO.setLabel("IPHONE");
        articleDTO.setCreatedByUserReference("sring");
        articleDTO.setInitialPrice(BigDecimal.valueOf(15));
        articleDTO.setDescription("barch a");

        byte[] json = TestUtil.convertObjectToJsonBytes(articleDTO);

//        MvcResult result = mockMvc.perform(post("/ws/articles/").header("Authorization", token)
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE).requestAttr("article",json))
//               .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("reference", is(notNullValue()))).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/ws/articles/").header("Authorization", token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE).requestAttr("article", json))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("reference", is(notNullValue()))).andReturn();

        JSONObject jsonObj = new JSONObject(result.getResponse().getContentAsString());

        assertNotNull(jsonObj);
        assertNotNull(jsonObj.get("reference"));
    }


}
