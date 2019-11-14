

import Algorithms.Algorithm;
import Objects.Job;
import Objects.Resource;
import validate.ValidateResult;

import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main (String[] args) throws Exception {
		long start = System.nanoTime();
        new Algorithm().doAll(/*"input/j1201_1.sm"*/);
        long end = System.nanoTime();
		System.out.println("Done in " + String.valueOf(end-start) +"ms.");
		ValidateResult.validate();
	}

}
