package com.madner;


import java.sql.*;
import java.util.function.Function;

class TaskStore {
    private static final String jdbcUrl = "jdbc:h2:/data/tasks;INIT=RUNSCRIPT FROM 'classpath:tasks.sql';";

    public static void getTasks() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement getStatement = connection.createStatement()) {
            ResultSet resultSet = getStatement.executeQuery("select * from tasks");
            System.out.println("All tasks...");
            if (resultSet.next()) {
                String task = printTask(resultSet);
                System.out.println(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String printTask(ResultSet resultSet) throws SQLException {
        return String.format("Task_Id: %d, Content: %s, isDone: %s", resultSet.getInt(1), resultSet.getString(2), processIsDone.apply(resultSet.getInt(3)));
    }

    private static final Function<Integer, String> processIsDone = (isDone) -> isDone == 1 ? "true" : "false";


    public static void addTask(String task) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement getStatement = connection.prepareStatement("insert into tasks (content, isDone) values (?, 0)")) {
            getStatement.setString(1, task);

            int insertCount = getStatement.executeUpdate();
            if (insertCount == 1) {
                System.out.println("Task added");
            } else {
                System.out.println("Task failed to add");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPending() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement getStatement = connection.createStatement();) {
            System.out.println(connection.isValid(1));

            ResultSet resultSet = getStatement.executeQuery("select * from tasks where isDone = 0;");
            System.out.println("Pending tasks...");
            if (resultSet.next()) {
                printTask(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void markPending(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement markPendingStatement = connection.prepareStatement("update tasks set isDone = 0 where id = ?")) {
            markPendingStatement.setInt(1, id);
            markPendingStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void markDone(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement markDoneStatement = connection.prepareStatement("update tasks set isDone = 1 where id = ?")) {
            markDoneStatement.setInt(1, id);
            markDoneStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDone() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement getStatement = connection.createStatement();) {
            System.out.println(connection.isValid(1));

            ResultSet resultSet = getStatement.executeQuery("select * from tasks where isDone = 1;");
            System.out.println("Completed tasks...");
            if (resultSet.next()) {
                printTask(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(int id, String newTask) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             PreparedStatement updateTaskStatement = conn.prepareStatement("update tasks set content=? where id=?")) {
            updateTaskStatement.setString(1, newTask);
            updateTaskStatement.setInt(2, id);
            int executed = updateTaskStatement.executeUpdate();
            if (executed != 1) System.out.println("Failed to update task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flushTasks() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl); Statement flushStatement = conn.createStatement()) {
            var flushedTaskCount = flushStatement.executeUpdate("truncate table tasks;");
            if(flushedTaskCount > 0) {
                System.out.println("Task list has been flushed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeTask(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement removeTaskStatement = connection.createStatement();) {
            System.out.println(connection.isValid(1));
            removeTaskStatement.executeUpdate("delete from tasks where id = %d".formatted(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}