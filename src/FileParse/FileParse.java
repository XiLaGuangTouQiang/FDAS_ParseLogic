package FileParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import Data.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileParse {
    public static String CASALLEXCELPATH = new String();
    public static String CASEXCELPATH = new String();


    public static void getExcel(String path) {
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    getExcel(files[i].getPath());
                }else{
                    FileData.allexcelpath.add(files[i].getPath());
                }
            }
        }else{
            FileData.allexcelpath.add(file.getPath());
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
                    FileData.allfilepathclass.add(files[i].getPath());
                }
            }
        }else{
            FileData.allfilepathclass.add(file.getPath());
        }
    }

    public static void ScanExcel() {
        for(int i=0;i<FileData.allexcelpath.size();i++){
            if((FileData.allexcelpath.get(i).endsWith("xlsx") || FileData.allexcelpath.get(i).endsWith("xls"))&& !FileData.allexcelpath.get(i).contains("~$") ){
                try {
                    FileInputStream Filepath = new FileInputStream(FileData.allexcelpath.get(i));
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
                            CASData.CASList.add(cas);
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
                                CASData.LogicNameList.add(LN);
                            }else{
                                System.out.printf(LN.LogicName);
                            }
                        }catch (Exception e){
                            System.out.printf(FileData.allexcelpath.get(i) + "\n");
                        }
                    }
                    for(int j=0;j<CASData.CASList.size();j++){

                        for(int l=0;l<CASData.LogicNameList.size();l++){
                            if(CASData.CASList.get(j).CASLogic.contains(CASData.LogicNameList.get(l).LogicName)){
                                CASData.CASList.get(j).LogicNameList.add(CASData.LogicNameList.get(l));
                            }
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.printf(FileData.allexcelpath.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.printf(FileData.allexcelpath.get(i));
                }
            }
        }
    }
}
