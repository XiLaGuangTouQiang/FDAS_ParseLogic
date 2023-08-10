package GUI;

import Data.CAS;
import Data.CASData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ICDPanel {
    public static void CreatSignalWindow(String Signal){
        Font Ft = new Font("宋体",Font.PLAIN,16);
        JFrame jfSignal = new JFrame();

        jfSignal.setFont(Ft);
        jfSignal.setLayout(null);
        jfSignal.setTitle("Warning");
        jfSignal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfSignal.setAlwaysOnTop(true);
        ImageIcon SignalIcon = new ImageIcon("./src/FDAS_ParseLogic_Icon.PNG");
        jfSignal.setIconImage(SignalIcon.getImage());
        ArrayList<CAS> SameCasList = new ArrayList<>();
        for (int i = 0; i< CASData.FCasList.size(); i++)
        {
            if(CASData.FCasList.get(i).CASLogic.contains(Signal))
            {
                SameCasList.add(CASData.FCasList.get(i));
            }
        }
        for(int i=0;i<SameCasList.size();i++)
        {
            JTextField SCas = new JTextField();
            SCas.setBounds(10,10 + i*20,50,20);
            SCas.setText(Integer.toString(SameCasList.get(i).CASID));
            jfSignal.add(SCas);
        }
        jfSignal.setBounds(500,300,150,Math.min((SameCasList.size()/2)*50, 150));
        jfSignal.setSize(300,150);
        jfSignal.setLocation(500,300);
        jfSignal.setVisible(true);
    }
}
