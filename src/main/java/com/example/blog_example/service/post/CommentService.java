package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.comment.Comment;
import com.example.blog_example.model.domain.post.comment.CommentRepository;
import com.example.blog_example.model.domain.post.detail.PostDetail;
import com.example.blog_example.model.domain.post.detail.PostDetailRepository;
import com.example.blog_example.model.domain.user.User;
import com.example.blog_example.model.domain.user.UserRepository;
import com.example.blog_example.model.dto.post.comment.*;
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
    private final PostDetailRepository postDetailRepository;
    private final UserRepository userRepository;

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
    public List<CommentVO> findByPostDetail(CommentFindByObjectDTO commentFindByObjectDTO) {
        PostDetail postDetail = postDetailRepository.findById(commentFindByObjectDTO.getObjectNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.findByPostDetail(postDetail).stream()
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

        PostDetail postDetail = postDetailRepository.findById(commentSaveDTO.getPostDetailNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.save(
                Comment.builder()
                        .content(commentSaveDTO.getContent())
                        .user(user)
                        .postDetail(postDetail)
                        .build())
                .getCommentNo();
    }

    @Transactional
    public Long update(CommentUpdateDTO commentUpdateDTO) {
        Comment comment = commentRepository.findById(commentUpdateDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        comment.update(comment.getContent());

        return comment.getCommentNo();
    }

    @Transactional
    public void delete(CommentDeleteDTO commentDeleteDTO) {
        Comment comment = commentRepository.findById(commentDeleteDTO.getCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentRepository.delete(comment);
    }
}
