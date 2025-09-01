package com.worknest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "commenter_user_id")
    private User commenter;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
    public Date getCommentDate() { return commentDate; }
    public void setCommentDate(Date commentDate) { this.commentDate = commentDate; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public User getCommenter() { return commenter; }
    public void setCommenter(User commenter) { this.commenter = commenter; }
}