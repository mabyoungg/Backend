package com.sparta.i_mu.dto.responseDto;

import com.sparta.i_mu.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;

    private String content;

    private LocalDateTime createdAt;

    public CommentResponseDto (Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

}
