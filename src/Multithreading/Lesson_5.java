package Multithreading;

public class Lesson_5 {
    public static void main(String[] args) throws InterruptedException {
//        doMainThreadDemo();
//        System.out.println(Thread.currentThread().getId());
//        System.out.println(Thread.currentThread().getState());
//        doThreadStartDemoOne();
//        doThreadStartDemoTwo();
//        doThreadStartDemoTree();
//        doThreadStartDemoFour();
//        doTreadSleepDemo();
//        doConcurrencyDemo();
        doRaceConditionDemo();
//        doRaceConditionDemoTwo();


    }

    static void doRaceConditionDemoTwo() throws InterruptedException {
        CounterSafe counter = new CounterSafe();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 2000000; i++) {
                counter.increase();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 2000000; i++) {
                counter.increase();
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 2000000; i++) {
                counter.increase();
            }
        });
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println(counter.count);
    }

    static void doRaceConditionDemo(){
        FuelTankSafe tank = new FuelTankSafe();
        System.out.println(Thread.currentThread().getName());
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(tank.get());
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(tank.get());
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

    static void doConcurrencyDemo() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread t1 = new Thread(() -> {
            System.out.println("T1: " + Thread.currentThread().getName());
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            System.out.println("T2: " + Thread.currentThread().getName());
            for (int i = 5; i < 10; i++) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        });
        t2.start();

        t1.join();
        t2.join();
        System.out.println("END");
    }

    static void doTreadSleepDemo() throws InterruptedException{
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(5000);
        System.out.println("END");
    }

    static void doThreadStartDemoFour(){
        Thread t1 = new ThreadOne();
        t1.start();
    }

    static void doThreadStartDemoTree() {
        Thread t1 = new Thread(() -> {
                System.out.println("Implements of Runnable (lmd)");
                for (int i = 0; i < 5; i++) {
                    System.out.println(i);
                }
        });
        t1.start();
    }

    static void doThreadStartDemoTwo() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Implements of Runnable (anonymous)");
                for (int i = 0; i < 5; i++) {
                    System.out.println(i);
                }
            }
        });
        t1.start();
    }




    public static void doThreadStartDemoOne(){
        Thread t1 = new Thread(new RunnableOne());
        t1.start();
    }

    static void doMainThreadDemo(){
        System.out.println(Thread.currentThread().getName());
    }


    static void doOneThreadDemo(){
        System.out.println("Loop 1");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
        System.out.println("Loop 2");
        for (int i = 5; i < 10; i++) {
            System.out.println(i);
        }
    }

}
class RunnableOne implements Runnable{
    @Override
    public void run() {
        System.out.println("Implements of Runnable");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
    }
}
class ThreadOne extends Thread{
    @Override
    public void run() {
        System.out.println("Extending of Thread");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
    }
}
