


public final class Unique{

	public static void main(String[] args) {
	//	System.out.println("Hello " + args[0]);
	// Idea is to make an array that is the length of args
	// and for each entry in args, assign it:
	// a 1 if it is unique
	// a 0 if it is not unique
	// a -1 if it is invalid
	// then at the end print out the unique ones and
	// warn about the invalid ones.

	int[] codes = new int[args.length];
	int isUnique;
	int printedWarning = 0;

	for (int i=0; i < args.length; i++) 
	{
		// check if the args[i] is an integer
		if (!isInteger(args[i]) 
		{
			//set value in codes array at i equal to -1
			//since args[i] is not an integer
			codes[i] = -1;
		}
		else // now we know args[i] is an integer
		{
			//need to check if args[i] is unique
			isUnique = 1;
			for (int j = 0; j < i; j++) {
				if (args[j] == args[i]) {
					//we have already encountered this num	
					isUnique = 0;
				}
			}
		
			//set codes[i] to a 0 or 1, depending if the number
			//has already been encountered or not	
			codes[i] = isUnique;

		}

		//print all the unique values, one per line
		//System.out.println("The unique values are:");	
		for (int l = 0; l < codes.length; l++) {
			if (codes[l] == 1) {
				System.out.println(args[l]);
			}
		}

		//warn the user of the invalid arguments to standard error
		for (int k = 0; k < codes.length; k++) {
			if (codes[k] == -1) {
				if (!printedWarning) {	
				System.err.println("Warning: The Following Values are not valid integers:");
				printedWarning = 1;
				}
				System.err.println(args[l]);	
	}



	}

	public static boolean isInteger(String str) {
		boolean isAnInt = false;
		try
		{
			Integer.parseInt(str);
			isAnInt = true; //str is a valid int
		}
		catch (NumberFormatException excep)
		{
			//leave isAnInt equal to false
		}
		return isAnInt;
	
	}
}
