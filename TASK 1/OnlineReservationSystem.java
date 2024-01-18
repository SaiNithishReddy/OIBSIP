import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationSystem reservationSystem = new ReservationSystem();

        // Sample users
        reservationSystem.addUser("user1", "password1");
        reservationSystem.addUser("user2", "password2");

        // Login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = reservationSystem.loginUser(username, password);

        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");

            // Reservation
            System.out.print("Enter train number: ");
            String trainNumber = scanner.nextLine();
            System.out.print("Enter class type: ");
            String classType = scanner.nextLine();
            System.out.print("Enter date of journey: ");
            String dateOfJourney = scanner.nextLine();
            System.out.print("Enter from: ");
            String from = scanner.nextLine();
            System.out.print("Enter to: ");
            String to = scanner.nextLine();

            reservationSystem.makeReservation(user, trainNumber, classType, dateOfJourney, from, to);

            // Cancellation
            System.out.print("Enter PNR for cancellation: ");
            int pnr = scanner.nextInt();
            reservationSystem.cancelReservation(pnr);

            // Display reservations
            System.out.println("Your reservations:");
            reservationSystem.displayReservations(user);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }
}


class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation {
    private final int pnr;
    private final String trainNumber;
    private final String trainName;
    private final String classType;
    private final String dateOfJourney;
    private final String from;
    private final String to;

    public Reservation(int pnr, String trainNumber, String trainName, String classType,
                       String dateOfJourney, String from, String to) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    public int getPnr() {
        return pnr;
    }

    // Getters for other fields

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Train: " + trainNumber + " - " + trainName +
                ", Class: " + classType + ", Date: " + dateOfJourney + ", From: " + from + ", To: " + to;
    }
}

class ReservationSystem {
    private static int nextPnr = 1;
    private final Map<String, User> users = new HashMap<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void makeReservation(User user, String trainNumber, String classType,
                                String dateOfJourney, String from, String to) {
        Reservation reservation = new Reservation(nextPnr++, trainNumber, getTrainName(trainNumber),
                classType, dateOfJourney, from, to);
        reservations.add(reservation);
        System.out.println("Reservation successful. Your PNR is: " + reservation.getPnr());
    }

    public void cancelReservation(int pnr) {
        Reservation reservation = findReservation(pnr);
        if (reservation != null) {
            reservations.remove(reservation);
            System.out.println("Cancellation successful for PNR: " + pnr);
        } else {
            System.out.println("Reservation not found for PNR: " + pnr);
        }
    }

    public void displayReservations(User user) {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private Reservation findReservation(int pnr) {
        for (Reservation reservation : reservations) {
            if (reservation.getPnr() == pnr) {
                return reservation;
            }
        }
        return null;
    }

    private String getTrainName(String trainNumber) {
        // Implement logic to get train name from the database or other source
        return "Sample Train" + trainNumber;
    }
}