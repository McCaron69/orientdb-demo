package com.ivushkin.todo.model;

import com.orientechnologies.orient.core.id.ORID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskModel {
    private String name;
    private boolean status;
    private Date deadline;
    private ORID recordId;

    public TaskModel(String taskName, boolean taskStatus, Date taskDeadline, ORID recordId) {
        this.name = taskName;
        this.status = taskStatus;
        this.deadline = taskDeadline;
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(deadline);
        return formattedDate;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public ORID getRecordId() {
        return recordId;
    }

    public String getRecordIdAsStringWithoutHashtag() {
        String modifiedRecordId = recordId.toString();
        modifiedRecordId = modifiedRecordId.substring(1);
        return modifiedRecordId;
    }

    public void setRecordId(ORID recordId) {
        this.recordId = recordId;
    }
}
