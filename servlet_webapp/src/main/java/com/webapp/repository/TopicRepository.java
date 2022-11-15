package com.webapp.repository;

import com.webapp.model.Topic;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class TopicRepository {

    private final Connection connection;
    private final QuestionRepository repository;

    public List<Topic> findAll() {
        List<Topic> topicList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "select topic_id, title, description from topics");

            while (resultSet.next()) {
                Topic topic = new Topic();
                topic.setIdTopic(resultSet.getLong("topic_id"));
                topic.setTitle(resultSet.getString("title"));
                topic.setDetails(resultSet.getString("description"));
                topic.setQuestions(repository.findAllForTopic(topic.getIdTopic()));
                topicList.add(topic);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }

    public Optional<Topic> findById(Long key) {
        List<Topic> topicList = findAll();
        return topicList.stream()
                .filter(x -> x.getIdTopic() == key)
                .findFirst();
    }
}
