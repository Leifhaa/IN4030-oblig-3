package src;

public class Factorization {
    private long base;
    private int[] primes;
    public Oblig3Precode precode;

    public Factorization(long base, int n, int[] primes) {
        this.base = base;
        this.primes = primes;
        precode = new Oblig3Precode(n);
    }

    public void factorize(){
        long rest = base;
        for (int i = 0; i < primes.length || rest == 0;){
            if (primes[i] * primes[i] > rest){
                precode.addFactor(base, rest);
                //Completed
                break;
            }
            else if (rest % primes[i] == 0){
                precode.addFactor(base, primes[i]);
                rest = rest / primes[i];
            }
            else{
                i++;
            }
        }
    }
}
