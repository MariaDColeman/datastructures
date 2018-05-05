/*
 * Maria Coleman
 * mcolem31
 */

import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.Random;
import java.util.ArrayList;

/**
 * Comparing various OrderedMap implementations.
 */
public abstract class MapBenchBase {
//    private static final int SIZE = 1024;
    private static final int SIZE = 1024;

    private static final int INIT = 1;
//    private static final Random r = new Random();
//    private static final Random r = new Random(123456789L);
    private static boolean t;

    private static ArrayList<Integer> tempList = new ArrayList<>(SIZE);  
    private static Integer[] ra = new Integer[SIZE];


    private static ArrayList<Integer> tempList2 = new ArrayList<>(SIZE);
    private static Integer[] ra2 = new Integer[SIZE];


        
    private static Integer[] ra3 = new Integer[SIZE];


    private static void init() {

        Random r = new Random(123456789L);

//        private ArrayList<Integer> tempList = new ArrayList<>(SIZE);
        tempList = new ArrayList<>(SIZE);

        for (int i = 0; i < SIZE; i++) {
            tempList.add(i);
        }

        ra = new Integer[SIZE];  
        for (int c = 0; c < SIZE; c++) {
            ra[c] = tempList.remove((int)(r.nextDouble() * tempList.size()));
        }

        Random r2 = new Random(987654321L);


        tempList2 = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            tempList2.add(i);
        }
        ra2 = new Integer[SIZE];
        //ra[0]=-1;
        for (int c = 0; c < SIZE; c++) {
            ra2[c] = tempList2.remove((int)(r2.nextDouble() * tempList2.size()));
        }

        Random r3 = new Random(132465789L);
       
        ra3 = new Integer[SIZE];
        ra3[0] = 1; //insert first
        for (int c = 1; c < SIZE; c++) {
            ra3[c] = (int) Math.round(r3.nextDouble()+0.1); //make it more likely its a 1 to insert more
        }

        


    }



    abstract Map<Integer, Integer> createUnit();

    // Code to benchmark.

    private static void insertLinear(Map<Integer, Integer> s) {
init();
        for (int i = 0; i < SIZE; i++) {
            s.insert(i, INIT);
        }
    }

    private static void insertRandom(Map<Integer, Integer> s) {
init();
        for (int i = 0; i < SIZE; i++) {
            s.insert(ra[i], INIT);
        }
    }

    private static void removeLinear(Map<Integer, Integer> s) {
      init(); 
       for (int i = 0; i < SIZE; i++) {
            s.remove(i);
        }
    }

    private static void removeRandom(Map<Integer, Integer> s) {
init();        
for (int i = 0; i < SIZE; i++) {
            s.remove(ra2[i]);
        }
    }

    private static void hasLinear(Map<Integer, Integer> s) {
init();       
 for (int i = 0; i < SIZE; i++) {
            t = s.has(i);
        }
    }

    private static void hasRandom(Map<Integer, Integer> s) {
init();       
 for (int i = 0; i < SIZE; i++) {
            t = s.has(ra[i]);
        }
    }

    private static void insertAndRemoveRandomlyMixed(Map<Integer, Integer> s) {
        init();
       // int prev = 0;
      //  int prev2 = 0;

        //need to keep track of prev in like a list so you can do repeated removals.
        ArrayList<Integer> prev = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            if ((ra3[i] == 1) || (s.size() == 0)) {
                s.insert(ra[i], INIT);
         //       prev = i;
                prev.add(ra[i]);
            } else {
                s.remove(prev.get(prev.size()-1));
                prev.remove(prev.size()-1);
                
            }
        }
    }



//    private static void hasRandom(Set<Integer> s) {
//        for (int i = 0; i < SIZE/2; i++) {
//            t = s.has(r.nextInt(SIZE/2*SIZE/2));
//        }
//    }


//    private static void hasBiased(OrderedMap<Integer, Integer> s) {
//        for (int i = 0; i < SIZE/8; i++) {
//            t = s.has(0);
//            t = s.has(0);
//            t = s.has(i);
//            t = s.has(i);
//            t = s.has(SIZE-i-1);
//            t = s.has(SIZE-i-1);
//            t = s.has(SIZE-1);
//            t = s.has(SIZE-1);
//        }
//    }

    // Individual benchmarks.

    @Bench
    public void insertLinear(Bee b) {
// init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
     //       init();
            b.start();

            insertLinear(s);
        }
    }

    @Bench
    public void insertRandom(Bee b) {
  //      init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
       //     init();
            b.start();

            insertRandom(s);
        }
    }

    @Bench
    public void insertAndRandomRandomlyMixed(Bee b) {
  //      init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
       //     init();
            b.start();

            insertAndRemoveRandomlyMixed(s);
        }
    }



    @Bench
    public void removeLinearInsertedLinear(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
//            init();
            insertLinear(s);
            b.start();

            removeLinear(s);
        }
    }

    @Bench
    public void removeLinearInsertedRandom(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
//            init();
            insertRandom(s);
            b.start();

            removeLinear(s);
        }
    }


    @Bench
    public void removeRandomInsertedLinear(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
  //          init();
            insertLinear(s);
            b.start();

            removeRandom(s);
        }
    }

    @Bench
    public void removeRandomInsertedRandom(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
  //          init();
            insertRandom(s);
            b.start();

            removeRandom(s);
        }
    }


    @Bench
    public void hasLinearInsertedLinear(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
       //     init();
            insertLinear(s);
            b.start();

            hasLinear(s);
        }
    }

    @Bench
    public void hasLinearInsertedRandom(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
       //     init();
            insertRandom(s);
            b.start();

            hasLinear(s);
        }
    }


    @Bench
    public void hasRandomInsertedLinear(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
         //   init();
            insertLinear(s);
            b.start();

            hasRandom(s);
        }
    }

    @Bench
    public void hasRandomInsertedRandom(Bee b) {
//init();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Map<Integer, Integer> s = this.createUnit();
         //   init();
            insertLinear(s);
            b.start();

            hasRandom(s);
        }
    }


//    @Bench
//    public void hasBiased(Bee b) {
//        for (int n = 0; n < b.reps(); n++) {
//            b.stop();
//            OrderedMap<Integer, Integer> s = this.createUnit();
//            insertLinear(s);
//            b.start();

//            hasBiased(s);
//        }
//    }
}
