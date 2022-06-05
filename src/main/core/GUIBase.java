package main.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GUIBase extends JFrame {

    public static final List<JComponent> TRANSLATION_LIST = new ArrayList<>();

    @SuppressWarnings({"SameParameterValue", "UnusedReturnValue"})
    public static @NotNull JLabel fLabel(@NotNull JFrame frame, int x, int y, int width, int height, String text) {
        JLabel l = new JLabel(translation(text));
        l.setBounds(x, y, width, height);
        frame.add(l);
        l.setToolTipText(text);
        TRANSLATION_LIST.add(l);
        return l;
    }

    @SuppressWarnings("SameParameterValue")
    public static @NotNull JTextField fTextField(@NotNull JFrame frame, int columns, int x, int y, int width, int height, String text) {
        JTextField tf = new JTextField(translation(text), columns);
        tf.setBounds(x, y, width, height);
        frame.add(tf);
        return tf;
    }

    @SuppressWarnings("SameParameterValue")
    public static @NotNull JButton fButton(@NotNull JFrame frame, int x, int y, int width, int height, String text, ActionListener l) {
        JButton b = new JButton(translation(text));
        b.setBounds(x, y, width, height);
        b.addActionListener(l);
        frame.add(b);
        b.setToolTipText(text);
        TRANSLATION_LIST.add(b);
        return b;
    }

    @SuppressWarnings({"SameParameterValue", "UnusedReturnValue"})
    public static @NotNull JButton fButton(@NotNull JFrame frame, int x, int y, int width, int height, Icon icon, ActionListener l) {
        JButton b = new JButton(icon);
        b.setBounds(x, y, width, height);
        b.addActionListener(l);
        frame.add(b);
        return b;
    }

    @SuppressWarnings("SameParameterValue")
    public static @NotNull JCheckBox fCheckBox(@NotNull JFrame frame, int x, int y, int width, int height, ActionListener l) {
        JCheckBox cb = new JCheckBox();
        cb.setBounds(x, y, width, height);
        cb.addActionListener(l);
        frame.add(cb);
        return cb;
    }

    @Contract("_ -> new")
    @SuppressWarnings("SameParameterValue")
    public static @NotNull ImageIcon JImageIcon(String path) {
        try {
            InputStream is = Main.class.getResourceAsStream(path);
            Image im = ImageIO.read(Objects.requireNonNull(is));
            return new ImageIcon(im);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static String translation(String s) {
        return s != null ? Main.getLanguagesManager().handleGetObject(s).toString() : null;
    }

}
