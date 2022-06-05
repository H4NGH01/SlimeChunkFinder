package main.core;

import main.core.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SlimeChunkFinderWindow extends GUIBase {

	private final SettingsGUI settingsGUI = new SettingsGUI(this);

	public final StringBuilder out = new StringBuilder();
	public String[] outArray;

	public final JPanel outputPanel = new JPanel(new BorderLayout());
	public final JTextPane output = new JTextPane();

	public boolean useChunkCoord = Boolean.parseBoolean(Main.getProperties().getProperty("use-chunk-coord"));

	public final JLabel lMinX = fLabel(this, 40, 60, 80, 20, this.useChunkCoord ? "min_chunk_X" : "min_coord_X");
	public final JLabel lMaxX = fLabel(this, 160, 60, 80, 20, this.useChunkCoord ? "max_chunk_X" : "max_coord_X");
	public final JLabel lMinZ = fLabel(this, 40, 120, 80, 20, this.useChunkCoord ? "min_chunk_Z" : "min_coord_Z");
	public final JLabel lMaxZ = fLabel(this, 160, 120, 80, 20, this.useChunkCoord ? "max_chunk_Z" : "max_coord_Z");

	public final JTextField fSeed = fTextField(this, 0, 70, 20, 190, 20, "");
	public final JTextField fMinX = fTextField(this, 16, 20, 80, 120, 20, "");
	public final JTextField fMaxX = fTextField(this, 16, 140, 80, 120, 20, "");
	public final JTextField fMinZ = fTextField(this, 16, 20, 140, 120, 20, "");
	public final JTextField fMaxZ = fTextField(this, 16, 140, 140, 120, 20, "");

	public SlimeChunkFinderWindow(SlimeChunkFinder finder) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setTitle(Main.getName() + " " + Main.getVersion());

		this.out.append("default_text" + "\n");
		this.outArray = this.out.toString().split("\n");

		this.outputPanel.setBackground(Color.white);
		this.outputPanel.setBounds(300, 20, 450, 500);
		this.output.setEditable(false);
		this.output.setContentType("text/html");
		this.output.setText(Util.toHTMLFormat(translation(this.outArray[0]).replaceAll("%n", "\n")));
		this.outputPanel.add(this.output, BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(this.output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.outputPanel.add(scroller);
		add(this.outputPanel);

		fLabel(this, 20, 20, 60, 20, "seed");
		if (Boolean.parseBoolean(Main.getProperties().getProperty("save-record"))) this.fSeed.setText(Main.getProperties().getProperty("last-seed"));

		fButton(this, 100, 180, 100, 30, "find", e -> {
			long seed;
			int minX;
			int maxX;
			int minZ;
			int maxZ;
			try {
				seed = Long.parseLong(this.fSeed.getText());
				minX = Integer.parseInt(this.fMinX.getText());
				maxX = Integer.parseInt(this.fMaxX.getText());
				minZ = Integer.parseInt(this.fMinZ.getText());
				maxZ = Integer.parseInt(this.fMaxZ.getText());
				finder.setSeed(seed);
				if (minX <= maxX && minZ <= maxZ) {
					if (!this.useChunkCoord) {
						minX /= 16;
						maxX /= 16;
						minZ /= 16;
						maxZ /= 16;
					}
					List<Result> rs = finder.find(minX, maxX, minZ, maxZ);
					this.out.delete(0, this.out.length());
					this.out.append("result_head" + "\n");
					for (Result r : rs) {
						this.out.append(r.getDetails()).append("\n").append(finder.getChunkArrayImage(r.getChunkArray()));
					}
					this.outArray = this.out.toString().split("\n");
					StringBuilder sb = new StringBuilder();
					for (String s : this.outArray) {
						sb.append(translation(s).replaceAll("%n", "\n").replaceAll("%ch", translation("chunk")).replaceAll("%cd", translation("coord")).replaceAll("%sc", translation("slime_chunk_amount")));
					}
					this.output.setText(Util.toHTMLFormat(sb.toString()));
					if (seed == 0 && minX == 0 && maxX == 0 && minZ == 0 && maxZ == 0) {
						this.output.setText(Util.toHTMLFormat(translation("easter_egg")));
					}
					Main.getProperties().setProperty("last-seed", String.valueOf(seed));
					try {
						Main.getProperties().store(new FileOutputStream("config.properties"), null);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					this.output.setText(translation("invalid_argument"));
				}
			} catch (Exception ex) {
				this.output.setText(translation("invalid_argument"));
			}
		});

		fButton(this, 20, 480, 30, 30, JImageIcon("/res/icon/settings.png"), e -> this.settingsGUI.setVisible(true));

		this.setVisible(true);
	}

}
