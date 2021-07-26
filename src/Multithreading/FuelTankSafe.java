package Multithreading;

public class FuelTankSafe {
    float size = 20.5f;
    float get(){
        System.out.println("ENTER");
        synchronized (this) {
            size -= 1.25;
            return size;
    }

    }
}
