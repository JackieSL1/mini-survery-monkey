package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CommentQuestion extends Question {
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentResponse> commentResponses;

    public List<CommentResponse> getCommentResponses() {
        return commentResponses;
    }

    public void setCommentResponses(List<CommentResponse> commentResponses) {
        this.commentResponses = commentResponses;
    }
}
