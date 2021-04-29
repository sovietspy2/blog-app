package com.example.blogapp.service;

import com.example.blogapp.model.FileUpload;
import com.example.blogapp.model.Post;
import com.example.blogapp.repository.FileUploadRepository;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadResolver implements GraphQLMutationResolver {

    private final PostRepository postRepository;

    private final FileUploadRepository fileUploadRepository;

    public boolean uploadFile(List<Part> files, Integer postId) throws Exception {

        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            log.error("Upload unsuccessful! No post with id: "+postId);
            return false;
        }

        for (Part part: files) {

            byte[] bytes = IOUtils.toByteArray(part.getInputStream());
            String encoded = Base64.getEncoder().encodeToString(bytes);

            FileUpload fileUpload = FileUpload.builder()
                    .filename(part.getSubmittedFileName())
                    .post(post.get())
                    .ctype(part.getContentType())
                    .content(encoded)
                    .build();

            fileUploadRepository.save(fileUpload);
        }

        return true;
    }
}
