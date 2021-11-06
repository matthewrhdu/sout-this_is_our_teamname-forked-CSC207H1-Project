package DatabaseController;

import DataCollection.DataGetter;
import FunctionsAndCommands.Commands.Command;
import FunctionsAndCommands.Commands.CreationCommands.GetAllTimeTablesCommand;
import FunctionsAndCommands.Commands.CreationCommands.MakeCourseCommand;
import FunctionsAndCommands.Commands.CreationCommands.MakeEventCommand;
import FunctionsAndCommands.Commands.CreationCommands.PrintHistoryCommand;
import GlobalHelpers.Constants;
import GlobalHelpers.InvalidInputException;
import TimeTableStuff.TimeTableManager;

/**
 * A factory class to create the individual commands of the class.
 *
 * === Attributes ===
 * courseManager: The TimetableManager to connect to
 * dataSource: The Data Getter to get the data from
 * controller: The controller that this is connected to
 * allowedFunctions: The list of allowed functions for the program as set out
 *  by the OperatorInterface
 */
public class CommandFactory {
    private TimeTableManager courseManager;
    private DataGetter dataSource;
    private final DatabaseController controller;
    private final String[] allowedFunctions;

    final String SCHEDULE_COURSE = Constants.COURSE;
    final String SCHEDULE_EVENT = Constants.NON_COURSE_OBJECT;
    final String GET_ALL_TIMETABLE = "Get All TimeTables";
    final String PRINT_HISTORY = "Get History";

    /**
     * Constructor. Sets the TimeTable Manager and DataSource of the file
     * TODO @Caules Use OperatorInterface
     * TODO @Aiden Add DataLoaders here too!
     */
    public CommandFactory(DatabaseController theController){
        this.courseManager = null;
        this.dataSource = null;
        this.controller = theController;
        // TODO @Caules please modify as needed
        this.allowedFunctions = new String[]{SCHEDULE_COURSE, SCHEDULE_EVENT,
                GET_ALL_TIMETABLE, PRINT_HISTORY};
    }

    /**
     * This is the Factory that will create all the command objects. PLEASE
     * ADD TO THIS CLASS ALL NEW FUNCTIONS THAT YOU ADD!!!
     *
     * What this should do is it will return the corresponding command object
     * to whatever has been inputted by the user. Please see
     * `DatabaseController` for how the command objects will be used
     *
     * @param inputCommand The STRING input command of the user. IT MUST
     *                     CORRESPOND TO THE STATIC CONSTANTS ABOVE
     * @return The correct command object
     * @throws InvalidInputException If the inputCommand is invalid, throw this!
     */
    public Command getCommand(String inputCommand) throws InvalidInputException {
        assert this.dataSource != null && this.courseManager != null;
        // To schedule a course
        switch (inputCommand) {
            case SCHEDULE_COURSE:
                return new MakeCourseCommand(courseManager, dataSource);
            // To schedule an event
            case SCHEDULE_EVENT:
                return new MakeEventCommand(courseManager);
            // To get all the timetables in the TimeTableManager
            case GET_ALL_TIMETABLE:
                return new GetAllTimeTablesCommand(courseManager);
            case PRINT_HISTORY:
                return new PrintHistoryCommand(controller);


            // ... ADD YOUR NEW OBJECTS HERE!

            // The command is invalid
            default:
                throw new InvalidInputException();
        }
    }

    // ========================= Setters and Getters ===========================
    /**
     * Returns an string array of all the allowable functions of the program
     *
     * @return a string array of all the allowed functions of the program
     */
    public String[] getAllowedFunctions() {
        return allowedFunctions;
    }

    /**
     * Sets the TimeTableManager to connect to
     * @param theManager the TimeTableManager to connect to
     */
    public void setManager(TimeTableManager theManager){
        this.courseManager = theManager;
    }

    /**
     * Sets the DataGetter to connect to
     * @param theDataSource the DataGetter to connect to
     */
    public void setDataSource(DataGetter theDataSource){
        this.dataSource = theDataSource;
    }
}