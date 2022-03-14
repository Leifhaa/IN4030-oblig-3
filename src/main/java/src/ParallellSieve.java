package src;

public class ParallellSieve {
    private int n;
    private final int m;
    private int cells;
    private int threads;

    public ParallellSieve(int n, int threads){
        this.n = n;
        this.m = (int) Math.sqrt(n);
        this.cells = n / 16 + 1;
        this.threads = threads;
    }

    public void Start(){
        Thread[] workers = new Thread[threads];
        int startByte = m / 16;
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

            SieveWorker sw = new SieveWorker(readFrom, readTo);
            workers[i] = new Thread(sw);
            workers[i].start();
            readFrom += readTo;

        }
    }
}
