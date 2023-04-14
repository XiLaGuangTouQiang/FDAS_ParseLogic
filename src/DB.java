
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DB {
    static byte[] NewDB;
    static byte[] CasDB;

    static ArrayList<byte[]> CASDB = new ArrayList<>();
    public void DBCreat() throws IOException {
        int CASlen = 0;
        byte[] CASNum = IntToByte(main.CASList.size());
        CASDB.add(CASNum);
        CASlen += 4;
        for(int i=0;i<main.CASList.size();i++){
            byte[] CASID = IntToByte(main.CASList.get(i).CASID);
            CASDB.add(CASID);
            CASlen += 4;
            byte[] CASName = main.CASList.get(i).CASLogic.getBytes();
            byte[] CASNameLen = IntToByte(CASName.length);
            CASDB.add(CASNameLen);
            CASlen += 4;
            CASDB.add(CASName);
            CASlen += CASNameLen.length;
            byte[] LogicNameNum = IntToByte(main.CASList.get(i).LogicNameList.size());
            CASDB.add(LogicNameNum);
            CASlen += 4;
            for (int j=0;j<main.CASList.get(i).LogicNameList.size();j++){
                byte[] LogicName = main.CASList.get(i).LogicNameList.get(j).LogicName.getBytes();
                byte[] LogicNameLen = IntToByte(LogicName.length);
                CASDB.add(LogicNameLen);
                CASlen += 4;
                CASDB.add(LogicName);
                CASlen += LogicName.length;
                byte[] ICDName = main.CASList.get(i).LogicNameList.get(j).ICDName.getBytes();
                byte[] ICDNameLen = IntToByte(ICDName.length);
                CASDB.add(ICDNameLen);
                CASlen += 4;
                CASDB.add(ICDName);
                CASlen += ICDName.length;
                byte[] DataType = main.CASList.get(i).LogicNameList.get(j).DataType.getBytes();
                byte[] DataTypeLen = IntToByte(DataType.length);
                CASDB.add(DataTypeLen);
                CASlen += 4;
                CASDB.add(DataType);
                CASlen += DataType.length;
                byte[] Comment = main.CASList.get(i).LogicNameList.get(j).Comment.getBytes();
                byte[] CommentLen = IntToByte(Comment.length);
                CASDB.add(CommentLen);
                CASlen += 4;
                CASDB.add(Comment);
                CASlen += Comment.length;
            }
            //byte[] LogicName = main.CASList.get(i).LogicNameList.get(i);
        }

        FileOutputStream f2 = new FileOutputStream(".\\CasDB.bin");
        BufferedOutputStream BF = new BufferedOutputStream(f2);
        DataOutputStream write = new DataOutputStream(BF);
        for(int i=0;i<CASDB.size();i++){
            write.write(CASDB.get(i));
        }
        write.close();
        BF.close();
        f2.close();
    }
    public void DBParse() throws IOException {
        File f = new File(".\\CasDB.bin");
        DataInputStream read = new DataInputStream(new FileInputStream(f));
        NewDB = new byte[(int)f.length()];
        read.read(NewDB);
        int offset = 0;
        int CASNum = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
        offset += 4;
        for(int i=0;i<CASNum;i++){
            CAS Fcas = new CAS();
            Fcas.CASID = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
            offset += 4;
            int len = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
            offset += 4;
            Fcas.CASLogic = ByteToString(NewDB,len,offset);
            offset += len;
            int num = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
            offset += 4;
            for(int j=0;j<num;j++){
                LogicName LN = new LogicName();
                len = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
                offset += 4;
                LN.LogicName = ByteToString(NewDB,len,offset);
                offset += len;
                len = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
                offset += 4;
                LN.ICDName = ByteToString(NewDB,len,offset);
                offset += len;
                len = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
                offset += 4;
                LN.DataType = ByteToString(NewDB,len,offset);
                offset += len;
                len = ByteToInt(Arrays.copyOfRange(NewDB,offset,offset + 4));
                offset += 4;
                LN.Comment = ByteToString(NewDB,len,offset);
                offset += len;
                Fcas.LogicNameList.add(LN);
            }
            main.FCasList.add(Fcas);
        }
    }
    public byte[] IntToByte(int num){
        byte[] src = new byte[4];
        src[3] =  (byte) (num & 0xFF);
        src[2] =  (byte) ((num>>8) & 0xFF);
        src[1] =  (byte) ((num>>16) & 0xFF);
        src[0] =  (byte) ((num>>24) & 0xFF);
        return  src;
    }

    private int ByteToInt(byte[] tempdata){
        int res = 0;
        int len = tempdata.length;
        for(int i =0; i<len;i++){
            int data = minus2plus((int)tempdata[i]);
            res+=(data)<<((len-i-1)*8);
        }
        return res;
    }

    private int minus2plus(int val){
        val = val<0? (val+256):val;
        return val;
    }

    private String ByteToString(byte[] tempdata, int len, int offset){
        StringBuilder temp1 = new StringBuilder();
        for(int i=0;i<len;i++){
            int b = ByteToInt(Arrays.copyOfRange(tempdata,offset+i,offset+i+1));
            if (b!=0)
                temp1.append((char)b);
        }
        String ConditionText = temp1.toString();
        return ConditionText;
    }
}
