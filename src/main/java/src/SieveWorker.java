package src;

public class SieveWorker implements Runnable {
    private final int readFrom;
    private final int readTo;
    private final int n;
    private final int root;
    private int numOfPrimes;
    byte[] oddNumbers;
    int[] primes;



    public SieveWorker(int readFrom, int readTo, int[] primes, int n) {
        this.readFrom = readFrom;
        this.readTo = readTo;
        this.root = Math.min((int)Math.sqrt(readTo), (int)Math.sqrt(n));;
        this.n = n;
        this.primes = primes;
        oddNumbers = new byte[(readTo - readFrom / 16) + 1];
    }

    @Override
    public void run() {
        numOfPrimes = 1;
        for (int i = 1; i < primes.length; i++){
            if (primes[i] > root){
                break;
            }
            //Cross out based on pre-known primes
            traverse(primes[i]);
            numOfPrimes++;
        }
    }

    private void traverse(int prime) {
        for (int i = prime*prime; i <= n; i += prime * 2)
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

        int start = (root % 2 == 0) ? root + 1 : root + 2;

        for (int i = start; i <= readTo; i += 2)
            if (isPrime(i))
                numOfPrimes++;

        int[] primes = new int[numOfPrimes];

        primes[0] = 2;

        int j = 1;

        for (int i = 3; i <= readTo; i += 2)
            if (isPrime(i))
                primes[j++] = i;

        return primes;
    }


}
