package com.springboot.services;

import com.springboot.domain.posts.Posts;
import com.springboot.domain.posts.PostsRepository;
import com.springboot.dto.PostsListResponseDto;
import com.springboot.dto.PostsResponseDto;
import com.springboot.dto.PostsSaveRequestDto;
import com.springboot.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(Posts requestDto){
        return postsRepository.save(requestDto).getId();
    }


    @Transactional
    public PostsResponseDto select(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자 없음 ID = "+id));
        PostsResponseDto responseDto = new PostsResponseDto(posts);
        return responseDto;
    }

    @Transactional
    public PostsResponseDto update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자 없음 ID = "+id));
        posts.setTitle(requestDto.getTitle());
        PostsResponseDto responseDto = new PostsResponseDto(posts);
        return responseDto;
    }

    @Transactional
    public Long delete(Long id){
        Optional<Posts> posts = postsRepository.findById(id);
        posts.orElseThrow(()-> new IllegalArgumentException("해당 사용자 없음 ID = "+id));

        posts.ifPresent(selectPosts->{
            postsRepository.delete(selectPosts);
        });
        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
