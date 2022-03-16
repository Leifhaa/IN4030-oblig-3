package src;

public class SieveWorker implements Runnable {
    private final int readFrom;
    private final int readTo;
    private byte[] byteArray;
    private final int root;
    private int numOfPrimes;
    int[] primes;


    public SieveWorker(int readFrom, int readTo, int[] primes, int n, byte[] byteArray) {
        this.readFrom = readFrom;
        this.readTo = readTo;
        this.byteArray = byteArray;
        this.root = Math.min((int) Math.sqrt(readTo), (int) Math.sqrt(n));
        this.primes = primes;
    }

    @Override
    public void run() {
        for (int i = 1; i < primes.length; i++) {
            if (primes[i] > root) {
                break;
            }
            //Cross out based on pre-known primes
            traverse(primes[i]);
        }

    }

    private void traverse(int prime) {
        for (int i = prime * prime; i <= readTo; i += prime * 2)
            if (i >= readFrom)
                mark(i);

    }

    private void mark(int num) {
        int bitIndex = (num % 16) / 2;
        int byteIndex = num / 16;
        byteArray[byteIndex] |= (1 << bitIndex);
    }
}
