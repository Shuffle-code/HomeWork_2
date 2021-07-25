package Multithreading;

public class CounterSafe {
    int count;
    synchronized void increase(){
        count ++;
    }
}
