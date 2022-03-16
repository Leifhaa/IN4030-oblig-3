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
        ParallelFactorization para = new ParallelFactorization(n, threads, primes);
        para.factorizeAll();
    }
}
