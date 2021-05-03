package com.example.blogapp.repository;

import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    //@EntityGraph(attributePaths = {"user"})
    Optional<User> findByPostsContains(Post post);

    //Optional<User> findByPosts(Post post);

    Optional<User> findById(Integer id);

    //List<User> findAllByIdIn(Set<Integer> postsIds);


    @Query(value = "select * from bloguser u where u.id in (select user_id from post p where p.id in (:postIds))", nativeQuery = true)
    List<User> findAllByPostIdIn(Set<Integer> postIds);

}
