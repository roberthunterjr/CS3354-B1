package com.threeq;

import java.util.LinkedList;
import java.util.Queue;

public class QManager {
    LineQ firstLine, secondLine, finalLine, completedLine;
    Checker checkerOne, checkerTwo, checkerThree;
    Boolean running;
    public QManager(int capacity) {
        this.running = true;
        this.firstLine = new LineQ("First Line", 20);
        this.secondLine = new LineQ("Second Line", 10);
        this.finalLine = new LineQ("FinalLine", 20);
        this.completedLine = new CompletedLineQ(capacity);
        this.checkerOne = new Checker(firstLine, finalLine);
        this.checkerTwo = new Checker(secondLine, finalLine);
        this.checkerThree = new Checker(finalLine, completedLine);
        checkerOne.start();
        checkerTwo.start();
        checkerThree.start();

    }

    public void addToQueue(String person) {
        Boolean goToOne = ((int)(Math.random() * 100)) % 2 == 0;
        if(firstLine.atCapacity() && secondLine.atCapacity()) {
            // The simulation fails. Stop running
            running = false;
        } else if(firstLine.atCapacity()) {
            // Add to secondLine
            secondLine.addToLine(person);
        } else if(secondLine.atCapacity()) {
            firstLine.addToLine(person);
        } else if(goToOne) {
            //  Add randomly
            this.firstLine.addToLine(person);
        } else {
            this.secondLine.addToLine(person);
        }
    }

    /**
     * Class that maintains people (strings) in the line
     * Utilize Queue datastructure with Linklist instantiated
     */
    private class LineQ {
        private int capacity;
        private int count;
        private String name;
        private Queue<String> storage = new LinkedList<String>();
        public LineQ(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
            this.count = 0;
        }
        public boolean addToLine(String person) {
            if(this.atCapacity()) {
                System.out.println(this.name + " at capacity! Did not add");
                return false;
            }
            System.out.println("Adding " + person + " to " + this.name);
            storage.add(person);
            this.count++;
            return true;
        }

        public String peek() {
           return this.storage.peek();
        }

        public String dequeue() {
            this.count = this.count - 1;
            System.out.println("Removing "+ storage.peek() + " from line " + this.name);
            return storage.poll();
        }
        public int getCapacity() {
            return this.capacity;
        }

        public int getCount() {
            return this.count;
        }
        public double getUsage() {
            return (this.count/this.capacity);
        }

        public boolean atCapacity() {
            return this.getCapacity() == this.getCount();
        }
    }

    /**
     * Special class extends LineQ to handle complete condition which triggers running to change to false
     */

    private class CompletedLineQ extends LineQ {
        public CompletedLineQ(int capacity) {
            super("CompletedLine", capacity);
        }

        @Override
        /**
         * Special override that checks the capacity each time items are added to this line.
         * When it hits capacity, set running to false which should kill other threads
         */
        public boolean addToLine(String person) {
            super.addToLine(person);
            System.out.println(this.getCount() + " out of " + this.getCapacity() + " people successfully processed");
            if (this.getCount() == this.getCapacity()) {
                running = false;
            }
            return true;
        }
    }

    /**
     * Checker Class used to manage the tasks of each queue. Executed on separate threads
     */
    private class Checker implements Runnable{
        private Thread t;
        private LineQ line;
        private LineQ output;
        private String name;
        private int currentTimeLimit;
        private int normalTimeLimit = 15 * 6 * 1000; // Time 15 min in milliseconds
        private int rushTimeLimit = 2 * 6 * 1000; // Time 2 min in milliseconds
        private int criticalTimeLimit = 3 * 1000; // Time 30 seconds in milliseconds
        public Checker(LineQ line, LineQ output) {
            this.name = line.name + " com.threeq.Checker";
            this.currentTimeLimit = this.normalTimeLimit;
            this.line = line;
            this.output = output;
        }
        public void run() {
            while(running) {
                int checkTime = (int) (this.currentTimeLimit * Math.random() + 1000);    //Check time between 1 sec and current Timelimit
                try {
                    Thread.sleep(checkTime);
                    if(line.peek() == null) {
                        System.out.println(this.line.name + " Empty....waiting");
                    } else if(output.atCapacity()) {
                        //We cant dequeue this line, we have to wait...bummer
                    } else {
                        // Next line is free for more people,
                        this.output.addToLine(line.dequeue());
                        this.checkLimits();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error" + e);
                }
            }
        }

        /**
         * Used to peek at the line and adjust the speed accordingly
         */
        public void checkLimits() {
            System.out.println(line.name + " Capacity at " + line.getCount() + " out of " + line.getCapacity());
            if (line.getUsage() > 0.75) {
                System.out.println(line.name + " Critical!");
                this.currentTimeLimit = this.criticalTimeLimit;
            } else if (line.getUsage() > 0.5) {
                System.out.println(line.name + " Filling fast!");
                this.currentTimeLimit = this.rushTimeLimit;
            } else {
                this.currentTimeLimit = this.normalTimeLimit;
            }
        }
        public void start() {
            this.t = new Thread(this, this.name);
            t.start();
        }
    }
}
