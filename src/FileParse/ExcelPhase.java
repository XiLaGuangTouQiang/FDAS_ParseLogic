package FileParse;

import Data.CAS;
import Data.CASData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelPhase {
    public static void getCASmsg() {
        FileInputStream Filepath = null;
        try {
            Filepath = new FileInputStream(FileParse.CASALLEXCELPATH);
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
                    CASData.CASList.add(cas);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
