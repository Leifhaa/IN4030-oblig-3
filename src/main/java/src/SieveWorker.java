package src;

public class SieveWorker implements Runnable {
    private int readFrom;
    private int readTo;

    public SieveWorker(int readFrom, int readTo) {
        this.readFrom = readFrom;
        this.readTo = readTo;
    }

    @Override
    public void run() {

    }
}
