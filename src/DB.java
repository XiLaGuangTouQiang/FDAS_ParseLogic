
import java.io.*;

public class DB {
    static byte[] NewDB;
    static byte[] CasDB;
    public void DBCreat() throws IOException {
/*        File f = new File("C:\\Users\\206226\\Desktop\\迎角限制.bin");
        DataInputStream read = new DataInputStream(new FileInputStream(f));
        NewDB = new byte[(int)f.length()];
        read.read(NewDB);
        System.out.printf("1");*/



        for(int i=0;i<main.CASList.size();i++){
            byte[] CASID = IntToByte(main.CASList.get(i).CASID);
            byte[] CASName = main.CASList.get(i).CASLogic.getBytes();
            for (int j=0;j<main.CASList.get(i).LogicNameList.size();j++){

            }
            //byte[] LogicName = main.CASList.get(i).LogicNameList.get(i);
            int offset = CASID.length + CASName.length;
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
