package main.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.core.utils.Util;
import org.jetbrains.annotations.NotNull;

public class SlimeChunkFinder extends Thread {
	
	public static Random rand = new Random();
	private static long seed;

	public static final List<Chunk[][]> CHUNK_ARRAY_LIST = new ArrayList<>();
	private static final Boolean[][] SPAWNABLE = new Boolean[17][17];

	static {
		for (int x = -8; x < 9; x++) {
			for (int z = -8; z < 9; z++) {
				SPAWNABLE[z + 8][x + 8] = x * x + z * z < 7.5 * 7.5 && x * x + z * z > 1.5 * 1.5;
			}
		}
	}

	public Chunk @NotNull [][] genArray(int x, int z) {
		Chunk[][] chunkArray = new Chunk[17][17];
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 17; j++) {
				chunkArray[i][j] = new Chunk(x + j, z + i);
			}
		}
		return chunkArray;
	}

	public int calcSlimeChunkSize(Chunk[][] chunkArray) {
		int i = 0;
		for (int x = 0; x < 17; x++) {
			for (int z = 0; z < 17; z++) {
				if (chunkArray[z][x].isSlimeChunk() && SPAWNABLE[z][x]) {
					i++;
				}
			}
		}
		return i;
	}
	
	public static @NotNull String genChunkArrayImage(Chunk[][] chunkArray) {
		StringBuilder s = new StringBuilder();
		for (int z = 0; z < 17; z++) {
			for (int x = 0; x < 17; x++) {
				s.append(SPAWNABLE[z][x] ? (chunkArray[z][x].isSlimeChunk() ? Util.toHTMLColor("green") : Util.toHTMLColor("yellow")) : Util.toHTMLColor("gray")).append("¨€");
			}
			s.append("%n" + "<p style='margin-top:-5'>");
		}
		s.append(Util.toHTMLColor("black"));
		return s.toString();
	}
	
	public static long getSeed() {
		return seed;
	}

	public void setSeed(long seedIn) {
		seed = seedIn;
	}

}
