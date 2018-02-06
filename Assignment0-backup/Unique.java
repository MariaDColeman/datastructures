/**
  * Program to print each unique integer presented on command line.
  * Maria Coleman
  * mcolem31
  */
public final class Unique {
    private Unique() {}

    /**
      * Print every unique integer.
      * @param args command line arguments to analyze
      */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Warning-No input was provided on command line");
        } else {
            int[] codes = new int[args.length];
            int isUnique;
            for (int i = 0; i < args.length; i++) {
                //check if the args[i] is an int
                if (!isInteger(args[i])) {
                    //set value in codes array at i equal to -1
                    //since args[i] is not an integer
                    codes[i] = -1;
                } else {
                    /*
                     * now we know args[i] is an integer
                     * need to check if args[i] is unique
                     */
                    isUnique = 1;
                    for (int j = 0; j < i; j++) {
                        if (args[j].equals(args[i])) {
                            isUnique = 0;
                            //we have already encountered this num
                        }
                    } //set codes[i] to a 0 or 1, depending if the number
                    //has already been encountered or not
                    codes[i] = isUnique;
                }
            }  //print all the unique values, one per line
            for (int l = 0; l < codes.length; l++) {
                if (codes[l] == 1) {
                    System.out.println(args[l]);
                }
            }
            //warn the user of the invalid arguments to standard error
            for (int k = 0; k < codes.length; k++) {
                if (codes[k] == -1) {
                    System.err.println(
                        "Ignored non-integer argument: "
                        + args[k]);
                }
            }
        }
    }

    /**
      * Function to determine if argument is an integer.
      * @param str the string the analyze
      * @return boolean true if an integer, false otherwise
      */
    public static boolean isInteger(String str) {
        boolean isAnInt = false;
        try {
            Integer.parseInt(str);
            isAnInt = true; //str is a valid int
        } catch (NumberFormatException excep) {
            //leave isAnInt equal to false
        }
        return isAnInt;
    }
}
