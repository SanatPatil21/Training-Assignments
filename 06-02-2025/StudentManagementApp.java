import java.util.*;
import java.util.stream.*;

class Student {
    private int rollNo;
    private int age;
    private String name;
    private int standard;
    private String school;
    private double percentage;
    private String gender;
    private Fees fees;

    public Student(int rollNo, int age, String name, int standard, String school, double percentage, String gender,
            Fees fees) {
        this.rollNo = rollNo;
        this.age = age;
        this.name = name;
        this.standard = standard;
        this.school = school;
        this.percentage = percentage;
        this.gender = gender;
        this.fees = fees;

    }

    public String toString() {
        return "Name: " + name + "\nRollNo: " + rollNo + "\nAge: " + age + "\nStandard: " + standard + "\nSchool: "
                + school + "\nPercentage: " + percentage + "\nGender: " + gender + "\nTotal Fees: "
                + fees.getTotalFees() + "\tFees Paid: " + fees.getFeesPaid() + "\t FeesPending: "
                + fees.getPendingFees() + "\n----------\n";
    }

    public int getRollNo() {
        return rollNo;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getStandard() {
        return standard;
    }

    public String getSchool() {
        return school;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getGender() {
        return gender;
    }

    public Double getFees() {
        return fees.getFeesPaid();
    }

    public Double getPendingFees() {
        return fees.getPendingFees();
    }
}

class Fees {
    private double totalFees;
    private double feesPaid;
    private double pendingFees;

    Fees(double totalFees, double feesPaid) {
        this.totalFees = totalFees;
        this.feesPaid = feesPaid;
        this.pendingFees = totalFees - feesPaid;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public double getPendingFees() {
        return pendingFees;
    }

}

class DataAdder {
    public static void addData(List<Student> universityRecords) {
        universityRecords.add(new Student(1, 17, "Sam", 10, "School", 89.5, "M", new Fees(20000, 10000)));
        universityRecords.add(new Student(2, 17, "John", 10, "School", 18.5, "M", new Fees(20000, 15000)));
        universityRecords.add(new Student(3, 17, "Mark", 10, "School", 89.5, "M", new Fees(20000, 10000)));
        universityRecords.add(new Student(4, 17, "Max", 10, "School", 89.5, "M", new Fees(20000, 10000)));
        universityRecords.add(new Student(1, 16, "Aarav", 9, "PODAR", 35.7, "M", new Fees(25000, 12000)));
        universityRecords.add(new Student(2, 15, "Diya", 8, "KES", 85.3, "F", new Fees(23000, 11000)));
        universityRecords.add(new Student(3, 14, "Ananya", 7, "IES", 88.7, "F", new Fees(24000, 11500)));
        universityRecords.add(new Student(4, 13, "Rohan", 6, "PTVA", 79.6, "M", new Fees(22000, 10500)));
        universityRecords.add(new Student(5, 17, "Vikram", 10, "ORION", 82.1, "M", new Fees(26000, 13000)));
        universityRecords.add(new Student(6, 12, "Ishita", 5, "STHS", 89.4, "F", new Fees(21000, 9500)));
        universityRecords.add(new Student(7, 11, "Kabir", 4, "SAHS", 77.8, "M", new Fees(20000, 9000)));
        universityRecords.add(new Student(8, 10, "Mira", 3, "SHBHS", 92.3, "F", new Fees(27000, 13500)));
        universityRecords.add(new Student(9, 18, "Dev", 11, "PODAR", 80.5, "M", new Fees(25000, 12000)));
        universityRecords.add(new Student(10, 16, "Anika", 9, "KES", 87.0, "F", new Fees(23000, 11000)));

    }
}

public class StudentManagementApp {
    public static void main(String[] args) {
        List<Student> universityRecords = new ArrayList<Student>();

        DataAdder.addData(universityRecords);

        // universityRecords.forEach(System.out::println);
        /*
         * REQUIREMENTS
         * How many students in each standard
         * How many students male & female
         * How many students have failed and pass (40%)
         * - Whole university
         * - School wise
         * Top 3 students (Whole university)
         * Top scorer school wise
         * Average age of male & female students
         * Total fees collected school wise
         * Total fees pending school wise
         * Total number of students (University)
         *
         */

        // How many students in each standard
        Map<Integer, Long> result1 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getStandard, Collectors.counting()));
        System.out.println("Standard - Count of Students");
        result1.forEach((standard, count) -> System.out.println(standard + " : " + count + "\t"));

        // How many students male & female
        Map<String, Long> result2 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getGender, Collectors.counting()));
        // System.out.println(result2);
        System.out.println("\nGender - Count of Students");
        result2.forEach((gender, count) -> System.out.println(gender + " : " + count + "\t"));

