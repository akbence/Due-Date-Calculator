
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class Tests {

    Task task;
    GregorianCalendar compare;
    GregorianCalendar baseDate;

    @BeforeEach
    void initialize() {
        task= new Task();
        compare= new GregorianCalendar();
        compare.clear(Calendar.SECOND);
        compare.clear(Calendar.MILLISECOND);

        baseDate= new GregorianCalendar();
        baseDate.clear(Calendar.SECOND);
        baseDate.clear(Calendar.MILLISECOND);
        baseDate.set(2018, 5, 21, 10, 15); //2018.06.21. 10:15 THURSDAY
    }

    @Test
    void testPlusZero() {
        compare.set(2018, 5, 21, 10, 15);
        assertEquals(compare,task.whenFinish(baseDate,0));
    }

    @Test
    void testPlusOneHour() {
        compare.set(2018, 5, 21, 11, 15);
        assertEquals(compare,task.whenFinish(baseDate,1));
    }

    @Test
    void testPlusNextDay() {
        compare.set(2018, 5, 22, 10, 15);
        assertEquals(compare,task.whenFinish(baseDate, 8));
    }

    @Test
    void testWeekendNotAllowed() {
        compare.set(2018, 5, 25, 10, 15);
        assertEquals(compare,task.whenFinish(baseDate, 16));
    }
    @Test
    void testAfter5Pm() {
        compare.set(2018, 5, 22, 9, 15);
        assertEquals(compare,task.whenFinish(baseDate,7));
    }

    @Test
    void  testDontReachWeekendWithHours(){
        compare.set(2018, 5, 25, 9, 15);
        assertEquals(compare,task.whenFinish(baseDate,15));
    }

    @Test
    void testBefore9AmException() {
        baseDate.set(2018, 5, 21, 7, 15);
        assertThrows(IllegalArgumentException.class, () -> {
            task.whenFinish(baseDate,0);
        });
    }

    @Test
    void testAfter5PmException() {
        baseDate.set(2018, 5, 21, 17, 15);
        assertThrows(IllegalArgumentException.class, () -> {
            task.whenFinish(baseDate,0);
        });
    }

    @Test
    void testWeekendStartException() {
        baseDate.set(2018, 5, 23, 12, 15);
        assertThrows(IllegalArgumentException.class, () -> {
            task.whenFinish(baseDate,0);
        });
    }



}