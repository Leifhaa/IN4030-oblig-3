package src;

public class ParallelSieve {
    private int n;
    private final int m;
    private int threads;
    private SieveOfEratosthenes sieve;
    private byte[] byteArray;

    /**
     * One byte represents a set of 16 numbers (see SieveOfErathosthens.java for more details)
     */
    int numbersInByte = 16;

    public ParallelSieve(int n, int threads){
        this.n = n;
        this.m = (int) Math.sqrt(n);
        this.threads = threads;
        sieve = new SieveOfEratosthenes(m);
    }

    public int[] collectPrimes() {
        int start = 1; // (readFrom % 2 == 0) ? readFrom + 1 : readFrom + 2;
        int numOfPrimes = 0;

        for (int i = start; i <= n; i += 2)
            if (isPrime(i))
                numOfPrimes++;


        int[] primes = new int[numOfPrimes];;
        int j = 0;
        for (int i = start; i <= n; i += 2)
            if (isPrime(i))
                primes[j++] = i;

        if (start == 1){
            primes[0] = 2;
        }
        return primes;
    }

    private boolean isPrime(int num) {
        int bitIndex = (num % 16) / 2;
        int byteIndex = num / 16;

        return (byteArray[byteIndex] & (1 << bitIndex)) == 0;
    }


    public void start(){
        int[] primes = sieve.getPrimes();
        Thread[] workers = new Thread[threads];
        int readFrom = 3;
        int readSize = n / threads;
        int cells = n / 16 + 1;
        byteArray = new byte[cells];


        for (int i = 0; i < threads; i++){
            int readTo = readFrom + readSize;
            int rest = readTo % 16;
            readTo += 16 - rest;
            if (readTo > n) {
                readTo = n;
            }


            SieveWorker sw = new SieveWorker(readFrom, readTo, primes, n, byteArray);
            workers[i] = new Thread(sw);
            workers[i].start();
            readFrom = readTo + 1;

        }
        for (int i = 0; i < this.threads; i++) {
            try { workers[i].join();
            } catch (InterruptedException e) {}
        }
    }


}
