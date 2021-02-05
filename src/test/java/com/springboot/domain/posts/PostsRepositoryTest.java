package com.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }


    @Test
    public void 게시글저장()throws Exception{
        String title = "test title";
        String content = "test conetent";
        postsRepository.save(Posts.builder().title(title).content(content).author("test@test.com").build());
        목록불러오기();


    }

    @Test
    public void 목록불러오기() throws Exception{
        List<Posts> list=postsRepository.findAll();

    }
}
