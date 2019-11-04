import java.util.ArrayList;
import java.util.Arrays;

public class Shift {

    private Job[] jobs;
    private Resource[] resources;
    private int[][] res;
    private ArrayList<Job> result = new ArrayList();
    private int index;
    private int duration;
    private int latestFinishCurrJob;

    public Shift(Job[] jobs, Resource[] resources, int[][] res, ArrayList<Integer> result, int duration) {
        this.jobs = jobs;
        this.resources = resources;
        this.res = res;
        result.forEach((e) -> this.result.add(jobs[e - 1]));
        this.index = result.size() - 2;
        this.duration = duration;
    }

    public void dispResult() {
        System.out.println(Arrays.toString(result.stream().mapToInt(e -> e.nummer).toArray()));
        System.out.println("Dauer nach shift: " + (result.get(result.size() - 1)).ende);
        new Gui(result.stream().mapToInt((i) -> i.nummer).toArray(), (result.get(result.size() - 1)).ende, res, 0.5f);
    }

    public void run() {
        for(latestFinishCurrJob = duration; index >= 0; --index) {
            Job currJob = result.get(index);
            currJob.nachfolger.forEach((n) -> latestFinishCurrJob = Math.min(latestFinishCurrJob, jobs[n - 1].start));
            int latestStart = latestFinishCurrJob - currJob.dauer;
            releaseJob(currJob);

            while(!checkRes(currJob, latestStart)) {
                --latestStart;
            }

            rebaseJob(currJob);
            latestFinishCurrJob = duration;
        }

        this.trim();
    }

    private void trim() {
        int start = (result.get(0)).start;
        result.forEach((e) -> {
            e.start = e.start - start;
            e.ende = e.ende - start;
        });
        for(int i = 0; i < (result.get(result.size() - 1)).ende; ++i) {
            res[i][0] = res[i + start][0];
            res[i][1] = res[i + start][1];
            res[i][2] = res[i + start][2];
            res[i][3] = res[i + start][3];
        }
        for(int i = (result.get(result.size() - 1)).ende + 1; i < Test.getHorizon(); ++i) {
            res[i][0] = Test.getRes(0);
            res[i][1] = Test.getRes(1);
            res[i][2] = Test.getRes(2);
            res[i][3] = Test.getRes(3);
        }
    }

    private boolean checkRes(Job job, int instant) {
        boolean b = true;
        for(int i = 0; i < job.dauer; ++i) {
            if (res[instant + i][0] < job.verwendeteResource(0) ||
                    res[instant + i][1] < job.verwendeteResource(1) ||
                    res[instant + i][2] < job.verwendeteResource(2) ||
                    res[instant + i][3] < job.verwendeteResource(3)) {
                b = false;
            }
        }
        return b;
    }

    private void rebaseJob(Job job) {
        for(int i = 0; i < job.dauer; ++i) {
            res[job.start + i][0] -= job.verwendeteResource(0);
            res[job.start + i][1] -= job.verwendeteResource(1);
            res[job.start + i][2] -= job.verwendeteResource(2);
            res[job.start + i][3] -= job.verwendeteResource(3);
        }

    }

    private void releaseJob(Job job) {
        for(int i = 0; i < job.dauer; ++i) {
            res[job.start + i][0] += job.verwendeteResource(0);
            res[job.start + i][1] += job.verwendeteResource(1);
            res[job.start + i][2] += job.verwendeteResource(2);
            res[job.start + i][3] += job.verwendeteResource(3);
        }

    }
}
