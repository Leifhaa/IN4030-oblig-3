import org.junit.Test;
import src.ParallelSieve;
import src.SieveOfEratosthenes;
import src.SieveWorker;

public class TestSieveWorker {

    @Test
    public void TestTransition() {
        int n = 100;
        SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
        int[] primes = sieve.getPrimes();
        SieveWorker sw = new SieveWorker(0, 16, primes, n);
        sw.run();
        int[] foo = sw.collectPrimes();

    }
}
