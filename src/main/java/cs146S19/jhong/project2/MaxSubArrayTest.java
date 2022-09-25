package cs146S19.jhong.project2;

import org.junit.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class MaxSubArrayTest {
	
	MaxSubArray maxSub; //maxSub variable that contains the max sub array functions 
	Scanner in; //scanner to read from the file
	Random rand; //Random will create random numbers to insert to the array
	
	@Before
	public void setUp() throws FileNotFoundException {
		maxSub = new MaxSubArray();
		in = new Scanner(new File("C:\\Users\\antho\\IdeaProjects\\MaxSubArray\\src\\main\\java\\cs146S19\\jhong\\project2\\maxSumtest.txt"), "UTF-8");
	}
	@Before
	public void setUp2() {
		maxSub = new MaxSubArray();
		rand = new Random();
	}
	
	@Test
	public void textFileTest() throws FileNotFoundException{ //test will read from maxSumtest.txt and populate the arrays using those values
		setUp(); 		
		
		while(in.hasNextLine()) {
			
			String numbers = in.nextLine();
			String numsArrStr[] = numbers.split(" ");
			int testArray[] = new int[numsArrStr.length];
			
			for(int i = 0; i < numsArrStr.length; i++) {
				//System.out.println(i);
				testArray[i] = Integer.parseInt(numsArrStr[i]);
			}
					
		
			maxSub.setAnswer(in.nextInt(), in.nextInt(), in.nextInt()); //Sets the correct answer in maxSub 
			
			if(in.hasNextLine()) {
				in.nextLine();
			}
			
			System.out.println("Correct Answer:");
			System.out.println(maxSub.getCorrectAnswer().toString());
			
			System.out.println("Brute Force Answer: ");
			System.out.println(maxSub.maxSubArrayBruteForce(testArray, 0, testArray.length).toString());
			
			//test to check if both the brute force answer and the correct answer are the same
			assertEquals(maxSub.getCorrectAnswer(), maxSub.maxSubArrayBruteForce(testArray, 0, testArray.length));
			
			System.out.println("Divide and Conquer Answer: ");
			System.out.println(maxSub.maxSubArrayDC(testArray, 0, testArray.length-1).toString());
			
			//test to check that if both the Divide and Conquer answer and the correct answer are the same
			assertEquals(maxSub.getCorrectAnswer(), maxSub.maxSubArrayDC(testArray, 0, testArray.length-1));
			
			System.out.println("Dynamic Answer: ");
			System.out.println(maxSub.maxSubArrayDynamic(testArray, testArray.length).toString());
			
			//Test to check that both the dynamic answer and the correct answer are the same
			assertEquals(maxSub.getCorrectAnswer(), maxSub.maxSubArrayDynamic(testArray, testArray.length));
			
			System.out.println();

		}
	}
	
	@Test
	public void randomTestBruteForce() {//test will run various array sizes of Brute force method
		setUp2();
		
		long startTime = 0;
		long endTime = 0;
		
		int arraySize[] = {100, 500, 1000, 10000, 20000, 30000, 50000};
		
		int []testArray;
		
		for(int k = 0; k < arraySize.length; k++) {
			long totalTime = 0;
			testArray = new int[arraySize[k]];
			for(int i = 0; i < 10 ;i++) {
				for(int j = 0; j < testArray.length; j++) {
					testArray[j] = rand.nextInt(100)-50;
				}
				
				startTime = System.nanoTime();
				maxSub.maxSubArrayBruteForce(testArray, 0, testArray.length);
				endTime = System.nanoTime();
				
				totalTime = endTime - startTime;
			}
			
			System.out.println("The average run time of the brute force method of array size " + arraySize[k] +": " + totalTime/10 + " nanoseconds");
		}
		System.out.println();
	}
	@Test
	public void randomTestDC() { //test will run various array sizes for Divide and Conquer algorithm
		setUp2();
		
		long startTime = 0;
		long endTime = 0;
		
		int arraySize[] = {100, 500, 1000, 10000, 20000, 30000, 50000, 500000, 1000000, 5000000, 10000000};
		
		int []testArray;
		
		for(int k = 0; k < arraySize.length; k++) {
			long totalTime = 0;
			testArray = new int[arraySize[k]];
			for(int i = 0; i < 10 ;i++) {
				for(int j = 0; j < testArray.length; j++) {
					testArray[j] = rand.nextInt(100)-50;
				}
				
				startTime = System.nanoTime();
				maxSub.maxSubArrayDC(testArray, 0, testArray.length-1);
				endTime = System.nanoTime();
				
				totalTime = endTime - startTime;
			}
			
			System.out.println("The average run time of the divide and conquer method of array size " + arraySize[k] +": " + totalTime/10 +" nanoseconds");
		}
		System.out.println();
	}
	
	@Test
	public void randomTestDynamic() {//test will run various array sizes for Kadane's algorithm
		setUp2();
		
		long startTime = 0;
		long endTime = 0;
		
		int arraySize[] = {100, 500, 1000, 10000, 20000, 30000, 50000, 500000, 1000000, 5000000, 10000000,15000000, 20000000, 30000000, 40000000};
		
		int []testArray;
		
		for(int k = 0; k < arraySize.length; k++) {
			long totalTime = 0;
			testArray = new int[arraySize[k]];
			for(int i = 0; i < 10 ;i++) {
				for(int j = 0; j < testArray.length; j++) {
					testArray[j] = rand.nextInt(100)-50;
				}
				
				startTime = System.nanoTime();
				maxSub.maxSubArrayDynamic(testArray, testArray.length);
				endTime = System.nanoTime();
				
				totalTime = endTime - startTime;
			}
			
			System.out.println("The average run time of Kadane's Alg of array size " + arraySize[k] +": " + totalTime/10 + " nanoseconds");
		}
		System.out.println();
	}
}
