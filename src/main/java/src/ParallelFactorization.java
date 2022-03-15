package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.CyclicBarrier;

public class ParallelFactorization {
    private int n;
    private final long base;
    private int threadCount;
    private int[] primes;
    private Thread[] threads;
    private FactorizeWorker[] workers;
    private ParallelSieve parallelSieve;
    private HashMap<Long, HashMap<Integer, ArrayList<Integer>>> factorMap;
    final CyclicBarrier barrierThreads;
    private int nFactorizations = 100;


    public ParallelFactorization(int n, int threadCount) {
        this.n = n;
        this.base = (long)n * n;
        this.threadCount = threadCount;
        this.parallelSieve = new ParallelSieve(n, threadCount);
        factorMap = new HashMap<>(nFactorizations);
        for (int i = 1; i < nFactorizations; i++) {
            factorMap.put(base - i, new HashMap<>());
        }
        barrierThreads = new CyclicBarrier(threadCount);
    }


    public void factorizeAll() {
        parallelSieve.start();
        primes = parallelSieve.collectPrimes();
        createThreads();
        startThreads();

        try {
            barrierThreads.await();
        } catch (Exception e) {

        }
    }


    /**
     * Creates all worker threads which should factorize
     */
    private void createThreads() {
        workers = new FactorizeWorker[threadCount];
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            createThread(i);
            /*
            if (i < primes.length % threadCount){
                //Has to read one extra prime as they don't divide perfectly
                createThread(primesPerThread + 1, i);
            }
            else{
            }

             */
        }
    }

    private void startThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }


    private void createThread(int i) {
        FactorizeWorker worker = new FactorizeWorker(i, n, threadCount, primes, factorMap, nFactorizations);
        workers[i] = worker;
        Thread t = new Thread(worker);
        threads[i] = t;
    }

}
