package com.example.blogapp.repository;

import com.example.blogapp.model.FileUpload;
import com.example.blogapp.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileUploadRepository extends CrudRepository<FileUpload, Integer> {
    List<FileUpload> findAllByPost(Post post);
}
