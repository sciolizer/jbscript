package com.sciolizer.jbscript.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Notepad {

    protected TextArea leftText;
    protected TextArea rightText;
    protected Font f;
    private final Charset charset = Charset.forName("UTF-8");

    public void initialize() {
        final JFrame jFrame = new JFrame("jbscript");
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        JPanel mainpanel = (JPanel) jFrame.getContentPane();
        mainpanel.setLayout(new BorderLayout());


        MenuBar mbar = new MenuBar();
        jFrame.setMenuBar(mbar);

        Menu file = new Menu("File");
        Menu format = new Menu("Format");
        Menu font2 = new Menu("Size");

        file.add(newMenuItem("New...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                Notepad note1 = new Notepad();
                note1.initialize();
            }
        }));
        file.add(newMenuItem("Open", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog(jFrame, "Open");
                dialog.setVisible(true);

                String directory = dialog.getDirectory();
                String fileName = dialog.getFile();
                if (directory == null || fileName == null) return;
                Path path = FileSystems.getDefault().getPath(directory, fileName);
                List<String> lines;
                try {
                    lines = Files.readAllLines(path, charset);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (String line : lines) {
                    builder.append(line).append('\n');
                }
                leftText.setText(builder.toString());
            }
        }));
        file.add(newMenuItem("Save As...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog1 = new FileDialog(jFrame, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);

                String str7 = dialog1.getDirectory();
                String str8 = dialog1.getFile();
                String str9 = str7 + str8;


                String str6 = leftText.getText();
                int len1 = str6.length();
                byte buf[] = str6.getBytes();

                File f1 = new File(str9);
                try {
                    try (FileOutputStream fobj1 = new FileOutputStream(f1)) {
                        for (int k = 0; k < len1; k++) {
                            fobj1.write(buf[k]);
                        }
                        fobj1.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                jFrame.setTitle(str8);


            }
        }));
        file.add(newMenuItem("Exit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));

        mbar.add(file);


        format.add(font2);

        for (int fontSize : new int[]{12, 14, 18, 20}) {
            MenuItem fsize = new MenuItem(String.valueOf(fontSize));
            final int size = fontSize;
            fsize.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String fontName = f.getName();
                    int fontStyle = f.getStyle();

                    f = new Font(fontName, fontStyle, size);
                    leftText.setFont(f);
                }
            });
            font2.add(fsize);
        }

        mbar.add(format);

        leftText = new TextArea(26, 60);
        mainpanel.add(leftText, BorderLayout.WEST);

        rightText = new TextArea(26, 60);
        mainpanel.add(rightText, BorderLayout.EAST);

        f = new Font("Monospaced", Font.PLAIN, 15);
        leftText.setFont(f);
        rightText.setFont(f);

        jFrame.pack();
    }

    private MenuItem newMenuItem(String caption, ActionListener action) {
        MenuItem menuItem = new MenuItem(caption);
        menuItem.addActionListener(action);
        return menuItem;
    }

    public static void main(String args[]) {
        Notepad note = new Notepad();
        note.initialize();
    }
}
