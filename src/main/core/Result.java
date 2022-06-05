package main.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Result(int x, int z, int q, Chunk[][] chunkArray) {

	public int getAmount() {
		return this.q;
	}

	@Contract(pure = true)
	public @NotNull String getDetails() {
		return "%ch: " + this.x + " ~ " + this.z + "%n%cd: " + this.x * 16 + " ~ " + this.z * 16 + "%n%sc: " + this.q + "%n";
	}

	public Chunk[][] getChunkArray() {
		return this.chunkArray;
	}
}
