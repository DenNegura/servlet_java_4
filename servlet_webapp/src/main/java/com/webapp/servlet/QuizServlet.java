package com.webapp.servlet;

import com.webapp.database.DatabaseConnection;
import com.webapp.model.Question;
import com.webapp.model.Response;
import com.webapp.model.Topic;
import com.webapp.repository.QuestionRepository;
import com.webapp.repository.ResponseRepository;
import com.webapp.repository.TopicRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class QuizServlet extends HttpServlet {
    // поддерживает два пути
    private static final String PATH_QUIZ = "quiz";

    private static final String PATH_RESULT = "quiz/result";

    // класс подключения к базе данных
    private DatabaseConnection databaseConnection;

    // класс для чтения ответов из бд
    private ResponseRepository responseRepository;
    // класс для чтения вопросов из бд
    private QuestionRepository questionRepository;
    // класс для чтения тем из бд
    private TopicRepository topicRepository;
    // тема, которая хранится в памяти, пока пользователь пользуется сервлетом
    private Topic topic;

    // метод вызываемый при инициализации сервлета сервером
    @Override
    public void init() {
        // настроика базы данных и производных классов
        databaseConnection = new DatabaseConnection(
                        "jdbc:sqlite:C:/Projects/java/lab_3/servlet_webapp/src/main/resources/questions.sqlite");
        responseRepository = new ResponseRepository(
                databaseConnection.getConnection());
        questionRepository = new QuestionRepository(
                databaseConnection.getConnection(), responseRepository);
        topicRepository = new TopicRepository(
                databaseConnection.getConnection(), questionRepository);
    }

    // обработчик GET запросов
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("part : " + request.getRequestURI());

        // если путь совпадает с переменной PATH_RESULT, тогда вызывается метод sendResult
        // если путь совпадает с переменной PATH_QUIZ, тогда вызывается метод sendQuiz
        if (request.getRequestURI().contains(PATH_RESULT)) {
            System.out.println("Enter in " + PATH_RESULT);
            sendResult(request, response);
        } else if (request.getRequestURI().contains(PATH_QUIZ)) {
            System.out.println("Enter in " + PATH_QUIZ);
            sendQuiz(request, response);
        }
    }

    // метод вызываемый при уничтожении сервлета
    @Override
    public void destroy() {
        // закрытие соединения с базой данных
        databaseConnection.closeConnection();
    }
    // метод отправляет тест пользователю
    private void sendQuiz(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            try {
                // в начале получаем id темы
                Long idTopic = Long.valueOf(request.getParameter("topic"));

                // нахоим соответствующую тему
                // тема содержит свои вопросы, каждый вопрос содержит ответы на себя
                topic = topicRepository.findById(idTopic).orElseThrow();

                response.setContentType("text/html;charset=UTF-8");

                // создание html страницы
                StringBuffer htmlPage = new StringBuffer();
                htmlPage.append("<html>")
                        .append("<head> <meta charset=\"UTF-8\">")
                        .append("<title> Quiz topic : ").append(topic.getTitle()).append(" </title> </head>");
                htmlPage.append("<body> <div style=\"width: 50%\">");
                // форма отправки на сервер ответов
                htmlPage.append("<form action = \"")
                        // на страницу PATH_RESULT (/quiz/result)
                        .append(PATH_RESULT)
                        // по методу GET
                        .append("\" method = \"GET\">");
                htmlPage.append("<h1> ").append(topic.getTitle()).append(" </h1>");
                htmlPage.append("<p> ").append(topic.getDetails()).append(" </p>");
                // прогоняем через цикл все вопросы на тему
                for (Question question : topic.getQuestions()) {
                    htmlPage.append("<h4> ").append(question.getDescription()).append(" </h4>");
                    // прогоняем все ответы на вопрос
                    for (Response responseForQuestion : question.getResponses()) {
                        htmlPage.append("<input type=\"radio\" name=\"")
                                .append(question.getIdQuestion())
                                .append("\"/value='")
                                .append(responseForQuestion.getDescription())
                                .append("'>")
                                .append(responseForQuestion.getDescription())
                                .append("<br/>");
                    }
                }
                htmlPage.append("<br/><br/><input type=\"submit\"/>");
                htmlPage.append("</form> </div> </body>")
                        .append("</html>");
                // отправка пользователю
                out.println(htmlPage);
            } catch (Exception e) {
                out.println("error");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // метод для обработки пройденого теста
    private void sendResult(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            try {
                response.setContentType("text/html;charset=UTF-8");
                int userPoints = 0; // подсчёт очков
                int topicPoints = 0;
                StringBuffer htmlPage = new StringBuffer();
                // создание html страницы
                htmlPage.append("<html>")
                        .append("<head> <meta charset=\"UTF-8\">")
                        .append("<title> Result for quiz : ").append(topic.getTitle()).append(" </title> </head>");
                htmlPage.append("<body> <div style=\"width: 50%\">");
                htmlPage.append("<h1> ").append(topic.getTitle()).append(" </h1>");
                htmlPage.append("<p> ").append(topic.getDetails()).append(" </p>");
                // следующий цикл выводит вопрос, правильный ответ на него, ответ пользователя
                // количество заработанных баллов, и баллов отданных на вопрос
                for (Question question : topic.getQuestions()) {
                    String userResponse = request.getParameter("" + question.getIdQuestion());
                    String correctResponse = question.getCorrectResponse().getDescription();
                    int questionPoints = correctResponse.equals(userResponse) ? question.getPoints() : 0;
                    userPoints += questionPoints;
                    topicPoints += question.getPoints();
                    htmlPage.append("<h4> ").append(question.getDescription()).append(" </h4>");
                    htmlPage.append("<ul>");
                    htmlPage.append("<li> Верный ответ : ")
                            .append(correctResponse)
                            .append("</li>");
                    htmlPage.append("<li> Выбранный ответ : ")
                            .append(userResponse == null ? "" : userResponse)
                            .append("</li>");
                    htmlPage.append("<li> Набрано баллов : ")
                            .append(questionPoints)
                            .append(" / ").append(question.getPoints())
                            .append("</li>");
                    htmlPage.append("</ul>");
                }
                htmlPage.append("</div> </body>")
                        .append("<html>");
                htmlPage.append("<br><br><p> Всего набрано баллов: ")
                        .append(userPoints).append(" / ").append(topicPoints);
                out.println(htmlPage);
            } catch (Exception e) {
                out.println("error");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
