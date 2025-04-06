package com.madner;

import org.apache.commons.cli.*;

class App {

    public App() {
    }

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("g", "get", false, "Get all tasks");
        options.addOption("i", "incomplete", false, "Get all pending tasks");
        options.addOption("d", "done", false, "Get all done tasks");
        options.addOption("f", "flush", false, "Flush all tasks");
        options.addOption("a", "add", true, "Add a new task");
        options.addOption("u", "update", true, "Update a task using task_id and new task");
        options.addOption("c", "complete", false, "Marks a task as complete using the task_id");
        options.addOption("p", "pending", false, "Marks a task as pending using the task_id");
        options.addOption("r", "remove", true, "Removes a task from list using the task_id");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption('a')) {
            TaskStore.addTask(cmd.getOptionValue('a'));
        } else if (cmd.hasOption('f')) {
            TaskStore.flushTasks();
        } else if (cmd.hasOption('g')) {
            TaskStore.getTasks();
        } else if (cmd.hasOption('i')) {
            TaskStore.getPending();
        } else if (cmd.hasOption('d')) {
            TaskStore.getDone();
        } else if (cmd.hasOption('u')) {
            var taskId = Integer.parseInt(cmd.getOptionValues('u')[0]);
            var newTask = cmd.getOptionValues('u')[1];
            TaskStore.updateTask(taskId, newTask);
        } else if (cmd.hasOption('a')) {
            TaskStore.addTask(cmd.getOptionValue('a'));
        } else if (cmd.hasOption('r')) {
            TaskStore.removeTask(Integer.parseInt(cmd.getOptionValue('r')));
        } else if (cmd.hasOption('p')) {
            TaskStore.markPending(Integer.parseInt(cmd.getOptionValue('r')));
        } else if (cmd.hasOption('c')) {
            TaskStore.markDone(Integer.parseInt(cmd.getOptionValue('r')));
        } else {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("tasker", options);
        }

    }

}