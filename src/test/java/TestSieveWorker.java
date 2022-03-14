import org.junit.Assert;
import org.junit.Test;
import src.SieveOfEratosthenes;
import src.SieveWorker;

public class TestSieveWorker {

    /*
    @Test
    public void TestSieveFirstChunk() {
        int n = 100;
        SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
        int[] primes = sieve.getPrimes();
        int cells = n / 16 + 1;
        byte[] byteArray = new byte[cells];
        SieveWorker sw = new SieveWorker(0, 16, primes, n, byteArray);
        sw.run();
        int[] results = sw.collectPrimes();
        Assert.assertEquals(results.length, 6);
        int[] expected = new int[]{2,3,5,7,11,13};
        Assert.assertArrayEquals(results, expected);
    }


    @Test
    public void TestSieveSecondChunk() {
        int n = 20000;
        SieveOfEratosthenes sieve = new SieveOfEratosthenes((int)Math.sqrt(n));
        int[] primes = sieve.getPrimes();
        int cells = n / 16 + 1;
        byte[] byteArray = new byte[cells];
        SieveWorker sw = new SieveWorker(16, 32, primes, 100000, byteArray);
        sw.run();
        int[] results = sw.collectPrimes();
        int[] expected = new int[]{17,19,23,29,31};
        Assert.assertArrayEquals(results, expected);
    }


    @Test
    public void TestSieveLast() {
        int n = 200_000_000_0;
        SieveOfEratosthenes sieve = new SieveOfEratosthenes((int)Math.sqrt(n));
        int[] primes = sieve.getPrimes();
        int cells = n / 16 + 1;
        byte[] byteArray = new byte[cells];
        SieveWorker sw = new SieveWorker( 2, n, primes, n, byteArray);
        sw.run();
        int[] results = sw.collectPrimes();
        //int[] expected = new int[]{17,19,23,29,31};
        //Assert.assertArrayEquals(results, expected);
    }

     */

}
