package com.example.blogapp.model;

import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String title;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "user_id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<FileUpload> files;

}
