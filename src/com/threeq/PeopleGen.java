package com.threeq;

public class PeopleGen implements Runnable {
    private Thread t;
    private int numPeople;
    private QManager lineManager;
    PeopleGen(int numPeople) {
        this.numPeople = numPeople;
        lineManager = new QManager(numPeople);
        System.out.println("Created People Gen Thread for " + numPeople + " people");
    }

    public void run() {
        System.out.println("Running the People Gen");
        int i = 1;
        while(i <= numPeople) {
            int random = ((int) (4 * 1000 * Math.random())) + 2000;
            try {
                Thread.sleep(random);
                System.out.println("Person " + i + " spawned");
                this.lineManager.addToQueue("Person " + i);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("People generation completed");
    }

    public void start() {
        System.out.println("Starting the com.threeq.PeopleGen");
        this.t = new Thread(this, "com.threeq.PeopleGen");
        t.start();
    }
}
