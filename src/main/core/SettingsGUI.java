package main.core;

import main.core.lang.Language;
import main.core.utils.Util;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsGUI extends GUIBase {

    private final SlimeChunkFinderWindow scfWindow;

    public final JCheckBox cb1 = fCheckBox(this, 20, 20, 20, 20, null);
    public final JCheckBox cb2 = fCheckBox(this, 20, 60, 20, 20, null);
    public final JButton bco = fButton(this, 110, 320, 80, 30, "confirm", null);
    public final JButton bca = fButton(this, 200, 320, 80, 30, "cancel", null);
    public final JButton bap = fButton(this, 290, 320, 80, 30, "apply", null);

    public final JComboBox<?> langComboBox;

    public SettingsGUI(SlimeChunkFinderWindow window) {
        this.scfWindow = window;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle(Main.getName() + " " + translation("settings"));

        fLabel(this, 50, 20, 120, 20, "settings.save-last-record");
        fLabel(this, 50, 60, 120, 20, "settings.use-chunk-coord");
        fLabel(this, 20, 100, 120, 20, "language");

        String[] langList = new String[Language.values().length];
        for (int i = 0; i < Language.values().length; i++) {
            langList[i] = Main.getLanguagesManager().getTranslateFromLanguage("language.name", Language.values()[i]);
        }
        this.langComboBox = new JComboBox<Object>(langList);

        this.langComboBox.setBounds(20, 130, 100, 30);
        for (int i = 0; i < Language.values().length; i++) {
            if (Language.values()[i].equals(Language.valueOf(Main.getProperties().getProperty("language")))) {
                this.langComboBox.setSelectedIndex(i);
                break;
            }
        }
        this.add(this.langComboBox);

        this.cb1.setSelected(Boolean.parseBoolean(Main.getProperties().getProperty("save-record")));
        this.cb2.setSelected(Boolean.parseBoolean(Main.getProperties().getProperty("use-chunk-coord")));

        this.bco.addActionListener(e1 -> {
            this.updateSettings();
            this.dispose();
        });
        this.bca.addActionListener(e1 -> {
            for (int i = 0; i < Language.values().length; i++) {
                if (Language.values()[i].equals(Language.valueOf(Main.getProperties().getProperty("language")))) {
                    this.langComboBox.setSelectedIndex(i);
                    break;
                }
            }
            this.cb1.setSelected(Boolean.parseBoolean(Main.getProperties().getProperty("save-record")));
            this.cb2.setSelected(Boolean.parseBoolean(Main.getProperties().getProperty("use-chunk-coord")));
            this.dispose();
        });
        this.bap.addActionListener(e1 -> this.updateSettings());
    }

    private void updateSettings() {
        Main.getProperties().setProperty("save-record", String.valueOf(cb1.isSelected()));
        Main.getProperties().setProperty("use-chunk-coord", String.valueOf(cb2.isSelected()));
        this.scfWindow.useChunkCoord = cb2.isSelected();

        this.scfWindow.lMinX.setToolTipText(this.cb2.isSelected() ? "min_chunk_X" : "min_coord_X");
        this.scfWindow.lMaxX.setToolTipText(this.cb2.isSelected() ? "max_chunk_X" : "max_coord_X");
        this.scfWindow.lMinZ.setToolTipText(this.cb2.isSelected() ? "min_chunk_Z" : "min_coord_Z");
        this.scfWindow.lMaxZ.setToolTipText(this.cb2.isSelected() ? "max_chunk_Z" : "max_coord_Z");

        for (int i = 0; i < Language.values().length; i++) {
            if (i == this.langComboBox.getSelectedIndex()) {
                Main.getProperties().setProperty("language", Language.values()[i].toString());
                Main.getLanguagesManager().setLanguages(Language.values()[i]);
                break;
            }
        }
        this.setTitle(Main.getName() + " " + translation("settings"));
        for (JComponent c : TRANSLATION_LIST) {
            if (c instanceof JLabel l) l.setText(translation(l.getToolTipText()));
            if (c instanceof JButton b) b.setText(translation(b.getToolTipText()));
        }
        StringBuilder str = new StringBuilder();
        for (String s : this.scfWindow.outArray) {
            str.append(translation(s).replaceAll("%n", "\n").replaceAll("%ch", translation("chunk")).replaceAll("%cd", translation("coord")).replaceAll("%sc", translation("slime_chunk_amount")));
        }
        this.scfWindow.output.setText(Util.toHTMLFormat(str.toString()));

        try {
            Main.getProperties().store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
