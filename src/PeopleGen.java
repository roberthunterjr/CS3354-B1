import java.util.concurrent.Callable;

public class PeopleGen implements Runnable {
    private Thread t;
    private int numPeople;
    private Callable task;
    PeopleGen(int numPeople, Callable task ) {
        this.task = task;
        this.numPeople = numPeople;
        System.out.println("Created People Gen Thread for " + numPeople + " people");
    }

    public void run() {
        System.out.println("Running the People Gen");
        int i = 1;
        while(i <= numPeople) {
            int random = (int)(12000* Math.random() + 2000);
            try {
                Thread.sleep(random);
                System.out.println("Person " + i + " spawned");
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("People generation completed");
    }

    public void start() {
        System.out.println("Starting the PeopleGen");
        this.t = new Thread(this, "PeopleGen");
        t.start();
    }
}
