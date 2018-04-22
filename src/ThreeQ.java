public class ThreeQ {
    public static void main(String[] args) {
        System.out.println("Hello World");
        QManager checkerLine = new QManager();
        PeopleGen pg = new PeopleGen(5);
        pg.start();
        System.out.println("After");
    }

}
