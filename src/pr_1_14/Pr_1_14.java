/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pr_1_14;

/**
 *
 * @author andre
 */
//Кравцов Андрей РИБО-02-22
public class Pr_1_14 {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        Thread t1 = new Thread(new Worker("Thread-0"));
        Thread t2 = new Thread(new Worker("Thread-1"));

        t1.start();
        t2.start();

        System.out.println("Для прерывания нажмите любую кнопку");

        try {
            System.in.read(); // Ждем нажатия клавиши для завершения программы
            t1.interrupt();
            t2.interrupt();
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Кравцов Андрей РИБО-02-22");
    }

    static class Worker implements Runnable {
        private final String name;
        private static final Object lock = new Object();
        private static int currentThread = 0;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    if (name.equals("Thread-" + currentThread)) {
                        System.out.println(name);
                        currentThread = (currentThread + 1) % 2;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }    
}
