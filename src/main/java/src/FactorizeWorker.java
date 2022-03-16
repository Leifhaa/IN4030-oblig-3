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

    public FactorizeWorker(int id, long base, int threadCount, int[] primes, FactorContainer factorMap, int nFactorizations) {
        this.id = id;
        this.base = base;
        this.threadCount = threadCount;
        this.primes = primes;
        this.factorContainer = factorMap;
        this.nFactorizations = nFactorizations;
    }

    @Override
    public void run() {
        for (int i = 1; i < nFactorizations + 1; i++){
            factorize(base - i);
        }
    }

    private void factorize(long number){
        for (int i = id; i < primes.length || factorContainer.rests.get(number) == 0;){
            if (Math.pow(primes[i], 2) > factorContainer.rests.get(number)){
                //Completed
                break;
            }
            else if (factorContainer.rests.get(number) % primes[i] == 0){
                factorContainer.addFactor(number, id, primes[i]);
            }
            else{
                i += threadCount;
            }
        }
    }
}
