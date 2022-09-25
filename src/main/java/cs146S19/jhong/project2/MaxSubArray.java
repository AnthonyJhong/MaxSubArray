package cs146S19.jhong.project2;
/**
 * MaxSubArray class will produce the maximum sub array of any given array of integers using three different methods
 * a brute force method, a divide and conquer method, and a method using Kadane's algorithm
 * This class also has an nested class called answer which includes 3 integers (sum, arrive index, departIndex) 
 * used as a return value for each of the methods that find the max sub array
 * @author AnthonyJhong
 */
public class MaxSubArray {
	
	public class Answer{
		private int arriveIndex; //Arrival index 
		private int departIndex; //depart index
		private int sum; //largest sum
		
		public Answer(int s, int arrival, int depart) {
			arriveIndex = arrival;
			departIndex = depart;
			sum = s;
		}

		public void setArriveIndex(int start) {
			arriveIndex = start;
		}

		public void setDepartIndex(int ret) {
			departIndex = ret;
		}
		public void setSum(int s) {
			sum = s;
		}
		
		public int getSum() {
			return sum;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Answer other = (Answer) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			return (arriveIndex == other.arriveIndex) && (departIndex == other.departIndex)
					&& (sum == other.sum);
		}
		/**
		 * Compares the sum of the current answer with the one entered in the parameter
		 * @param a answer object being compared
		 * @return boolean
		 */
		public boolean compareAnswers(Answer a) {
			return sum > a.sum;
		}
		private MaxSubArray getOuterType() {
			return MaxSubArray.this;
		}
		public String toString() {
			return "Sum: " + sum + ", Arrive Index: " + arriveIndex + ", Return Index: " + departIndex;		}
		}
	
	private Answer correctAnswer; //correct answer of the current array being assessed
	
	public MaxSubArray() {correctAnswer = new Answer(0,0,0);}
	
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}
	public void setAnswer(int sum, int low, int high) {
		correctAnswer.setDepartIndex(high);
		correctAnswer.setArriveIndex(low);
		correctAnswer.setSum(sum);
	}
	
	/**
	 * This will find the max sub array of an array using the brute force method
	 * @param arr the array being assessed
	 * @param start the index to start on
	 * @param size the size of the array
	 * @return returns an answer object
	 */
	public Answer maxSubArrayBruteForce(int []arr, int start, int size) {
		int max = 0; //place holder of the largest sub array sum
		Answer answer = new Answer(0, 0, -1); //Instantiate an Answer Object that will be returned at the end of the mehtod
		int val = 0; //value that will be compared to the largest sum
		
		for(int i = 0; i < size; i++) {
			val = 0;	//sets val to 0
			for(int j = i; j < size; j++) {
				val += arr[j]; //adds arr[j] to val
				if(val > max) { //if val is greater than max then replaces max with the value of val
					max = val;
					answer.setArriveIndex(i);
					answer.setDepartIndex(j);
					answer.setSum(max);
				}
			}
		}
		return answer;
	}
	/**
	 * Finds the max sum of the crossing array
	 * @param arr array being assessed
	 * @param low the lowest index being assessed
	 * @param mid the middle of the array
	 * @param high the highest index being assessed
	 * @return answer object
	 */
	public Answer findMaxCrossing(int []arr, int low, int mid, int high) {
		int sum = 0; //a temp sum value that will be compared to the leftSum
		int leftSum = Integer.MIN_VALUE;  //sets left sum to smallest possible number
		int maxLeft = 0; //sets index of max left to 0
		for(int i = mid; i >= low; i--) {
			sum += arr[i]; //adds arr[i] to sum
			if(sum >= leftSum) { //if the sum is greater than leftSum change the value of leftSum to sum and the maxLeft to i
				leftSum = sum;
				maxLeft = i;
			}
		}
	
		int sum2 = 0; //set sum to 0
		int rightSum = Integer.MIN_VALUE;//sets the right sum to the smallest number possible 
		int maxRight = 0;
		
		for(int i = mid+1; i <= high; i++) {
			sum2 += arr[i];
			if(sum2 >= rightSum) {
				rightSum = sum2;
				maxRight = i;
			}
		}
		Answer answer = new Answer(leftSum + rightSum, maxLeft, maxRight); //Answer object that will be returned at end of method
		return answer;
	}
	/**
	 * Finds the max sub array using Divide and Conquer method
	 * @param arr array being assessed
	 * @param low lowest index being assessed
	 * @param high highest index being assessed
	 * @return
	 */
	public Answer maxSubArrayDC(int []arr, int low, int high) {
		
		if(high == low) {
			if(arr[low] < 0) { //if arr[low] is less than zero return an answer object (0, 0, -1)
				return new Answer(0,0, -1);
			}
			else {
				Answer answer = new Answer(arr[low], low , high);
				return answer;
			}
		}
		else {
			int mid = (low + high)/2; //finds the mid point of the section of the array being assessed
			Answer leftAnswer = maxSubArrayDC(arr, low, mid); //recursive call for left side of array initializes answer of the left part of the array
			Answer rightAnswer = maxSubArrayDC(arr, mid+1, high); //recursive call for right side of the array initialized answer of the left part of the array
			Answer crossAnswer = findMaxCrossing(arr, low, mid, high); //calls findMaxCrossing for the particular portion of array initializes answer of the crossing of the array
			
			
			if(leftAnswer.getSum() == rightAnswer.getSum() && leftAnswer.compareAnswers(crossAnswer))
				return leftAnswer;
			
			if(leftAnswer.compareAnswers(rightAnswer) && leftAnswer.compareAnswers(crossAnswer))//if left answer is bigger than right and crossing sum return left answer
				return leftAnswer;
			else if(rightAnswer.compareAnswers(leftAnswer) && rightAnswer.compareAnswers(crossAnswer))//if right answer is bigger than left and crossing sum return right answer
				return rightAnswer;
			else //return crossing number
				return crossAnswer;
		}
	}
	/**
	 * Finds the max sub array using Kadane's ALgorithm
	 * @param arr the array being assessed
	 * @param size size of the array
	 * @return answer object
	 */
	public Answer maxSubArrayDynamic(int []arr, int size) {
		int maxSum = 0; //largest current sum
		int maxTemp = 0; 
		int arrive = 0; //arrival index
		int depart = -1; //depart index
		int tempArrive = 0; //temp arrive place holder
		
		for(int i = 0; i < size; i++) {
			maxTemp += arr[i]; // add arr[i] to maxTemp
			
			if(maxTemp < 0) { //if max temp is less than 0 set max temp to 0 and set arrive to i+1
				maxTemp = 0; 
				arrive = i + 1;
			}

			if(maxSum < maxTemp) { //if maxSum is less than maxTemp set maxSum to maxTemp and depart to i and place arrive into tempArrive
				maxSum = maxTemp;
				depart = i;
				tempArrive = arrive;
			}
		}
		arrive = tempArrive; //set Arrive to tempArrive
		
		Answer answer = new Answer(maxSum, arrive, depart); //Answer object that will be returned at the end of method
		return answer;
		
	}

}