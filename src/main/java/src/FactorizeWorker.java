package src;

public class FactorizeWorker implements Runnable {

    private int id;
    private long base;
    private int threadCount;
    /**
     * Primes which is used in factorization for this worker.
     */
    public int[] primes;
    private FactorContainer factorContainer;
    private int nFactorizations;

    public FactorizeWorker(int id, int n, int threadCount, int[] primes, FactorContainer factorMap, int nFactorizations) {
        this.id = id;
        this.base = (long)n * n;
        this.threadCount = threadCount;
        this.primes = primes;
        this.factorContainer = factorMap;
        this.nFactorizations = nFactorizations;
    }

    @Override
    public void run() {
        for (int i = 1; i < nFactorizations + 1; i++){
            long numberToFactorize = base - i;
            factorContainer.registerWorker(numberToFactorize, id);
            factorize(base - i);
        }
    }

    private void factorize(long number){
        long rest = number;
        for (int i = id; i < primes.length || rest == 0;){
            if (Math.pow(primes[i], 2) > rest){
                //Completed
                break;
            }
            else if (rest % primes[i] == 0){
                factorContainer.addFactor(number, id, primes[i]);
                rest = rest / primes[i];
            }
            else{
                i += threadCount;
            }
        }
    }
}
