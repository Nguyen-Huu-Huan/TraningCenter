import java.util.Scanner;

public class StudentHub {
    private MyBooking myBooking = new MyBooking();
    private ClassList classList = new ClassList();

    public StudentHub (ClassList classList) {
        this.classList = classList;
        this.myBooking = new MyBooking();
    }

    public void showMenu () {
        System.out.println("Main menu - select one option:");
        System.out.println("b. Book a class");
        System.out.println("v. View my bookings");
        System.out.println("c. Cancel class");
        System.out.println("e. Enrol all booked classes");
        System.out.println("q. Quit");
    }

    public void run () {
        // Show the main menu and take the user input until the user wants to quit.
        this.showMenu();
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        while (!option.toLowerCase().equals("q")) {
            try {
                // Let the user choose an option and run the appropriate method.
                switch (option.toLowerCase()) {
                    case "b":
                        this.bookClass();
                        break;
                    case "v":
                        this.view();
                        break;
                    case "c":
                        this.cancel();
                        break;
                    case "e":
                        this.enrol();
                        break;
                    // If the user entered an invalid option, throw an exception.
                    default:
                        throw new InvalidOptionException("invalid entry");
                }

            } catch (InvalidOptionException e) {
                // Handle invalid option by retaking the user input (no menu shown).
                option = scanner.nextLine();
                continue;
            }
            // Show the main menu after an action is finished and retake the user input until quit.
            this.showMenu();
            option = scanner.nextLine();
        }
        // Handle the quit option by calling the quit method.
        this.quit();
    }

    public void bookClass () {
        // Display all available classes
        this.classList.display();
        // Take the user input for the class number to be booked.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose a class by its number (enter `q` to go back):");
        String option = scanner.nextLine();
        // Handle the booking request until the user wants to quit and return back to the main menu.
        while (!option.toLowerCase().equals("q")) {
            try {
                // Handle the case when the user entered a non-number input.
                try {
                    Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    throw new InvalidOptionException("invalid entry");
                }
                // Parse the user input to an integer and validate if it is a valid class number.
                int optionNumber = Integer.parseInt(option);
                if (optionNumber < 1 || optionNumber > this.classList.countClasses()) {
                    // invalid class number (not between 1 and the total number of classes), throw an exception.
                    throw new InvalidOptionException("invalid entry");
                } else {
                    // valid class number
                    TrainingClass chosenTrainingClass = this.classList.getTrainingClass(optionNumber - 1);
                    // Check if the class is overlapping with the existing bookings, enrolment, or not available,
                    // and throw error
                    if (this.myBooking.isOverlappedClasses(chosenTrainingClass, "booked")) {
                        throw new InvalidOptionException("you have already selected this class");
                    } else if (this.myBooking.isOverlappedClasses(chosenTrainingClass, "enrolled")) {
                        throw new InvalidOptionException("you have already enrolled this class");
                    } else if (!chosenTrainingClass.isAvailable()) {
                        throw new InvalidOptionException("this class is already full");
                    }
                    // The class is valid and ready to be booked
                    this.myBooking.bookClass(chosenTrainingClass);
                    System.out.println(chosenTrainingClass.toString() + " is selected");
                    this.classList.display();
                    System.out.println("Please choose a class by its number (enter `q` to go back):");
                }
            } catch (InvalidOptionException e) {
                // When the class is overlapping with the existing bookings or enrolment, display all classes
                if (e.getMessage().equals("you have already selected this class") || e.getMessage().equals("you have already enrolled this class")) {
                    this.classList.display();
                    System.out.println("Please choose a class by its number (enter `q` to go back):");
                }
            }
            // Retake the user input until the user wants to quit.
            option = scanner.nextLine();
        }
    }

    public void view () {
        // Check if there are any bookings, and display the bookings if there are.
        if (myBooking.countClasses("all") == 0) {
            System.out.println("No booked classes. Back to the main menu.");
            System.out.println();
        } else {
            this.myBooking.viewBooking();
            this.myBooking.viewEnrolment();
        }
    }

    public void cancel () {
        // Check if there are any bookings / enrolments, and break if there are not.
        while (true) {
            if (myBooking.countClasses("all") == 0) {
                System.out.println("No booked classes. Back to the main menu.");
                System.out.println();
                break;
            } else {
                // Display all booked classes
                this.myBooking.viewBooking();
                this.myBooking.viewEnrolment();
                // Take the user input for the class number to be cancelled.
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please choose the class by its number (enter `q` to go back):");
                String option = scanner.nextLine();
                // Handle the cancelation request until the user wants to quit and return back to the main menu.
                while (!option.toLowerCase().equals("q")) {
                    try {
                        // Handle the case when the user entered a non-number input.
                        try {
                            Integer.parseInt(option);
                        } catch (NumberFormatException e) {
                            throw new InvalidOptionException("invalid entry");
                        }
                        // Parse the user input to an integer and validate if it is a valid class number.
                        int indexToBeRemove = Integer.parseInt(option) - 1;
                        if (indexToBeRemove < 0 || indexToBeRemove >= this.myBooking.countClasses("all")) {
                            throw new InvalidOptionException("invalid entry");
                        } else {
                            TrainingClass classToBeCancelled = this.myBooking.removeClass(indexToBeRemove);
                            // Break the loop if there is no more bookings/ enrolments.
                            if (myBooking.countClasses("all") == 0) {
                                System.out.println("No booked classes. Back to the main menu.");
                                System.out.println();
                                break;
                            }
                            // Display all booked & enrolled classes after the class is cancelled.
                            this.myBooking.viewBooking();
                            this.myBooking.viewEnrolment();
                            System.out.println("Please choose the class by its number (enter `q` to go back):");
                        }
                    } catch (InvalidOptionException e) {
                    }
                    option = scanner.nextLine();
                }
                break;
            }
        }
    }

    public void enrol () {
        // Check if there is any booked classes, if yes, enrol all booked classes. Otherwise, print out the message.
        if (this.myBooking.countClasses("booked") == 0) {
            System.out.println("No booked classes. Back to the main menu.");
            System.out.println();
        } else {
            System.out.println("You have successfully enrolled in:");
            // Reuse the viewBooking() method to display all booked classes, but with a different header.
            this.myBooking.setColumnNames(new String[] {"", "Start Date", "Duration", "Level"});
            this.myBooking.viewBooking();
            // Set the column names back to the default.
            this.myBooking.setColumnNames(new String[] {"Bookings", "Start Date", "Duration", "Level"});
            // Enrol all booked classes.
            this.myBooking.enrolAllBookedClasses();
        }
    }

    public void quit () {
        // If the booking class list is empty, thank the user for using the program.
        // Otherwise, notify the user of the remaining booked classes that are not enrolled,
        // and ask if the user still wants to quit.
        if (this.myBooking.countClasses("booked") > 0) {
            //
            System.out.println("Are you sure? The booking list is not empty.");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            while (true) {
                try {
                    // If the user wants to quit, thank the user for using the program and exit the program.
                    // Else, return back to the main menu
                    if (option.toLowerCase().equals("y")) {
                        System.out.println("Thank you, good bye");
                        break;
                    } else if (option.toLowerCase().equals("n")) {
                        this.run();
                        break;
                    } else {
                        throw new InvalidOptionException("invalid entry");
                    }
                } catch (InvalidOptionException e) {
                    option = scanner.nextLine();
                }
            }
        } else {
            System.out.println("Thank you, good bye");
        }
    }
}

