import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    private Timetable timetable;
    private BookingManager bookingManager;

    public ReportGenerator(Timetable timetable, BookingManager bookingManager) {
        this.timetable = timetable;
        this.bookingManager = bookingManager;
    }

    public void printMonthlyLessonReport(int month) {
        List<Lesson> monthLessons = timetable.getByMonth(month);
        if (monthLessons.isEmpty()) {
            System.out.println("No lessons found for month: " + month);
            return;
        }
        System.out.println("\n" + "=".repeat(60));
        String monthName = (month == 5) ? "May" : (month == 6) ? "June" : "Month " + month;
        System.out.println("  MONTHLY LESSON REPORT - " + monthName.toUpperCase());
        Map<Integer, List<Lesson>> byWeek = new LinkedHashMap<>();
        for (Lesson l : monthLessons) {
            byWeek.computeIfAbsent(l.getWeekNumber(), k -> new ArrayList<>()).add(l);
        }

        String[] dayOrder = {"Saturday", "Sunday"};
        String[] timeOrder = {"Morning", "Afternoon", "Evening"};

        for (Map.Entry<Integer, List<Lesson>> entry : byWeek.entrySet()) {
            System.out.println("\n  WEEK " + entry.getKey());
            System.out.println("  " + "-".repeat(72));
            System.out.printf("  %-8s %-12s %-10s %-10s %-10s %-10s%n",
                    "ID", "Exercise", "Day", "Time", "Attended", "AvgRating");
            System.out.println("  " + "-".repeat(72));

            for (String day : dayOrder) {
                for (String time : timeOrder) {
                    for (Lesson l : entry.getValue()) {
                        if (l.getDay().equalsIgnoreCase(day) && l.getTimeSlot().equalsIgnoreCase(time)) {
                            int attended = countAttended(l);
                            double avg = l.getAverageRating();
                            String avgStr = (attended == 0) ? "N/A" : String.format("%.2f", avg);
                            System.out.printf("  %-8d %-12s %-10s %-10s %-10d %-10s%n",
                                    l.getLessonId(), l.getExerciseType(),
                                    l.getDay(), l.getTimeSlot(), attended, avgStr);
                        }
                    }
                }
            }
        }
        System.out.println("=".repeat(80));
    }

    public void printMonthlyChampionReport(int month) {
        List<Lesson> monthLessons = timetable.getByMonth(month);
        if (monthLessons.isEmpty()) {
            System.out.println("No lessons found for month: " + month);
            return;
        }

        // Sum income per exercise type
        Map<String, Double> incomeByType = new LinkedHashMap<>();
        Map<String, Integer> attendedByType = new LinkedHashMap<>();

        // Initialise with 0 so all types show even if no income
        for (Lesson l : monthLessons) {
            incomeByType.putIfAbsent(l.getExerciseType(), 0.0);
            attendedByType.putIfAbsent(l.getExerciseType(), 0);
        }

        for (Lesson l : monthLessons) {
            int attended = countAttended(l);
            double income = attended * l.getPrice();
            incomeByType.merge(l.getExerciseType(), income, Double::sum);
            attendedByType.merge(l.getExerciseType(), attended, Integer::sum);
        }

        String monthName = (month == 5) ? "May" : (month == 6) ? "June" : "Month " + month;
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  MONTHLY CHAMPION EXERCISE REPORT - " + monthName.toUpperCase());
        System.out.println("=".repeat(60));
        System.out.printf("  %-14s %-12s %-12s%n", "Exercise Type", "Attended", "Total Income");
        System.out.println("  " + "-".repeat(42));

        String champion = null;
        double highestIncome = -1;

        for (Map.Entry<String, Double> entry : incomeByType.entrySet()) {
            String type = entry.getKey();
            double income = entry.getValue();
            int attended = attendedByType.get(type);
            System.out.printf("  %-14s %-12d £%-11.2f%n", type, attended, income);
            if (income > highestIncome) {
                highestIncome = income;
                champion = type;
            }
        }

        System.out.println("  " + "-".repeat(42));
        if (champion != null) {
            System.out.printf("  CHAMPION: %s  (Total Income: £%.2f)%n", champion, highestIncome);
        } else {
            System.out.println("  No attended lessons yet. No champion to show.");
        }
        System.out.println("=".repeat(60));
    }

    private int countAttended(Lesson lesson) {
        int count = 0;
        for (Booking b : bookingManager.getAllBookings()) {
            if (b.getLesson().getLessonId() == lesson.getLessonId()
                    && b.getStatus() == Booking.Status.ATTENDED) {
                count++;
            }
        }
        return count;
    }
}