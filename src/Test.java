

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Test {
	
	private static ArrayList<Integer>planbar = new ArrayList<>();

	private static Job[] jobs;
	private static Resource[] resources;
	private static int horizon = 0;
	private static int[][] res;
	private static int res1;
	private static int res2;
	private static int res3;
	private static int res4;

	private static ArrayList<Integer> result = new ArrayList<>();

	public static void main (String[] args) throws FileNotFoundException{
		jobs = Job.read(new File("input/j1201_5.sm"));//best makespan=112
		resources = Resource.read(new File("input/j1201_5.sm"));
		//jobs = Job.read(new File("input/j12046_8.sm"));
		//resources = Resource.read(new File("input/j12046_8.sm"));

		setup();
		updatePool(jobs[0]);
		jobs[0].start = 0;
		jobs[0].ende = 0;

		alg();

		printResult();

		Shift s = new Shift(jobs, resources, res, result, jobs[result.get(result.size()-1)-1].ende);
		s.run();
		s.dispResult();
	}

	private static void printResult() {
		/*
		for(int[] temp: res) {
			System.out.println(Arrays.toString(temp));
		}
		 */
		System.out.println(Arrays.toString(result.toArray()));
		System.out.println("Dauer: " + jobs[result.get(result.size()-1)-1].ende);
		Gui g = new Gui(result.stream().mapToInt(i -> i).toArray() ,jobs[result.get(result.size()-1)-1].ende ,res);
	}

	private static void alg() {
		while(!planbar.isEmpty()) {
			planbar.sort((e1,e2) -> Integer.compare(jobs[e2 - 1].nachfolger.size(),jobs[e1 - 1].nachfolger.size()));
			Integer start = fruehesterZeitpunkt(jobs[planbar.get(0)-1]);
			if(start == null) {
				throw new IllegalArgumentException("Element nicht erlaubt im Pool");
			}
			while(!isPlatz(jobs[planbar.get(0)-1], start)) {
				start++;
			}
			einplanen(jobs[planbar.get(0)-1], start);
		}
	}

	private static void einplanen(Job job, int startzeit) {
		for (int i = 0; i < job.dauer; i++) {
			res[startzeit+i][0] -= job.verwendeteResource(0);
			res[startzeit+i][1] -= job.verwendeteResource(1);
			res[startzeit+i][2] -= job.verwendeteResource(2);
			res[startzeit+i][3] -= job.verwendeteResource(3);
		}
		job.start = startzeit;
		job.ende = startzeit + job.dauer;
		result.add(job.nummer);
		updatePool(job);
	}

	private static boolean isPlatz(Job job, int startzeit) {
		int platz = 0;
		for (int i = 0; i < job.dauer; i++) {
			if(res[startzeit+i][0] > job.verwendeteResource(0) &&
					res[startzeit+i][1] > job.verwendeteResource(1) &&
					res[startzeit+i][2] > job.verwendeteResource(2) &&
					res[startzeit+i][3] > job.verwendeteResource(3)) {
				platz++;
			}
		}
		return platz == job.dauer;
	}

	private static Integer fruehesterZeitpunkt(Job job) {
		int min = 0;
		for (int i = 0; i < job.vorgaenger.size(); i++) {
			if(jobs[job.vorgaenger.get(i)-1].ende == null) {
				return null;
			}
			min = Math.max(min,jobs[job.vorgaenger.get(i)-1].ende);
		}
		return min;
	}

	private static void setup() {
		for (Job job : jobs) {
			job.calculatePredecessors(jobs);
			horizon += job.dauer;
		}
		res = new int[horizon][4];
		res1 = resources[0].maxVerfuegbarkeit;
		res2 = resources[1].maxVerfuegbarkeit;
		res3 = resources[2].maxVerfuegbarkeit;
		res4 = resources[3].maxVerfuegbarkeit;
		for (int i = 0; i < horizon; i++) {
			res[i][0] = res1;
			res[i][1] = res2;
			res[i][2] = res3;
			res[i][3] = res4;
		}
	}

	private static void updatePool(Job job) {
		if(job.nummer != 1) {
			for (int i = 0; i < planbar.size(); i++) {
				if(planbar.get(i) == job.nummer) {
					planbar.remove(i);
					break;
				}
			}
		}
		for (int i = 0; i < job.nachfolger.size(); i++) {
			jobs[job.nachfolger.get(i)-1].vorgaengerFlag--;
			if(jobs[job.nachfolger.get(i)-1].vorgaengerFlag == 0) {
				planbar.add(job.nachfolger.get(i));
			}
		}
	}

	private static void auslesen(Job[] jobs) {
		int gesamtDauer = 0;
		for (Job job : jobs) {
			gesamtDauer += job.dauer();

			System.out.print("Nummer: " + job.nummer() + "     |    ");
			System.out.print("Nachfolger: ");
			ArrayList<Integer> nachfolger = job.nachfolger();
			for (Integer value : nachfolger) {
				System.out.print(" " + value + " ");

			}
			System.out.print(" Vorgaenger: ");
			ArrayList<Integer> vorgaenger = job.vorgaenger();
			vorgaenger.stream().map(integer -> " " + integer + " ").forEach(System.out::print);
			System.out.print("     |    ");
			System.out.print("Dauer: " + job.dauer() + "     |    ");
			System.out.println("R1: " + job.verwendeteResource(0) + "  R2: " + job.verwendeteResource(1) +
					"  R3: " + job.verwendeteResource(2) + "  R4: " + job.verwendeteResource(3));
		}
		System.out.println("T = " + gesamtDauer);
	}
	
	private static void auslesen(Resource[] resource) {
		for (Resource value : resource) {
			System.out.print("Resource: " + value.nummer() + "     |    ");
			System.out.println("Verfuegbarkeit: " + value.maxVerfuegbarkeit());
		}
	}

	public static int getHorizon() {
		return horizon;
	}

	public static int getRes(int num) {
		switch (num) {
			case 0:
				return res1;
			case 1:
				return res2;
			case 2:
				return res3;
			case 3:
				return res4;
			default:
				throw new IllegalArgumentException("Arg has to be between 0 and 3!");
		}
	}
}
