package com.example.blogapp.service;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.repository.CommentRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CommentDataLoader {

    private final CommentRepository commentRepository;

    private DataLoader<Integer, Comment> loader;

    public CommentDataLoader(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
        //loader = new DataLoader<Integer, Comment>(keys -> CompletableFuture.supplyAsync(() -> commentRepository.findAllById(keys)));
    }

    public DataLoader<Integer, Comment> getLoader() {
        return loader;
    }

    public String getKey(){
        return "comment";
    }

    public CompletableFuture<Comment> load(Integer commentId){
        return loader.load(commentId);
    }


}
