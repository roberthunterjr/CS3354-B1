import java.util.LinkedList;
import java.util.Queue;

public class QManager {
    LineQ firstLine, secondLine, finalLine, outLine;
    Checker checkerOne, checkerTwo, checkerThree;
    public QManager() {
        this.firstLine = new LineQ("First Line", 20);
        this.secondLine = new LineQ("Second Line", 10);
        this.finalLine = new LineQ("FinalLine", 20);
        this.outLine = new LineQ("Disposable", 50);
        this.checkerOne = new Checker(firstLine, finalLine);
        this.checkerTwo = new Checker(secondLine, finalLine);
        this.checkerThree = new Checker(finalLine, outLine);
        checkerOne.start();
        checkerTwo.start();
        checkerThree.start();

    }

    public void addToQueue(String person) {
        Boolean goToOne = ((int)(Math.random() * 100)) % 2 == 0;
        if(goToOne) {
            this.firstLine.addToLine(person);
        } else {
            this.secondLine.addToLine(person);
        }
    }
    private static class LineQ {
        private int capacity;
        private int count;
        private String name;
        private Queue<String> storage = new LinkedList<String>();
        public LineQ(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
        public void addToLine(String person) {
            System.out.println("Adding " + person + " to line " + this.name);
            storage.add(person);
        }

        public String dequeue() {
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
    }
    private static class Checker implements Runnable{
        private Thread t;
        private LineQ line;
        private LineQ output;
        private String name;
        private int currentTimeLimit;
        private int normalTimeLimit = 15 * 60 * 1000; // Time 15 min in milliseconds
        private int rushTimeLimit = 2 * 60 * 1000; // Time 2 min in milliseconds
        private int criticalTimeLimit = 30 * 1000; // Time 30 seconds in milliseconds
        public Checker(LineQ line, LineQ output) {
            this.name = line.name + " Checker";
            this.currentTimeLimit = this.normalTimeLimit;
            this.line = line;
            this.output = output;
        }
        public void run() {
            while(true) {
                int checkTime = (int) (this.currentTimeLimit * Math.random() + 1000);    //Check time between 1 sec and current Timelimit
                try {
                    Thread.sleep(checkTime);
                    this.output.addToLine(line.dequeue());
                    this.checkLimits();
                } catch (InterruptedException e) {

                }
            }
        }

        /**
         * Used to peek at the line and adjust the speed accordingly
         */
        public void checkLimits() {
            System.out.println(line.name + " Capacity at " + line.getCapacity());
            if (line.getCapacity() > 0.75) {
                this.currentTimeLimit = this.criticalTimeLimit;
            } else if (line.getCapacity() > 0.5) {
                this.currentTimeLimit = this.rushTimeLimit;
            } else {
                this.currentTimeLimit = this.normalTimeLimit;
            }
        }
        public void start() {
            t.start();
        }
    }
}
