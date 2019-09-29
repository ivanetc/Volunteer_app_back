package VolunteerAppProject.Database;

public class SQL {

    public static final String insertNewVolunteer = "INSERT INTO Volunteer VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String insertNewEvent = "INSERT INTO Event VALUES(null, ?, ?, ?, ?, ?, ?, ?);";
    public static final String insertNewTimeInterval = "INSERT INTO TimePeriodsTable VALUES(null, ?, ?, ?);";
    public static final String insertVolunteersToEvents = "INSERT INTO VolunteersToEvents VALUES(null, ?, ?);";
    public static final String insertTimeIntervalsToEvents = "INSERT INTO TimeIntervalsToEvents VALUES(null, ?, ?);";

    public static final String selectVolunteerInfo = "SELECT * FROM Volunteer WHERE user_id = ?";
    public static final String selectEventInfo = "SELECT * FROM Event WHERE event_id = ?";
    public static final String selectFutureEvents = "SELECT * FROM Event WHERE date > CURDATE()";
    public static final String selectNamesForEvent = "SELECT v.surname, v.first_name FROM Volunteer AS v, VolunteersToEvents as vte WHERE vte.volunteer_id = v.user_id AND vte.event_id = ?";

    public static final String groupBySize = "SELECT v.clothes_size, COUNT(*) AS sizes_count FROM VolunteersToEvents AS vte, Volunteer AS v WHERE vte.volunteer_id = v.user_id AND vte.event_id = ? GROUP BY v.clothes_size";
}