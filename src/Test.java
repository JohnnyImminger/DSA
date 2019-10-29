

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
		//jobs = Job.read(new File("input/j1201_5.sm"));//best makespan=112
		//resources = Resource.read(new File("input/j1201_5.sm"));
		jobs = Job.read(new File("input/j12046_8.sm"));
		resources = Resource.read(new File("input/j12046_8.sm"));

		setup();
		updatePool(jobs[0]);
		jobs[0].start = 0;
		jobs[0].ende = 0;

		alg();
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
			//planbar.sort((e1,e2) -> Integer.valueOf(jobs[e2-1].nachfolger.size()).compareTo(jobs[e1-1].nachfolger.size()));
			Integer start = frühesterZeitpunkt(jobs[planbar.get(0)-1]);
			if(start == null) {
				throw new IllegalArgumentException("Element nicht erlaubt im Pool");
			}
			while(!isPlatz(jobs[planbar.get(0)-1], start)) {
				start++;
			}
			einplanen(jobs[planbar.get(0)-1], start);
		}
		printResult();
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
		if(platz == job.dauer) {
			return true;
		}
		return false;
	}

	private static Integer frühesterZeitpunkt(Job job) {
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
		for(int i = 0; i < jobs.length; i++){
			jobs[i].calculatePredecessors(jobs);
			horizon += jobs[i].dauer;
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
		for (int i = 0; i < jobs.length; i++){
			gesamtDauer += jobs[i].dauer();
			
			System.out.print("Nummer: " + jobs[i].nummer()+"     |    ");
			System.out.print("Nachfolger: ");
			ArrayList<Integer> nachfolger = jobs[i].nachfolger();
			for(int j = 0; j < nachfolger.size(); j++){
				System.out.print(" " + nachfolger.get(j) +" ");
				
			}
			System.out.print(" Vorgaenger: ");
			ArrayList<Integer> vorgaenger = jobs[i].vorgaenger();
			for(int j = 0; j < vorgaenger.size(); j++){
				System.out.print(" " + vorgaenger.get(j) +" ");
				
			}
			System.out.print("     |    ");
			System.out.print("Dauer: " + jobs[i].dauer() + "     |    ");
			System.out.println("R1: " + jobs[i].verwendeteResource(0) +  "  R2: " + jobs[i].verwendeteResource(1) + 
					"  R3: " + jobs[i].verwendeteResource(2) + "  R4: " + jobs[i].verwendeteResource(3));
		}
		System.out.println("T = " + gesamtDauer);
	}
	
	private static void auslesen(Resource[] resource) {
		for (int i = 0; i < resource.length; i++){
			System.out.print("Resource: " + resource[i].nummer()+"     |    ");
			System.out.println("Verf�gbarkeit: " + resource[i].maxVerfuegbarkeit());
		}
	}
}