        // How many students have failed and pass (40%) university wise
        Map<String, Long> result3 = universityRecords.stream()
                .collect(Collectors.groupingBy(s -> s.getPercentage() > 40 ? "Pass" : "Fail", Collectors.counting()));
        System.out.println("\nResult - Count of Students [University Level]");
        result3.forEach((result, count) -> System.out.println(result + " : " + count + "\t"));

        // How many students have failed and pass (40%) school wise
        Map<String, Map<String, Long>> result4 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getSchool,
                        Collectors.groupingBy(s -> s.getPercentage() > 40 ? "Pass" : "Fail", Collectors.counting())));
        System.out.println("\nResult - Count of Students [School Level]");
        result4.forEach((school, result) -> result.forEach(
                (resultBySchool, count) -> System.out.println(school + " : " + resultBySchool + " : " + count + "\t")));
        // System.out.println(result4);

        // Top 3 students (Whole university)
        /*
         * List<Student> result5 = universityRecords.stream()
         * .sorted(Comparator.comparingDouble(Student::getPercentage).reversed())
         * .limit(3)
         * .collect(Collectors.toList());
         * System.out.println("\nTop 3 Students");
         * result5.forEach(s -> System.out.println("Name: " + s.getName() + "Standard: "
         * + s.getStandard()
         * + " Percentage: " + s.getPercentage() + " School: " + s.getName()));
         */
        //Instead of Using Stream we can directly apply the comparator on the List Itself
        universityRecords.sort(Comparator.comparingDouble(Student::getPercentage).reversed());
        System.out.println("\nTop 3 Students University Wise");
        universityRecords.subList(0, 3).forEach(System.out::println);

        // Top scorer school wise
        // NOTE:- maxBy returns Optional so ifPresent is used
        Map<String, Optional<Student>> result6 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getSchool,
                        Collectors.maxBy(Comparator.comparingDouble(Student::getPercentage))));

        System.out.println("\nTop Scorer School Wise");
        result6.forEach((school, studentOpt) -> {
            studentOpt.ifPresent(student -> {
                System.out.println("School: " + school + ", Name: " + student.getName() + ", Percentage: "
                        + student.getPercentage());
            });
        });

        // Average age of male & female students
        Map<String, Double> result7 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getGender, Collectors.averagingInt(Student::getAge)));
        System.out.println("\n Average age of male & female students");
        result7.forEach((gender, avgAge) -> {
            System.out.println("Gender: " + gender + ", Average Age: " + avgAge);
        });

        // Total fees collected school wise
        Map<String, Double> result8 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getSchool,
                        Collectors.summingDouble(student -> student.getFees())));

        System.out.println("\nTotal Fees Collected School Wise");
        result8.forEach((school, totalFees) -> {
            System.out.println("School: " + school + ", Total Fees Collected: " + totalFees);
        });

        // Total fees pending school wise
        Map<String, Double> result9 = universityRecords.stream()
                .collect(Collectors.groupingBy(Student::getSchool,
                        Collectors.summingDouble(student -> student.getPendingFees())));

        System.out.println("\nTotal Fees Pending School Wise");
        result9.forEach((school, totalFees) -> {
            System.out.println("School: " + school + ", Total Fees Pendings: " + totalFees);
        });

        // Total number of students (University)
        long result10 = universityRecords.size();
        System.out.println("\nTotal Number of Students: " + result10);

    }
}
