import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[][] swap = preencherSwap();
        int[][] ram = preencherRam(swap);

        imprimir(ram, swap);

        for (int i = 0; i < 1000; i++) {

            if ((i + 1) % 10 == 0) {
                resetarBitR(ram);
            }
            simulador(ram, swap, 5);
        }

        imprimir(ram, swap);
    }

    public static void imprimir(int[][] ram, int[][] swap) {
        System.out.println("\n");

        for (int[] i : swap) {
            System.out.println("");
            System.out.print("[ ");
            for (int j : i) {
                System.out.print(j + ", ");
            }
            System.out.print("]");
        }

        System.out.println("\n");

        for (int[] i : ram) {
            System.out.println("");
            System.out.print("[ ");
            for (int j : i) {
                System.out.print(j + ", ");
            }
            System.out.print("]");
        }

        System.out.println("\n");
    }

    // ok
    public static boolean simulador(int[][] ram, int[][] swap, int algoritmo) {

        int numPagina = sortearNum(0, 100);

        // Verificar se esta carregada na RAM
        for (int i = 0; i < 10; i++) {
            if (numPagina == ram[i][1]) {
                ram[i][3] = 1;
                Random r = new Random();
                double chance = r.nextDouble();
                double probabilidade = 0.3;

                if (chance < probabilidade) {
                    ram[i][2] += 1;
                    ram[i][4] = 1;
                }
                return true;
            }
        }

        // Algotimos de substituicao de pagina
        switch (algoritmo) {
            case 1:
                nru(ram, swap, numPagina);
                break;
            case 2:
                fifo(ram, swap, numPagina);
                break;
            case 3:
                fifoSc(ram, swap, numPagina);
                break;
            case 4:
                clock(ram, swap, numPagina);
                break;
            case 5:
                wsClock(ram, swap, numPagina);
                break;
            default:
                throw new RuntimeException();
        }
        return false;
    }

    private static void wsClock(int[][] ram, int[][] swap, int numPagina) {
        int ponteiro = 0;
        int ep = sortearNum(100, 9999);

        while (true) {

            if (ram[ponteiro][3] == 0) {

                if (ep <= ram[ponteiro][5]) {

                } else {
                    if (ram[ponteiro][4] == 1){
                        atualizarSwap(ram[ponteiro][0], swap);
                    }else{
                        ram[ponteiro] = null;
                        ram[ponteiro] = swap[buscarPagina(swap, numPagina)].clone();
                        return;
                    }
                }
            }

            // move o ponteiro para o proxima pagina
            ram[ponteiro][3] = 0;
            ponteiro = (ponteiro + 1) % 10;
            ram[ponteiro][5] -= 1;
        }
    }

    // ok
    private static void clock(int[][] ram, int[][] swap, int numPagina) {

        int ponteiro = 0;
        while (true) {

            if (ram[ponteiro][3] == 0) {
                if (ram[ponteiro][4] == 1){
                    atualizarSwap(ram[ponteiro][0], swap);
                }else{
                    ram[ponteiro] = null;
                    ram[ponteiro] = swap[buscarPagina(swap, numPagina)].clone();
                    return;
                }
            }
            // move o ponteiro para o proxima pagina
            ram[ponteiro][3] = 0;
            ponteiro = (ponteiro + 1) % 10;
        }

    }

    // ok
    public static void nru(int[][] ram, int[][] swap, int numPaginaSorteada) {

        ArrayList<ArrayList<int[]>> classes = new ArrayList<>();
        classes.add(new ArrayList<int[]>());// bit R=0 e bit M=0 (classe nivel 1)
        classes.add(new ArrayList<int[]>());// bit R=0 e bit M=1 (classe nivel 2)
        classes.add(new ArrayList<int[]>());// bit R=1 e bit M=0 (classe nivel 3)
        classes.add(new ArrayList<int[]>());// bit R=1 e bit M=1 (classe nivel 4)

        // classificar paginas pelos bits M e R
        for (int[] pagina : ram) {
            // deslocar bit para a esquerda e combina-lo para formar um numero de 0 a 3 para
            // criar os indexes das classes
            int pos = pagina[3] << 1 | pagina[4];
            classes.get(pos).add(pagina);
        }

        int numPagSair = 0;
        // escolher pagina com menor prioridade para sair da ram
        for (int i = 0; i < classes.size(); i++) {
            if (!classes.get(i).isEmpty()) {
                numPagSair = classes.get(i).get(0)[0];
                classes.get(i).remove(0);
                break;
            }
        }
        int posPaginaSair = buscarPagina(ram, numPagSair);

        if (ram[posPaginaSair][4] == 1)
            atualizarSwap(numPagSair, swap);

        ram[posPaginaSair] = null;
        ram[posPaginaSair] = swap[buscarPagina(swap, numPaginaSorteada)].clone();

    }

    // ok
    public static void fifo(int[][] ram, int[][] swap, int numPagina) {

        if (ram[0][4] == 1) {
            // atualizar bit M=0 na memoria
            atualizarSwap(ram[0][4], swap);
        }
        ram[0] = null;
        ram[0] = ram[1].clone();
        ram[1] = null;
        ram[1] = ram[2].clone();
        ram[2] = null;
        ram[2] = ram[3].clone();
        ram[3] = null;
        ram[3] = ram[4].clone();
        ram[4] = null;
        ram[4] = ram[5].clone();
        ram[5] = null;
        ram[5] = ram[6].clone();
        ram[6] = null;
        ram[6] = ram[7].clone();
        ram[7] = null;
        ram[7] = ram[8].clone();
        ram[8] = null;
        ram[8] = ram[9].clone();
        ram[9] = null;
        ram[9] = swap[buscarPagina(swap, numPagina)].clone();

    }

    // ok
    public static void fifoSc(int[][] ram, int[][] swap, int numPagina) {

        if (ram[0][4] == 1) {
            // atualizar bit M=0 na memoria
            atualizarSwap(ram[0][0], swap);
        }
        if (ram[0][3] == 1) {
            int[] pagina = ram[0].clone();
            ram[0] = null;
            ram[0] = ram[1].clone();
            ram[1] = null;
            ram[1] = ram[2].clone();
            ram[2] = null;
            ram[2] = ram[3].clone();
            ram[3] = null;
            ram[3] = ram[4].clone();
            ram[4] = null;
            ram[4] = ram[5].clone();
            ram[5] = null;
            ram[5] = ram[6].clone();
            ram[6] = null;
            ram[6] = ram[7].clone();
            ram[7] = null;
            ram[7] = ram[8].clone();
            ram[8] = null;
            ram[8] = ram[9].clone();
            ram[9] = null;
            ram[9] = pagina.clone();
            return;
        }

        ram[0] = null;
        ram[0] = ram[1].clone();
        ram[1] = null;
        ram[1] = ram[2].clone();
        ram[2] = null;
        ram[2] = ram[3].clone();
        ram[3] = null;
        ram[3] = ram[4].clone();
        ram[4] = null;
        ram[4] = ram[5].clone();
        ram[5] = null;
        ram[5] = ram[6].clone();
        ram[6] = null;
        ram[6] = ram[7].clone();
        ram[7] = null;
        ram[7] = ram[8].clone();
        ram[8] = null;
        ram[8] = ram[9].clone();
        ram[9] = null;
        ram[9] = swap[buscarPagina(swap, numPagina)].clone();
    }

    // ok
    public static void resetarBitR(int[][] ram) {
        for (int[] i : ram) {
            i[3] = 0;
        }
    }

    // ok
    public static int buscarPagina(int[][] arr, int numPagina) {
        int pos = 0;
        for (int[] i : arr) {
            if (i[0] == numPagina)

                return pos;
            pos++;
        }
        throw new RuntimeException();
    }

    // ok
    public static int[][] preencherSwap() {
        int[][] swap = new int[100][6];

        for (int i = 0; i < 100; i++) {
            swap[i][0] = i; // numero da pagina(N)
            swap[i][1] = i + 1; // instrucao(I)
            swap[i][2] = sortearNum(0, 50); // Dado(D)
            swap[i][3] = 0; // Bit de acesso(R)
            swap[i][4] = 0; // Bit de modificacao(M)
            swap[i][5] = sortearNum(100, 9999); // Tempo de envelhecimento(T)
        }

        return swap;
    }

    // ok
    public static void atualizarSwap(int numPagina, int[][] swap) {
        for (int[] i : swap) {
            if (i[0] == numPagina) {
                i[4] = 0;
            }
        }
    }

    // ok
    public static int[][] preencherRam(int[][] swap) {
        Random r = new Random();
        int[][] ram = new int[10][6];
        int[] numSorteados = new int[10]; //paginas do swap
        int num = r.nextInt(10);
        // sortear numeros nao repetidos
        for (int i = 0; i < 10; i++) {
            boolean control = true;

            while (control) {
                control = false;
                num = r.nextInt(100);
                for (int j = 0; j < 10; j++) {
                    if (num == numSorteados[j]) {
                        control = true;
                    }
                }
            }
            numSorteados[i] = num;
        }
        // preenchendo a memoria ram
        for (int i = 0; i < 10; i++) {
            ram[i] = swap[numSorteados[i]]; // copiar linhas do swap para ram
        }
        return ram;
    }

    // ok
    public static int sortearNum(int start, int end) {
        Random r = new Random();
        int num = 0;
        for (int i = 0; i <= end; i++) {
            num = r.nextInt(end++);
            if (num <= end && num >= start) {
                break;
            }

        }
        return num;
    }
}