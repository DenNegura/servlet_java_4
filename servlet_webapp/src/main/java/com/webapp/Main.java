package com.webapp;

import com.webapp.database.DatabaseConnection;
import com.webapp.model.Topic;
import com.webapp.repository.QuestionRepository;
import com.webapp.repository.ResponseRepository;
import com.webapp.repository.TopicRepository;

import java.util.Optional;

public class Main {

    static DatabaseConnection databaseConnection =
            new DatabaseConnection(
                    "jdbc:sqlite:C:/Projects/java/lab_3/servlet_webapp/src/main/resources/questions.sqlite");

    static ResponseRepository responseRepository =
            new ResponseRepository(databaseConnection.getConnection());

    static QuestionRepository questionRepository =
            new QuestionRepository(databaseConnection.getConnection(), responseRepository);

    static TopicRepository topicRepository =
            new TopicRepository(databaseConnection.getConnection(), questionRepository);

    public static void main(String[] args) {
        Optional<Topic> topic = topicRepository.findById(1L);

        System.out.println(topic);
    }
}