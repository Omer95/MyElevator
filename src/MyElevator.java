import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

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
//    private HashMap<Integer, int[]> userDestinations;
//    private ArrayList<User> userCallList;
    private ArrayList<User> userCallStack;
    private ArrayList<User> usersInElevator;

    public Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.state = ElevatorState.STOP;
        this.currentFloor = 0;
//        this.userDestinations = new HashMap<Integer, int[]>();
//        this.userCallList = new ArrayList<User>();
        this.userCallStack = new ArrayList<User>();
        this.usersInElevator = new ArrayList<User>();
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
//    public void addUserDestination(int userId, int userFloor, int destination) {
//        int[] floorAndDest = {userFloor, destination};
//        this.userDestinations.put(userId, floorAndDest);
//        System.out.println("UserDestination Updated: " + this.userDestinations.toString());
//    }
//    public void addToUserCallList(User user) {
//        this.userCallList.add(user);
//    }
    public void addToUserCallStack(User user) {
        this.userCallStack.add(user);
        System.out.println("User: " + user.getUserId() + " called elevator");
    }

    public String toString() {
        return(
                "Elevator No: " + this.elevatorId +
                        "\nCurrent State: " + this.state.name() +
                        "\nCurrent Floor: " + this.currentFloor
        );
    }
    public void run() {
        while (true) {
            // poll user call list
            if (this.userCallStack.isEmpty()) {
                // check if users in elevator is empty, otherwise go to destinations
                System.out.println("Elevator: " + this.elevatorId + " is resting");
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e) {
                    System.out.println("Exception caught");
                }
            } else {
                // move to next user
                User nextUser = this.userCallStack.get(0);
                if (nextUser.getGoingUp()) {
                    this.changeElevatorState(ElevatorState.UP);
                    // go up
                    for (int i = this.currentFloor; i < nextUser.getDestination(); i++) {
                        // add user to elevator
                        for (User user : this.userCallStack) {
                            if (user.getUserFloor() == this.currentFloor) {
                                System.out.println("User: " + user.getUserId() + " got on elevator: " + this.elevatorId);
                                this.usersInElevator.add(user);
                                this.userCallStack.remove(user);
                            }
                        }
                        // drop user off on their floor and update users' floors
                        for (User user : this.usersInElevator) {
                            user.setUserFloor(this.currentFloor);
                            if (user.getDestination() == this.currentFloor) {
                                System.out.println("Dropping off user: " + user.getUserId() + " on floor: " + this.currentFloor);
                                this.usersInElevator.remove(user);
                                user.setGoingUp(null);
                            }
                        }
                        // go up one floor
                        this.currentFloor++;
                    }
                } else {
                    this.changeElevatorState(ElevatorState.DOWN);
                    // go down
                    for (int i = this.currentFloor; i > nextUser.getDestination(); i--) {
                        // add user to elevator
                        for (User user : this.userCallStack) {
                            if (user.getUserFloor() == this.currentFloor) {
                                System.out.println("User: " + user.getUserId() + " got on elevator: " + this.elevatorId);
                                this.usersInElevator.add(user);
                                this.userCallStack.remove(user);
                            }
                        }
                        // drop user off on their floor and update users' floors
                        for (User user : this.usersInElevator) {
                            user.setUserFloor(this.currentFloor);
                            if (user.getDestination() == this.currentFloor) {
                                System.out.println("Dropping off user: " + user.getUserId() + " on floor: " + this.currentFloor);
                                this.usersInElevator.remove(user);
                                user.setGoingUp(null);
                            }
                        }
                        // go down one floor
                        this.currentFloor--;
                    }
                }
                // remove next user
                this.userCallStack.remove(nextUser);
            }
        }
    }

}

class User {
    private int userId;
    private int userFloor;
    private ArrayList<Elevator> elevatorsToUse;
    private int destination;
    private Boolean goingUp;

    public User(int userId, ArrayList<Elevator> elevatorsToUse) {
        this.userId = userId;
        this.userFloor = 0;
        this.elevatorsToUse = elevatorsToUse;
        this.destination = 0;
        this.goingUp = null;
    }

    public int getUserFloor() {
        return this.userFloor;
    }
    public int getUserId() {
        return this.userId;
    }
    public Boolean getGoingUp() {
        return this.goingUp;
    }
    public int getDestination() {
        return this.destination;
    }
    public void setUserFloor(int floor) {
        this.userFloor = floor;
    }
    public void setGoingUp(Boolean up) {
        this.goingUp = up;
    }

    public void callElevator(int destination) {
        this.destination = destination;
        this.goingUp = false;
        if (destination > this.userFloor) {
            this.goingUp = true;
        }
        for (int i = 0; i < this.elevatorsToUse.size(); i++) {
            Elevator elevator = this.elevatorsToUse.get(i);
            if ((elevator.getElevatorState() == ElevatorState.STOP && elevator.getCurrentFloor() == this.userFloor) || // same floor as user and stopped
               (elevator.getElevatorState() != ElevatorState.UP && this.userFloor == 0) || // user is on ground floor and elevator not going up
               (goingUp && this.userFloor > elevator.getCurrentFloor() && elevator.getElevatorState() != ElevatorState.DOWN) || // user wants to go up and elevator coming up from below
               (!goingUp && this.userFloor < elevator.getCurrentFloor() && elevator.getElevatorState() != ElevatorState.UP) || // user wants to go down and elevator coming down from above
                elevator.getElevatorState() == ElevatorState.STOP) { // elevator is stopped (not very efficient)
                System.out.println("Calling Elevator: " + elevator.getElevatorId());
                elevator.addToUserCallStack(this);
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
