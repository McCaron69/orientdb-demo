package com.ivushkin.todo.repository;

import com.ivushkin.todo.model.TaskModel;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksDatabase {
    OrientDB orient;
    private final String dbName;
    private final String dbUser;
    private final String dbUserPassword;
    OClass taskTable;

    public TasksDatabase(String dbUrl, String dbName, String dbUser, String dbUserPassword) {
        orient = new OrientDB("remote:" + dbUrl, OrientDBConfig.defaultConfig());

        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbUserPassword = dbUserPassword;

        initializeTablesIfNotExists();
    }

    private ODatabaseSession createDbSession() {
        return orient.open(dbName, dbUser, dbUserPassword);
    }

    public List<TaskModel> getTasksList() {
        List<TaskModel> list = new ArrayList<>();

        ODatabaseSession dbSession = createDbSession();

        OResultSet rs = dbSession.query("SELECT * FROM `Task`");

        while (rs.hasNext()) {
            OResult item = rs.next();

            boolean taskStatus = false;

            if(item.getProperty("status") != null && (boolean) item.getProperty("status")) {
                taskStatus = true;
            }

            TaskModel task = new TaskModel(item.getProperty("name"), taskStatus, item.getProperty("deadline"), item.getIdentity().get());
            list.add(task);
        }

        rs.close();
        dbSession.close();

        return list;
    }

    public void changeTaskStatus(String taskRecordIdString) {
        ODatabaseSession dbSession = createDbSession();

        ORID taskRecordId = new ORecordId("#" + taskRecordIdString);
        OElement task = dbSession.load(taskRecordId);

        if(task.getProperty("status")) {
            task.setProperty("status", false);
        } else {
            task.setProperty("status", true);
        }

        task.save();

        dbSession.close();
    }

    public void deleteTask(String taskRecordIdString) {
        ODatabaseSession dbSession = createDbSession();

        ORID taskRecordId = new ORecordId("#" + taskRecordIdString);
        OElement task = dbSession.load(taskRecordId);

        task.delete();

        dbSession.close();
    }

    public OVertex createTask(String taskName, Date taskDeadline) {
        ODatabaseSession dbSession = createDbSession();

        OVertex result = dbSession.newVertex("Task");
        result.setProperty("name", taskName);
        result.setProperty("status", false);
        result.setProperty("deadline", taskDeadline);
        result.save();

        dbSession.close();

        return result;
    }

    private void initializeTablesIfNotExists() {
        ODatabaseSession dbSession = createDbSession();

        taskTable = dbSession.getClass("Task");

        if(taskTable == null) {
            dbSession.createVertexClass("Task");
            taskTable = dbSession.getClass("Task");
        }

        if(taskTable.getProperty("status") == null) {
            taskTable.createProperty("status", OType.BOOLEAN);
        }

        if(taskTable.getProperty("name") == null) {
            taskTable.createProperty("name", OType.STRING);
            taskTable.createIndex("task_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
        }

        if(taskTable.getProperty("deadline") == null) {
            taskTable.createProperty("deadline", OType.DATE);
        }

        dbSession.close();
    }
}
