package com.ivushkin.todo.repository;

public class TasksDatabaseHandler {
    static TasksDatabase dbInstance;

    public static TasksDatabase getInstance() { return dbInstance; }

    public static void createDatabaseConnection(String dbUrl, String dbName, String dbUser, String dbUserPassword) {
        dbInstance = new TasksDatabase(dbUrl, dbName, dbUser, dbUserPassword);
    }
}
