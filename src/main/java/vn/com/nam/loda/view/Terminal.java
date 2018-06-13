package vn.com.nam.loda.view;

import javax.swing.*;

public class Terminal extends JTextArea {
    public Terminal(){
        super();
    }

    public Terminal(String s) {
        super(s);
    }

    public Terminal(int i, int i1) {
        super(i, i1);
    }

    @Override
    public void append(String s) {
        super.append(s+"\n");
    }
}
