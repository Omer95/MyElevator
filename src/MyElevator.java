import java.util.ArrayList;

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
    private int destination;

    public Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.state = ElevatorState.STOP;
        this.currentFloor = 0;
        this.destination = -1;
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

    public String toString() {
        return(
                "Elevator No: " + this.elevatorId +
                        "\nCurrent State: " + this.state.name() +
                        "\nCurrent Floor: " + this.currentFloor
        );
    }

    public void run() {
        System.out.println("Elevator No: " + this.elevatorId + " moving from: " + this.currentFloor + " to: " + this.destination);
        if (this.destination > this.currentFloor) {
            this.state = ElevatorState.UP;
            for (int i = 0; i < this.destination; i++) {
                try {
                    Thread.sleep(2000);
                    this.currentFloor++;
                    System.out.println("Elevator No: " + this.elevatorId + " on floor: " + this.currentFloor);
                }
                catch (Exception e) {
                    System.out.println("Exception caught");
                }

            }
            this.state = ElevatorState.STOP;
        } else if (this.destination < this.currentFloor) {
            System.out.println("Elevator No: " + this.elevatorId + " moving from: " + this.currentFloor + " to: " + this.destination);
            this.state = ElevatorState.DOWN;
            for (int i = this.currentFloor; i > this.destination; i--) {
                try {
                    Thread.sleep(2000);
                    this.currentFloor--;
                    System.out.println("Elevator No: " + this.elevatorId + " on floor: " + this.currentFloor);
                }
                catch (Exception e) {
                    System.out.println("Exception caught");
                }
            }
            this.state = ElevatorState.STOP;
        } else {
            System.out.println("Elevator No: " + this.elevatorId + " is already on floor: " + this.destination);
        }
    }
    public void move(int destinationFloor) {
        this.destination = destinationFloor;
        this.start();
    }

}

class User {
    private String userId;
    private int userFloor;
    private ArrayList<Elevator> elevatorsToUse;

    public User(String userId, ArrayList<Elevator> elevatorsToUse) {
        this.userId = userId;
        this.userFloor = 0;
        this.elevatorsToUse = elevatorsToUse;
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

        el1.move(2);
        el2.move(5);


    }

//    public Elevator callElevatorUp(ArrayList<Elevator> elevators) {
//        Elevator tmp = elevators.get(0);
//        int floorDiff = MAX_FLOORS;
//        for (int i = 0; i < elevators.size(); i++) {
//            if (Math.abs(myCurrentFloor - elevators.get(i).getCurrentFloor()) < floorDiff &&
//                elevators.) {
//                floorDiff = Math.abs(myCurrentFloor - elevators.get(i).getCurrentFloor());
//                tmp = elevators.get(i);
//            }
//        }
//        tmp.changeFloor(myCurrentFloor);
//        return tmp;
//    }


}
