package com.ivushkin.todo.controller;

import com.ivushkin.todo.model.FormTaskModel;
import com.ivushkin.todo.repository.TasksDatabaseHandler;
import com.ivushkin.todo.view.TasksView;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("todo")
public class TasksController {
    @GetMapping
    public ResponseEntity getTasks() {
        return ResponseEntity.ok(TasksView.createTasksPage());
    }

    @GetMapping(path = "/updatestatus/{taskRecordId}")
    public RedirectView updateTask(@PathVariable String taskRecordId) {
        TasksDatabaseHandler.getInstance().changeTaskStatus(taskRecordId);

        return new RedirectView("/todo");
    }

    @GetMapping(path = "/add")
    public ResponseEntity getAddTaskForm() {
        return ResponseEntity.ok(TasksView.createAddTaskPage());
    }

    @PostMapping("add")
    public RedirectView addTask(@RequestParam("name") String name, @RequestParam String deadline) throws Exception {
        Date deadlineDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);

        TasksDatabaseHandler.getInstance().createTask(name, deadlineDateFormatted);
        return new RedirectView("/todo");
    }

    @GetMapping(path = "/delete/{taskRecordId}")
    public RedirectView deleteTask(@PathVariable String taskRecordId) {
        TasksDatabaseHandler.getInstance().deleteTask(taskRecordId);

        return new RedirectView("/todo");
    }
}
