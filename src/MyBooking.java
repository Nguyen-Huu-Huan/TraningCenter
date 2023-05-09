import java.util.Scanner;

public class MyBooking {
    private TrainingClass[] bookedClasses = new TrainingClass[40];
    private TrainingClass[] enrolledClasses = new TrainingClass[40];
    private String[] columnNames = {"Bookings", "Start Date", "Duration", "Level"};

    public void viewBooking () {
        // An array of integer to store the column widths
        int[] columnWidths = {40, 15, 12, 12};
        // Get number of booked, enrolled, and total classes using the countClasses method
        int numBookedClasses = countClasses("booked");
        int numEnrolledClasses = countClasses("enrolled");
        int numTotalClasses = numBookedClasses + numEnrolledClasses;
        // If there is no classes, print out a message
        if (numTotalClasses == 0) {
            System.out.print("No booked classes. Back to the menu");
        } else {
            // rowValues is used to store the values of each row. The default value is set to the header row
            String[] rowValues = this.columnNames;
            // Print of the message if there is no booked classes
            if (numBookedClasses == 0) {
                System.out.println("No booked classes.");
            } else {
                // Including the header, there will be number of booked classes + 1 rows to loop through
                for (int i = 0; i < numBookedClasses + 1; i++) {
                    // If i == 0 (the header row), print 3 spaces to skip the index column
                    if (i == 0) {
                        System.out.print("   ");
                    } else {
                        // For the rest of the rows, print the index of the row,
                        // and change the value of rowValues to the values of the booked class
                        System.out.print(i + ". ");
                        String[] bookedClassInfo = bookedClasses[i - 1].getInfo();
                        rowValues = new String[] {bookedClassInfo[0], bookedClassInfo[3], bookedClassInfo[1], bookedClassInfo[2]};
                    }
                    // Print out each row information using the data from rowValues array
                    for (int j = 0; j < columnWidths.length; j++) {
                        System.out.print(rowValues[j]);
                        // Excluding the length of each value and add spaces to fill the column width
                        for (int k = 0; k < (columnWidths[j] - rowValues[j].length()); k++) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

    public void viewEnrolment () {
        // An array of integer to store the column widths
        int[] columnWidths = {40, 15, 12, 12};
        // Get number of booked, and enrolled classes using the countClasses method
        int numEnrolledClasses = countClasses("enrolled");
        int numBookedClasses = countClasses("booked");
        // Only print out the enrolment if there are enrolled classes
        if (numEnrolledClasses > 0) {
            // rowValues is used to store the values of each row. The default value is set to the header row
            String[] rowValues = new String[] {"-- Enrolled -------", "Start Date", "Duration", "Level"};
            // Including the header, there will be number of enrolled classes + 1 rows to loop through
            for (int i = 0; i < numEnrolledClasses + 1; i++) {
                // If i == 0 (the header row), print 3 dash lines to skip the index column
                if (i == 0) {
                    System.out.print("---");
                } else {
                    // For the rest of the rows, print the index of the row,
                    // The newIndex is used for printing the index of the row,
                    // (taking into account the indices of the booked classes)),
                    // and change the value of rowValues to the values of the enrolled class
                    int newIndex = (i + numBookedClasses);
                    System.out.print(newIndex + ". ");
                    String[] enrolledClassInfo = enrolledClasses[i - 1].getInfo();
                    rowValues = new String[] {enrolledClassInfo[0], enrolledClassInfo[3], enrolledClassInfo[1], enrolledClassInfo[2]};
                }
                // Print out each row information using the data from rowValues array
                for (int j = 0; j < columnWidths.length; j++) {
                    System.out.print(rowValues[j]);
                    for (int k = 0; k < (columnWidths[j] - rowValues[j].length()); k++) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public boolean isOverlappedClasses (TrainingClass trainingClass, String classType) {
        // Check if the class is overlapped with other classes (booked or enrolled)
        // The targetClasses is used to store the data of classes of type chosen (booked or enrolled)
        TrainingClass[] targetClasses = null;
        if (classType.equals("booked")) {
            targetClasses = this.bookedClasses;
        } else if (classType.equals("enrolled")) {
            targetClasses = this.enrolledClasses;
        }
        // Loop through each class in the targetClasses array, if the class is overlapped, return true
        for (int i = 0; i < this.countClasses(classType); i++) {
            if (targetClasses[i].equals(trainingClass)) {
                return true;
            }
        }
        // The class is not overlapped with other classes
        return false;
    }

    public TrainingClass[] getBookedClasses () {
        return bookedClasses;
    }

    public void setBookedClasses (TrainingClass[] bookedClasses) {
        this.bookedClasses = bookedClasses;
    }

    public TrainingClass removeClass (int index) {
        // Get number of booked, and enrolled classes using the countClasses method
        int numBookedClasses = countClasses("booked");
        int numEnrolledClasses = countClasses("enrolled");
        // The targetClasses is used to store the data of classes of type chosen (booked or enrolled)
        TrainingClass targetClass = null;
        // If the index is in the range of booked classes, remove the class from the booked classes
        // else if the index is beyond the range of booked classes, remove the class from the enrolled classes
        if (index > numBookedClasses - 1) {
            // Reindex to match with the index of the enrolled classes array
            index -= numBookedClasses;
            // Take the user input to confirm (y/n) if the user wants to remove the enrolled class
            Scanner scanner = new Scanner(System.in);
            System.out.println("Are you sure to cancel " + enrolledClasses[index].toString() + "?");
            String removeOption = scanner.nextLine();
            while (true) {
                try {
                    targetClass = enrolledClasses[index];
                    if (removeOption.toLowerCase().equals("y")) {
                        // Shift all enrolled classes (after the target class) to the left to remove the target class
                        for (int i = index; i < numEnrolledClasses - 1; i++) {
                            enrolledClasses[i] = enrolledClasses[i + 1];
                        }
                        enrolledClasses[numEnrolledClasses - 1] = null;
                        // Reduce the number of students in the target class by one, print out the message, and break
                        targetClass.setNumOfStudents(targetClass.getNumOfStudents() - 1);
                        System.out.println(targetClass.toString() + " is cancelled!");
                        break;
                    } else if (removeOption.toLowerCase().equals("n")) {
                        break;
                    } else {
                        throw new InvalidOptionException("invalid entry");
                    }
                } catch (InvalidOptionException e) {
                    removeOption = scanner.nextLine();
                }
            }
        } else {
            // Similar to the above, but the target class is the booked class and there is no need to confirm
            targetClass = bookedClasses[index];
            for (int i = index; i < numBookedClasses - 1; i++) {
                bookedClasses[i] = bookedClasses[i + 1];
            }
            targetClass.setNumOfStudents(targetClass.getNumOfStudents() - 1);
            System.out.println(targetClass.toString() + " is cancelled!");
            bookedClasses[numBookedClasses - 1] = null;
        }
        return targetClass;
    }

    public int countClasses (String classType) {
        // Initialize the numClasses to count the number of classes of type chosen
        int numClasses = 0;
        // allClasses array is used to store the data of all classes (booked and enrolled)
        TrainingClass[] allClasses = null;
        if (classType.equals("booked")) {
            allClasses = this.bookedClasses;
        } else if (classType.equals("enrolled")) {
            allClasses = this.enrolledClasses;
        } else if (classType.equals("all")) {
            // If the class type is all, the number of classes is the sum of the number of booked and enrolled classes
            int numBookedClasses = this.countClasses("booked");
            int numEnrolledClasses = this.countClasses("enrolled");
            numClasses = numBookedClasses + numEnrolledClasses;
            return numClasses;
        }
        // Loop through each class in the allClasses array, if the class is not null, increase the number of classes
        for (int i = 0; i < allClasses.length; i++) {
            if (allClasses[i] != null) {
                numClasses++;
            }
        }
        return numClasses;
    }

    public void bookClass (TrainingClass trainingClass) {
        // Get the number of booked classes
        int numBookedClasses = countClasses("booked");
        // Increase the number of students in the target class by one
        trainingClass.setNumOfStudents(trainingClass.getNumOfStudents() + 1);
        // Add the target class to the first index of the booked classes array by:
        // shifting all booked classes (after the target class) to the right to add the target class
        if (numBookedClasses > 0) {
            for (int i = numBookedClasses; i > 0; i--) {
                this.bookedClasses[i] = this.bookedClasses[i - 1];
            }
        }
        bookedClasses[0] = trainingClass;
    }

    public void enrolAllBookedClasses () {
        // Get the number of booked and enrolled classes
        int numBookedClasses = this.countClasses("booked");
        int numEnrolledClasses = this.countClasses("enrolled");
        // Transfer all booked classes to the enrolled classes by appending them to the end of the enrolled classes array
        for (int index = 0; index < numBookedClasses; index++) {
            enrolledClasses[index + numEnrolledClasses] = this.bookedClasses[index];
        }
        // Set the booked classes array to its original state (all null)
        this.bookedClasses = new TrainingClass[40];
    }

    public TrainingClass[] getEnrolledClasses () {
        return enrolledClasses;
    }

    public void setEnrolledClasses (TrainingClass[] enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public String[] getColumnNames () {
        return columnNames;
    }

    public void setColumnNames (String[] newColumnNames) {
        this.columnNames = newColumnNames;
    }
}
