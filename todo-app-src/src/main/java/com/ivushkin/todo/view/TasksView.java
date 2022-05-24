package com.ivushkin.todo.view;

import com.ivushkin.todo.model.TaskModel;
import com.ivushkin.todo.repository.TasksDatabaseHandler;

import java.util.List;

public class TasksView {
    public static String createTasksPage() {
        List<TaskModel> tasksList = TasksDatabaseHandler.getInstance().getTasksList();

        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Tasks</title>\n" +
                "</head>" +
                "<body>" +
                "   <div style=\"width:960px;margin:0 auto 0 auto;\">" +
                "   <table>" +
                "   <tr>" +
                "       <th>Task</th>" +
                "       <th>Deadline</th>" +
                "       <th>Status</th>" +
                "   </tr>";

        for (TaskModel task : tasksList) {
            response += "<tr>" +
                    "   <td>" + task.getName() + "</td>" +
                    "   <td>" + task.getFormattedDate() + "</td>" +
                    "   <td>" + (task.getStatus() ? "<a href=\"/todo/updatestatus/" + task.getRecordIdAsStringWithoutHashtag() + "\" style=\"background-color:green;\">done</a>" : "<a href=\"/todo/updatestatus/" + task.getRecordIdAsStringWithoutHashtag() + "\" style=\"background-color:red;\">not done</a></td>") +
                    "   <td><a href=\"/todo/delete/" + task.getRecordIdAsStringWithoutHashtag() + "\">delete</a></td>" +
                    "</tr>";
        }

        response += "</table><a href=\"/todo/add\">add new task</a>" +
                "</div>" +
                "</body>\n" +
                "</html>";

        return response;
    }
    public static String createAddTaskPage() {
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Tasks</title>\n" +
                "</head>" +
                "<body>" +
                "   <div style=\"width:960px;margin:0 auto 0 auto;\">" +
                "       <form action=\"\" method=\"post\">" +
                "           <label for=\"name\">Task name:</label><br>" +
                "           <input type=\"text\" id=\"name\" name=\"name\"><br>" +
                "           <label for=\"deadline\">Deadline date:</label><br>" +
                "           <input type=\"date\" id=\"deadline\" name=\"deadline\"><br><br>" +
                "           <input type=\"submit\" value=\"Submit\">" +
                "       </form>" +
                "   </div>" +
                "</body>" +
                "</html>";

        return response;
    }
}
