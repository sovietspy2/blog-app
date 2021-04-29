package com.example.blogapp.service;

import com.example.blogapp.model.Blog;
import com.example.blogapp.model.FileUpload;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.FileUploadRepository;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PostRepository postRepository;

    private final FileUploadRepository fileUploadRepository;

    public List<Post> getPosts(Integer page, Integer pageSize) {
        return postRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Post getPost(Integer postId) {
        return postRepository.findById(postId).orElseGet(Post::new);
    }

    public Post createPost(String title, String text, User user, Blog blog) {
        return postRepository.save(Post.builder()
                .title(title)
                .text(text)
                .blog(blog)
                .user(user)
                .build());
    }

    public boolean uploadFile(List<Part> files) throws IOException {

        fileUploadRepository.findById(1);

        Post testPost = postRepository.findById(1).get();

        for (Part part: files) {

            FileUpload fileUpload = FileUpload.builder()
                    .filename("test.txt")
                    .post(testPost)
                    .ctype(part.getContentType())
                    .content(IOUtils.toByteArray(part.getInputStream()))
                    .build();

            fileUploadRepository.save(fileUpload);


        }

        return true;
    }


}
