package HW_5;

import java.util.Arrays;


public class Main {

    private static final int size = 200000;
    private static final int h = size / 2;
    private static float[] arr = new float[size];

    public static void main(String[] args) {
        new Main().method_1(arr);
        new Main().methodTwoTread(arr);
        new Main().methodOneTread(arr);



//        increase(singleTime, multiTime);
    }

    static void calculations(float arr[]) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    static void methodTwoTread(float arr[]){

        Arrays.fill(arr, 1);
        long start = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> calculations(a1));
        Thread t2 = new Thread(() -> calculations(a2));

        t2.start();
        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long end = System.currentTimeMillis() - start;

        System.out.println("Runtime two tread: " + end);
    }

    static void methodOneTread(float arr[]){

        Arrays.fill(arr, 1);


        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        long start = System.currentTimeMillis();

        calculations(a1);
        calculations(a2);

        long end = System.currentTimeMillis() - start;

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);



        System.out.println("Runtime one tread: " + end);
    }


    static void method_1 (float [] arr){
        Arrays.fill(arr, 1);

        long start = System.currentTimeMillis();

        calculations(arr);

        long end = System.currentTimeMillis() - start;

        System.out.println("Runtime without tread: " + end);
    }
}

