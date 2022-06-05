package main.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.core.utils.Util;
import org.jetbrains.annotations.NotNull;

public class SlimeChunkFinder {
	
	public static Random rand = new Random();
	private static long seed;
	
	private final List<Chunk[][]> chunkArrayList = new ArrayList<>();
	private static final Boolean[][] SPAWNABLE = new Boolean[17][17];
	
	public SlimeChunkFinder() {
		for (int x = -8; x < 9; x++) {
			for (int z = -8; z < 9; z++) {
				SPAWNABLE[z + 8][x + 8] = x * x + z * z < 7.5 * 7.5 && x * x + z * z > 1.5 * 1.5;
			}
		}
	}
	
	public void setSeed(long seedIn) {
		seed = seedIn;
		rand.setSeed(seed);
	}
	
	public List<Result> find(int x1, int x2, int z1, int z2) {
		this.chunkArrayList.clear();
		int size = 0;
		for (int z = z1; z < z2; z++) {
			for (int x = x1; x < x2; x++) {
				Chunk[][] chunks = genArray(x, z);
				if (size < calcSlimeChunkSize(chunks)) this.chunkArrayList.clear();
				size = Math.max(size, calcSlimeChunkSize(chunks));
				if (size == calcSlimeChunkSize(chunks)) this.chunkArrayList.add(chunks);
			}
		}
		List<Result> results = new ArrayList<>();
		for (Chunk[][] chunk : this.chunkArrayList) {
			if (size == calcSlimeChunkSize(chunk)) results.add(new Result(chunk[8][8].getX(), chunk[8][8].getZ(), size, chunk));
		}
		return results;
	}

	private Chunk @NotNull [][] genArray(int x, int z) {
		Chunk[][] chunkArray = new Chunk[17][17];
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 17; j++) {
				chunkArray[i][j] = new Chunk(x + j - 8, z + i - 8);
			}
		}
		return chunkArray;
	}
	
	private int calcSlimeChunkSize(Chunk[][] chunkArray) {
		int i = 0;
		for (int x = -8; x < 9; x++) {
			for (int z = -8; z < 9; z++) {
				if (chunkArray[z + 8][x + 8].isSlimeChunk() && SPAWNABLE[z + 8][x + 8]) {
					i++;
				}
			}
		}
		return i;
	}
	
	public String getChunkArrayImage(Chunk[][] chunkArray) {
		StringBuilder s = new StringBuilder();
		for (int z = -8; z < 9; z++) {
			for (int x = -8; x < 9; x++) {
				s.append(SPAWNABLE[z + 8][x + 8] ? (chunkArray[z + 8][x + 8].isSlimeChunk() ? Util.toHTMLColor("green") : Util.toHTMLColor("yellow")) : Util.toHTMLColor("gray")).append("¨€");
			}
			s.append("%n" + "<p style='margin-top:-5'>");
		}
		s.append(Util.toHTMLColor("black"));
		return s.toString();
	}
	
	public static long getSeed() {
		return seed;
	}
	
	public boolean isSlimeChunk(long seed, int chunkX, int chunkZ) {
        rand.setSeed(seed + ((long) chunkX * chunkX * 4987142) + (chunkX * 5947611L) + ((long) chunkZ * chunkZ) * 4392871L + (chunkZ * 389711L) ^ 987234911L);
        return rand.nextInt(10) == 0;
    }
	
}
