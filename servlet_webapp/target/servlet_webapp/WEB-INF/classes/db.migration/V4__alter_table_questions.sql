ALTER TABLE QUESTIONS ADD correct_response_id INTEGER
    REFERENCES responses(response_id);