package com.sciolizer.jbscript.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: jball
 * Date: 8/17/13
 * Time: 8:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class trail extends JFrame {

        MenuBar mbar;
        Menu file,edit,format,font,font1,font2;

        JPanel mainpanel;
        TextArea text;

        Font f;
        String months[]={
                "Jan","Feb","Mar","Apr",
                "May","Jun","Jul","Aug",
                "Sep","Oct","Nov","Dec"};

        GregorianCalendar gcalendar;


        String command=" ";
        String str=" ";

        String str1=" ",str2=" ",str3=" ";
        String str4=" ";

        String str6=" ";
        String str7=" ",str8=" ",str9=" ";

        int len1;

        int i=0;
        int pos1;
        int len;

        public trail(String str)
        {

            super(str);

            mainpanel=new JPanel();
            mainpanel=(JPanel)getContentPane();
            mainpanel.setLayout(new FlowLayout());


            mbar=new MenuBar();
            setMenuBar(mbar);

            file=new Menu("File");
            edit=new Menu("Edit");
            format=new Menu("Format");
            font=new Menu("Font");
            font1=new Menu("Font Style");
            font2=new Menu("Size");

            addMenuChild(file, "New...", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    trail note1 = new trail("Untitled-Notepad");
                    note1.setSize(500, 500);
                    note1.setVisible(true);
                }
            });
            addMenuChild(file, "Open", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    str4 = " ";
                    FileDialog dialog = new FileDialog(trail.this, "Open");
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
            });
            addMenuChild(file, "Save As...", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        if (command.equals("Save As...")) {
                            FileDialog dialog1 = new FileDialog(trail.this, "Save As", FileDialog.SAVE);
                            dialog1.setVisible(true);

                            str7 = dialog1.getDirectory();
                            str8 = dialog1.getFile();
                            str9 = str7 + str8;


                            str6 = text.getText();
                            len1 = str6.length();
                            byte buf[] = str6.getBytes();

                            File f1 = new File(str9);
                            FileOutputStream fobj1 = new FileOutputStream(f1);
                            for (int k = 0; k < len1; k++) {
                                fobj1.write(buf[k]);
                            }
                            fobj1.close();
                        }

                        trail.this.setTitle(str8);

                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }

                }
            });
            addMenuChild(file, "Exit", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            mbar.add(file);


            addMenuChild(edit, "Cut (Ctrl+X)", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    trail.this.str = text.getSelectedText();
                    i = text.getText().indexOf(trail.this.str);
                    text.replaceRange(" ", i, i + trail.this.str.length());
                }
            });
            addMenuChild(edit, "Copy (Ctrl+C)", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    trail.this.str = text.getSelectedText();
                }
            });
            addMenuChild(edit, "Paste (Ctrl+V)", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pos1=text.getCaretPosition();
                    text.insert(trail.this.str,pos1);
                }
            });

            addMenuChild(edit, "Delete", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String msg = text.getSelectedText();
                    i = text.getText().indexOf(msg);
                    text.replaceRange(" ", i, i + msg.length());
                }
            });
            addMenuChild(edit, "Select All (Ctrl+A)", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String strText = text.getText();
                    int strLen = strText.length();
                    text.select(0, strLen);
                }
            });
            addMenuChild(edit, "Time/Date", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gcalendar=new GregorianCalendar();
                    String h=String.valueOf(gcalendar.get(Calendar.HOUR));
                    String m=String.valueOf(gcalendar.get(Calendar.MINUTE));
                    String s=String.valueOf(gcalendar.get(Calendar.SECOND));
                    String date=String.valueOf(gcalendar.get(Calendar.DATE));
                    String mon=months[gcalendar.get(Calendar.MONTH)];
                    String year=String.valueOf(gcalendar.get(Calendar.YEAR));
                    String hms="Time"+" - "+h+":"+m+":"+s+" Date"+" - "+date+" "+mon+" "+year;
                    int loc=text.getCaretPosition();
                    text.insert(hms,loc);
                }
            });
            mbar.add(edit);

            format.add(font);
            format.add(font1);
            format.add(font2);

            addMenuChild(font, "Courier", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String fontName = f.getName();
                    String fontFamily = f.getFamily();
                    int fontSize = f.getSize();
                    int fontStyle = f.getStyle();

                    f = new Font("Courier", fontStyle, fontSize);
                    text.setFont(f);
                }
            });

            ActionListener fontFaceAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String fontName=f.getName();
                    String fontFamily=f.getFamily();
                    int fontSize=f.getSize();
                    int fontStyle=f.getStyle();

                    f=new Font(e.getActionCommand(),fontStyle,fontSize);
                    text.setFont(f);
                }
            };
            for (String face : Arrays.asList("Sans Serif", "Monospaced", "Symbol")) {
                addMenuChild(font, face, fontFaceAction);
            }

            font1.add(menuItemFontStyle("Regular", Font.PLAIN));
            font1.add(menuItemFontStyle("Bold", Font.BOLD));
            font1.add(menuItemFontStyle("Italic", Font.ITALIC));
            font1.add(menuItemFontStyle("Bold Italic", Font.BOLD|Font.ITALIC));

            for (int fontSize : new int[]{12, 14, 18, 20}) {
                MenuItem fsize = new MenuItem(String.valueOf(fontSize));
                final int size = fontSize;
                fsize.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String fontName=f.getName();
                        String fontFamily=f.getFamily();
                        int fontSize=f.getSize();
                        int fontStyle=f.getStyle();

                        f=new Font(fontName,fontStyle,size);
                        text.setFont(f);
                    }
                });
                font2.add(fsize);
            }

            mbar.add(format);

            text=new TextArea(26,60);
            mainpanel.add(text);

            f=new Font("Monotype Corsiva",Font.PLAIN,15);
            text.setFont(f);
        }

    private void addMenuChild(Menu parent, String caption, ActionListener action) {
        MenuItem menuItem = new MenuItem(caption);
        parent.add(menuItem);
        menuItem.addActionListener(action);
    }

    private MenuItem menuItemFontStyle(String caption, final int fontStyle) {
        MenuItem ret = new MenuItem(caption);
        ret.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String fontName=f.getName();
                String fontFamily=f.getFamily();
                int fontSize=f.getSize();
//                int fontStyle=f.getStyle();

                f=new Font(fontName,fontStyle,fontSize);
                text.setFont(f);
            }
        });
        return ret;
    }


    public static void main(String args[])
    {
        trail note = new trail("Untitled-Notepad");
        note.setSize(500,500);
        note.setVisible(true);
    }
}
