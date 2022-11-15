CREATE TABLE responses (
                           response_id INTEGER PRIMARY KEY AUTOINCREMENT ,
                           question_id INTEGER,
                           description VARCHAR(30),
                           CONSTRAINT question_pk FOREIGN KEY (question_id)
                               REFERENCES questions (question_id)
)
