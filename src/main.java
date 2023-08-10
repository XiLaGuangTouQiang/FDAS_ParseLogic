
import GUI.*;
import DB.*;
import javax.swing.*;
import java.io.*;




public class main {

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }
        MainFrame JF = new MainFrame();
        File f = new File(".\\CasDB.bin");
        if(f.exists()){
            DB db = new DB();
            try {
                db.DBParse();
            }catch (Exception e){
                WarningPanel.CreatWarningWindow("CAS Load Failed !" + e);
            }
            //JF.CreatWarningWindow("CAS Has Load!");
        }else {
            WarningPanel.CreatWarningWindow("CAS isnt Exist,please Updata!");
        }
        JF.CreatWindow();
    }
}
