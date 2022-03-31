package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A thread safe container where multiple threads can write prime numbers of a digit.
 */
public class FactorContainer {
    private HashMap<Long, HashMap<Integer, ArrayList<Integer>>> factorMap;
    public HashMap<Long, Long> rests = new HashMap<>();
    public HashMap<Long, Long> tmpRoots = new HashMap<>();
    private int workers;


    public FactorContainer(long readFrom, long readTo, int workers) {
        this.workers = workers;
        factorMap = new HashMap<>();
        for (long i = readTo - 1; i >= readFrom; i--) {
            factorMap.put(i, new HashMap<>());
            rests.put(i, i);
            tmpRoots.put(i, (long)Math.sqrt(i));
            for (int j = 0; j < workers; j++) {
                registerWorker(i, j);
            }
        }
    }

    public void registerWorker(long numberToFactorize, int workerId) {
        HashMap<Integer, ArrayList<Integer>> map = factorMap.get(numberToFactorize);
        map.put(workerId, new ArrayList<>());
    }

    public synchronized void addFactor(long number, int workerId, int prime) {
        HashMap<Integer, ArrayList<Integer>> map = factorMap.get(number);
        map.get(workerId).add(prime);

        //Update the 'rest' number
        long newRest = rests.get(number) / prime;
        rests.put(number, newRest);

        //Update the square root for the current number
        tmpRoots.put(number, (long)Math.sqrt(newRest));
    }

    public void saveResults(int n) {
        Oblig3Precode oblig3Precode = new Oblig3Precode(n);
        for (Map.Entry<Long, HashMap<Integer, ArrayList<Integer>>> number :
                factorMap.entrySet()) {
            long total = 0;
            for (int i = 0; i < workers; i++) {
                for (int j = 0; j < number.getValue().get(i).size(); j++) {
                    int factor = number.getValue().get(i).get(j);
                    total = total == 0 ? factor : total * factor;
                    oblig3Precode.addFactor(number.getKey(), factor);
                }
            }

            if (total != number.getKey() && total != 0) {
                oblig3Precode.addFactor(number.getKey(), number.getKey() / total);
            }
        }
        oblig3Precode.writeFactors("Parallel");
    }
}
