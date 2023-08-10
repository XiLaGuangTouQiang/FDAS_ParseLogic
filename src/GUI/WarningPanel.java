package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarningPanel {
    public static int CreatWarningWindow(String WarningText){
        Font Ft = new Font("宋体",Font.PLAIN,16);
        JFrame jfwarning = new JFrame();
        jfwarning.setSize(300,150);
        jfwarning.setLocation(500,300);
        jfwarning.setFont(Ft);
        jfwarning.setLayout(null);
        jfwarning.setTitle("Warning");
        jfwarning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfwarning.setAlwaysOnTop(true);
        ImageIcon WarningIcon = new ImageIcon("./src/FDAS_ParseLogic_Icon2.PNG");
        jfwarning.setIconImage(WarningIcon.getImage());
        Label Wtext = new Label(WarningText,Label.CENTER);
        Wtext.setFont(Ft);
        Wtext.setBounds(0,25,300,25);
        JButton Btn = new JButton();
        Btn.setBounds(120,75,50,25);
        Btn.setText("OK");
        Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfwarning.dispose();
            }
        });
        jfwarning.add(Btn);
        jfwarning.add(Wtext);
        jfwarning.setVisible(true);
        return 0;
    }
}
