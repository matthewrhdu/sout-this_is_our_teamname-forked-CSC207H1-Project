package FunctionsAndCommands.Commands.CreationCommands;

import EntitiesAndObjects.NonCourseObject;
import FunctionsAndCommands.Commands.Command;
import GlobalHelpers.Constants;
import GlobalHelpers.InputCheckers.Predicate;
import GlobalHelpers.InputCheckers.InputChecker;
import GlobalHelpers.Search;
import GlobalHelpers.StringToTime;
import TimeTableStuff.TimeTableManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * A command to create a Non-Course Object.
 *
 * === Private Attributes ===
 * manager: The manager that will eventually schedule the object
 */
public class MakeEventCommand implements Command {
    // Some Constants:
    final String NAME = "Name";
    final String START_TIME = "Start Time";
    final String END_TIME = "End Time";
    final String LOCATION = "Location";
    final String DATE = "Date";
    final String TERM = "Term";
    final String TYPE = "Type";

    private final TimeTableManager manager;
    private NonCourseObject scheduledObject;

    /**
     * A constructor to set the command
     * @param theManager The manager to connect to
     */
    public MakeEventCommand(TimeTableManager theManager){
        this.manager = theManager;
        this.scheduledObject = null;
    }

    /**
     * Prompts the user to create a timetableObject
     */
    @Override
    public void execute() {
        LinkedHashMap<String, InputChecker> prompts = new LinkedHashMap<>();
        prompts.put(NAME, new InputChecker("Enter a name for an object " +
                "(eg; Dinner with Prof Gries and Friends)", new isTrivial()));
        prompts.put(START_TIME, new InputChecker("Enter the Start Time (in" +
                " a 12h clock format. (eg: 10:00AM or 9:00PM. No space " +
                "between time and AM/PM)",
                new isTime()));
        prompts.put(END_TIME, new InputChecker("Enter the End Time (in a " +
                "12h clock format. (eg: 10:00AM or 9:00PM. No space between " +
                "time and AM/PM)", new isTime()));
        prompts.put(LOCATION, new InputChecker("Enter the Location (eg; " +
                "MY150, Home, Middle of Nowhere)", new isTrivial()));
        prompts.put(DATE, new InputChecker("Enter the Day of the week (eg;" +
                " Monday, Tuesday, Wednesday, etc.)", new isDate()));
        prompts.put(TERM, new InputChecker("Enter the Term (Fall/Winter)",
                new isTrivial())); // TODO Fix me!
        prompts.put(TYPE, new InputChecker("Enter the Type of the Object " +
                "(Event, Activity, etc ...", new isTrivial())); // TODO Fix me!

        HashMap<String, String> responses = new HashMap<>();

        for (String prompt : prompts.keySet()) {
            responses.put(prompt, prompts.get(prompt).checkCorrectness());
        }

        NonCourseObject toSchedule = new NonCourseObject(
                StringToTime.makeTime(responses.get(START_TIME)),
                StringToTime.makeTime(responses.get(END_TIME)),
                responses.get(LOCATION),
                responses.get(DATE),
                responses.get(TERM),
                responses.get(TYPE));

        this.scheduledObject = toSchedule;

        manager.schedule(toSchedule);
    }

    // ============================= Helper Methods ============================
    @Override
    public String toString() {
        if (this.hasScheduled()){
            return "Scheduled the item" + this.scheduledObject.toString();
        } else {
            return "No Course Scheduled";
        }
    }

    /**
     * Return if there has already been an object been scheduled
     * @return true iff there has been a course scheduled.
     */
    protected boolean hasScheduled(){
        return scheduledObject != null;
    }

    // ====================== Predicates =======================================
    private static class isTime extends Predicate {
        @Override
        public boolean run(String prompt) {
            return Pattern.matches("^[0-24]{2}:[0-5][0-9][AP]M$",
                    prompt);
        }
    }

    private static class isTrivial extends Predicate {
        @Override
        public boolean run(String prompt) {
            return true;
        }
    }

    private static class isDate extends Predicate {
        @Override
        public boolean run(String prompt) {
            String[] validDates = {Constants.MONDAY, Constants.TUESDAY,
                    Constants.WEDNESDAY, Constants.THURSDAY, Constants.FRIDAY
                    , Constants.SATURDAY, Constants.SUNDAY};
            return Search.BinarySearch(prompt, validDates);
        }
    }
}