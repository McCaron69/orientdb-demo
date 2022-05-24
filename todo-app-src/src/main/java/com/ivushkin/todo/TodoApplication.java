package com.ivushkin.todo;

import com.ivushkin.todo.repository.TasksDatabaseHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);

		TasksDatabaseHandler.createDatabaseConnection("orientdb", "tasks", "admin", "admin");
	}
}
