package com.example.blog_example.service.comment;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.comment.comment.CommentRepository;
import com.example.blog_example.model.domain.comment.liked.CommentLikedRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.comment.*;
import com.example.blog_example.model.vo.post.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentLikedRepository commentLikedRepository;

    @Transactional(readOnly = true)
    public List<CommentVO> findAll() {
        return commentRepository.findAll().stream()
                .map(CommentVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentVO find(CommentFindDTO commentFindDTO) {
        return CommentVO.from(
                commentRepository.findById(commentFindDTO.getCommentNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<CommentVO> findByPost(CommentFindByObjectDTO commentFindByObjectDTO) {
        Post post = postRepository.findById(commentFindByObjectDTO.getObjectNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.findByPost(post).stream()
                .map(CommentVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentVO> findByUser(CommentFindByObjectDTO commentFindByObjectDTO) {
        User user = userRepository.findById(commentFindByObjectDTO.getObjectNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return commentRepository.findByUser(user).stream()
                .map(CommentVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(CommentSaveDTO commentSaveDTO) {
        User user = userRepository.findById(commentSaveDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Post post = postRepository.findById(commentSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.save(
                Comment.builder()
                        .content(commentSaveDTO.getContent())
                        .user(user)
                        .post(post)
                        .build())
                .getCommentNo();
    }

    @Transactional
    public Long update(CommentUpdateDTO commentUpdateDTO) {
        Comment comment = commentRepository.findById(commentUpdateDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        comment.update(commentUpdateDTO.getContent());

        return comment.getCommentNo();
    }

    @Transactional
    public void delete(CommentDeleteDTO commentDeleteDTO) {
        Comment comment = commentRepository.findById(commentDeleteDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(CommentIsLikedDTO commentIsLikedDTO) {
        Comment comment = commentRepository.findById(commentIsLikedDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        return commentLikedRepository.findByComment(comment) != null;
    }
}
