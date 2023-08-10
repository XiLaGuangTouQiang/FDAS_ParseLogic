package Data;
import java.util.ArrayList;
import java.util.List;

public class CASData {
    public static ArrayList<CAS> FCasList = new ArrayList<>();
    public static List<LogicName> LogicNameList = new ArrayList<>();
    public static List<CAS> CASList = new ArrayList<>();

    public static void CasDelICD(){
        for(int i=0;i<CASData.CASList.size();i++){
            for(int j=0;j<CASData.CASList.get(i).LogicNameList.size();j++){
                for(int k=j;k<CASData.CASList.get(i).LogicNameList.size();k++){
                    if(CASData.CASList.get(i).LogicNameList.get(j).ICDName.equals(CASData.CASList.get(i).LogicNameList.get(k).ICDName) && j!=k){
                        CASData.CASList.get(i).LogicNameList.remove(k);
                        if(k-j != 1){
                            k--;
                        }
                    }
                }
            }
            for(int j=0;j<CASData.CASList.get(i).LogicNameList.size();j++) {
                for (int k = j; k < CASData.CASList.get(i).LogicNameList.size(); k++) {
                    if (CASData.CASList.get(i).LogicNameList.get(j).ICDName.equals(CASData.CASList.get(i).LogicNameList.get(k).ICDName) && j != k) {
                        CASData.CASList.get(i).LogicNameList.remove(k);
                        if (k - j != 1) {
                            k--;
                        }
                    }
                }
            }
        }
    }
}
