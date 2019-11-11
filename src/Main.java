

import Algorithms.Algorithm;
import Objects.Job;
import Objects.Resource;

import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main (String[] args) throws IOException {
		long start = System.nanoTime();
        new Algorithm().doOne("input/j1201_1.sm");
        long end = System.nanoTime();
		System.out.println("Done in " + String.valueOf(end-start) +"ms.");
	}

}
