package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.Phaser.Control;

public class Main {

    public static void main(String[] args) {

        MyPhaser phaser=new MyPhaser();

        // Creates 5 students and register them in the phaser
        Student students[]=new Student[5];
        for (int i=0; i<students.length; i++){
            students[i]=new Student(phaser);
            phaser.register();  //register（）和bulkRegister(int parties),减少则使用 arriveAndDeregister()。
        }

        // Create 5 threads for the students and start them
        Thread threads[]=new Thread[students.length];
        for (int i=0; i<students.length; i++) {
            threads[i]=new Thread(students[i],"Student "+i);
            threads[i].start();
        }

        // Wait for the finalization of the threads
        for (int i=0; i<threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check that the Phaser is in the Terminated state
        System.out.printf("TaskDelay: The phaser has finished: %s.\n",phaser.isTerminated());

    }
}
