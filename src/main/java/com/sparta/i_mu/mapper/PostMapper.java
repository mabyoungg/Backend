package com.sparta.i_mu.mapper;

import com.sparta.i_mu.dto.responseDto.*;
import com.sparta.i_mu.entity.Comment;
import com.sparta.i_mu.entity.Post;
import com.sparta.i_mu.repository.CommentRepository;
import com.sparta.i_mu.repository.PostSongLinkRepository;
import com.sparta.i_mu.repository.WishlistRepository;
import com.sparta.i_mu.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final WishlistRepository wishlistRepository;
    private final CommentRepository commentRepository;
    private final PostSongLinkRepository postSongLinkRepository;
    private final SongMapper songMapper;

    public PostResponseDto mapToPostResponseDto(Post post) {
        Long wishlistCount = wishlistRepository.countByPostId(post.getId());
//        List<Comment> comments = commentRepository.findAllByPostId(post.getId());
        List<SongResponseDto> songs = postSongLinkRepository.findAllByPostId(post.getId())
                .stream()
                .map(postSongLink -> songMapper.entityToResponseDto(postSongLink.getSong())) // SongResponseDto로의 매핑 로직이 필요합니다
                .collect(Collectors.toList());

        return PostResponseDto.builder()
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .category(post.getCategory().getId())
                .createdAt(post.getCreatedAt())
                //.wishlist(post.getWishlist())
                .wishlistCount(wishlistCount)
                //.comments(comments)
                .songs(songs)
                .location(post.getLocation())
                .build();
    }

    public PostResponseDto mapToPostResponseDto(Post post, Optional<UserDetailsImpl> userDetails) {
        boolean isWishlist = userDetails.isPresent() && wishlistRepository.existsByPostIdAndUserId(post.getId(), userDetails.get().getUser().getId());

        Long wishlistCount = wishlistRepository.countByPostId(post.getId());

        List<CommentResponseDto> comments = commentRepository.findAllByPostId(post.getId())
                .stream()
                .map(CommentResponseDto::new)
                .toList();

        List<SongResponseDto> songs = postSongLinkRepository.findAllByPostId(post.getId())
                .stream()
                .map(postSongLink -> songMapper.entityToResponseDto(postSongLink.getSong())) // SongResponseDto로의 매핑 로직이 필요합니다
                .collect(Collectors.toList());

        return PostResponseDto.builder()
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .userImage(post.getUser().getUserImage())
                .content(post.getContent())
                .category(post.getCategory().getId())
                .createdAt(post.getCreatedAt())
                .wishlist(isWishlist)
                .wishlistCount(wishlistCount)
                .comments(comments)
                .songs(songs)
                .location(post.getLocation())
                .build();
    }

    public PostListResponseDto mapToPostListResponseDto(Post post) {
        List<PostListSongResponseDto> songs = postSongLinkRepository.findAllByPostId(post.getId())
                .stream()
                .map(postSongLink -> songMapper.entityToPostListSongResponseDto(postSongLink.getSong()))
                .collect(Collectors.toList());

        return PostListResponseDto.builder()
                .postId(post.getId())
//                .postTitle(post.getPostTitle())
                .content(post.getContent())
                .songs(songs)
                .build();
    }

}