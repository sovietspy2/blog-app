package com.example.blogapp.repository;

import com.example.blogapp.model.FileUpload;
import com.example.blogapp.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FileUploadRepository extends CrudRepository<FileUpload, Integer> {
    List<FileUpload> findAllByPost(Post post);

    List<FileUpload> findAllByIdIn(List<Integer> ids);

    //List<FileUpload> findAllByPostIdIn(List<Integer> ids);

    @Query("SELECT F FROM FileUpload F WHERE F.post.id IN  :ids")
    List<FileUpload> findAllByPostIdIn(@Param("ids") Set<Integer> ids);



}
