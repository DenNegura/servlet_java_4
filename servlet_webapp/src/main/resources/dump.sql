PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE topics (
                        topic_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        title VARCHAR(30),
                        description VARCHAR(255)
);
INSERT INTO topics VALUES(1,'Футбол','Mы пoдгoтoвили для вac cлoжный тecт, кoтopый пoзвoлит oпpeдeлить, нacкoлькo вaм знaкoм миp фyтбoлa.');
CREATE TABLE questions (
                           question_id INTEGER PRIMARY KEY AUTOINCREMENT ,
                           topic_id INTEGER,
                           description VARCHAR(100),
                           points INTEGER, correct_response_id INTEGER
    REFERENCES responses(response_id),
                           CONSTRAINT topic_pk FOREIGN KEY (topic_id)
                               REFERENCES TOPICS (topic_id)
);
INSERT INTO questions VALUES(1,1,'Какой футболист установил рекорд чемпионатов мира по числу забитых голов в одном матче?',20,4);
INSERT INTO questions VALUES(2,1,'Эту московскую футбольную команду во времена СССР называли "пятым колесом в телеге".',20,6);
INSERT INTO questions VALUES(3,1,'Кто из списка ниже не был капитаном сборной России по футболу?.',20,9);
CREATE TABLE responses (
                           response_id INTEGER PRIMARY KEY AUTOINCREMENT ,
                           question_id INTEGER,
                           description VARCHAR(30),
                           CONSTRAINT question_pk FOREIGN KEY (question_id)
                               REFERENCES questions (question_id)
);
INSERT INTO responses VALUES(1,1,'Эйсебио');
INSERT INTO responses VALUES(2,1,'Пеле');
INSERT INTO responses VALUES(3,1,'Мирослав Клозе');
INSERT INTO responses VALUES(4,1,'Олег Саленко');
INSERT INTO responses VALUES(5,2,'"Торпедо"');
INSERT INTO responses VALUES(6,2,'"Локомотив"');
INSERT INTO responses VALUES(7,2,'"Динамо"');
INSERT INTO responses VALUES(8,2,'"Cпapтaк"');
INSERT INTO responses VALUES(9,3,'Руслан Нигматуллин');
INSERT INTO responses VALUES(10,3,'Федор Смолов');
INSERT INTO responses VALUES(11,3,'Игорь Семшов');
INSERT INTO responses VALUES(12,3,'Александр Мостовой');
DELETE FROM sqlite_sequence;
INSERT INTO sqlite_sequence VALUES('topics',1);
INSERT INTO sqlite_sequence VALUES('questions',3);
INSERT INTO sqlite_sequence VALUES('responses',12);
COMMIT;
