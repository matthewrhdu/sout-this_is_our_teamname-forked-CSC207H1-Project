package Interfaces;

import Controllers.DatabaseController;
import Controllers.CommandFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


/**
 * An UserInterface class
 * It sets the DatabaseController.
 *
 * === Private Attributes ===
 *  control: The DatabaseController object.
 *  operator: The OperatorInterface object.
 */
public class UserInterface {
    private final DatabaseController control;
    private final OperatorInterface operator;

    /**
     * Constructor of the UserInterface.
     * Sets control and operator.
     */
    public UserInterface() {
        this.control = new DatabaseController();
        this.operator = new OperatorInterface(this.control);
    }


    /**
     * Runs the UserInterface.
     *
     * The IOException will be raised if the given file, datasource.txt, is not found.
     */
    public void run() throws IOException {
        // As long as the program is running
        boolean running = true;

        // Set the DataGetter of the program to be the one in the datasource.json.
        this.readDatasource();
        CommandFactory theFactory = new CommandFactory(control);
        this.operator.SetDatasource(theFactory, this.readDatasource());


        String[] banFunctions = this.readFunctions();
        System.out.println(Arrays.toString(banFunctions));
        if (!Arrays.toString(banFunctions).equals("[]")) {
            theFactory.setAllowedFunctions(banFunctions);
        }


        while (running) {
            System.out.println("\nCurrent datasource: " + this.operator.getDatasource());
            running = control.run();
        }
    }


    // ============================ Helper Methods =================================
    /**
     * Gets the OperatorInterface
     *
     * @return An OperatorInterface object.
     */
    public OperatorInterface getOperator() {
        return this.operator;
    }


    /**
     * Read the datasource.txt file.
     *
     * The IOException will be raised if the given file is not found.
     *
     * @return A string in datasource.txt.
     */
    private String readDatasource() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // Read the file.
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/Interfaces/datasource.txt"));
        String s;
        // Check whether the datasource.json is empty.
        if ((s = bufferedReader.readLine()) != null) {
            stringBuilder.append(s.trim());
        }
        return stringBuilder.toString();
    }


    /**
     * Read the functions.txt file.
     *
     * The IOException will be raised if the given file is not found.
     *
     * @return A string in datasource.txt.
     */
    private String[] readFunctions() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // Read the file.
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/Interfaces/functions.txt"));
        String s;
        String newString1 = "";
        // Check whether the datasource.json is empty.
        if ((s = bufferedReader.readLine()) != null) {
            stringBuilder.append(s.trim());
            String rawString = stringBuilder.toString();
            newString1 = rawString.substring(1, rawString.length() - 1);
        }
        // rawString is the String contains "[" and "]", need to delete them first and then convert the String into Array

        System.out.println(newString1);

        return newString1.split(",\\s+");
    }
}
