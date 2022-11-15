package com.webapp.repository;

import com.webapp.model.Question;
import com.webapp.model.Response;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QuestionRepository {

    private final Connection connection;

    private final ResponseRepository repository;

    public List<Question> findAllForTopic(Long key) {
        List<Question> questionList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "select question_id, " +
                            "description, correct_response_id, points " +
                            "from questions where topic_id = " + key + ";");

            while (resultSet.next()) {
                Question question = new Question();
                question.setIdQuestion(resultSet.getLong("question_id"));
                question.setIdTopic(key);
                question.setDescription(resultSet.getString("description"));
                question.setPoints(resultSet.getInt("points"));
                List<Response> responses = repository.findAllForQuestion(question.getIdQuestion());
                long idCorrectResponse = resultSet.getLong("correct_response_id");
                question.setResponses(responses);
                question.setCorrectResponse(responses
                        .stream()
                        .filter(x -> x.getIdResponse() == idCorrectResponse)
                        .findFirst().orElse(null));

                questionList.add(question);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questionList;
    }
}
