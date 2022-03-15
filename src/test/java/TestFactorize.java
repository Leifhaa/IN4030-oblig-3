import org.junit.Assert;
import org.junit.Test;
import src.Factorization;
import src.ParallelSieve;
import src.SieveOfEratosthenes;

public class TestFactorize {
    @Test
    public void TestFactorize532(){
        int n = 532;
        ParallelSieve paraSieve = new ParallelSieve(n, 8);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        Factorization factorize = new Factorization(n, paraPrimes);
        factorize.factorizeAll();
        factorize.precode.writeFactors("Sequentual");
    }


    @Test
    public void Test_sequentialFactorize_200_000_000_0(){
        int n = 200_000_000_0;
        SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
        int[] paraPrimes = sieve.getPrimes();
        Factorization factorize = new Factorization(n, paraPrimes);
        factorize.factorizeAll();
        factorize.precode.writeFactors("Sequentual");
    }


}
