
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class main {
    static List<String> allfilepathclass = new ArrayList<>();
    static List<String> allexcelpath = new ArrayList<>();
    static List<LogicName> LogicNameList = new ArrayList<>();
    static List<CAS> CASList = new ArrayList<>();
    static String CASALLEXCELPATH = new String();
    static String CASEXCELPATH = new String();
    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }


        GUI JF = new GUI();
        JF.CreatWindow();

    }


    public static void getCASmsg() {
        FileInputStream Filepath = null;
        try {
            Filepath = new FileInputStream(CASALLEXCELPATH);
            XSSFWorkbook wb = new XSSFWorkbook(Filepath);
            XSSFSheet sheet = wb.getSheet("FDAS Alert Definition");
            for(int i=2;i<sheet.getPhysicalNumberOfRows();i++){
                CAS cas = new CAS();
                try{
                    cas.CASID = (int)Float.parseFloat(sheet.getRow(i).getCell(1).toString());
                }catch (Exception e){
                    cas.CASID = -1;
                }
                cas.CASLogic = sheet.getRow(i).getCell(14).toString();
                if(cas.CASID>=0){
                    CASList.add(cas);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void ScanExcel() {
        for(int i=0;i<allexcelpath.size();i++){
            if((allexcelpath.get(i).endsWith("xlsx") || allexcelpath.get(i).endsWith("xls"))&& !allexcelpath.get(i).contains("~$") ){
                try {
                    FileInputStream Filepath = new FileInputStream(allexcelpath.get(i));
                    XSSFWorkbook wb = new XSSFWorkbook(Filepath);
                    XSSFSheet CASsheet = wb.getSheet("FDAS Alert Definition");
                    for(int r=2;r<CASsheet.getPhysicalNumberOfRows();r++){
                        CAS cas = new CAS();
                        try{
                            float ID = Float.parseFloat(CASsheet.getRow(r).getCell(1).toString());
                            cas.CASID = (int) ID;
                            cas.CASLogic = CASsheet.getRow(r).getCell(14).toString();
                        }catch (Exception e) {
                            cas.CASID = -1;
                        }

                        if(cas.CASID>=0){
                            CASList.add(cas);
                        }
                    }

                    XSSFSheet sheet = wb.getSheet("ICD Parameters");
                    XSSFRow Namerow = sheet.getRow(0);
                    int Nnum = 0;
                    int ICDnum = 0;
                    int Typenum = 0;
                    int Comnum = 0;
                    for(int c=0;c<Namerow.getPhysicalNumberOfCells();c++){
                        XSSFCell Ncell = Namerow.getCell(c);
                        if(Ncell.toString().contains("Logic Statements Parameter Alias")){
                            Nnum = c;
                        }
                        if(Ncell.toString().contains("ICD Unique Parameter Name")){
                            ICDnum = c;
                        }
                        if(Ncell.toString().contains("Data Type")){
                            Typenum = c;
                        }
                        if(Ncell.toString().contains("Comment")){
                            Comnum = c;
                        }
                    }

                    for(int r=1;r<sheet.getPhysicalNumberOfRows();r++){
                        LogicName LN = new LogicName();
                        try {
                            LN.LogicName = sheet.getRow(r).getCell(Nnum).toString();
                            LN.ICDName = sheet.getRow(r).getCell(ICDnum).toString();
                            LN.DataType = sheet.getRow(r).getCell(Typenum).toString();
                            LN.Comment = sheet.getRow(r).getCell(Comnum).toString();
                            if(LN.LogicName.length()>0){
                                LogicNameList.add(LN);
                            }else{
                                System.out.printf(LN.LogicName);
                            }
                        }catch (Exception e){
                            System.out.printf(allexcelpath.get(i) + "\n");
                        }
                    }
                    for(int j=0;j<CASList.size();j++){

                        for(int l=0;l<LogicNameList.size();l++){
                            if(CASList.get(j).CASLogic.contains(LogicNameList.get(l).LogicName)){
                                CASList.get(j).LogicNameList.add(LogicNameList.get(l));
                            }
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.printf(allexcelpath.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.printf(allexcelpath.get(i));
                }
            }
        }
    }


    public static void getExcel(String path) {
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    getExcel(files[i].getPath());
                }else{
                    allexcelpath.add(files[i].getPath());
                }
            }
        }else{
            allexcelpath.add(file.getPath());
        }
    }

    public static void CasDelICD(){
        for(int i=0;i<CASList.size();i++){
            for(int j=0;j<CASList.get(i).LogicNameList.size();j++){
                for(int k=j;k<CASList.get(i).LogicNameList.size();k++){
                    if(CASList.get(i).LogicNameList.get(j).ICDName.equals(CASList.get(i).LogicNameList.get(k).ICDName) && j!=k){
                        CASList.get(i).LogicNameList.remove(k);
                        if(k-j != 1){
                            k--;
                        }
                    }
                }
            }
            for(int j=0;j<CASList.get(i).LogicNameList.size();j++) {
                for (int k = j; k < CASList.get(i).LogicNameList.size(); k++) {
                    if (CASList.get(i).LogicNameList.get(j).ICDName.equals(CASList.get(i).LogicNameList.get(k).ICDName) && j != k) {
                        CASList.get(i).LogicNameList.remove(k);
                        if (k - j != 1) {
                            k--;
                        }
                    }
                }
            }
        }
    }

    public static void getfileclass(String path) {
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    getfileclass(files[i].getPath());
                }else{
                    allfilepathclass.add(files[i].getPath());
                }
            }
        }else{
            allfilepathclass.add(file.getPath());
        }
    }
}
