public class Main {
    public static void main(String[] args) {
        Garfo g1 = new Garfo(1);
        Garfo g2 = new Garfo(2);
        Garfo g3 = new Garfo(3);
        Garfo g4 = new Garfo(4);
        Garfo g5 = new Garfo(5);

        Filosofo f1 = new Filosofo(g1, g2, "Joao Pedrao", 20000, 8000);
        Filosofo f2 = new Filosofo(g2, g3, "Buda", 18000, 8500);
        Filosofo f3 = new Filosofo(g3, g4, "Socrates", 40000, 10000);
        Filosofo f4 = new Filosofo(g4, g5, "Platao", 10000, 8300);
        Filosofo f5 = new Filosofo(g5, g1, "Aristoteles", 5000, 7200);

    }
}