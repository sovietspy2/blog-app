package com.example.blogapp.repository;

import com.example.blogapp.model.Blog;
import com.example.blogapp.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BlogRepository extends CrudRepository<Blog, Integer> {

    Optional<Blog> findByPostsContains(Post post);

    List<Blog> findAllByIdIn(Set<Integer> postsIds);

    //@Query("SELECT F FROM Blog F WHERE F.posts IN  :ids")
    @Query(value = "select * from Blog b where b.id in (select blog_id from Post p where p.id in (:ids))", nativeQuery = true)
    List<Blog> findAllByPostIdIn(@Param("ids") Set<Integer> ids);

    Optional<Blog> findById(Integer id);


}
