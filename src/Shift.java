import java.util.ArrayList;

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
        System.out.println("Nach dem shift: "+(result.get(this.result.size() - 1)).ende);
        new Gui(result.stream().mapToInt((i) -> i.nummer).toArray(), (result.get(result.size() - 1)).ende, res,0.5f);
    }

    public void run() {
        for(this.latestFinishCurrJob = this.duration; this.index >= 0; --this.index) {
            Job currJob = this.result.get(this.index);
            currJob.nachfolger.forEach((n) -> this.latestFinishCurrJob = Math.min(this.latestFinishCurrJob, this.jobs[n - 1].start));
            int latestStart = this.latestFinishCurrJob - currJob.dauer;
            this.releaseJob(currJob);

            while(!this.checkRes(currJob, latestStart)) {
                --latestStart;
            }

            this.rebaseJob(currJob);
            this.latestFinishCurrJob = this.duration;
        }

        this.trim();
    }

    private void trim() {
        int start = (this.result.get(0)).start;
        this.result.forEach((e) -> {
            e.start = e.start - start;
            e.ende = e.ende - start;
        });
        for(int i = 0; i < (this.result.get(this.result.size() - 1)).ende; ++i) {
            this.res[i][0] = this.res[i + start][0];
            this.res[i][1] = this.res[i + start][1];
            this.res[i][2] = this.res[i + start][2];
            this.res[i][3] = this.res[i + start][3];
        }
        for(int i = (this.result.get(this.result.size() - 1)).ende + 1; i < Test.getHorizon(); ++i) {
            this.res[i][0] = Test.getRes(0);
            this.res[i][1] = Test.getRes(1);
            this.res[i][2] = Test.getRes(2);
            this.res[i][3] = Test.getRes(3);
        }
    }

    private boolean checkRes(Job job, int instant) {
        boolean b = true;
        for(int i = 0; i < job.dauer; ++i) {
            if (this.res[instant + i][0] < job.verwendeteResource(0) ||
                    this.res[instant + i][1] < job.verwendeteResource(1) ||
                    this.res[instant + i][2] < job.verwendeteResource(2) ||
                    this.res[instant + i][3] < job.verwendeteResource(3)) {
                b = false;
            }
        }
        return b;
    }

    private void rebaseJob(Job job) {
        for(int i = 0; i < job.dauer; ++i) {
            this.res[job.start + i][0] -= job.verwendeteResource(0);
            this.res[job.start + i][1] -= job.verwendeteResource(1);
            this.res[job.start + i][2] -= job.verwendeteResource(2);
            this.res[job.start + i][3] -= job.verwendeteResource(3);
        }

    }

    private void releaseJob(Job job) {
        for(int i = 0; i < job.dauer; ++i) {
            this.res[job.start + i][0] += job.verwendeteResource(0);
            this.res[job.start + i][1] += job.verwendeteResource(1);
            this.res[job.start + i][2] += job.verwendeteResource(2);
            this.res[job.start + i][3] += job.verwendeteResource(3);
        }

    }
}
