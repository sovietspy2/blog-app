package com.example.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
@Builder
@AllArgsConstructor
@NamedEntityGraph(name = "Post.comments",
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
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private Blog blog;
}
