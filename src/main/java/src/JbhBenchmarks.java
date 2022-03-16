package src;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

import static src.Main.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)

public class JbhBenchmarks {

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void testSequential(){
        int n = 200_000_000;
        int[] paraPrimes = runSequentialSieve(n);
        runSequentialFactorization(n, paraPrimes);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void testParallel(){
        int n = 200_000_000;
        int nThreads = 4;
        int[] paraPrimes = runParallelSievve(n, nThreads);
        runParallelFactorization(n, nThreads, paraPrimes);
    }
}
