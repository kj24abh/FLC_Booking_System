import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private final double price;
    private int lessonId;
    private String exerciseType;
    private String day;      
    private String timeSlot;  
    private int weekNumber;    
    private int monthNumber; 
    private int capacity = 4;
    private List<Member> bookedMembers = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();

    public Lesson(int lessonId, String exerciseType, String day, String timeSlot,
                  int weekNumber, int monthNumber, double price) {
        this.lessonId = lessonId;
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.weekNumber = weekNumber;
        this.monthNumber = monthNumber;
    }

    public boolean hasSpace() {
        return bookedMembers.size() < capacity;
    }

    public boolean addMember(Member m) {
        if (!hasSpace()) return false;
        if (bookedMembers.contains(m)) return false;
        bookedMembers.add(m);
        return true;
    }

    public boolean removeMember(Member m) {
        return bookedMembers.remove(m);
    }

    public boolean hasMember(Member m) {
        return bookedMembers.contains(m);
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        int sum = 0;
        for (int r : ratings) sum += r;
        return (double) sum / ratings.size();
    }

    public int getAttendedCount() {
        return ratings.size(); // each attended booking adds a rating
    }

    public int getLessonId() { return lessonId; }
    public String getExerciseType() { return exerciseType; }
    public String getDay() { return day; }
    public String getTimeSlot() { return timeSlot; }
    public int getWeekNumber() { return weekNumber; }
    public int getMonthNumber() { return monthNumber; }
    public double getPrice() { return price; }
    public int getBookedCount() { return bookedMembers.size(); }
    public List<Member> getBookedMembers() { return bookedMembers; }

    @Override
    public String toString() {
        return String.format("LessonID:%d | %s | %s | %s | Week:%d | Month:%d | Price:£%.2f | Booked:%d/4",
                lessonId, exerciseType, day, timeSlot, weekNumber, monthNumber, price, bookedMembers.size());
    }
}