package com.example.blog_example.service.comment;

import com.example.blog_example.model.domain.comment.comment.Comment;
import com.example.blog_example.model.domain.comment.comment.CommentRepository;
import com.example.blog_example.model.domain.comment.liked.CommentLiked;
import com.example.blog_example.model.domain.comment.liked.CommentLikedRepository;
import com.example.blog_example.model.domain.post.post.Post;
import com.example.blog_example.model.domain.post.post.PostRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.comment.CommentSaveDTO;
import com.example.blog_example.model.dto.comment.CommentUpdateDTO;
import com.example.blog_example.model.vo.post.CommentVO;
import com.example.blog_example.util.enums.LikedState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public CommentVO find(Long commentNo) {
        return CommentVO.from(commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<CommentVO> findByPost(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.findByPost(post).stream()
                .map(CommentVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentVO> findByUser(Long userNo) {
        User user = userRepository.findById(userNo)
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
    public void delete(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentRepository.delete(comment);
    }

    @Transactional
    public LikedState changeLiked(Long commentNo, Long userNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        if (Objects.equals(comment.getUser().getUserNo(), userNo))
            throw new IllegalArgumentException("좋아요 하려는 유저가 댓글을 쓴 유저와 같습니다.");

        if (commentLikedRepository.existsByComment(comment)) {
            commentLikedRepository.deleteByComment(comment);
        } else {
            User user = userRepository.findById(userNo)
                            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

            commentLikedRepository.save(
                    CommentLiked.builder()
                            .comment(comment)
                            .user(user)
                            .build());
        }

        return commentLikedRepository.existsByComment(comment) ? LikedState.LIKED : LikedState.CANSEL;
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        return commentLikedRepository.existsByComment(comment);
    }

    @Transactional(readOnly = true)
    public Integer countLiked(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        return commentLikedRepository.countByComment(comment).intValue();
    }
}
