package Multithreading;

public class FuelTank {
    float size = 20.5f;
    float get(){
        size -= 1.25;
        return size;
    }
}
