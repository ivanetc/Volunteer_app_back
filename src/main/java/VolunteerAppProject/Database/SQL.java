package VolunteerAppProject.Database;

public class SQL {

    public static final String insertNewVolunteer = "INSERT INTO Volunteer VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String insertNewEvent = "INSERT INTO Event VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String insertNewTimeInterval = "INSERT INTO TimePeriodsTable VALUES(null, ?, ?, ?);";
    public static final String insertVolunteersToEvents = "INSERT INTO VolunteersToEvents VALUES(null, ?, ?);";
    public static final String insertTimeIntervalsToEvents = "INSERT INTO TimeIntervalsToEvents VALUES(null, ?, ?);";
    //public static final String getVolunteersForEvent = "SELECT volunteer_id FROM VolunteersToEvents WHERE event_id = ?";
}