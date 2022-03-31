package src;

import java.util.concurrent.CyclicBarrier;

public class Factorization {
    private int base;
    private int[] primes;
    public Oblig3Precode precode;

    public Factorization(int base, int[] primes) {
        this.base = base;
        this.primes = primes;
        precode = new Oblig3Precode(base);
    }

    public void factorizeAll(){
        long factorizeTo = (long) base * base;
        for (int i = 0; i < 100; i++){
            factorizeTo--;
            factorize(factorizeTo);
        }
    }


    private void factorize(long number){
        long rest = number;
        long root = (long) Math.sqrt(number);
        for (int i = 0; i < primes.length || rest == 0; i++){
            if (primes[i] > root){
                break;
            }
            while (rest % primes[i] == 0){
                precode.addFactor(number, primes[i]);
                rest = rest / primes[i];
                root = (long)Math.sqrt(rest);
            }
        }

        if (rest > 1){
            precode.addFactor(number, rest);
        }
    }
}
