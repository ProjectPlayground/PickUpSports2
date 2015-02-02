package pickupsports2.ridgewell.pickupsports2.data;

import org.joda.time.DateTime;

import java.util.List;

import ridgewell.pickupsports2.common.Event;

/**
 *
 * An interface for classes that can provide a list of pickup sport
 * events that have been registered. Subclasses can store the Events
 * in a database, server, etc.
 *
 * Created by jules on 2/2/15.
 */
public interface EventSource {

    /**
     * Returns a list of all Events that begin on or after the start date
     * but before the end date.
     *
     * @param start the starting date of the date range
     * @param end the ending date of the date range
     * @return a list of all Events that begin on or after the start date
     *         but before the end date
     */
    public List<Event> getEventsInDateRange(DateTime start, DateTime end);
}
