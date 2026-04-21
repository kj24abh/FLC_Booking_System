public class Booking {
    public enum Status { BOOKED, ATTENDED, CHANGED, CANCELLED }

    private int bookingId;
    private Member member;
    private Lesson lesson;
    private Status status;
    private String review;
    private int rating;

    public Booking(int bookingId, Member member, Lesson lesson) {
        this.bookingId = bookingId;
        this.member = member;
        this.lesson = lesson;
        this.status = Status.BOOKED;
    }

    public void attend(String review, int rating) {
        this.review = review;
        this.rating = rating;
        this.status = Status.ATTENDED;
        lesson.addRating(rating);
    }

    public void changeLesson(Lesson newLesson) {
        this.lesson.removeMember(this.member);
        this.lesson = newLesson;
        newLesson.addMember(this.member);
        this.status = Status.CHANGED;
    }

    public void cancel() {
        lesson.removeMember(this.member);
    }

    public int getBookingId() { return bookingId; }
    public Member getMember() { return member; }
    public Lesson getLesson() { return lesson; }
    public Status getStatus() { return status; }
    public String getReview() { return review; }
    public int getRating() { return rating; }

    public String toShortString() {
        return String.format("BookingID:%-3d | %-10s | %-10s | %s",
                bookingId, lesson.getExerciseType(), lesson.getDay(), lesson.getTimeSlot());
    }

    @Override
    public String toString() {
        return String.format("BookingID:%-3d | %-10s | %-10s | %-10s | Status:%s",
                bookingId, lesson.getExerciseType(), lesson.getDay(), lesson.getTimeSlot(), status);
    }
}