package src;

public class Main {

    public static void main(String[] args) {
        int n;
        int threads;
        int mode;

        if (args.length != 3) {
            printInstructions();
        }


        try {
            n = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("First argument is not an integer");
            printInstructions();
            return;
        }
        if (n < 16) {
            System.out.println("First argument can not be lower than 16");
            return;
        }

        try {
            threads = Integer.parseInt(args[1]);
        }
        catch (Exception e){
            System.out.println("Second argument is not an integer");
            printInstructions();
            return;
        }

        if (threads < 0){
            System.out.println("Threads argument can not be negative");
            printInstructions();
            return;
        }
        if (threads == 0){
            threads = Runtime.getRuntime().availableProcessors();
        }

        try {
            mode = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Third argument is not an integer");
            printInstructions();
            return;
        }

        if (mode < 0 || mode > 2) {
            System.out.println("Third argument has to be between 0 and 2");
            printInstructions();
            return;
        }

        if (mode == 0) {
            SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
            int[] paraPrimes = sieve.getPrimes();
            Factorization factorize = new Factorization(n, paraPrimes);
            factorize.factorizeAll();
            factorize.precode.writeFactors("Sequentual");
        }
        else if (mode == 1){
            ParallelFactorization para = new ParallelFactorization(n, threads);
            para.factorizeAll();
        }
    }



    private static void printInstructions() {
        System.out.println("Invalid arguments. Correct use of program is: \n" +
                "      \"java main <n> <t> <m> where:" +
                "<n> is the number which should be factorized \n" +
                "<t> is number of threads to use (0 means use all computer's cores) \n" +
                "<m> an positive integer from 0-2. 0 means run in sequential. 1 Means run in paralell. 2 means run benchmarks");
    }
}