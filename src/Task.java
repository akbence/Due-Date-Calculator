

import java.util.Calendar;

public class Task {

    /**
     * Calculates the time which is needed by the project
     *
     * @param calendar Actual date which implements the Calendar interface (e.g. GregorianCalendar).
     * @param hours    The hours which is needed to complete a project.
     * @return The date in Calendar format.
     */
    public Calendar whenFinish(Calendar calendar, int hours) {
        checkIfValidDate(calendar);
        Calendar cleanCalendar = clearSecondsAndMilliseconds(calendar);
        int neededDays = calculateDays(hours);
        int neededHours = calculateHours(hours);
        Calendar finishDateCalendar = updateDaysAndHours(cleanCalendar, neededDays, neededHours);
        return finishDateCalendar;
    }

    private void checkIfValidDate(Calendar calendar) {
        checkIfWorkingHours(calendar);
        checkIfWeekday(calendar);
    }

    private void checkIfWeekday(Calendar calendar) {
        if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            throw new IllegalArgumentException("Basedate not in weekday");
        }
    }

    private void checkIfWorkingHours(Calendar calendar) {
        if (calendar.get(Calendar.HOUR) < 9 || calendar.get(Calendar.HOUR) > 17) {
            throw new IllegalArgumentException("Basedate not in working hours");
        }
    }

    private Calendar updateDaysAndHours(Calendar cleanCalendar, int neededDays, int neededHours) {
        Calendar updatedDays = updateDays(neededDays, cleanCalendar);
        Calendar updatedHours = updateHours(neededHours, updatedDays);
        return updatedHours;
    }

    private Calendar updateDays(int neededDays, Calendar cleanCalendar) {
        Calendar updatedDays = (Calendar) cleanCalendar.clone();
        for (int i = 0; i < neededDays; i++) {
            updatedDays.add(Calendar.DAY_OF_YEAR, 1);
            updatedDays = addTwoDaysIfSaturday(updatedDays);
        }
        return updatedDays;
    }

    private Calendar updateHours(int neededHours, Calendar updatedDays) {
        Calendar updatedHours = (Calendar) updatedDays.clone();
        for (int i = 0; i < neededHours; i++) {
            updatedHours.add(Calendar.HOUR, 1);
            updatedHours = addDayIfWorkshiftOver(updatedHours);
        }
        return updatedHours;
    }

    private Calendar addDayIfWorkshiftOver(Calendar calendar) {
        Calendar checkedWorkshift = (Calendar) calendar.clone();
        if (checkedWorkshift.get(Calendar.HOUR_OF_DAY) == 17) {
            checkedWorkshift.add(Calendar.DAY_OF_YEAR, 1);
            checkedWorkshift.set(Calendar.HOUR_OF_DAY, 9);
            checkedWorkshift = addTwoDaysIfSaturday(checkedWorkshift);
        }
        return checkedWorkshift;
    }

    private Calendar addTwoDaysIfSaturday(Calendar calendar) {
        Calendar checkIfWeekend = (Calendar) calendar.clone();
        if (checkIfWeekend.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            checkIfWeekend.add(Calendar.DAY_OF_YEAR, 2);
        }
        return checkIfWeekend;
    }

    private int calculateHours(int neededHours) {
        neededHours = neededHours % 8;
        return neededHours;
    }

    private int calculateDays(int neededHours) {
        int neededDays = neededHours / 8;
        return neededDays;
    }

    private Calendar clearSecondsAndMilliseconds(final Calendar cal) {
        Calendar cleanCal = (Calendar) cal.clone();
        cleanCal.clear(Calendar.SECOND);
        cleanCal.clear(Calendar.MILLISECOND);
        return cleanCal;
    }
}
