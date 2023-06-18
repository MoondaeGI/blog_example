package com.example.blog_example.service.post;

import com.example.blog_example.model.domain.post.comment.Comment;
import com.example.blog_example.model.domain.post.comment.CommentRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.dto.post.comment.CommentDeleteDTO;
import com.example.blog_example.model.dto.post.comment.CommentFindDTO;
import com.example.blog_example.model.dto.post.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.post.comment.CommentUpdateDTO;
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

    @Transactional
    public Long save(CommentSaveDTO commentSaveDTO) {
        Post post = postRepository.findById(commentSaveDTO.getPostNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.save(
                Comment.builder()
                        .content(commentSaveDTO.getContent())
                        .post(post)
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
