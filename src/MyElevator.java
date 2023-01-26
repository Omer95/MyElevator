import java.util.ArrayList;
import java.util.HashMap;

enum ElevatorState {
    UP, DOWN, STOP;
}
enum Direction {
    UP, DOWN;
}
class Elevator extends Thread{
    private int elevatorId;
    private ElevatorState state;
    private int currentFloor;
    private HashMap<Integer, int[]> userDestinations;
    private ArrayList<User> userCallList;

    public Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.state = ElevatorState.STOP;
        this.currentFloor = 0;
        this.userDestinations = new HashMap<Integer, int[]>();
        this.userCallList = new ArrayList<User>();
    }

    public int getElevatorId() {
        return this.elevatorId;
    }
    public ElevatorState getElevatorState() {
        return this.state;
    }
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public void changeElevatorState(ElevatorState state) {
        this.state = state;
    }
    public void changeFloor(int floor) {
        this.currentFloor = floor;
    }
    public void addUserDestination(int userId, int userFloor, int destination) {
        int[] floorAndDest = {userFloor, destination};
        this.userDestinations.put(userId, floorAndDest);
        System.out.println("UserDestination Updated: " + this.userDestinations.toString());
    }
    public void addToUserCallList(User user) {
        this.userCallList.add(user);
    }

    public String toString() {
        return(
                "Elevator No: " + this.elevatorId +
                        "\nCurrent State: " + this.state.name() +
                        "\nCurrent Floor: " + this.currentFloor
        );
    }

//    public void fun() {
//        System.out.println("Elevator No: " + this.elevatorId + " moving from: " + this.currentFloor + " to: " + this.destination);
//        if (this.destination > this.currentFloor) {
//            this.state = ElevatorState.UP;
//            for (int i = 0; i < this.destination; i++) {
//                try {
//                    Thread.sleep(2000);
//                    this.currentFloor++;
//                    System.out.println("Elevator No: " + this.elevatorId + " on floor: " + this.currentFloor);
//                }
//                catch (Exception e) {
//                    System.out.println("Exception caught");
//                }
//
//            }
//            this.state = ElevatorState.STOP;
//        } else if (this.destination < this.currentFloor) {
//            System.out.println("Elevator No: " + this.elevatorId + " moving from: " + this.currentFloor + " to: " + this.destination);
//            this.state = ElevatorState.DOWN;
//            for (int i = this.currentFloor; i > this.destination; i--) {
//                try {
//                    Thread.sleep(2000);
//                    this.currentFloor--;
//                    System.out.println("Elevator No: " + this.elevatorId + " on floor: " + this.currentFloor);
//                }
//                catch (Exception e) {
//                    System.out.println("Exception caught");
//                }
//            }
//            this.state = ElevatorState.STOP;
//        } else {
//            System.out.println("Elevator No: " + this.elevatorId + " is already on floor: " + this.destination);
//        }
//    }
    public void run() {
        while (true) {
            // poll elevator destinations and call stack
            if (this.userDestinations.isEmpty()) {
                try {
                    System.out.println("Elevator is idle");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Exception caught");
                }
            } else {
                for (int i = 0; i < this.userDestinations.size(); i++) {
                    System.out.println(this.userDestinations.toString());
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Exception caught");
                }
            }
        }
    }
//    public void move(int destinationFloor) {
//        while (this.isAlive()) {
//            try {
//                System.out.println("Elevator is busy");
//                Thread.sleep(2000);
//            }
//            catch (Exception e) {
//                System.out.println("Exception caught");
//            }
//        }
//        this.destination = destinationFloor;
//        this.start();
//    }

}

class User {
    private int userId;
    private int userFloor;
    private ArrayList<Elevator> elevatorsToUse;
    private int destination;

    public User(int userId, ArrayList<Elevator> elevatorsToUse) {
        this.userId = userId;
        this.userFloor = 0;
        this.elevatorsToUse = elevatorsToUse;
        this.destination = 0;
    }

    public int getUserFloor() {
        return this.userFloor;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserFloor(int floor) {
        this.userFloor = floor;
    }

    public void callElevator(int destination) {
        this.destination = destination;
        boolean up = false;
        if (destination > this.userFloor) {
            up = true;
        }
        for (int i = 0; i < this.elevatorsToUse.size(); i++) {
            Elevator elevator = this.elevatorsToUse.get(i);
            if ((elevator.getElevatorState() == ElevatorState.STOP && elevator.getCurrentFloor() == this.userFloor) || // same floor as user and stopped
               (elevator.getElevatorState() != ElevatorState.UP && this.userFloor == 0) || // user is on ground floor and elevator not going up
               (up && this.userFloor > elevator.getCurrentFloor() && elevator.getElevatorState() != ElevatorState.DOWN) || // user wants to go up and elevator coming up from below
               (!up && this.userFloor < elevator.getCurrentFloor() && elevator.getElevatorState() != ElevatorState.UP) || // user wants to go down and elevator coming down from above
                elevator.getElevatorState() == ElevatorState.STOP) { // elevator is stopped (not very efficient)
                System.out.println("Calling Elevator: " + elevator.getElevatorId());
                elevator.addToUserCallList(this);
                break;
            }
        }
    }


}

public class MyElevator {

    public static final int MAX_FLOORS = 20;
    private int myCurrentFloor = 0;

    public static void main(String[] args) {
        Elevator el1 = new Elevator(1);
        Elevator el2 = new Elevator(2);
        Elevator el3 = new Elevator(3);

        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(el1);
        elevators.add(el2);
        elevators.add(el3);

        el1.start();

        User omer = new User(101, elevators);
        try {
            System.out.println("Waiting a bit");
            Thread.sleep(4000);
        }
        catch (Exception e) {
            System.out.println("Waiting a bit");
        }
        omer.callElevator(10);


    }


}
