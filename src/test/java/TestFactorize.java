import org.junit.Assert;
import org.junit.Test;
import src.Factorization;
import src.ParallelSieve;

public class TestFactorize {
    @Test
    public void TestFactorize532(){
        int n = 532;
        ParallelSieve paraSieve = new ParallelSieve(n, 8);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        Factorization factorize = new Factorization(n, n, paraPrimes);
        factorize.factorize();
        factorize.precode.writeFactors("Sequentual");
    }


    @Test
    public void TestFactorize200_000_000_0(){
        int n = 200_000_000_0;
        ParallelSieve paraSieve = new ParallelSieve(n, 8);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        Factorization factorize = new Factorization(n, n, paraPrimes);
        factorize.factorize();
        factorize.precode.writeFactors("Sequentual");
    }
}
