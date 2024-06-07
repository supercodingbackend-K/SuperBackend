package service;

import lombok.RequiredArgsConstructor;
import domain.Member;
import domain.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MemberRespository;
import repository.PostRepository;


import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class PostService<body> {
    private final PostRepository postRepository;
    private final MemberRespository memberRespository;

    public Post writePost(String title String body, Long writerMemberId) {
        Member writer = memberRespository.findById(writerMemberId)
                .orElseThrow(() -> new IllegalAccessError("Member not found"));

        return postRepository.save(
                        Post.builder()
                                .title(title)
                                .body(body)
                                .writer(writer)
                                .createdAt(LocalDateTime.now())
                                .build()
                );
    }
}


