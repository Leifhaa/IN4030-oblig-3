package src;

public class ParallelSieve {
    private int n;
    private final int m;
    private int cells;
    private int threads;
    private SieveOfEratosthenes sieve;

    /**
     * One byte represents a set of 16 numbers (see SieveOfErathosthens.java for more details)
     */
    int numbersInByte = 16;

    public ParallelSieve(int n, int threads){
        this.n = n;
        this.m = (int) Math.sqrt(n);
        this.cells = n / numbersInByte + 1;
        this.threads = threads;
        sieve = new SieveOfEratosthenes(n);
    }

    public void start(){
        sieve.getPrimes();
        Thread[] workers = new Thread[threads];
        int startByte = m / numbersInByte;
        int readSize = (cells - startByte) / threads;
        int readFrom = startByte;

        for (int i = 0; i < threads; i++){
            int readTo = readSize;
            if (i < ((cells - startByte) % threads)){
                /*
                    The threads could not split evenly so this thread will read an extra element
                 */
                readTo++;
            }

            //SieveWorker sw = new SieveWorker(readFrom, readTo, 1);
            //workers[i] = new Thread(sw);
            //workers[i].start();
            readFrom += readTo;

        }
    }


}
