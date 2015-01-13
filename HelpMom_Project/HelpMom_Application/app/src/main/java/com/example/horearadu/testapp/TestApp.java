package com.example.horearadu.testapp;

import android.app.Application;

/**
 * Created by horearadu on 11/01/15.
 */
public class TestApp extends Application {
    private Integer loggedUserID;
    private Integer currentTopicID;

    public TestApp() {
        super();
    }

    public Integer getLoggedUserID() {
        return loggedUserID;
    }

    public Integer getCurrentTopicID() {
        return currentTopicID;
    }

    public void setLoggedUserID(Integer id) {
        loggedUserID = id;
    }

    public void setCurrentTopicID(Integer id) {
        currentTopicID = id;
    }

}
