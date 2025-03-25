package com.madner;


import org.apache.commons.cli.*;
import java.io.PrintWriter;

public class App {

    private static final Option AddToList = new Option("a", "add", true, "Add a new task to the list");
    private static final Option FetchTasks = new Option("f", "fetch", false, "Fetch all tasks in the list");
    private static final Option RemoveFromList = new Option("r", "remove", true, "Remove a task from the list");
    private static final Option MarkAsDone = new Option("d", "done", true, "Add a new task to the list");
    private static final Option MarkAsPending = new Option("p", "pending", true, "Add a new task to the list");
    private static final Option EditTask = new Option("e", "edit", true, "Add a new task to the list");

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);

        printWriter.println("Tasker: " + Math.class.getPackage().getImplementationVersion());
        printWriter.println();

        helpFormatter.printOptions(printWriter, 100, options, 4, 2);
        helpFormatter.printUsage(printWriter, 100, "tasker [options] [arguments]");

        printWriter.close();
    }

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(AddToList);
        options.addOption(RemoveFromList);
        options.addOption(MarkAsDone);
        options.addOption(MarkAsPending);
        options.addOption(EditTask);
        options.addOption(FetchTasks);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("a")) {
            System.out.println("Adding a new task with title:" + cmd.getOptionValue("a"));
        } else if (cmd.hasOption("r")) {
            System.out.println("Removing a task with id: " + cmd.getOptionValue("r"));
        } else if (cmd.hasOption("d")) {
            System.out.println("Mark task as done for task with id: " + cmd.getOptionValue("d"));
        } else if (cmd.hasOption("p")) {
            System.out.println("Mark task as pending for task with id: " + cmd.getOptionValue("p"));
        } else if (cmd.hasOption("e")) {
            System.out.println("Edit a task with id: " + cmd.getOptionValue("e"));
        } else if (cmd.hasOption("f")) {
            System.out.println("Fetching all tasks ");
        }
        else {
            App.printHelp(options);
        }

    }
}
