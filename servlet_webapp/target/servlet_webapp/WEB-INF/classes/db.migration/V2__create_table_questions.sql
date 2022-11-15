CREATE TABLE questions (
                           question_id INTEGER PRIMARY KEY AUTOINCREMENT ,
                           topic_id INTEGER,
                           description VARCHAR(100),
                           points INTEGER,
                           CONSTRAINT topic_pk FOREIGN KEY (topic_id)
                               REFERENCES TOPICS (topic_id)
)