package com.springboot.web;

import com.springboot.dto.PostsResponseDto;
import com.springboot.dto.PostsSaveRequestDto;
import com.springboot.dto.PostsUpdateRequestDto;
import com.springboot.dto.PostsXMLSaveRequestDto;
import com.springboot.services.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts/JSONSave")
    public Long JsonSave(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto.toEntity());
    }

    @PostMapping("/api/v1/posts/XMLSave")
    public Long XmlSave(@RequestBody PostsXMLSaveRequestDto requestDto){
        return postsService.save(requestDto.toEntity());
    }

    @GetMapping("/api/v1/posts/select/{id}")
    public PostsResponseDto update(@PathVariable Long id){
        return postsService.select(id);
    }

    @PostMapping("/api/v1/posts/update/{id}")
    public PostsResponseDto update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }
    @GetMapping("/api/v1/posts/delete/{id}")
    public Long delete(@PathVariable Long id){
        return postsService.delete(id);
    }

}
