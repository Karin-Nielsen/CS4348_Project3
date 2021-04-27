/**
 * File name:	Scheduler.java
 * Author: 		Karin Nielsen
 * Date:		04/21/2021
 * Version:		1.0
 * Course: 		CS4348.002 Fall 2021
 * Description: Project 3 for Operating Systems Concepts: OS Scheduling Algorithms. 
 * 				Program is designed to read in a simple .txt file with three tab-delimited columns: the 1st column 
 * 				is the job name, the 2nd column is the job's arrival time, and the 3rd column is the job's service/runtime. 
 * 				Implement and print the results of using the FCFS, SPN, and HRRN algorithms on the data in the jobs.txt file.
 * 
 * 				NOTE: 	Based on example jobs.txt file, examples in CH9 of the textbook, and examples from CH9 class 
 * 						slides, algorithm implementations were designed with the assumption that a) the first job to run 
 * 						would always start at an arrival time of zero and b) there are no gaps between jobs based on 
 * 						arrival/service times for each job. 
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Scheduler {

	public static void main(String[] args) {

		/**
		 * Find file reading data from. Exit program if 
		 * jobs.txt file not present in same folder as 
		 * file titled Scheduler.java */
		File jobsFile = new File("jobs.txt");

		if(!jobsFile.exists()) {
			System.err.println("ERROR: There is no file titled 'jobs.txt' in the same folder as Scheduler.java.\n" + "Exiting Program.");
			System.exit(1);
		}

		try {

			/* local try/catch variables */
			String jobX = "X"; //for printing jobs to chart
			String space = " "; //for chart's alignment

			/**
			 * Define total number of tokens read from file and save variable for 
			 * size of arrays: job names, arrival times, and service times.
			 * */			
			Scanner fileSize = new Scanner(jobsFile);
			fileSize.useDelimiter("\\s");

			/* get size for arrays */
			int arrSize = 0; 

			@SuppressWarnings("unused")
			String unusedString = null;
			for (int i = 0 ; fileSize.hasNext()  ; i++) {
				unusedString = fileSize.next();
				arrSize = i + 1;
			}

			fileSize.close();
			arrSize = arrSize / 3;

			/** 
			 * Define arrays and store, respectively, job names, arrival times, and 
			 * service times. 
			 */
			String[] arrJobs = new String[arrSize]; 
			String[] arrArriveS = new String[arrSize]; 
			String[] arrRuntimeS = new String[arrSize]; 
			int[] arrArriveInt = new int[arrSize];
			int[] arrRuntimeInt1 = new int[arrSize];
			int[] arrRuntimeInt2 = new int[arrSize]; 

			/* Scanner object to save job names, store job names in array */
			Scanner jobNameScan = new Scanner(jobsFile);
			jobNameScan.useDelimiter("\\s");
			String jobName = null;


			for(int i = 0; i < arrSize ; i++) {
				jobName = jobNameScan.next();
				arrJobs[i] = jobName;
				jobNameScan.next(); //skip arrival time
				jobNameScan.next(); //skip service time
			}

			jobNameScan.close();

			/* Scanner object to save arrival times, store arrival times in array */
			Scanner arrivalTimeScan = new Scanner(jobsFile);
			arrivalTimeScan.useDelimiter("\\s");
			String arrivalTime = null;

			for(int i = 0; i < arrSize ; i++) {

				arrivalTimeScan.next();
				arrivalTime = arrivalTimeScan.next();
				arrArriveS[i] = arrivalTime;
				arrArriveInt[i] = Integer.parseInt(arrArriveS[i]); 
				arrivalTimeScan.next();
			}

			arrivalTimeScan.close();

			/* Scanner object to save service times, store service times in array */
			Scanner serviceTimeScan = new Scanner(jobsFile);
			serviceTimeScan.useDelimiter("\\s");
			String serviceTime = null;	

			/* Copy 1 for FCFS and SPN */
			for(int i = 0; i < arrSize ; i++) {

				serviceTimeScan.next(); //skip job name
				serviceTimeScan.next(); //skip arrival time
				serviceTime = serviceTimeScan.next();
				arrRuntimeS[i] = serviceTime;
				arrRuntimeInt1[i] = Integer.parseInt(arrRuntimeS[i]);
			}

			/* Copy 2 for HRRN */
			for (int i = 0; i < arrSize ; i++) {
				arrRuntimeInt2[i] = arrRuntimeInt1[i];	
			}

			serviceTimeScan.close();

			/* ~~~~~ Begin FCFS implementation ~~~~~ */

			/** 
			 * FCFS Prioritize Jobs by arrival time Algorithm: compare each job's runtime 
			 * against each other job's runtime. for index 0 to n-1, assign priority 0 
			 * through n-1 per arrival time. for index n, compare index n to index 0; and 
			 * update each index to correct priority. 
			 */			
			int[] arrPriorityFCFS = new int[arrSize];
			int comp1 = 0;
			int comp2 = 0;

			for (int i = 0; i < arrSize ; i++) {

				comp1 = arrArriveInt[i];
				int k = i + 1;

				for (int j = i ; j < arrSize ; j++) {

					/* when at final index n, compare n with index 0 and switch priorities if needed */
					if (k >= arrSize) {

						comp2 = arrArriveInt[i-i];

						if (comp1 > comp2) {

							arrPriorityFCFS[i] = i; 

						} else if (comp1 < comp2) {

							arrPriorityFCFS[i] = i-i;
							arrPriorityFCFS[i-i] = i;

						} else {

							arrPriorityFCFS[i] = i;
						}

						/* indices 0 through n -1; compare index i against k */
					} else { 

						comp2 = arrArriveInt[k];

						if (comp1 < comp2) {

							arrPriorityFCFS[i] = i;

						} else if (comp1 > comp2) {

							arrPriorityFCFS[i] = k;
							arrPriorityFCFS[k] = i;

						} else {

							arrPriorityFCFS[i] = i;
						}
					}

					k++;

				}
			}

			/* get total runtime */
			int allJobsRuntime = 0;

			for (int i = 0 ; i < arrSize ; i++) {

				int temp = arrRuntimeInt1[i];
				allJobsRuntime = allJobsRuntime + temp;
			}

			/**
			 * Printing FCFS chart: title, job names.  
			 * Alignment/printing current running job to chart 
			 * by priority - highest (0) priority will run first, 
			 * second highest (1) priority 2nd, so one through all jobs.
			 * */
			System.out.println("\n---FCFS---\n");

			for (int i = 0 ; i < arrSize ; i++) {

				System.out.print(arrJobs[i] + " ");
			}

			System.out.println();

			for (int i = 0 ; i < arrSize ; i++) {

				for (int j = 0 ; j < arrSize ; j++) {

					if (arrPriorityFCFS[j] == i) {

						String graphSpaceFinal = "";

						for (int k = 0 ; k < j ; k++) {

							graphSpaceFinal = graphSpaceFinal + space + space; 
						}

						for (int k = 0 ; k < arrRuntimeInt1[j] ; k++) {

							System.out.println(graphSpaceFinal + jobX);
						}
					}
				}
			}	

			/* ~~~~~ Finish FCFS implementation ~~~~~ */



			/* ~~~~~ Begin SPN implementation ~~~~~ */

			/* Printing SPN chart: title, job names */ 
			System.out.println("\n---SPN---\n");

			for (int i = 0 ; i < arrSize ; i++) {
				System.out.print(arrJobs[i] + " ");
			}

			System.out.println();	

			/** 
			 * SPN Prioritize Jobs by shortest service time. 
			 * Algorithm: for all jobs that have arrived and have not run, 
			 * sort those jobs' service times to find the shortest. Run 
			 * the job with the shortest service time until no jobs left.
			 */	
			boolean[] arrCompletedSPN = new boolean[arrSize];
			Arrays.fill(arrCompletedSPN, false);
			boolean[] arrAvailSPN = new boolean[arrSize];
			Arrays.fill(arrAvailSPN, false);
			int currTimeSPN = 0;

			/* will run through each job in list */
			for (int a = 0 ; a < arrSize ; a++) {

				for (int i = 0 ; i < arrSize ; i++) {

					if (arrCompletedSPN[i] != true && arrArriveInt[i] <= currTimeSPN) {

						arrAvailSPN[i] = true;
					} else {

						arrAvailSPN[i] = false;
					}
				}

				/* change values of jobs we are excluding to a very high runtime to make it effectively unavailable */ 
				for (int i = 0 ; i < arrSize ; i++) {

					if (arrCompletedSPN[i] == true) {

						arrRuntimeInt1[i] = 999;
					}
				}

				/* compare each index with minimum to determine shortest service time and index location */
				int indexSPN = 0;
				int shortest = arrRuntimeInt1[indexSPN];

				for (int i = 1; i < arrSize; i++){

					if (arrRuntimeInt1[i] < shortest && arrAvailSPN[i] == true){

						shortest = arrRuntimeInt1[i];
						indexSPN = i;
					}
				}

				/**
				 * Printing SPN chart: correct alignment for running job 
				 * printed to chart by shortest service time, not yet 
				 * run, and has arrived. Each job will run in this fashion   
				 * until no jobs left. 
				 * */
				int jobServiceTimeSPN = arrRuntimeInt1[indexSPN];
				String graphSpaceFinal = "";

				for (int k = 0 ; k < indexSPN ; k++) {

					graphSpaceFinal = graphSpaceFinal + space + space;
				}

				for (int k = 0 ; k < jobServiceTimeSPN ; k++) {

					System.out.println(graphSpaceFinal + jobX);
				}

				/* update current time and set job has been completed to true*/
				currTimeSPN = currTimeSPN + jobServiceTimeSPN;
				arrCompletedSPN[indexSPN] = true;
			} 

			/* ~~~~~~ Finish SPN implementation ~~~~~~ */



			/* ~~~~~~ Begin HRRN implementation ~~~~~~ */

			/* Printing HRRN chart: title, job names */ 
			System.out.println("\n---HRRN---\n");

			for (int i = 0 ; i < arrSize ; i++) {
				System.out.print(arrJobs[i] + " ");
			}

			System.out.println();

			/**
			 * HRRN Prioritize Jobs by response ratio R: 
			 * 		R = ((wait + service) / service) 
			 * Algorithm: for all jobs that have arrived and have not run, 
			 * sort those jobs' R values to find the largest. Run 
			 * the job with the largest R value until no jobs left.
			 * */
			boolean[] arrCompletedHRRN = new boolean[arrSize]; 
			Arrays.fill(arrCompletedHRRN, false); 
			boolean[] arrAvailHRRN = new boolean[arrSize]; 
			Arrays.fill(arrAvailHRRN, false);
			double[] arrWaitHRRN = new double[arrSize];
			double[] arrCalcR = new double[arrSize];
			int currTimeHRRN = 0;

			/* will run through each job in list */
			for (int a = 0 ; a < arrSize ; a++) {

				for (int i = 0 ; i < arrSize ; i++) {

					if (arrCompletedHRRN[i] != true && arrArriveInt[i] <= currTimeHRRN) {

						arrAvailHRRN[i] = true;
					} else {

						arrAvailHRRN[i] = false;
					}
				}

				/* change values of jobs we are excluding to a very low wait time to make 
				 * it effectively unusable. otherwise calculate wait time as normal.
				 * */
				for (int i = 0 ; i < arrSize ; i++) {

					if (arrCompletedHRRN[i] == true || arrAvailHRRN[i] == false) {

						arrWaitHRRN[i] = -99;

					} else {

						arrWaitHRRN[i] = currTimeHRRN - arrArriveInt[i];
					}
				} 

				/* find R values for each job */
				for (int i = 0 ; i < arrSize ; i++) {

					arrCalcR[i] = ( ( arrWaitHRRN[i] + arrRuntimeInt2[i]) / arrRuntimeInt2[i] );
				}

				/* compare each index with largest to determine highest R and index location */
				int indexHRRN = 0;
				double largest = arrCalcR[indexHRRN];

				for (int i = 1; i < arrSize; i++){

					if (arrCalcR[i] > largest){

						largest = arrCalcR[i];
						indexHRRN = i;
					}
				}

				/**
				 * Printing HRRN chart: correct alignment for running job 
				 * run, and has arrived. Each job will run in this fashion  
				 * until no jobs left. 
				 * */
				int jobServiceTimeHRRN = arrRuntimeInt2[indexHRRN];
				String graphSpaceFinal = "";

				for (int k = 0 ; k < indexHRRN ; k++) {

					graphSpaceFinal = graphSpaceFinal + space + space;
				}

				for (int k = 0 ; k < jobServiceTimeHRRN ; k++) {

					System.out.println(graphSpaceFinal + jobX);
				}

				/* update current time and set job has been completed to true*/
				currTimeHRRN = currTimeHRRN + jobServiceTimeHRRN;
				arrCompletedHRRN[indexHRRN] = true;
			}

			/* ~~~~~~ Finish HRRN implementation ~~~~~~ */

		} catch (FileNotFoundException fnfe) {

			System.out.println(fnfe.getMessage());
		}
	}
}
