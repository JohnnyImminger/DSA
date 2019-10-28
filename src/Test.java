

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

	public static void main (String[] args) throws FileNotFoundException{
//		jobs = Job.read(new File("j1201_5.sm"));//best makespan=112
//		res = Resource.read(new File("j1201_5.sm"));
		jobs = Job.read(new File("input/j12046_8.sm"));
		resources = Resource.read(new File("input/j12046_8.sm"));

		setup();
		updatePool(jobs[0]);
		System.out.println(planbar.size());

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
	}

	private static void updatePool(Job job) {
		if(!(planbar.contains(job.nummer) && job.nummer == 0)) {
			throw new IllegalArgumentException();
		}
		planbar.remove(planbar.indexOf(job.nummer));
		planbar.addAll(new ArrayList<Integer>(job.nachfolger));
	}

	private static void doJob(Job job) {

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
