# README 

## Project 3 for UTD CS4348 Fall 2021: Operating Systems Concepts

# Assignment Instructions

### Language/Platform

  * This project must target a Unix platform and execute properly on our CS1 server.
  * The project must be written in C, C++, or Java.


### Overview

  * This project will simulate a scheduler scheduling a set of jobs.
  * The project will allow the user to execute a set of scheduling algorithms on an input set of jobs.  It will output a representation of how the jobs are executed.


### Design

  * You may design your own implementation approach, but here are a few constraints.  
  * Your program should read in a list of jobs from a tab-delimited text file named jobs.txt. The format of the text file should have one line for each job, where each line has a job name, a start time and a duration. The job name must be a letter from A-Z. The first job should be named A, and the remaining jobs should be named sequentially following the alphabet, so if there are five jobs, they are named A-E. The arrival times of these jobs should be in order.
  * The jobs should be scheduled first using the **FCFS scheduler**, then scheduled again using the **SPN scheduler**, and once more using the **HRRN scheduler**.
  * Your output should be a graph as shown in the slides. The graph can be text or you can use a graphics package such as JavaFX to draw the graph. For text, you may draw the graph down the page rather than across.
  * Your program should be able to reproduce the sample shown in the book as well as any similar set of jobs.


## Instructions to Compile/Run

  * Java Files: Scheduler.java
  * Compiled on UTD CS1 server using command: `javac Scheduler.java`
  * Run on UTD CS1 server using command: `java Scheduler`


### Developed using:

  * Java version: 11.0.8
  * Java IDE: Eclipse IDE for Java Developers (includes Incubating components)
  * Version: 2020-06 (4.16.0)
  * Build id: 20200615-1200
  * OS: Windows 10, v.10.0, x86_64 / win32
