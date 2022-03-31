import org.junit.Test;
import src.ParallelFactorization;
import src.ParallelSieve;

public class TestParallelFactorize {


    @Test
    public void Test_distribute_2billion(){
        int n = 200_000_000_0;
        int threads = 4;
        ParallelSieve sieve = new ParallelSieve(n, threads);
        sieve.start();
        int[] primes = sieve.collectPrimes();
        long start = System.nanoTime();
        ParallelFactorization para = new ParallelFactorization(n, threads, primes);
        para.factorizeAll();
        double end = (double) (System.nanoTime() - start) / 1000000;
        System.out.println("Finished in: " + end + "ms");
    }
}
