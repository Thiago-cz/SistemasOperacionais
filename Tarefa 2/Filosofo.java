import java.util.Random;

public class Filosofo implements Runnable {
    public Garfo garfoEsq;
    public Garfo garfoDir;
    public String nome;
    public boolean comer; // false para comer e true para refletir
    public int tempoComer;
    public int tempoRefletir;
    boolean pegouGarfoEsq = false;
    boolean pegouGarfoDir = false;

    public Filosofo(Garfo garfoEsq, Garfo garfoDir, String nome, int tempoComer, int tempoRefletir) {

        this.garfoDir = garfoDir;
        this.garfoEsq = garfoEsq;
        this.nome = nome;
        this.tempoComer = tempoComer;
        this.tempoRefletir = tempoRefletir;
        this.comer = false;
        Thread t = new Thread(this);
        t.start();
    }

    public void decidirAcao() {

        if (this.comer) {
            refletir();
        } else {
            comer();
        }

    }

    public void comer() {

        try {
            if (garfoDir.disponivel) {
                System.out.println(
                        "O filosofo" + nome + " pegou o garfo da sua direita (garfo " + garfoDir.numeroGarfo + ")");
                garfoDir.disponivel = false;
                pegouGarfoDir = true;
            }
            if (garfoEsq.disponivel) {
                System.out.println(
                        "O filosofo " + nome + " pegou o garfo da sua esquerda (garfo " + garfoEsq.numeroGarfo + ")");
                garfoEsq.disponivel = false;
                pegouGarfoEsq = true;
            }
            if (pegouGarfoDir && pegouGarfoEsq) {
                System.out.println("Filosofo " + nome + " esta comendo!!");
                Thread.sleep(tempoComer);
                System.out.println("Filosofo " + nome + " terminou de comer!!!");
                garfoDir.disponivel = true;
                garfoEsq.disponivel = true;
                pegouGarfoDir = false;
                pegouGarfoEsq = false;
                this.comer = true;
            } else {
                refletir();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void refletir() {
        try {
            System.out.println("O filosofo " + nome + " esta refletindo!!");
            if (pegouGarfoDir) {
                pegouGarfoDir = false;
                garfoDir.disponivel = true;
                System.out.println(
                        "O filosofo " + nome + " soltou o garfo da direita (garfo" + garfoDir.numeroGarfo + ")");
            } else {
                System.out.println(
                        "O filosofo " + nome + " tentou pegar o garfo da direita ocupado(garfo" + garfoDir.numeroGarfo
                                + ")");

            }
            if (pegouGarfoEsq) {
                pegouGarfoEsq = false;
                garfoEsq.disponivel = true;
                System.out.println(
                        "O filosofo " + nome + " soltou o garfo da esquerda (garfo" + garfoEsq.numeroGarfo + ")");
            } else {
                System.out.println(
                        "O filosofo " + nome + " tentou pegar o garfo da esquerda ocupado(garfo" + garfoEsq.numeroGarfo
                                + ")");
            }
            Thread.sleep(tempoRefletir);
            System.out.println("O filosofo " + nome + " terminou de refletir!!");
            this.comer = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        Random r = new Random();

        try {
            Thread.sleep(r.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            decidirAcao();
        }
    }

}
