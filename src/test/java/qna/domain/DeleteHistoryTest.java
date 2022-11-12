package qna.domain;

import static qna.domain.DeleteHistory.questionHistory;
import static qna.domain.Question.newQuestion;
import static qna.domain.UserTest.JAVAJIGI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteHistoryTest {

    private Question Q1;

    @BeforeEach
    void setUp() {
        Q1 = newQuestion(1L, "title1", "contents1").writeBy(JAVAJIGI);
    }

    @Test
    @DisplayName("question 삭제 기록을 남긴다")
    void deleteQuestionHistory() {
        Assertions.assertThat(questionHistory(Q1))
            .isInstanceOf(DeleteHistory.class);
    }

    @Test
    @DisplayName("answer 삭제 기록을 남긴다")
    void deleteAnswerHistory() {
        DeleteHistories deleteHistories = new DeleteHistories();
        Q1.addAnswer(Answer.newAnswer(1L, JAVAJIGI, Q1, "test"));
        deleteHistories.deleteAnswerHistories(Q1);

        Assertions.assertThat(
                deleteHistories.deleteHistories())
            .isNotEmpty();
    }

}
