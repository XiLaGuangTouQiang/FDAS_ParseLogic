package GUI;


import Data.*;
import FileParse.*;
import DB.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;


public class MainFrame {
    static String CasNum = "-1";
    static int CasIndex = -1;
    static int LogicNum = -1;
    public void CreatWindow(){
        CasIndex = -1;
        Font Ft = new Font("宋体",Font.BOLD,16);
        JFrame jf = new JFrame();
        jf.setLocation(500,300);
        jf.setSize(700,500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jf.setTitle("FDAS_ParseLogic");
        ImageIcon imaicon = new ImageIcon("./src/FDAS_ParseLogic_Icon2.PNG");
        jf.setIconImage(imaicon.getImage());
        JPanel barpanel = new JPanel();
        barpanel.setLayout(null);
        barpanel.setBackground(Color.white);
        barpanel.setBounds(0,0,800,25);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Updata");
        JMenuItem item1 = new JMenuItem("Updata CAS Excel");
        JMenuItem item2 = new JMenuItem("Updata CAS Logic");
        menu.add(item1);
        menu.add(item2);
        bar.add(menu);
        bar.setBounds(0,0,60,25);
        bar.setVisible(true);
        barpanel.add(bar);
        jf.add(barpanel);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfChoose = new JFileChooser();
                jfChoose.setApproveButtonText("确定");
                jfChoose.setDialogTitle("选择Excel文件夹");
                jfChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                Component chatFrame = null;
                int result = jfChoose.showOpenDialog(chatFrame);
                if(JFileChooser.APPROVE_OPTION == result){
                    FileParse.CASALLEXCELPATH = jfChoose.getSelectedFile().getPath();
                }
                XMLBuild xmlbuild = new XMLBuild();
                FileParse.getExcel(FileParse.CASALLEXCELPATH);
                FileParse.ScanExcel();
                CASData.CasDelICD();
                DB db = new DB();
                try {
                    db.DBCreat();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                xmlbuild.CASXMLBuild();
                xmlbuild.ICDXMLbuild();
                System.out.printf("Updata CAS Exel");
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfChoose = new JFileChooser();
                jfChoose.setApproveButtonText("确定");
                jfChoose.setDialogTitle("选择CAS汇总Excel文件");
                jfChoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
                Component chatFrame = null;
                int result = jfChoose.showOpenDialog(chatFrame);
                if(JFileChooser.APPROVE_OPTION == result){
                    FileParse.CASEXCELPATH = jfChoose.getSelectedFile().getPath();
                }
                XMLBuild xmlbuild = new XMLBuild();
                ExcelPhase.getCASmsg();
                xmlbuild.CASXMLBuild();
            }
        });

        JTextField  JTextP = new JTextField();
        JTextP.setBounds(100,100,60,20);

        JTextP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }


            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    CasNum = JTextP.getText();
                    if(!isNumber(CasNum) ){
                        WarningPanel.CreatWarningWindow("ID Warning");
                    }else{
                        CasIndex = -1;
                        for(int i = 0; i< CASData.FCasList.size(); i++){
                            if(CASData.FCasList.get(i).CASID == Integer.valueOf(CasNum)){
                                LogicNum = CASData.FCasList.get(i).LogicNameList.size();
                                CasIndex = i;
                                break;
                            }
                        }
                        if(CasIndex == -1){
                            WarningPanel.CreatWarningWindow("ID isnt in DB");
/*                            JTextPane  JlogicTP = new JTextPane();
                            JlogicTP.setBounds(100,200,100,20);
                            JlogicTP.setEditable(false);
                            JlogicTP.setText("LogicName");
                            jf.add(JlogicTP);*/
                        }else {
                            /*LogicNam*/
                            for(int i=0;i<LogicNum;i++){
                                String temp = CASData.FCasList.get(CasIndex).LogicNameList.get(i).LogicName;
                                JTextPane  JlogicNameTP = new JTextPane();
                                JlogicNameTP.setBounds(75,200 + 30*i,temp.length() * 7,20);
                                JlogicNameTP.setEditable(false);
                                JlogicNameTP.setText(CASData.FCasList.get(CasIndex).LogicNameList.get(i).LogicName);
                                jf.add(JlogicNameTP);
                                /*Creat Search Same Signal*/
                                JButton Search = new JButton();
                                Search.setBounds(15,200 + 30*i,120,20);
                                Search.setText("Search");
                                jf.add(Search);
                                Search.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        ICDPanel.CreatSignalWindow(JlogicNameTP.getText());
                                    }
                                });
                            }
                            /*Logic*/
                            JTextPane  JlogicTP = new JTextPane();
                            String Logic = CASData.FCasList.get(CasIndex).CASLogic;
                                JlogicTP.setBounds(250,175 + 30,20 * 7,(Logic.length() / 20) * 20);
                                JlogicTP.setEditable(false);
                                JlogicTP.setText(Logic);
                            jf.add(JlogicTP);
                        }
                    }
                }
                jf.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jf.add(JTextP);
        /*Search CAS*/


        /*Label*/
        Label IDLabel = new Label("Search ID",Label.CENTER);
        IDLabel.setBounds(20,100,80,20);
        IDLabel.setFont(Ft);
        jf.add(IDLabel);


/*        JButton CASGen = new JButton();
        CASGen.setText("Generate CAS");
        CASGen.setBounds(200,100,80,20);
        CASGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Excel Ex = new Excel();
                Ex.ExcelBuild();
            }
        });
        jf.add(CASGen);*/

/*      JTextField JTR = new JTextField();
        JTR.setBounds(0,300,100,20);
        JTR.setEditable(false);
        JTR.setText("text");
        JTextArea JTA = new JTextArea();
        JTA.setBounds(120,300,100,20);
        JTA.setEditable(false);
        JTA.setText("text");
        jf.add(JTA);
        jf.add(JTR);*/
        /*Logic*/
        JLabel LlogicName = new JLabel("Logic");
        LlogicName.setBounds(100,150,60,20);
        LlogicName.setFont(Ft);
        jf.add(LlogicName);
        JLabel LLogic = new JLabel("Logic");
        LLogic.setBounds(300,150,60,20);
        LLogic.setFont(Ft);
        jf.add(LLogic);


/*        *//*ICD*//*
        JLabel LICD = new JLabel("ICD:");
        LICD.setBounds(20,340,60,20);
        JTextPane  JICDTP = new JTextPane();
        JICDTP.setBounds(240,340,100,20);
        JICDTP.setEditable(false);
        JICDTP.setText("text");
        jf.add(LICD);
        jf.add(JICDTP);*/

        JButton DetailBtn = new JButton("MoreDetail");
        DetailBtn.setBounds(200,100,120,30);
        DetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jf.add(DetailBtn);
        jf.setVisible(true);


    }
    public int CreateICDWindow(String str){
        JFrame jfICD = new JFrame("ICD Message");
        jfICD.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfICD.setVisible(true);
        return 0;
    }
    public int CreatDetailwindow(){

        JFrame jfDetail = new JFrame();
        jfDetail.setLayout(null);
        jfDetail.setTitle("Warning");
        jfDetail.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfDetail.setSize(500,300);
        ImageIcon WarningIcon = new ImageIcon("./src/FDAS_ParseLogic_Icon2.PNG");
        jfDetail.setIconImage(WarningIcon.getImage());


        jfDetail.setVisible(true);
        return 0;
    }

    public Boolean isNumber(String str){
        for(char c:str.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }


}
