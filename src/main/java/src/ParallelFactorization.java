package src;

import java.util.concurrent.CyclicBarrier;

public class ParallelFactorization {
    private int n;
    private final long base;
    private int threadCount;
    private int[] primes;
    private Thread[] threads;
    private FactorizeWorker[] workers;
    private ParallelSieve parallelSieve;
    private FactorContainer factorContainer;
    private int nFactorizations = 100;


    public ParallelFactorization(int n, int threadCount) {
        this.n = n;
        this.base = (long)n * n;
        this.threadCount = threadCount;
        this.parallelSieve = new ParallelSieve(n, threadCount);
        this.factorContainer = new FactorContainer(base - nFactorizations, base, threadCount);
    }


    public void factorizeAll() {
        parallelSieve.start();
        primes = parallelSieve.collectPrimes();
        createThreads();
        startThreads();

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
        }
        Oblig3Precode res = factorContainer.getResults(n);
    }


    /**
     * Creates all worker threads which should factorize
     */
    private void createThreads() {
        workers = new FactorizeWorker[threadCount];
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            createThread(i);
        }
    }

    private void startThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }


    private void createThread(int i) {
        FactorizeWorker worker = new FactorizeWorker(i, n, threadCount, primes, factorContainer, nFactorizations);
        workers[i] = worker;
        Thread t = new Thread(worker);
        threads[i] = t;
    }

}
