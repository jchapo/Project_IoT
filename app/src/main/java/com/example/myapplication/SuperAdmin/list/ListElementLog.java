package com.example.myapplication.SuperAdmin.list;

import java.io.Serializable;
import java.util.Date;

public class ListElementLog implements Serializable {
    private Date timestamp;
    private String user;
    private LogType logType;
    private String message;

    public ListElementLog(Date timestamp, String user, LogType logType, String message) {
        this.timestamp = timestamp;
        this.user = user;
        this.logType = logType;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
enum LogType {
    INFO,
    WARNING,
    ERROR,
    CRITICAL
}