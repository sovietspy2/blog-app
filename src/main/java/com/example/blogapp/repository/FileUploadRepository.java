package com.example.blogapp.repository;

import com.example.blogapp.model.FileUpload;
import org.springframework.data.repository.CrudRepository;

public interface FileUploadRepository extends CrudRepository<FileUpload, Integer> {

}
