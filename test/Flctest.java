import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Flctest {

    private Timetable timetable;
    private BookingManager bookingManager;
    private Member member1;
    private Member member2;
    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();
        bookingManager = new BookingManager();
        member1 = new Member(1, "Alice");
        member2 = new Member(2, "Bob");
        lesson = new Lesson(1, "Yoga", "Saturday", "Morning", 1, 5, 10.00);
        timetable.addLesson(lesson);
    }
    @Test
    public void testBookingSuccess() {
        Booking booking = bookingManager.makeBooking(member1, lesson);
        assertNotNull(booking, "Booking should succeed when lesson has space");
        assertEquals(Booking.Status.BOOKED, booking.getStatus());
    }

    @Test
    public void testNoDuplicateBooking() {
        bookingManager.makeBooking(member1, lesson);
        Booking duplicate = bookingManager.makeBooking(member1, lesson);
        assertNull(duplicate, "Duplicate booking should not be allowed");
    }

    @Test
    public void testLessonCapacity() {
        Member m1 = new Member(1, "A");
        Member m2 = new Member(2, "B");
        Member m3 = new Member(3, "C");
        Member m4 = new Member(4, "D");
        Member m5 = new Member(5, "E");

        bookingManager.makeBooking(m1, lesson);
        bookingManager.makeBooking(m2, lesson);
        bookingManager.makeBooking(m3, lesson);
        bookingManager.makeBooking(m4, lesson);
        Booking overflow = bookingManager.makeBooking(m5, lesson);

        assertNull(overflow, "5th booking should fail due to capacity limit");
        assertEquals(4, lesson.getBookedCount());
    }

    @Test
    public void testCancelBookingReleasesSpace() {
        Booking booking = bookingManager.makeBooking(member1, lesson);
        assertNotNull(booking);
        assertEquals(1, lesson.getBookedCount());

        bookingManager.cancelBooking(booking.getBookingId());
        assertEquals(Booking.Status.CANCELLED, booking.getStatus());
        assertEquals(0, lesson.getBookedCount());
    }

    @Test
    public void testAverageRating() {
        Booking b1 = bookingManager.makeBooking(member1, lesson);
        Booking b2 = bookingManager.makeBooking(member2, lesson);

        bookingManager.attendLesson(b1.getBookingId(), "Great class!", 4);
        bookingManager.attendLesson(b2.getBookingId(), "Loved it!", 5);

        double expected = (4.0 + 5.0) / 2;
        assertEquals(expected, lesson.getAverageRating(), 0.01, "Average rating should be 4.5");
    }
}