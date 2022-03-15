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
    private int workers;


    public FactorContainer(long readFrom, long readTo, int workers) {
        this.workers = workers;
        factorMap = new HashMap<>();
        for (long i = readTo - 1; i >= readFrom; i--) {
            factorMap.put(i, new HashMap<>());
        }
    }

    public void registerWorker(long numberToFactorize, int workerId) {
        HashMap<Integer, ArrayList<Integer>> map = factorMap.get(numberToFactorize);
        map.put(workerId, new ArrayList<>());
    }

    public void addFactor(long number, int workerId, int prime) {
        HashMap<Integer, ArrayList<Integer>> map = factorMap.get(number);
        map.get(workerId).add(prime);
    }

    public Oblig3Precode getResults(int n) {
        Oblig3Precode oblig3Precode = new Oblig3Precode(n);
        for (Map.Entry<Long, HashMap<Integer, ArrayList<Integer>>> number :
                factorMap.entrySet()) {
            System.out.println(number.getKey());
            ArrayList<Integer> tmp = number.getValue().get(0);
            for (int i = 1; i < workers; i++) {
                tmp.addAll(number.getValue().get(i));
            }
            Collections.sort(tmp);

            int total = tmp.get(0);
            for (int i = 1; i < tmp.size(); i++) {
                total *= tmp.get(i);
            }

            for (Integer integer : tmp) {
                oblig3Precode.addFactor(number.getKey(), integer);
            }

            if (total != number.getKey()) {
                oblig3Precode .addFactor(number.getKey(), number.getKey() / total);
            }


        }
        oblig3Precode.writeFactors("Paralell");
        return oblig3Precode;
    }
}
