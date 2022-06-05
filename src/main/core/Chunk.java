package main.core;

public class Chunk {
	
	private final int x;
	private final int z;
	private final boolean slimeChunk;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		this.slimeChunk = isSlimeChunk(this.x, this.z);
	}
	
	public final int getX() {
		return this.x;
	}
	
	public final int getZ() {
		return this.z;
	}
	
	public final boolean isSlimeChunk() {
		return this.slimeChunk;
	}
	
	private static boolean isSlimeChunk(int chunkX, int chunkZ) {
		SlimeChunkFinder.rand.setSeed(SlimeChunkFinder.getSeed() + ((long) chunkX * chunkX * 4987142) + ((long) chunkX * 5947611) + ((long) chunkZ * chunkZ * 4392871L) + ((long) chunkZ * 389711 ^ 987234911L));
        return SlimeChunkFinder.rand.nextInt(10) == 0;
    }
	
}
