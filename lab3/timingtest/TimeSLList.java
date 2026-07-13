package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int op = 10000;
        AList Ns = new AList<>();
        AList times = new AList<>();
        AList ops = new AList();

        for (int n = 1000; n <= 128000; n *= 2){

            SLList sll = new SLList();
            Ns.addLast(n);
            ops.addLast(op);

            for(int i = 0; i < n; i++){
                sll.addLast(i);
            }

            Stopwatch sw = new Stopwatch();
            for(int i = 0; i < op; i++){
                sll.getLast();
            }
            times.addLast(sw.elapsedTime());
        }

        printTimingTable(Ns, times, ops);
    }

}
