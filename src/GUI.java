

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GUI {
    public void CreatWindow(){
        Font Ft = new Font("宋体",Font.BOLD,16);
        JFrame jf = new JFrame();
        jf.setLocation(500,300);
        jf.setSize(500,500);
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
                    main.CASALLEXCELPATH = jfChoose.getSelectedFile().getPath();
                }
                XMLBuild xmlbuild = new XMLBuild();
                main.getExcel(main.CASALLEXCELPATH);
                main.ScanExcel();
                main.CasDelICD();
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
                    main.CASEXCELPATH = jfChoose.getSelectedFile().getPath();
                }
                XMLBuild xmlbuild = new XMLBuild();
                main.getCASmsg();
                xmlbuild.CASXMLBuild();
            }
        });

        JTextField  JTextP = new JTextField();
        JTextP.setBounds(100,100,60,20);
        JTextP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    String Value = JTextP.getText();

                    if(!isNumber(Value) ){
                        CreatWarningWindow("ID Warning");
                    }else{
                        CreateICDWindow(Value);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jf.add(JTextP);

        /*Label*/
        Label IDLabel = new Label("Search ID",Label.CENTER);
        IDLabel.setBounds(20,100,80,20);
        IDLabel.setFont(Ft);
        jf.add(IDLabel);


        JButton CASGen = new JButton();
        CASGen.setText("Generate CAS");
        CASGen.setBounds(200,100,80,20);
        CASGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Excel Ex = new Excel();
                Ex.ExcelBuild();
            }
        });
        jf.add(CASGen);

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
        JLabel Llogic = new JLabel("Logic:");
        Llogic.setBounds(20,300,60,20);
        JTextPane  JlogicTP = new JTextPane();
        JlogicTP.setBounds(240,300,100,20);
        JlogicTP.setEditable(false);
        JlogicTP.setText("text");
        jf.add(Llogic);
        jf.add(JlogicTP);
        /*ICD*/
        JLabel LICD = new JLabel("ICD:");
        LICD.setBounds(20,340,60,20);
        JTextPane  JICDTP = new JTextPane();
        JICDTP.setBounds(240,340,100,20);
        JICDTP.setEditable(false);
        JICDTP.setText("text");
        jf.add(LICD);
        jf.add(JICDTP);

        JButton DetailBtn = new JButton("MoreDetail");
        DetailBtn.setBounds(20,360,120,30);
        DetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jf.add(DetailBtn);
        jf.setVisible(true);


    }


    public int CreatWarningWindow(String WarningText){
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
