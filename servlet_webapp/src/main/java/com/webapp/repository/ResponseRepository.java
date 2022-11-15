package com.webapp.repository;

import com.webapp.model.Response;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ResponseRepository {

    private final Connection connection;

    public List<Response> findAllForQuestion(Long key) {
        List<Response> responseList;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "select response_id, description " +
                            "from responses where question_id = " + key + ";");
            responseList = new ArrayList<>();
            while (resultSet.next()) {
                Response response = new Response();
                response.setIdResponse(resultSet.getLong("response_id"));
                response.setIdQuestion(key);
                response.setDescription(resultSet.getString("description"));
                responseList.add(response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return responseList;
    }
}
