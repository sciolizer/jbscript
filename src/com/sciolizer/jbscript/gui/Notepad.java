package com.sciolizer.jbscript.gui;

import com.sciolizer.jbscript.DefaultModule;
import com.sciolizer.jbscript.annotation.Inject;
import com.sciolizer.jbscript.lang.Lexer;
import com.sciolizer.jbscript.lang.ast.intelligence.Reformater;
import com.sciolizer.jbscript.lang.parser.ParseFailException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Notepad {

    protected JTextPane mainText;
    protected JTextPane rowHeaderText;
    protected Font f;
    private final Charset charset = Charset.forName("UTF-8");

    @Inject
    protected Reformater reformater;

    public void initialize() {
        final JFrame jFrame = new JFrame("jbscript");
        Dimension dimension = new Dimension(1000, 500);
        jFrame.setPreferredSize(dimension);
        jFrame.pack();
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
                mainText.setText(builder.toString());
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


                String str6 = mainText.getText();
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
                    mainText.setFont(f);
                }
            });
            font2.add(fsize);
        }

        mbar.add(format);

        mainText = new JTextPane();
        JScrollPane jScrollPaneLeft = new JScrollPane(mainText);
        jScrollPaneLeft.setPreferredSize(null);
        mainpanel.add(jScrollPaneLeft, BorderLayout.CENTER);

        mainText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    try {
                        int createdLineOffset = mainText.getCaret().getDot();
                        int createdLine = getLineOfOffset(mainText, createdLineOffset);
                        int previousLineOffset = getLineStartOffset(mainText, createdLine - 1);
                        int len = createdLineOffset - previousLineOffset;
                        String text = mainText.getText(previousLineOffset, len);
                        text = text.substring(0, text.length() - 1); // remove '\n'
                        String replacement;
                        try {
                            replacement = reformater.reformat(text) + '\n'; // add it back
                        } catch (ParseFailException | Lexer.LexFailException e1) {
                            setHeader(createdLine - 1, "error");
//                            rowHeaderText.getStyledDocument().insertString(0, e1.getMessage(), new SimpleAttributeSet());
                            return;
                        }
//                        StyledDocument styledDocument = mainText.getStyledDocument();
//                        styledDocument.remove(previousLineOffset, len);
//                        styledDocument.insertString(previousLineOffset, replacement, new SimpleAttributeSet());
                        setHeader(createdLine - 1, replacement);
                    } catch (BadLocationException ble) {
                        System.err.println("offsetRequested: " + ble.offsetRequested());
                        ble.printStackTrace();
                        throw new UndeclaredThrowableException(ble);
                    }

                }
            }
        });

        rowHeaderText = new JTextPane();

        jScrollPaneLeft.setRowHeaderView(rowHeaderText);

        f = new Font("Monospaced", Font.PLAIN, 15);
        mainText.setFont(f);
        rowHeaderText.setFont(f);
        rowHeaderText.setBackground(Color.LIGHT_GRAY);

        jFrame.pack();
    }

    protected void setHeader(int line, String header) {
        StringBuilder newText = new StringBuilder();
        String[] split = rowHeaderText.getText().split("\n");
        for (int i = 0; i <= line || i < split.length; i++) {
            if (i == line) {
                newText.append(header).append('\n');
            } else {
                if (i < split.length) {
                    newText.append(split[i]).append('\n');
                } else {
                    newText.append('\n');
                }
            }
        }
        String newValue = newText.toString();
        rowHeaderText.setText(newValue);
    }

    static int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException {
        Document doc = comp.getDocument();
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    private MenuItem newMenuItem(String caption, ActionListener action) {
        MenuItem menuItem = new MenuItem(caption);
        menuItem.addActionListener(action);
        return menuItem;
    }

    public static void main(String[] args) {
        new DefaultModule().getNotepad();
    }
}
