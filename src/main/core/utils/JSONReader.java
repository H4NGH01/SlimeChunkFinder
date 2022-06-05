package main.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class JSONReader {

	private final String path;
	private InputStream stream;
	private BufferedReader reader;
	
	public JSONReader(String path) {
		this.path = path;
	}

	public HashMap<String, Object> getMap() {
		HashMap<String, Object> map = new HashMap<>();
		this.stream = this.getClass().getResourceAsStream(this.path);
		this.reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.stream)));
		try {
			for (String line; (line = this.reader.readLine()) != null;) {
				if (line.length() < 2) continue;
				String[] array = new String[2];
				int r = 0;
				StringBuilder b = new StringBuilder();
				for (int i = 0; i  < line.length(); i++) {
					char c = line.charAt(i);
					if (i > 0 && c == '"' && line.charAt(i - 1) != '\\' && r == 0) {
						r++;
					}
					if (r == 1 || r == 2) b.append(c);
					if (c == ':' && r == 1) {
						array[0] = b.substring(1, b.length() - 2);
						b.delete(0, b.length());
						r++;
					}
					if (i + 1 == line.length() && r == 2) {
						array[1] = b.toString().replaceAll(" ", "").replaceAll(",", "");
						r++;
					}
				}
				map.put(array[0], array[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String getString(String key) {
		if (key == null) return null;
		this.stream = this.getClass().getResourceAsStream(this.path);
		this.reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.stream)));
		try {
			for (String line; (line = this.reader.readLine()) != null;) {
				if (line.length() < 2) continue;
				String[] array = new String[2];
				int r = 0;
				StringBuilder b = new StringBuilder();
				for (int i = 0; i  < line.length(); i++) {
					char c = line.charAt(i);
					if (i > 0 && c == '"' && line.charAt(i - 1) != '\\') {
						r++;
					}
					if (r == 1 || r == 3) b.append(c);
					if (c == ':' && r == 2) {
						array[0] = b.toString().replaceFirst("\"", "");
						b.delete(0, b.length());
					}
					if (c == '"' && r == 4) {
						array[1] = b.toString().replaceFirst("\"", "");
					}
				}
				if (key.equals(array[0])) {
					try {
						this.stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return array[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public int getInteger(String key) {
		if (key == null) return 0;
		this.stream = this.getClass().getResourceAsStream(this.path);
		this.reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.stream)));
		try {
			for (String line; (line = this.reader.readLine()) != null;) {
				if (line.length() < 2) continue;
				String[] array = new String[2];
				int r = 0;
				StringBuilder b = new StringBuilder();
				for (int i = 0; i  < line.length(); i++) {
					char c = line.charAt(i);
					if (i > 0 && c == '"' && line.charAt(i - 1) != '\\') {
						r++;
					}
					if (r == 1 || r == 3) b.append(c);
					if (c == ':' && r == 2) {
						array[0] = b.toString().replaceFirst("\"", "");
						b.delete(0, b.length());
						r++;
					}
					if (i + 1 == line.length() && r == 3) {
						array[1] = b.toString().replaceAll(" ", "").replaceAll(",", "");
						r++;
					}
				}
				if (key.equals(array[0])) {
					try {
						this.stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return Integer.parseInt(array[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unused")
	public double getDouble(String key) {
		if (key == null) return 0d;
		this.stream = this.getClass().getResourceAsStream(this.path);
		this.reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.stream)));
		try {
			for (String line; (line = this.reader.readLine()) != null;) {
				if (line.length() < 2) continue;
				String[] array = new String[2];
				int r = 0;
				StringBuilder b = new StringBuilder();
				for (int i = 0; i  < line.length(); i++) {
					char c = line.charAt(i);
					if (i > 0 && c == '"' && line.charAt(i - 1) != '\\') {
						r++;
					}
					if (r == 1 || r == 3) b.append(c);
					if (c == ':' && r == 2) {
						array[0] = b.toString().replaceFirst("\"", "");
						b.delete(0, b.length());
						r++;
					}
					if (i + 1 == line.length() && r == 3) {
						array[1] = b.toString().replaceAll(" ", "").replaceAll(",", "");
						r++;
					}
				}
				if (key.equals(array[0])) {
					try {
						this.stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return Double.parseDouble(array[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0d;
	}
}
