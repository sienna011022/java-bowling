package qna.domain;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import qna.CannotDeleteException;

@Entity
public class Question extends AbstractEntity {

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question(long id, String title, String contents) {
        super(id);
        this.title = title;
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public Question writeBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public void validOwner(User loginUser) throws CannotDeleteException {
        if (!writer.equals(loginUser)) {
            throw new CannotDeleteException("로그인한 사용자와 일치하지 않습니다");
        }
    }

    public void delete(User loginUser) throws CannotDeleteException {
        validOwner(loginUser);
        this.deleted = true;
        deleteAnswer();
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question [id=" + Id() + ", title=" + title + ", contents=" + contents
            + ", writer=" + writer + "]";
    }

    public DeleteHistory questionHistory() {
        return new DeleteHistory(ContentType.QUESTION, this.Id(), writer, LocalDateTime.now());
    }

    public List<DeleteHistory> answersHistory() {
        return answers
            .stream()
            .map(answer -> answer.answerHistory())
            .collect(Collectors.toList());
    }

    public void deleteAnswer() throws CannotDeleteException {
        for (Answer answer : answers) {
            answer.delete(writer);
        }
    }

    public boolean isAnswersDeleted() {
        return answers.stream()
            .map(answer -> answer.isDeleted())
            .allMatch(Predicate.isEqual(true));
    }
}
