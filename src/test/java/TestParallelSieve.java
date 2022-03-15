import org.junit.Assert;
import org.junit.Test;
import src.ParallelSieve;
import src.SieveOfEratosthenes;

public class TestParallelSieve {
    @Test
    public void TestSingleThread(){
        int n = 200_000_0;
        ParallelSieve paraSieve = new ParallelSieve(n, 1);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        SieveOfEratosthenes seqSieve = new SieveOfEratosthenes(n);
        int[] seqPrimes = seqSieve.getPrimes();

        Assert.assertArrayEquals(paraPrimes, seqPrimes);
    }

    @Test
    public void TestPara_2billion(){
        int n = 200_000_000_0;
        ParallelSieve paraSieve = new ParallelSieve(n, 8);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();
    }

    @Test
    public void TestPara_100(){
        int n = 85;
        ParallelSieve paraSieve = new ParallelSieve(n, 8);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();
    }


    @Test
    public void TestCompare_2billion(){
        int n = 200_000_000_0;
        ParallelSieve paraSieve = new ParallelSieve(n, 4);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        SieveOfEratosthenes seqSieve = new SieveOfEratosthenes(n);
        int[] seqPrimes = seqSieve.getPrimes();

        Assert.assertArrayEquals(paraPrimes, seqPrimes);
    }

    @Test
    public void TestSeq_2billion(){
        int n = 200_000_000_0;
        SieveOfEratosthenes seqSieve = new SieveOfEratosthenes(n);
        int[] seqPrimes = seqSieve.getPrimes();
    }


    @Test
    public void TestMultiThread(){
        int n = 200_000_0;
        ParallelSieve paraSieve = new ParallelSieve(n, 2);
        paraSieve.start();
        int[] paraPrimes = paraSieve.collectPrimes();

        SieveOfEratosthenes seqSieve = new SieveOfEratosthenes(n);
        int[] seqPrimes = seqSieve.getPrimes();

        Assert.assertArrayEquals(paraPrimes, seqPrimes);
    }
}
