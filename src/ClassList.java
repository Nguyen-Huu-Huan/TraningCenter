public class ClassList {
    private TrainingClass[] trainingClasses = new TrainingClass[40];

    public ClassList () {
    }

    public void display () {
        // An array of integer to store the column widths
        int[] columnWidths = {30, 12, 12, 15, 15};
        String[] columnNames = {"Class", "Duration", "Level", "Start Date", "Availability"};
        // rowValues is used to store the values of each row. The default value is set to the header row
        String[] rowValues = columnNames;
        // Including the header, there will be at max the trainingClasses array length + 1 rows to loop through
        for (int i = 0; i <= trainingClasses.length; i++) {
            // If i == 0 (the header row), print 3 spaces to skip the index column
            if (i == 0) {
                System.out.print("   ");
            } else {
                // For the rest of the rows, print the index of the row,
                // and change the value of rowValues to the values of training class (not null)
                System.out.print(i + ". ");
                if (trainingClasses[i - 1] != null) {
                    rowValues = trainingClasses[i - 1].getInfo();
                }
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

    public TrainingClass getTrainingClass (int index) {
        return trainingClasses[index];
    }

    public int countClasses () {
        // Count the number of classes in the list of all available classes
        int count = 0;
        for (int i = 0; i < trainingClasses.length; i++) {
            // Increase the count if the class is not null
            if (trainingClasses[i] != null) {
                count++;
            }
        }
        return count;
    }

    public TrainingClass[] getTrainingClasses () {
        return trainingClasses;
    }

    public void setTrainingClasses (TrainingClass[] trainingClasses) {
        this.trainingClasses = trainingClasses;
    }
}
