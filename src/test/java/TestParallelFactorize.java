import org.junit.Test;
import src.ParallelFactorization;

public class TestParallelFactorize {
    @Test
    public void Testme(){
        ParallelFactorization para = new ParallelFactorization(27, 4);
        para.factorizeAll();
    }


    @Test
    public void Test_distribute_2billion(){
        int n = 200_000_000_0;
        ParallelFactorization para = new ParallelFactorization(n, 4);
        para.factorizeAll();
    }
}
