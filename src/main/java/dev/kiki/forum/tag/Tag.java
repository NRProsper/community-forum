package dev.kiki.forum.tag;

import dev.kiki.forum.BaseEntity;
import dev.kiki.forum.question.Question;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tags")
@Data
public class Tag extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Question> questions;

}
