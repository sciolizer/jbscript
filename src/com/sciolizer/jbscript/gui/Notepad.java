package com.sciolizer.jbscript.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Notepad {

    TextArea text;

    Font f;

    String str = " ";

    String str1 = " ", str2 = " ", str3 = " ";
    String str4 = " ";

    String str6 = " ";
    String str7 = " ", str8 = " ", str9 = " ";

    int len1;

    int i = 0;
    int pos1;
    int len;


    public void initialize() {
        final JFrame jFrame = new JFrame("Untitled-Notepad");
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
        Menu edit = new Menu("Edit");
        Menu format = new Menu("Format");
        Menu font = new Menu("Font");
        Menu font1 = new Menu("Font Style");
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
                str4 = " ";
                FileDialog dialog = new FileDialog(jFrame, "Open");
                dialog.setVisible(true);

                str1 = dialog.getDirectory();
                str2 = dialog.getFile();
                str3 = str1 + str2;
                File f = new File(str3);
                try {
                    FileInputStream fobj = new FileInputStream(f);
                    len = (int) f.length();
                    for (int j = 0; j < len; j++) {
                        char str5 = (char) fobj.read();
                        str4 = str4 + str5;

                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                text.setText(str4);
            }
        }));
        file.add(newMenuItem("Save As...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog1 = new FileDialog(jFrame, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);

                str7 = dialog1.getDirectory();
                str8 = dialog1.getFile();
                str9 = str7 + str8;


                str6 = text.getText();
                len1 = str6.length();
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

        edit.add(newMenuItem("Cut (Ctrl+X)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notepad.this.str = text.getSelectedText();
                i = text.getText().indexOf(Notepad.this.str);
                text.replaceRange(" ", i, i + Notepad.this.str.length());
            }
        }));
        edit.add(newMenuItem("Copy (Ctrl+C)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notepad.this.str = text.getSelectedText();
            }
        }));
        edit.add(newMenuItem("Paste (Ctrl+V)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos1 = text.getCaretPosition();
                text.insert(Notepad.this.str, pos1);
            }
        }));

        edit.add(newMenuItem("Delete", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = text.getSelectedText();
                i = text.getText().indexOf(msg);
                text.replaceRange(" ", i, i + msg.length());
            }
        }));
        edit.add(newMenuItem("Select All (Ctrl+A)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strText = text.getText();
                int strLen = strText.length();
                text.select(0, strLen);
            }
        }));

        mbar.add(edit);

        format.add(font);
        format.add(font1);
        format.add(font2);

        ActionListener fontFaceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fontSize = f.getSize();
                int fontStyle = f.getStyle();

                f = new Font(e.getActionCommand(), fontStyle, fontSize);
                text.setFont(f);
            }
        };
        for (String face : Arrays.asList("Courier", "Sans Serif", "Monospaced", "Symbol")) {
            font.add(newMenuItem(face, fontFaceAction));
        }

        font1.add(menuItemFontStyle("Regular", Font.PLAIN));
        font1.add(menuItemFontStyle("Bold", Font.BOLD));
        font1.add(menuItemFontStyle("Italic", Font.ITALIC));
        font1.add(menuItemFontStyle("Bold Italic", Font.BOLD | Font.ITALIC));

        for (int fontSize : new int[]{12, 14, 18, 20}) {
            MenuItem fsize = new MenuItem(String.valueOf(fontSize));
            final int size = fontSize;
            fsize.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String fontName = f.getName();
                    int fontStyle = f.getStyle();

                    f = new Font(fontName, fontStyle, size);
                    text.setFont(f);
                }
            });
            font2.add(fsize);
        }

        mbar.add(format);

        text = new TextArea(26, 60);
        mainpanel.add(text, BorderLayout.CENTER);

        f = new Font("Monotype Corsiva", Font.PLAIN, 15);
        text.setFont(f);

        jFrame.pack();
//        jFrame.addListen
    }

    private MenuItem newMenuItem(String caption, ActionListener action) {
        MenuItem menuItem = new MenuItem(caption);
        menuItem.addActionListener(action);
        return menuItem;
    }

    private MenuItem menuItemFontStyle(String caption, final int fontStyle) {
        MenuItem ret = new MenuItem(caption);
        ret.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String fontName = f.getName();
                int fontSize = f.getSize();

                f = new Font(fontName, fontStyle, fontSize);
                text.setFont(f);
            }
        });
        return ret;
    }


    public static void main(String args[]) {
        Notepad note = new Notepad();
        note.initialize();
    }
}
