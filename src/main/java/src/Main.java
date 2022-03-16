package src;

public class Main {

    public static void main(String[] args) {
        int n;
        int threads;
        int mode;

        if (args.length != 3) {
            printInstructions();
        }


        try {
            n = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("First argument is not an integer");
            printInstructions();
            return;
        }
        if (n < 16) {
            System.out.println("First argument can not be lower than 16");
            return;
        }

        try {
            threads = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Second argument is not an integer");
            printInstructions();
            return;
        }

        if (threads < 0) {
            System.out.println("Threads argument can not be negative");
            printInstructions();
            return;
        }
        if (threads == 0) {
            threads = Runtime.getRuntime().availableProcessors();
        }

        try {
            mode = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Third argument is not an integer");
            printInstructions();
            return;
        }

        if (mode < 0 || mode > 3) {
            System.out.println("Third argument has to be between 0 and 3");
            printInstructions();
            return;
        }

        if (mode == 0) {
            long start = System.nanoTime();
            int[] paraPrimes = runSequentialSieve(n);
            runSequentialFactorization(n, paraPrimes);
            double end = (double) (System.nanoTime() - start) / 1000000;
            System.out.println("Finished in: " + end + "ms");
        } else if (mode == 1) {
            long start = System.nanoTime();
            int[] primes = runParallelSievve(n, threads);
            runParallelFactorization(n, threads, primes);
            double end = (double) (System.nanoTime() - start) / 1000000;
            System.out.println("Finished in: " + end + "ms");
        } else if (mode == 2) {
            runBenchmarks(threads);
        }
    }

    private static void runParallelFactorization(int n, int threads, int[] primes) {
        ParallelFactorization para = new ParallelFactorization(n, threads, primes);
        para.factorizeAll();
    }

    private static int[] runParallelSievve(int n, int threads) {
        ParallelSieve sieve = new ParallelSieve(n, threads);
        sieve.start();
        return sieve.collectPrimes();
    }

    private static void runSequentialFactorization(int n, int[] paraPrimes) {
        Factorization factorize = new Factorization(n, paraPrimes);
        factorize.factorizeAll();
        factorize.precode.writeFactors("Sequentual");
    }

    private static int[] runSequentialSieve(int n) {
        SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
        int[] paraPrimes = sieve.getPrimes();
        return paraPrimes;
    }

    private static void runBenchmarks(int threads) {
        System.out.println("Running benchmarks. This can take a while, please wait...");
        int[] testNumbers = {2000000, 20000000, 200000000, 2000000000};

        System.out.println("Starting benchmarking sieves...");
        for (int testNumber : testNumbers) {
            speedTestSieves(testNumber, threads);
        }
        System.out.println("Finished benchmarking sieves...");
        System.out.println("--------------------------------");


        System.out.println("Starting benchmarking factorization...");
        for (int testNumber : testNumbers) {
            speedTestFactorization(testNumber, threads);
        }
        System.out.println("Finished benchmarking factorization...");
        System.out.println("--------------------------------");

        for (int testNumber : testNumbers) {
            speedTestWholeProcedures(testNumber, threads);
        }

    }

    private static void speedTestWholeProcedures(int testNumber, int threads) {
        int totalRuns = 7;
        double[] seqTimes = new double[totalRuns];
        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            int[] sieve = runSequentialSieve(testNumber);
            runSequentialFactorization(testNumber, sieve);
            seqTimes[i] = (System.nanoTime() - time) / 1000000.0;
        }
        double seqMedian = seqTimes[(seqTimes.length) / 2];
        System.out.println("Sequential used median time " + seqMedian + " for n = " + testNumber);

        double[] paraTimes = new double[totalRuns];
        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            int[] sieve = runParallelSievve(testNumber, threads);
            runParallelFactorization(testNumber, threads, sieve);
            paraTimes[i] = (System.nanoTime() - time) / 1000000.0;
        }

        double paraMedian = paraTimes[(paraTimes.length) / 2];
        System.out.println("Parallel used median time " + paraMedian + " for n = " + testNumber);
    }

    private static void speedTestFactorization(int testNumber, int threads) {
        int totalRuns = 7;
        int[] primes = runParallelSievve(testNumber, threads);

        double[] seqFac = new double[totalRuns];
        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            runSequentialFactorization(testNumber, primes);
            seqFac[i] = (System.nanoTime() - time) / 1000000.0;
        }
        double seqMedian = seqFac[(seqFac.length) / 2];
        System.out.println("Sequential Factorization used median time " + seqMedian + " for n = " + testNumber);


        double[] paraFac = new double[totalRuns];
        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            runParallelFactorization(testNumber, threads, primes);
            paraFac[i] = (System.nanoTime() - time) / 1000000.0;
        }
        double parMedian = paraFac[(paraFac.length) / 2];
        System.out.println("Paralell Factorization used median time " + parMedian + " for n = " + testNumber);
        System.out.println("Speedup: " + seqMedian /  parMedian);
    }


    private static void speedTestSieves(int testNumber, int threads) {
        int totalRuns = 7;
        double[] seqSieve = new double[totalRuns];

        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            runSequentialSieve(testNumber);
            seqSieve[i] = (System.nanoTime() - time) / 1000000.0;
        }
        double seqMedian = seqSieve[(seqSieve.length) / 2];
        System.out.println("Sequential Sieve used median time " + seqMedian + " for n = " + testNumber);


        double[] parSieve = new double[totalRuns];
        for (int i = 0; i < totalRuns; i++) {
            long time = System.nanoTime();
            runParallelSievve(testNumber, threads);
            parSieve[i] = (System.nanoTime() - time) / 1000000.0;
        }
        double parMedian = parSieve[(seqSieve.length) / 2];
        System.out.println("Parallel Sieve used median time " + parMedian + " for n = " + testNumber);
        System.out.println("Speedup: " + seqMedian /  parMedian);
    }


    private static void printInstructions() {
        System.out.println("Invalid arguments. Correct use of program is: \n" +
                "      \"java main <n> <t> <m> where:" +
                "<n> is the number which should be factorized \n" +
                "<t> is number of threads to use (0 means use all computer's cores) \n" +
                "<m> an positive integer from 0-3. 0 means run in sequential. 1 Means run in parallel. 2 means run benchmarks, 3 means run test");
    }
}