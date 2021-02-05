package com.springboot.web;

import com.springboot.domain.posts.PostsRepository;
import com.springboot.dto.PostsSaveRequestDto;
import org.junit.runner.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown()throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception{
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder().t(title).c(content).a(author).build();

        String url = "http://127.0.0.1:"+port+"/api/v1/posts";
        ResponseEntity<Object> entity = restTemplate.postForEntity(url, postsSaveRequestDto, Object.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
