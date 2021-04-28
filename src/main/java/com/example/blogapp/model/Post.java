package com.example.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
@Builder
@AllArgsConstructor
@NamedEntityGraph(name = "Post.graph",
        attributeNodes = {
            @NamedAttributeNode("comments"),
            @NamedAttributeNode("user"),
            @NamedAttributeNode("blog")
        }
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
