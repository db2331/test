import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeExpertSystem {

    static class EmployeeRecord {
        String name;
        int score;
        String performance;
        String recommendation;
        List<String> suggestions;

        EmployeeRecord(String name, int score, String performance, String recommendation, List<String> suggestions) {
            this.name = name;
            this.score = score;
            this.performance = performance;
            this.recommendation = recommendation;
            this.suggestions = suggestions;
        }
    }

    private List<EmployeeRecord> employeeRecords = new ArrayList<>();

    private static class Question {
        String text;
        int weight;

        Question(String text, int weight) {
            this.text = text;
            this.weight = weight;
        }
    }

    private final List<Question> questions = List.of(
        new Question("Does the employee meet deadlines", 2),
        new Question("Does the employee work well in a team", 1),
        new Question("Does the employee take initiative", 1),
        new Question("Does the employee have leadership qualities", 2),
        new Question("Does the employee consistently exceed expectations", 2),
        new Question("Is the employee punctual", 1),
        new Question("Does the employee adapt well to new challenges", 1),
        new Question("Does the employee show strong problem-solving skills", 2),
        new Question("Does the employee maintain a positive attitude", 1),
        new Question("Does the employee effectively communicate with colleagues", 1)
    );

    private void provideSuggestions(List<String> suggestions, List<String> negatives) {
        for (String neg : negatives) {
            if (neg.contains("deadlines"))
                suggestions.add("Attend time management training.");
            else if (neg.contains("team"))
                suggestions.add("Participate in team-building activities.");
            else if (neg.contains("leadership"))
                suggestions.add("Take a leadership development course.");
            else if (neg.contains("initiative"))
                suggestions.add("Be proactive in task ownership.");
            else if (neg.contains("problem-solving"))
                suggestions.add("Improve analytical and logical thinking.");
            else if (neg.contains("punctual"))
                suggestions.add("Work on punctuality and discipline.");
            else if (neg.contains("communicate"))
                suggestions.add("Improve verbal and written communication.");
        }
    }

    private String generateRecommendation(int score, List<String> negatives) {
        if (score >= 17)
            return "Recommend for Promotion";
        else if (score >= 13)
            return "Eligible for Bonus/Recognition";
        else if (score <= 9)
            return "Needs Training & Mentoring";
        else
            return "Satisfactory but monitor performance";
    }

    public void evaluateEmployee(Scanner sc) {
        System.out.print("\nEnter Employee Name: ");
        sc.nextLine(); // Consume newline
        String name = sc.nextLine();

        System.out.println("\nPerformance Evaluation (yes/no):");

        int score = 0;
        List<String> negativeReasons = new ArrayList<>();

        for (Question q : questions) {
            System.out.print(q.text + "? ");
            String answer = sc.next();

            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                score += q.weight;
            } else {
                negativeReasons.add("Not able to " + q.text.substring(17));
            }
        }

        String performance;
        if (score >= 18) performance = "Excellent";
        else if (score >= 14) performance = "Good";
        else if (score >= 10) performance = "Average";
        else performance = "Poor";

        List<String> suggestions = new ArrayList<>();
        provideSuggestions(suggestions, negativeReasons);

        String recommendation = generateRecommendation(score, negativeReasons);

        employeeRecords.add(new EmployeeRecord(name, score, performance, recommendation, suggestions));

        System.out.println("\nFinal Evaluation: " + performance);
        System.out.println("Score: " + score + " / 20");
        System.out.println("Recommendation: " + recommendation);

        if (!negativeReasons.isEmpty()) {
            System.out.println("\nAreas for Improvement:");
            for (String reason : negativeReasons)
                System.out.println("- " + reason);
        }

        if (!suggestions.isEmpty()) {
            System.out.println("\nExpert Suggestions:");
            for (String suggestion : suggestions)
                System.out.println("* " + suggestion);
        }
    }

    public void showEmployeeRecords() {
        if (employeeRecords.isEmpty()) {
            System.out.println("\nNo employee records available.");
            return;
        }

        System.out.println("\nEmployee Records:\n");
        int ex = 0, gd = 0, av = 0, pr = 0;
        for (EmployeeRecord record : employeeRecords) {
            System.out.println("Name: " + record.name
                    + ", Score: " + record.score
                    + ", Performance: " + record.performance
                    + ", Recommendation: " + record.recommendation);

            switch (record.performance) {
                case "Excellent": ex++; break;
                case "Good": gd++; break;
                case "Average": av++; break;
                default: pr++; break;
            }
        }

        System.out.println("\nSummary Statistics:");
        System.out.println("Excellent: " + ex + ", Good: " + gd
                + ", Average: " + av + ", Poor: " + pr);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nMenu:\n1. Evaluate Employee\n2. Show Records\n3. Exit\nChoice: ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1: evaluateEmployee(sc); break;
                case 2: showEmployeeRecords(); break;
                case 3: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        EmployeeExpertSystem system = new EmployeeExpertSystem();
        system.run();
    }
}
