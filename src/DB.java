
import java.io.*;
import java.util.ArrayList;

public class DB {
    static byte[] NewDB;
    static byte[] CasDB;
    static ArrayList<byte[]> CASDB = new ArrayList<>();
    public void DBCreat() throws IOException {
/*        File f = new File("C:\\Users\\206226\\Desktop\\迎角限制.bin");
        DataInputStream read = new DataInputStream(new FileInputStream(f));
        NewDB = new byte[(int)f.length()];
        read.read(NewDB);
        System.out.printf("1");*/
        int offset = 0;
        for(int i=0;i<main.CASList.size();i++){
            int CASlen = 0;
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
            offset += CASlen;
        }
        FileOutputStream f2 = new FileOutputStream(".\\迎角限制2.bin");
        BufferedOutputStream BF = new BufferedOutputStream(f2);
        DataOutputStream write = new DataOutputStream(BF);
        /*write.write(NewDB);*/
        write.close();
        BF.close();
        f2.close();
    }
    public byte[] IntToByte(int num){
        byte[] src = new byte[4];
        src[0] =  (byte) (num & 0xFF);
        src[1] =  (byte) ((num>>8) & 0xFF);
        src[2] =  (byte) ((num>>16) & 0xFF);
        src[3] =  (byte) ((num>>24) & 0xFF);
        return  src;
    }
}
