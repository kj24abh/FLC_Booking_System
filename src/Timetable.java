import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getByDay(String day) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getDay().equalsIgnoreCase(day)) result.add(l);
        }
        return result;
    }

    public List<Lesson> getByExerciseType(String type) {
        for (Lesson l : lessons) {
            if (l.getExerciseType().equalsIgnoreCase(type)) result.add(l);
        }
        return result;
    }

    public List<Lesson> getByMonth(int month) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getMonthNumber() == month) result.add(l);
        }
        return result;
    }

    public Lesson getLessonById(int id) {
        for (Lesson l : lessons) {
            if (l.getLessonId() == id) return l;
        }
        return null;
    }

    public List<Lesson> getAllLessons() {
        return lessons;
    }
}