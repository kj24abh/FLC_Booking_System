import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LeisureCentre {

    private Timetable timetable = new Timetable();
    private BookingManager bookingManager = new BookingManager();
    private ReportGenerator reportGenerator;
    private Member[] members;
    private Scanner scanner = new Scanner(System.in);

    public LeisureCentre() {
        setupMembers();
        reportGenerator = new ReportGenerator(timetable, bookingManager);
        setupPreloadedData();
    }


    private void setupMembers() {
        members = new Member[]{
                new Member(1,  "Alice Smith"),
                new Member(2,  "Bob Jones"),
                new Member(3,  "Carol White"),
                new Member(4,  "David Brown"),
                new Member(5,  "Eve Davis"),
                new Member(6,  "Frank Miller"),
                new Member(7,  "Grace Wilson"),
                new Member(8,  "Henry Moore"),
                new Member(9,  "Isla Taylor"),
                new Member(10, "Jack Anderson")
        };
    }

    private void setupPreloadedData() {
        // 10 members x 2 attended bookings = 20 reviews
        int[][] data = {
            {1,1},{1,7},  {2,2},{2,8},  {3,3},{3,9},
            {4,4},{4,10}, {5,5},{5,11}, {6,6},{6,12},
            {7,13},{7,19},{8,14},{8,20}, {9,15},{9,21},{10,16},{10,22}
        };
        String[] reviews = {
            "Great session!","Very energetic!","Loved it!","Good workout.",
            "Excellent class.","Really enjoyed it.","Fantastic instructor!","Will come again.",
            "Brilliant session.","Highly recommend.","Amazing class!","Very well run.",
            "Challenging but fun.","Great atmosphere.","Superb session.","Thoroughly enjoyed.",
            "Wonderful class.","Very motivating.","Best class yet!","Absolutely loved it."
        };
        int[] ratings = {5,4,5,3,4,5,5,4,5,4,5,3,4,5,5,4,5,4,5,5};
        for (int i = 0; i < data.length; i++) {
            Member m = members[data[i][0] - 1];
            Lesson l = timetable.getLessonById(data[i][1]);
            Booking b = bookingManager.makeBookingQuiet(m, l);
            if (b != null) bookingManager.attendLessonQuiet(b.getBookingId(), reviews[i], ratings[i]);
        }
    }

    private void setupTimetable() {
        String[][] schedule = {
                // ---- WEEK 1 - May ----
                {"1",  "Yoga",      "Saturday", "Morning",   "1", "5", "10.00"},
                {"2",  "Zumba",     "Saturday", "Afternoon", "1", "5", "8.00"},
                {"3",  "Aquacise",  "Saturday", "Evening",   "1", "5", "9.00"},
                {"4",  "BoxFit",    "Sunday",   "Morning",   "1", "5", "12.00"},
                {"5",  "BodyBlitz", "Sunday",   "Afternoon", "1", "5", "11.00"},
                {"6",  "Yoga",      "Sunday",   "Evening",   "1", "5", "10.00"},
                // ---- WEEK 2 - May ----
                {"7",  "Zumba",     "Saturday", "Morning",   "2", "5", "8.00"},
                {"8",  "BoxFit",    "Saturday", "Afternoon", "2", "5", "12.00"},
                {"9",  "BodyBlitz", "Saturday", "Evening",   "2", "5", "11.00"},
                {"10", "Aquacise",  "Sunday",   "Morning",   "2", "5", "9.00"},
                {"11", "Yoga",      "Sunday",   "Afternoon", "2", "5", "10.00"},
                {"12", "Zumba",     "Sunday",   "Evening",   "2", "5", "8.00"},
                // ---- WEEK 3 - May ----
                {"13", "BoxFit",    "Saturday", "Morning",   "3", "5", "12.00"},
                {"14", "Aquacise",  "Saturday", "Afternoon", "3", "5", "9.00"},
                {"15", "Yoga",      "Saturday", "Evening",   "3", "5", "10.00"},
                {"16", "BodyBlitz", "Sunday",   "Morning",   "3", "5", "11.00"},
                {"17", "Zumba",     "Sunday",   "Afternoon", "3", "5", "8.00"},
                {"18", "BoxFit",    "Sunday",   "Evening",   "3", "5", "12.00"},
                // ---- WEEK 4 - May ----
                {"19", "Aquacise",  "Saturday", "Morning",   "4", "5", "9.00"},
                {"20", "BodyBlitz", "Saturday", "Afternoon", "4", "5", "11.00"},
                {"21", "Zumba",     "Saturday", "Evening",   "4", "5", "8.00"},
                {"22", "Yoga",      "Sunday",   "Morning",   "4", "5", "10.00"},
                {"23", "BoxFit",    "Sunday",   "Afternoon", "4", "5", "12.00"},
                {"24", "Aquacise",  "Sunday",   "Evening",   "4", "5", "9.00"},
                // ---- WEEK 5 - June ----
                {"25", "Yoga",      "Saturday", "Morning",   "5", "6", "10.00"},
                {"26", "Zumba",     "Saturday", "Afternoon", "5", "6", "8.00"},
                {"27", "BodyBlitz", "Saturday", "Evening",   "5", "6", "11.00"},
                {"28", "BoxFit",    "Sunday",   "Morning",   "5", "6", "12.00"},
                {"29", "Aquacise",  "Sunday",   "Afternoon", "5", "6", "9.00"},
                {"30", "Yoga",      "Sunday",   "Evening",   "5", "6", "10.00"},
                // ---- WEEK 6 - June ----
                {"31", "Zumba",     "Saturday", "Morning",   "6", "6", "8.00"},
                {"32", "BoxFit",    "Saturday", "Afternoon", "6", "6", "12.00"},
                {"33", "Aquacise",  "Saturday", "Evening",   "6", "6", "9.00"},
                {"34", "BodyBlitz", "Sunday",   "Morning",   "6", "6", "11.00"},
                {"35", "Yoga",      "Sunday",   "Afternoon", "6", "6", "10.00"},
                {"36", "Zumba",     "Sunday",   "Evening",   "6", "6", "8.00"},
                // ---- WEEK 7 - June ----
                {"37", "BoxFit",    "Saturday", "Morning",   "7", "6", "12.00"},
                {"38", "BodyBlitz", "Saturday", "Afternoon", "7", "6", "11.00"},
                {"39", "Yoga",      "Saturday", "Evening",   "7", "6", "10.00"},
                {"40", "Aquacise",  "Sunday",   "Morning",   "7", "6", "9.00"},
                {"41", "Zumba",     "Sunday",   "Afternoon", "7", "6", "8.00"},
                {"42", "BoxFit",    "Sunday",   "Evening",   "7", "6", "12.00"},
                // ---- WEEK 8 - June ----
                {"43", "Aquacise",  "Saturday", "Morning",   "8", "6", "9.00"},
                {"44", "Yoga",      "Saturday", "Afternoon", "8", "6", "10.00"},
                {"45", "Zumba",     "Saturday", "Evening",   "8", "6", "8.00"},
                {"46", "BodyBlitz", "Sunday",   "Morning",   "8", "6", "11.00"},
                {"47", "BoxFit",    "Sunday",   "Afternoon", "8", "6", "12.00"},
                {"48", "Aquacise",  "Sunday",   "Evening",   "8", "6", "9.00"},
        };

        for (String[] row : schedule) {
            timetable.addLesson(new Lesson(
                    Integer.parseInt(row[0]), row[1], row[2], row[3],
                    Integer.parseInt(row[4]), Integer.parseInt(row[5]),
                    Double.parseDouble(row[6])
            ));
        }
    }


    public void run() {
        System.out.println("  Welcome to Furzefield Leisure Centre!  ");
        System.out.println("==========================================");
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> bookLesson();
                case 2 -> changeOrCancelBooking();
                case 3 -> attendLesson();
                case 4 -> monthlyLessonReport();
                case 5 -> monthlyChampionReport();
                case 0 -> { System.out.println("Goodbye!"); running = false; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Book a group exercise lesson");
        System.out.println("2. Change / Cancel a booking");
        System.out.println("3. Attend a lesson");
        System.out.println("4. Monthly lesson report");
        System.out.println("5. Monthly champion exercise type report");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    private void bookLesson() {
        Member member = selectMember();
        if (member == null) return;

        System.out.println("\nHow would you like to view the timetable?");
        System.out.println("1. By day (Saturday / Sunday)");
        System.out.println("2. By exercise type");
        int choice = readInt("Enter choice: ");

        List<Lesson> lessons;
        if (choice == 1) {
            String day = readString("Enter day (Saturday/Sunday): ");
            if (!day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday")) {
                System.out.println("Invalid day. Must be Saturday or Sunday.");
                return;
            }
            lessons = timetable.getByDay(day);
        } else if (choice == 2) {
            System.out.println("Available types: Yoga, Zumba, Aquacise, BoxFit, BodyBlitz");
            String type = readString("Enter exercise type: ");
            lessons = timetable.getByExerciseType(type);
        } else {
            System.out.println("Invalid option.");
            return;
        }

        if (lessons.isEmpty()) {
            System.out.println("No lessons found.");
            return;
        }

        printLessonsGroupedByWeek(lessons);

        int lessonId = readInt("Enter Lesson ID to book (0 to cancel): ");
        if (lessonId == 0) return;

        Lesson lesson = timetable.getLessonById(lessonId);
        if (lesson == null) {
            System.out.println("Lesson ID not found.");
            return;
        }
        bookingManager.makeBooking(member, lesson);
    }


    private void changeOrCancelBooking() {
        Member member = selectMember();
        if (member == null) return;

        List<Booking> memberBookings = bookingManager.getBookingsByMember(member);
        List<Booking> changeable = new ArrayList<>();
        for (Booking b : memberBookings) {
            if (b.getStatus() == Booking.Status.BOOKED || b.getStatus() == Booking.Status.CHANGED)
                changeable.add(b);
        }
        if (changeable.isEmpty()) {
            System.out.println("No bookings available to change or cancel.");
            return;
        }

        System.out.println("\nYour bookings:");
        for (Booking b : changeable) System.out.println(b);

        int bookingId = readInt("Enter Booking ID (0 to go back): ");
        if (bookingId == 0) return;

        System.out.println("1. Change booking to a different lesson");
        System.out.println("2. Cancel booking");
        int choice = readInt("Enter choice: ");

        if (choice == 1) {
            System.out.println("\nHow would you like to find the new lesson?");
            System.out.println("1. By day");
            System.out.println("2. By exercise type");
            int viewChoice = readInt("Enter choice: ");
            List<Lesson> lessons;
            if (viewChoice == 1) {
                String day = readString("Enter day (Saturday/Sunday): ");
                lessons = timetable.getByDay(day);
            } else {
                System.out.println("Available types: Yoga, Zumba, Aquacise, BoxFit, BodyBlitz");
                String type = readString("Enter exercise type: ");
                lessons = timetable.getByExerciseType(type);
            }
            if (lessons.isEmpty()) { System.out.println("No lessons found."); return; }
            printLessonsGroupedByWeek(lessons);
            int newLessonId = readInt("Enter new Lesson ID (0 to cancel): ");
            if (newLessonId == 0) return;
            Lesson newLesson = timetable.getLessonById(newLessonId);
            if (newLesson == null) { System.out.println("Lesson not found."); return; }
            bookingManager.changeBooking(bookingId, newLesson);

        } else if (choice == 2) {
            bookingManager.cancelBooking(bookingId);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void attendLesson() {
        Member member = selectMember();
        if (member == null) return;

        List<Booking> memberBookings = bookingManager.getBookingsByMember(member);
        List<Booking> attendable = new ArrayList<>();
        for (Booking b : memberBookings) {
            if (b.getStatus() == Booking.Status.BOOKED || b.getStatus() == Booking.Status.CHANGED) {
                attendable.add(b);
            }
        }

        if (attendable.isEmpty()) {
            System.out.println("No active bookings to attend.");
            return;
        }

        System.out.println("\nActive bookings:");
        for (Booking b : attendable) System.out.println(b.toShortString());

        int bookingId = readInt("Enter Booking ID to attend (0 to go back): ");
        if (bookingId == 0) return;

        String review = readString("Write your review: ");
        int rating = 0;
        while (rating < 1 || rating > 5) {
            rating = readInt("Enter rating (1=Very Dissatisfied, 2=Dissatisfied, 3=Ok, 4=Satisfied, 5=Very Satisfied): ");
            if (rating < 1 || rating > 5) System.out.println("Rating must be between 1 and 5.");
        }
        bookingManager.attendLesson(bookingId, review, rating);
    }


    private void monthlyLessonReport() {
        System.out.println("Available months: 5 (May), 6 (June)");
        int month = readInt("Enter month number: ");
        reportGenerator.printMonthlyLessonReport(month);
    }


    private void monthlyChampionReport() {
        System.out.println("Available months: 5 (May), 6 (June)");
        int month = readInt("Enter month number: ");
        reportGenerator.printMonthlyChampionReport(month);
    }

    private void listMembers() {
        System.out.println("\n--- Registered Members ---");
        for (Member m : members) System.out.println(m);
    }

    private Member selectMember() {
        listMembers();
        int id = readInt("Enter your Member ID: ");
        for (Member m : members) {
            if (m.getMemberId() == id) return m;
        }
        System.out.println("Member not found.");
        return null;
    }

    private void printLessonsGroupedByWeek(List<Lesson> lessons) {

        Map<Integer, List<Lesson>> byWeek = new LinkedHashMap<>();
        for (Lesson l : lessons) {
            byWeek.computeIfAbsent(l.getWeekNumber(), k -> new ArrayList<>()).add(l);
        }

        String[] dayOrder = {"Saturday", "Sunday"};
        String[] timeOrder = {"Morning", "Afternoon", "Evening"};
        System.out.printf("  %-10s %-12s %-12s %-10s %-8s %-6s%n",
                "LessonID", "Exercise", "Day", "TimeSlot", "Spaces", "Price");
        System.out.println("  " + "-".repeat(68));
        for (Map.Entry<Integer, List<Lesson>> weekEntry : byWeek.entrySet()) {
            int weekNum = weekEntry.getKey();
            List<Lesson> weekLessons = weekEntry.getValue();
            System.out.println("  WEEK " + weekNum);
            System.out.println("  " + "-".repeat(68));

            for (String day : dayOrder) {
                for (String time : timeOrder) {
                    for (Lesson l : weekLessons) {
                        if (l.getDay().equalsIgnoreCase(day) && l.getTimeSlot().equalsIgnoreCase(time)) {
                            int spaces = 4 - l.getBookedCount();
                            System.out.printf("  %-10d %-12s %-12s %-10s %-8s £%.2f%n",
                                    l.getLessonId(), l.getExerciseType(),
                                    l.getDay(), l.getTimeSlot(),
                                    spaces + "/4", l.getPrice());
                        }
                    }
                }
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}