

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task {

    public static void main(String[] args) {

    }

    /**
     * Calculates the time which is needed by the project
     * @param calendar Actual date which implements the Calendar interface (e.g. GregorianCalendar).
     * @param hours The hours which is needed to complete a project.
     * @return The date in Calendar format.
     */
    public Calendar whenFinish (Calendar calendar, int hours) {
        Calendar cleanCalendar = clearSecondsAndMilliseconds(calendar);
        int neededDays = calculateDays(hours);
        int neededHours = calculateHours(hours);
        Calendar finishDateCalendar=updateDaysAndHours(cleanCalendar, neededDays, neededHours);
        return finishDateCalendar;
    }

    private Calendar updateDaysAndHours(Calendar cleanCalendar, int neededDays, int neededHours) {
        Calendar updatedDays;
        updatedDays = updateDays(cleanCalendar, neededDays);
        Calendar updatedHours;
        updatedHours = updateHours(neededHours, updatedDays);
        return updatedHours;
    }

    private Calendar updateDays(Calendar cleanCalendar, int neededDays) {
        Calendar updatedDays;
        updatedDays=(Calendar) cleanCalendar.clone();
        for (int i = 0; i < neededDays; i++) {
            updatedDays.add(Calendar.DAY_OF_YEAR, 1);
            skipIfWeekend(updatedDays);
        }
        return updatedDays;
    }

    private Calendar updateHours(int neededHours, Calendar updatedDays) {
        Calendar updatedHours;
        updatedHours=(Calendar) updatedDays.clone();
        for (int i = 0; i < neededHours; i++) {
            updatedHours.add(Calendar.HOUR, 1);
            addDayIfWorkshiftOver(updatedHours);
        }
        return updatedHours;
    }

    private void addDayIfWorkshiftOver(Calendar updatedDays) {
        if(updatedDays.get(Calendar.HOUR_OF_DAY)==17) {
            updatedDays.add(Calendar.DAY_OF_YEAR, 1);
            updatedDays.set(Calendar.HOUR_OF_DAY, 9);
            skipIfWeekend(updatedDays);
        }
    }

    private void skipIfWeekend(Calendar updatedDays) {
        if (updatedDays.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            updatedDays.add(Calendar.DAY_OF_YEAR, 2);
        }
    }

    private int calculateHours(int neededHours) {
        neededHours=neededHours%8;
        return neededHours;
    }
    private int calculateDays(int neededHours) {
        int neededDays=0;
        neededDays=neededHours/8;
        return neededDays;
    }

    private Calendar clearSecondsAndMilliseconds(final Calendar cal) {
        Calendar cleanCal = (Calendar) cal.clone();
        cleanCal.clear(Calendar.SECOND);
        cleanCal.clear(Calendar.MILLISECOND);
        return cleanCal;
    }
}