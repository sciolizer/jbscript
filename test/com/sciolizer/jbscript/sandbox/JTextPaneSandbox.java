package com.sciolizer.jbscript.sandbox;

import org.junit.Test;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

// First created by jball on 8/24/13 at 12:34 PM
public class JTextPaneSandbox {
    @Test
    public void testDemoTextPane() throws Exception {
        JTextPane jTextPane = new JTextPane();
        JScrollPane jScrollPane = new JScrollPane(jTextPane);
        display(jScrollPane);
    }

    @Test
    public void testAttributeSet() throws Exception {
        JTextPane jTextPane = new JTextPane();
        StyledDocument styledDocument = jTextPane.getStyledDocument();
        SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(simpleAttributeSet, Color.RED);
        styledDocument.insertString(0, "start", simpleAttributeSet);
        SimpleAttributeSet simpleAttributeSet1 = new SimpleAttributeSet();
        StyleConstants.setForeground(simpleAttributeSet1, Color.BLACK);
        styledDocument.insertString(0, "black", simpleAttributeSet1);
        JScrollPane jScrollPane = new JScrollPane(jTextPane);
        display(jScrollPane);
    }

    @Test
    public void testGuiHeight() throws Exception {
        final JTextPane jTextPane = new JTextPane();
        jTextPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int dot = e.getDot();
                Rectangle rectangle;
                try {
                    rectangle = jTextPane.getUI().modelToView(jTextPane, dot);
                } catch (BadLocationException e1) {
                    throw new UndeclaredThrowableException(e1);
                }
                System.out.println(rectangle);
            }
        });
        JScrollPane jScrollPaneLeft = new JScrollPane(jTextPane);

        display(jScrollPaneLeft);
    }

    @Test
    public void testLargeRowHeader() throws Exception {
        JTextPane mainText = new JTextPane();
        JScrollPane jScrollPane = new JScrollPane(mainText);

        JTextPane copyText = new JTextPane();
        copyText.setStyledDocument(mainText.getStyledDocument());
        copyText.setEditable(false);

        jScrollPane.setRowHeaderView(copyText);
        display(jScrollPane);
    }

    @Test
    public void testCustomLayout() throws Exception {
        final Container container = new Container();
        container.setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String name, Component comp) {
                System.out.println("addLayoutComponent: " + name + ", " + comp);
            }

            @Override
            public void removeLayoutComponent(Component comp) {
                System.out.println("removeLayoutComponent: " + comp);
            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                System.out.println("preferredLayoutSize: " + parent);
                return new Dimension(100, 100);
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                System.out.println("minimumLayoutSize: " + parent);
                return new Dimension(50, 50);
            }

            @Override
            public void layoutContainer(Container parent) {
                System.out.println("layoutContainer: " + parent);
                int y = 0;
                for (Component component : parent.getComponents()) {
                    component.setLocation(0, y);
                    component.setSize(20, 10);
                    y += 10;
                }
            }
        });

        JButton jButton = new JButton("+");
        final AtomicInteger counter = new AtomicInteger();
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.add(new JLabel("" + counter.getAndIncrement()));
                container.revalidate();
            }
        });
        container.add(jButton);

        display(container);

    }

    protected void display(Component component) throws InterruptedException {
        JFrame jFrame = new JFrame("myJFrame");

        BorderLayout borderLayout = new BorderLayout();
        Container contentPane = jFrame.getContentPane();
        contentPane.setLayout(borderLayout);
        contentPane.add(component, BorderLayout.CENTER);

        jFrame.pack();

        jFrame.setSize(200, 200);
        jFrame.setVisible(true);
        final CountDownLatch cdl = new CountDownLatch(1);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cdl.countDown();
            }
        });
        cdl.await();
    }
}
