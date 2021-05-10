package com.example.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bloguser")
@Builder
@AllArgsConstructor
@NamedEntityGraph(name = "User.posts",
        attributeNodes = @NamedAttributeNode("posts")
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;
}
