package com.springboot.dto;

import com.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
//루트 엘리먼트가 <message-list>인 XML 생성
@XmlRootElement(name = "message-list")
public class PostsXMLSaveRequestDto {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "content")
    private String content;
    @XmlElement(name = "author")
    private String author;

    @Builder
    public PostsXMLSaveRequestDto(String t, String c, String a){
        this.title = t;
        this.content = c;
        this.author = a;
    }

    public Posts toEntity(){
        return Posts.builder().title(title).content(content).author(author).build();
    }
}
