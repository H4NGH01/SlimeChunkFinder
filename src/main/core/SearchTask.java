package main.core;

import java.util.ArrayList;
import java.util.List;

public class SearchTask extends Thread {

    private static boolean TASKING = false;

    private final int x1;
    private final int x2;
    private final int z1;
    private final int z2;

    private final List<Result> results = new ArrayList<>();

    public SearchTask(int x1, int x2, int z1, int z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.z1 = z1;
        this.z2 = z2;
        this.start();
        TASKING = true;
    }

    @Override
    public void run() {
        SlimeChunkFinder finder = Main.getFinder();
        int size = 0;
        int progress = 0;
        for (int z = z1; z < z2; z++) {
            for (int x = x1; x < x2; x++) {
                Chunk[][] chunks = finder.genArray(x, z);
                if (size < finder.calcSlimeChunkSize(chunks)) SlimeChunkFinder.CHUNK_ARRAY_LIST.clear();
                size = Math.max(size, finder.calcSlimeChunkSize(chunks));
                if (size == finder.calcSlimeChunkSize(chunks)) SlimeChunkFinder.CHUNK_ARRAY_LIST.add(chunks);
                progress++;
                Main.getWindow().fpb.setValue(progress);
            }
        }
        for (Chunk[][] chunk : SlimeChunkFinder.CHUNK_ARRAY_LIST) {
            if (size == finder.calcSlimeChunkSize(chunk)) this.results.add(new Result(chunk[8][8].getX(), chunk[8][8].getZ(), size, chunk));
        }
        TASKING = false;
        Main.getWindow().search(this.results);
    }

    public static boolean isTASKING() {
        return TASKING;
    }

    public void cancel() {
        this.stop();
        TASKING = false;
    }
}
