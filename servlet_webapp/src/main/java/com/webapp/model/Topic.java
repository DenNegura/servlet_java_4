package com.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private long idTopic;

    private String title;

    private String details;

    private List<Question> questions;
}
