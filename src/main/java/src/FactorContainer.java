package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A thread safe container where multiple threads can write prime numbers of a digit.
 */
public class FactorContainer {
    Oblig3Precode oblig3Precode;
    public long rest;
    public long tmpRoot;
    private int workers;


    public FactorContainer(int workers, int n) {
        this.workers = workers;
        this.oblig3Precode = new Oblig3Precode(n);
    }


    public synchronized void addFactor(long base, int factor) {
        oblig3Precode.addFactor(base, factor);

        rest = rest / factor;

        updateTmpRoot();
    }

    private void updateTmpRoot(){
        tmpRoot = (long)Math.sqrt(rest);
    }

    public void saveResults() {
        oblig3Precode.writeFactors("Parallel");
    }

    public void setNumber(long number) {
        this.rest = number;
        updateTmpRoot();
    }

    public void reset(long base) {
        if (rest > 1){
            oblig3Precode.addFactor(base, rest);
            rest = 0;
        }
    }
}
