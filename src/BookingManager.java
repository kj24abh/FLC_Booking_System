import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Booking> bookings = new ArrayList<>();
    private int nextBookingId = 1;

    public Booking makeBooking(Member member, Lesson lesson) {
        if (!lesson.hasSpace()) {
            System.out.println("Sorry, this lesson is full.");
            return null;
        }
        for (Booking b : bookings) {
            if (b.getMember().getMemberId() == member.getMemberId()
                    && b.getLesson().getLessonId() == lesson.getLessonId()
                    && b.getStatus() != Booking.Status.CANCELLED) {
                System.out.println("Duplicate booking not allowed.");
                return null;
            }
        }
        if (hasTimeConflict(member, lesson, -1)) {
            System.out.println("Time conflict! You already have a booking on "
                    + lesson.getDay() + " " + lesson.getTimeSlot()
                    + " in Week " + lesson.getWeekNumber() + ".");
            return null;
        }
        lesson.addMember(member);
        Booking booking = new Booking(nextBookingId++, member, lesson);
        System.out.println("Booking successful! " + booking.toShortString());
        return booking;
    }

    public boolean changeBooking(int bookingId, Lesson newLesson) {
        Booking b = getBookingById(bookingId);
        if (b == null) { System.out.println("Booking not found."); return false; }
        if (b.getStatus() == Booking.Status.CANCELLED) { System.out.println("Cannot change a cancelled booking."); return false; }
        if (b.getStatus() == Booking.Status.ATTENDED) { System.out.println("Cannot change an already attended booking."); return false; }
        if (!newLesson.hasSpace()) { System.out.println("New lesson is full. Change unsuccessful."); return false; }

        for (Booking existing : bookings) {
            if (existing.getMember().getMemberId() == b.getMember().getMemberId()
                    && existing.getLesson().getLessonId() == newLesson.getLessonId()
                    && existing.getStatus() != Booking.Status.CANCELLED
                    && existing.getBookingId() != bookingId) {
                System.out.println("Member already has a booking for that lesson. Change unsuccessful.");
                return false;
            }
        }
        if (hasTimeConflict(b.getMember(), newLesson, bookingId)) {
            System.out.println("Time conflict! You already have a booking on "
                    + newLesson.getDay() + " " + newLesson.getTimeSlot()
                    + " in Week " + newLesson.getWeekNumber() + ". Change unsuccessful.");
            return false;
        }
        b.changeLesson(newLesson);
        System.out.println("Booking changed successfully! " + b.toShortString());
        return true;
    }

    public boolean cancelBooking(int bookingId) {
        Booking b = getBookingById(bookingId);
        if (b == null) { System.out.println("Booking not found."); return false; }
        if (b.getStatus() == Booking.Status.CANCELLED) { System.out.println("Already cancelled."); return false; }
        if (b.getStatus() == Booking.Status.ATTENDED) { System.out.println("Cannot cancel an attended lesson."); return false; }
        b.cancel();
        System.out.println("Booking cancelled successfully.");
        return true;
    }

    public boolean attendLesson(int bookingId, String review, int rating) {
        Booking b = getBookingById(bookingId);
        if (b == null) { System.out.println("Booking not found."); return false; }
        if (b.getStatus() == Booking.Status.CANCELLED) { System.out.println("This booking is cancelled."); return false; }
        if (b.getStatus() == Booking.Status.ATTENDED) { System.out.println("Already attended."); return false; }
        b.attend(review, rating);
        System.out.println("Lesson attended. Thank you for your review!");
        return true;
    }

    private boolean hasTimeConflict(Member member, Lesson newLesson, int excludeBookingId) {
        for (Booking b : bookings) {
            if (b.getStatus() == Booking.Status.CANCELLED) continue;
            if (b.getStatus() == Booking.Status.ATTENDED) continue;
            if (b.getBookingId() == excludeBookingId) continue;
            if (b.getMember().getMemberId() != member.getMemberId()) continue;
            Lesson existing = b.getLesson();
            if (existing.getWeekNumber() == newLesson.getWeekNumber()
                    && existing.getDay().equalsIgnoreCase(newLesson.getDay())
                    && existing.getTimeSlot().equalsIgnoreCase(newLesson.getTimeSlot())) {
                return true;
            }
        }
        return false;
    }

    public Booking getBookingById(int id) {
        for (Booking b : bookings) {
            if (b.getBookingId() == id) return b;
        }
        return null;
    }

    public List<Booking> getBookingsByMember(Member member) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getMember().getMemberId() == member.getMemberId()) result.add(b);
        }
        return result;
    }

    public List<Booking> getAllBookings() { return bookings; }

    public Booking makeBookingQuiet(Member member, Lesson lesson) {
        if (!lesson.hasSpace()) return null;
        for (Booking b : bookings) {
            if (b.getMember().getMemberId() == member.getMemberId()
                    && b.getLesson().getLessonId() == lesson.getLessonId()
                    && b.getStatus() != Booking.Status.CANCELLED) return null;
        }
        lesson.addMember(member);
        Booking booking = new Booking(nextBookingId++, member, lesson);
        bookings.add(booking);
        return booking;
    }

    public void attendLessonQuiet(int bookingId, String review, int rating) {
        Booking b = getBookingById(bookingId);
        if (b != null) b.attend(review, rating);
    }
}