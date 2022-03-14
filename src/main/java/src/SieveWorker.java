package src;

public class SieveWorker implements Runnable {
    private final int readFrom;
    private final int readTo;
    private final int root;
    private int numOfPrimes;
    byte[] oddNumbers;
    int[] primes;


    public SieveWorker(int readFrom, int readTo, int[] primes, int n) {
        this.readFrom = readFrom;
        this.readTo = readTo;
        this.root = Math.min((int) Math.sqrt(readTo), (int) Math.sqrt(n));
        this.primes = primes;
        oddNumbers = new byte[(readTo - readFrom / 16) + 1];
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
        oddNumbers[byteIndex] |= (1 << bitIndex);
    }

    private boolean isPrime(int num) {
        int bitIndex = (num % 16) / 2;
        int byteIndex = num / 16;

        return (oddNumbers[byteIndex] & (1 << bitIndex)) == 0;
    }

    public int[] collectPrimes() {
        int start = (readFrom % 2 == 0) ? readFrom + 1 : readFrom + 2;

        for (int i = start; i <= readTo; i += 2)
            if (isPrime(i))
                numOfPrimes++;


        int[] primes = new int[numOfPrimes];;
        int j = 0;
        for (int i = start; i <= readTo; i += 2)
            if (isPrime(i))
                primes[j++] = i;

        if (start == 1){
            primes[0] = 2;
        }
        return primes;
    }


}
