package Commands.CreationCommands;

import Commands.Command;
import InterfaceAdaptors.DatabaseController;

import java.util.Stack;

/**
 * TODO REMOVE THIS SENTENCE
 *
 * Prints the history of the user's actions
 */
public class PrintHistoryCommand implements Command {
    private final DatabaseController TheController;

    public PrintHistoryCommand(DatabaseController theController){
        TheController = theController;
    }

    @Override
    public void execute() {
        Stack<Command> history = this.TheController.getCommandHistory();
        for (Command item : history){
            System.out.println(item);
        }
    }

    @Override
    public String toString() {
        return "Printed the history of the commands";
    }
}
