package com.springboot.web;

import com.springboot.domain.posts.Posts;
import com.springboot.domain.posts.PostsRepository;
import com.springboot.dto.PostsSaveRequestDto;
import com.springboot.dto.PostsUpdateRequestDto;
import org.junit.runner.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

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

    private String LOCAL_HOST="http://127.0.0.1";

    @Test
    public void Posts_등록() throws Exception{
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder().t(title).c(content).a(author).build();

        String url = getLocalHostUrlPort()+"/api/v1/posts/JSONSave";
        ResponseEntity<Object> entity = restTemplate.postForEntity(url, postsSaveRequestDto, Object.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void Posts_수정() throws Exception{
        Posts savedPosts = Posts_TestSave();
        Long id = savedPosts.getId();

        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();
        String url = getLocalHostUrlPort()+"/api/v1/posts/update/"+id;
        ResponseEntity<Object> entity = restTemplate.postForEntity(url, requestDto, Object.class);
        System.out.println(entity);

    }
    @Test
    public void Posts_삭제() throws Exception {
        Posts savedPosts = Posts_TestSave();
        Long id = savedPosts.getId();
        String url = getLocalHostUrlPort()+"/api/v1/posts/delete/"+id;
        ResponseEntity<Object> entity = restTemplate.postForEntity(url, null, Object.class);

    }
    @Test
    public void BaseTimeEntity_등록()throws Exception{
        LocalDateTime now = LocalDateTime.of(2021,2,10,0,0,0);
        postsRepository.save(Posts.builder().title("test").content("ctest").build());

        List<Posts> list = postsRepository.findAll();
        Posts posts = list.get(0);

        System.out.println(">>>>>>>>>>>>>createDate="+posts.getCreateDate()+" \n>>>>>>>>>>>>>modifyDate"+posts.getModifiedDate());
        assertThat(posts.getCreateDate().isAfter(now));
        assertThat(posts.getModifiedDate().isAfter(now));
    }

    //테스트 저장
    public Posts Posts_TestSave()throws Exception{
        return postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

    }
    //로컬호스트 URL과 Port
    private String getLocalHostUrlPort(){
        return LOCAL_HOST+":"+ port;
    }
}
