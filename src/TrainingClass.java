public class TrainingClass {
    private String name;
    private String duration;
    private String level;
    private String startDate;
    private int capacity;
    private int numOfStudents = 0;

    public TrainingClass (String name, String duration, String level, String startDate, int capacity) {
        this.name = name;
        this.duration = duration;
        this.level = level;
        this.startDate = startDate;
        this.capacity = capacity;
        this.numOfStudents = 0;
    }

    // The overide method is used to print out the information of the class (with its name and start date)
    @Override
    public String toString () {
        return name + " starting " + startDate;
    }

    // getInfo method is used to return the reformatted values of the training class
    public String[] getInfo () {
        // Reformat the availability of the class to FULL if the number of students reach the capacity
        String availability = String.valueOf(capacity - numOfStudents);
        if (availability.equals("0")) {
            availability = "FULL";
        }
        return new String[] {name, duration, level, startDate, availability};
    }

    // the method is used to check if the class is available
    public boolean isAvailable () {
        return numOfStudents < capacity;
    }

    public String getName () {
        return name;
    }

    public String getDuration () {
        return duration;
    }

    public String getLevel () {
        return level;
    }

    public String getStartDate () {
        return startDate;
    }

    public int getCapacity () {
        return capacity;
    }

    public int getNumOfStudents () {
        return numOfStudents;
    }

    public void setNumOfStudents (int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

}
