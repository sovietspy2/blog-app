package com.example.blogapp.repository;

import com.example.blogapp.model.Blog;
import com.example.blogapp.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends CrudRepository<Blog, Integer> {

    Optional<Blog> findByPostsContains(Post post);

    List<Blog> findAllByIdIn(List<Integer> postsIds);


}
