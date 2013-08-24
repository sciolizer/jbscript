package com.sciolizer.jbscript.sandbox;

import org.junit.Test;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

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
