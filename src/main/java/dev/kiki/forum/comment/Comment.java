package dev.kiki.forum.comment;

import dev.kiki.forum.BaseEntity;
import dev.kiki.forum.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

//TIP: Used Polymorphic relationship 🕺🕺🤓 to associate a comment to both Question and Answer
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "commentable_id", nullable = false)
    private UUID commentableId;

    @Column(name = "commentable_type", nullable = false)
    private String commentableType;

    //Utility methods to help in persisting a method with its type
    public void setCommentOnQuestion(UUID questionId) {
        this.commentableId =questionId;
        this.commentableType = "Question";
    }
    public void setCommentOnAnswer(UUID answerId) {
        this.commentableId =answerId;
        this.commentableType = "Answer";
    }


}
