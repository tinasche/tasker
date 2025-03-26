package com.madner;


import java.sql.*;

class TaskStore {

    private final static String fetchAllStatement = "SELECT * from tasks";
    private final static String fetchAllPendingStatement = "SELECT * from tasks WHERE";
    private final static String insertStatement = "INSERT INTO tasks () VALUES ()";
    private final static String dbUrl = "jdbc:sqlite:tasks.db";

    public TaskStore() {
        try (Connection conn = DriverManager.getConnection(dbUrl); Statement statement = conn.createStatement()) {
            String createStatement = """
                    CREATE TABLE IF NOT EXISTS tasks(
                    id INT PRIMARY KEY,
                    content TEXT,
                    isDone BOOLEAN NOT NULL CHECK(isdone IN (0,1)));
                    """;
            int executed = statement.executeUpdate(createStatement);
            System.out.println(executed);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static String AddTask(String task) {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement statement = conn.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(fetchAllStatement);
            System.out.println(conn.getClientInfo());


            return "Adding a new task with title:" + task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static String RemoveTask(int id) {
        return "Removing a task with id: " + id;
    }

    static String EditTask(int id, String task) {
        return "Editing task with id %d with new content as %s".formatted(id, task);
    }

    static String MarkTaskAsPending(int id) {
        return "Mark a task with %d as pending".formatted(id);
    }

    static String MarkTaskAsDone(int id) {
        return "Mark a task with %d as done".formatted(id);
    }

    static String GetTasks() {
        return "Fetching all tasks";
    }
}
