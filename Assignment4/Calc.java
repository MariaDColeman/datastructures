/*
 * Maria Coleman
 * mcolem31
 */

import java.util.Scanner;

/**
 * A basic RPN calculator program.
 */
public final class Calc {

    private Calc() {
        //for checkstyle
    }

    /**
     * Main method for Calc program.
     * @param args command line arguments ignored
     */
    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>();
        Scanner sc = new Scanner(System.in);
        String value = "";
        value = sc.next();
        while (!("!".equals(value))) {
            if (isInteger(value)) {
                Integer valueInt = Integer.parseInt(value);
                stack.push(valueInt);
            } else if (isValidOperator(value)) {
                try {
                    Integer first = stack.top();
                    stack.pop();
                    try {
                        Integer second = stack.top();
                        stack.pop();
                        stack.push(determineOp(first, second, value));
                    } catch (EmptyException e) {
                        System.err.println("#Not enough input arguments.");
                        stack.push(first);
                    }
                } catch (EmptyException e) {
                    System.err.println("#Not enough input arguments.");
                }
            } else if ("?".equals(value)) {
                System.out.println(stack.toString());
            } else if ("^".equals(value)) {
                System.out.println(Integer.toString(stack.top()));
                stack.pop();
            }
            value = sc.next();
        }
    }

    private static Integer determineOp(Integer first, Integer second,
        String value) {
        if ("+".equals(value)) {
            return (second + first);
        }
        if ("-".equals(value)) {
            return (second - first);
        }
        if ("*".equals(value)) {
            return (second * first);
        }
        if ("/".equals(value)) {
            return (second / first);
        }
        return (second % first);
    }

    /**
     * Function to determine if argument is an integer.
     * @param str the string to analyze
     * @return boolean true if an integer, false otherwise
     */
    public static boolean isInteger(String str) {
        boolean isAnInt = false;
        try {
            Integer.parseInt(str);
            isAnInt = true; //str is a valid int
        } catch (NumberFormatException excep) {
            // leave isAnInt equal to false
            // only print error message for invalid input if it
            // is also not one of the operators allowed
            if (!("+".equals(str) || "-".equals(str) || "*".equals(str)
                || "/".equals(str) || "%".equals(str) || "?".equals(str)
                || "^".equals(str) || "!".equals(str))) {

                System.err.println("#Invalid input.");
            }
        }
        return isAnInt;
    }

    /**
     * Function to determine if argument is a valid operator.
     * @param str the string to analyze
     * @return boolean true if a valid operator, false otherwise
     */
    public static boolean isValidOperator(String str) {
        return ("+".equals(str) || "-".equals(str) || "*".equals(str)
            || "/".equals(str) || "%".equals(str));

    }

}
