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
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadResolver implements GraphQLMutationResolver {

    private final PostRepository postRepository;

    private final FileUploadRepository fileUploadRepository;

    private final SomeOtherApi someOtherApi;

    final ExecutorService executorService = Executors.newFixedThreadPool(8);

    public boolean uploadFile(List<Part> files, Integer postId) throws Exception {

        CompletableFuture<String> message = someOtherApi.getImportantData();

        log.info("Message in COMMENTSERVICE: "+message.toString());

        CompletableFuture<String> value = process();


        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            log.error("Upload unsuccessful! No post with id: "+postId);
            return false;
        }

        for (Part part: files) {

            // error handling
            log.info("this is info");
            log.debug("this is debug");

            InputStream is = part.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);
            String encoded = Base64.getEncoder().encodeToString(bytes);
            is.close();

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

    private CompletableFuture<String> process() {
        return CompletableFuture.supplyAsync(() -> {
            log.info(" --------------");
            log.debug("DEBUG MESSAGE: " + "GENERAL KENOBI");
            log.info(" -------------");
            return "Hello There!";
        }, executorService);
    }

}
