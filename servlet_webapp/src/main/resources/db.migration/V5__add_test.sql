-- Topic
INSERT INTO TOPICS (topic_id, title, description)
VALUES (1, 'Футбол', 'Mы пoдгoтoвили для вac cлoжный тecт, ' ||
                     'кoтopый пoзвoлит oпpeдeлить, нacкoлькo вaм знaкoм миp фyтбoлa.');
-- Question #1
INSERT INTO QUESTIONS (question_id, topic_id, description, points)
VALUES (1, 1, 'Какой футболист установил рекорд чемпионатов мира по числу забитых голов в одном матче?', 20);

-- Response #1.1
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (1, 1, 'Эйсебио');

-- Response #1.2
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (2, 1, 'Пеле');

-- Response #1.3
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (3, 1, 'Мирослав Клозе');

-- Response #1.4
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (4, 1, 'Олег Саленко');

-- Insert correct response
UPDATE QUESTIONS
    SET CORRECT_RESPONSE_ID = 4
    WHERE QUESTION_ID = 1;



-- Question #2

INSERT INTO QUESTIONS (question_id, topic_id, description, points)
VALUES (2, 1, 'Эту московскую футбольную команду во времена СССР называли "пятым колесом в телеге".', 20);

-- Response #5
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (5, 2, '"Торпедо"');

-- Response #6
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (6, 2, '"Локомотив"');

-- Response #7
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (7, 2, '"Динамо"');

-- Response #8
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (8, 2, '"Cпapтaк"');

-- Insert correct response
UPDATE QUESTIONS
SET CORRECT_RESPONSE_ID = 6
WHERE QUESTION_ID = 2;




-- Question #3

INSERT INTO QUESTIONS (question_id, topic_id, description, points)
VALUES (3, 1, 'Кто из списка ниже не был капитаном сборной России по футболу?.', 20);

-- Response #9
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (9, 3, 'Руслан Нигматуллин');

-- Response #10
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (10, 3, 'Федор Смолов');

-- Response #11
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (11, 3, 'Игорь Семшов');

-- Response #12
INSERT INTO RESPONSES (response_id, question_id, description)
VALUES (12, 3, 'Александр Мостовой');

-- Insert correct response
UPDATE QUESTIONS
SET CORRECT_RESPONSE_ID = 9
WHERE QUESTION_ID = 3;