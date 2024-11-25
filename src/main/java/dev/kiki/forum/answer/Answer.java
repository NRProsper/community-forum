package dev.kiki.forum.answer;

import dev.kiki.forum.BaseEntity;
import dev.kiki.forum.question.Question;
import dev.kiki.forum.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "answers")
@NoArgsConstructor
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private Integer votes;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    private Boolean isAccepted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
