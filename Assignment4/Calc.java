/*
 * Maria Coleman
 * mcolem31
 */

import java.util.*;

public class Calc {

    public static void main (String[] args) {
        Stack<Integer> stack = new ArrayStack<>();
        Scanner sc = new Scanner(System.in);
        String value = "";
        value = sc.next();
        while (!value.equals("!")) {
            //System.out.println(value);
            if (isInteger(value)) {
                Integer valueInt = Integer.parseInt(value);
                stack.push(valueInt);
            } else if (isValidOperator(value)) {
                if (stack.empty()) {
                    System.err.println("#Not enough arguments.");
                } else {
                Integer first = stack.top();
                stack.pop();
                if (stack.empty()) {
                    System.err.println("#Not enough arguments.");
                    stack.push(first);
                } else {
                Integer second = stack.top();
                stack.pop();
                if (value.equals("+")) {
                    //stack.push(add(first, second));
                    stack.push(first + second);
                } else if (value.equals("-")) {
                    //stack.push(sub(first, second));
                    stack.push(first - second);
                } else if (value.equals("*")) {
                    //stack.push(mult(first, second));
                    stack.push(first * second);
                } else if (value.equals("/")) {
                    //stack.push(div(first, second));
                    stack.push(first / second);
                } else {
                    //stack.push(mod(first, second));
                    stack.push(first % second);
                }
                }
                }
            }
            else if (value.equals("?")) {
                System.out.println(stack.toString());
            }
            else if (value.equals("^")) {
                System.out.println(Integer.toString(stack.top()));
                stack.pop();
            }
            value = sc.next();
        }
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
            if (!(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("?") || str.equals("^") || str.equals("!"))) {
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
        return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%"));
    }


}
