package src;

public class ParallelFactorization {
    private int n;
    private final long base;
    private int threadCount;
    private int[] primes;
    private Thread[] threads;
    private FactorizeWorker[] workers;
    private FactorContainer factorContainer;
    private int nFactorizations = 100;


    public ParallelFactorization(int n, int threadCount, int[] primes) {
        this.n = n;
        this.base = (long) n * n;
        this.threadCount = threadCount;
        this.primes = primes;
        this.factorContainer = new FactorContainer(threadCount, n);
    }


    public void factorizeAll() {
        long readFrom = base - 100;
        for (long number = readFrom; number < base; number++) {
            factorContainer.setNumber(number);
            createThreads(number);
            startThreads();
            for (int i = 0; i < threadCount; i++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                    System.out.println("Exception : " + e);
                }
            }
            factorContainer.reset(number);
        }
        factorContainer.saveResults();
    }


    /**
     * Creates all worker threads which should factorize
     */
    private void createThreads(long base) {
        workers = new FactorizeWorker[threadCount];
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            createThread(i, base);
        }
    }

    private void startThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }


    private void createThread(int i, long base) {
        FactorizeWorker worker = new FactorizeWorker(i, base, threadCount, primes, factorContainer);
        workers[i] = worker;
        Thread t = new Thread(worker);
        threads[i] = t;
    }
}
