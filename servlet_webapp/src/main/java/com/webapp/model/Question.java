package com.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private long idQuestion;

    private long idTopic;

    private String description;

    private List<Response> responses;

    private Response correctResponse;

    private int points;
}
