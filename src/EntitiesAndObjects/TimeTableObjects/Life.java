 package EntitiesAndObjects.TimeTableObjects;

import java.sql.Time;

public class Life extends TimeTableObject{
    private final String description;

    public Life(Time theStartTime,
                Time theEndTime,
                String theLocation,
                String theDate,
                String term,
                String description) {
        super(theStartTime, theEndTime, theLocation, theDate, term);
        this.description = description;
    }

    /**
     * Get the description of the activity
     *
     * @return description The description of the activity
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.getStartTime() + " - " + this.getEndTime() + ": " +
                this.description + " at " + this.getLocation();
    }
}