package src;

public class FactorizeWorker implements Runnable {

    private final long number;
    private int id;
    private int threadCount;
    /**
     * Primes which is used in factorization for this worker.
     */
    public int[] primes;
    private FactorContainer factorContainer;

    public FactorizeWorker(int id, long number, int threadCount, int[] primes, FactorContainer factorMap) {
        this.id = id;
        this.number = number;
        this.threadCount = threadCount;
        this.primes = primes;
        this.factorContainer = factorMap;
    }

    @Override
    public void run() {
        try {
            factorize(number);
        }
        catch (Exception e){
            System.out.println("hey");
        }
    }

    private void factorize(long number){
        for (int i = id; i < primes.length; i += threadCount){
            if (primes[i] > factorContainer.tmpRoot){
                //Completed
                break;
            }
            while (factorContainer.rest % primes[i] == 0){
                factorContainer.addFactor(number, primes[i]);
            }
        }
    }
}
