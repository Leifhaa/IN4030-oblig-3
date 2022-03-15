package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FactorizeWorker implements Runnable {

    private int id;
    private long base;
    private int threadCount;
    /**
     * Primes which is used in factorization for this worker.
     */
    public int[] primes;
    private HashMap<Long, HashMap<Integer, ArrayList<Integer>>> factorMap;
    private int nFactorizations;

    public FactorizeWorker(int id, int n, int threadCount, int[] primes, HashMap<Long, HashMap<Integer, ArrayList<Integer>>> factorMap, int nFactorizations) {
        this.id = id;
        this.base = (long)n * n;
        this.threadCount = threadCount;
        this.primes = primes;
        this.factorMap = factorMap;
        this.nFactorizations = nFactorizations;
    }

    @Override
    public void run() {
        for (int i = 1; i < nFactorizations; i++){
            long numberToFactorize = base - i;
            factorMap.get(numberToFactorize).put(id, new ArrayList<>());
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
                addFactor(number, primes[i]);
                rest = rest / primes[i];
            }
            else{
                i += threadCount;
            }
        }
    }

    private void addFactor(long number, int prime){
        factorMap.get(number).get(id).add(prime);
    }
}
