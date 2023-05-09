public class TrainingCentre {
    public static void main (String[] args) {
        // Create a new ClassList object and add some classes to it
        ClassList classList = new ClassList();
        TrainingClass class1 = new TrainingClass("3D Modelling", "8 weeks", "Advanced", "1 July", 40);
        class1.setNumOfStudents(30);
        TrainingClass class2 = new TrainingClass("Paper Folding", "2 days", "Easy", "1 July", 40);
        class2.setNumOfStudents(10);
        TrainingClass class3 = new TrainingClass("Java", "5 weeks", "Medium", "15 July", 40);
        class3.setNumOfStudents(35);
        TrainingClass class4 = new TrainingClass("Painting", "3 weeks", "Easy", "10 June", 40);
        class4.setNumOfStudents(40);
        TrainingClass class5 = new TrainingClass("3D Modelling", "8 weeks", "Advanced", "20 June", 40);
        class5.setNumOfStudents(40);
        TrainingClass[] trainingClasses = {class1, class2, class3, class4, class5};
        classList.setTrainingClasses(trainingClasses);

        // Display welcome message and run the student hub
        System.out.println("Welcome to Funtastic Training Centre");
        StudentHub studentHub = new StudentHub(classList);
        studentHub.run();
    }
}
