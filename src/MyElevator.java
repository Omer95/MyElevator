import java.util.ArrayList;
import java.lang.Math;

enum ElevatorState {
    UP, DOWN, STOP;
}
enum Direction {
    UP, DOWN;
}
class Elevator {
    private int elevatorId;
    private ElevatorState state;
    private int currentFloor;

    public Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.state = ElevatorState.STOP;
        this.currentFloor = 0;
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
