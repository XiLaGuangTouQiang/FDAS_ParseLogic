
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class XMLBuild {
    public XMLBuild(){}
    public void ICDXMLbuild() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("ALLCASICD");
        for(int i = 0; i< main.LogicNameList.size(); i++){
            Element  CAS = root.addElement("ICD");
            CAS.addAttribute("LogicName",main.LogicNameList.get(i).LogicName);
            CAS.addAttribute("ICDName",main.LogicNameList.get(i).ICDName);
            CAS.addAttribute("DataType",main.LogicNameList.get(i).DataType);
            CAS.addAttribute("Comment",main.LogicNameList.get(i).Comment);
        }
        OutputFormat format = new OutputFormat();
        format.setEncoding("utf-8");
        format.setNewlines(true);
        //format.setIndent(true);
        //format.setIndent(" ");
        format.setNewLineAfterDeclaration(false);
        format.setExpandEmptyElements(true);
        try {
            String xmlFileName =main.CASALLEXCELPATH + "\\LogicName.xml";
            File xmlFile= new File(xmlFileName);
            if(!xmlFile.exists())
            {
                xmlFile.createNewFile();
            }

            XMLWriter writer= new XMLWriter(new FileOutputStream(xmlFileName),format);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CASXMLBuild(){
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("ALLCAS");
        for(int i=0;i<main.CASList.size();i++){
            Element  CAS = root.addElement("CAS");
            CAS.addAttribute("CASID", String.valueOf(main.CASList.get(i).CASID));
            CAS.addAttribute("CASLogic", main.CASList.get(i).CASLogic);
            for(int j=0;j<main.CASList.get(i).LogicNameList.size();j++){
                Element ICD = CAS.addElement("ICD");
                ICD.addAttribute("LogicName", main.CASList.get(i).LogicNameList.get(j).LogicName);
                ICD.addAttribute("DataType", main.CASList.get(i).LogicNameList.get(j).DataType);
                ICD.addAttribute("ICDName", main.CASList.get(i).LogicNameList.get(j).ICDName);
                ICD.addAttribute("Comment", main.CASList.get(i).LogicNameList.get(j).Comment);
            }
        }
        OutputFormat format = new OutputFormat();
        format.setEncoding("utf-8");
        format.setNewlines(true);
        //format.setIndent(true);
        //format.setIndent(" ");
        format.setNewLineAfterDeclaration(false);
        format.setExpandEmptyElements(true);
        try {
            String xmlFileName =main.CASALLEXCELPATH + "\\CASName.xml";
            File xmlFile= new File(xmlFileName);
            if(!xmlFile.exists())
            {
                xmlFile.createNewFile();
            }

            XMLWriter writer= new XMLWriter(new FileOutputStream(xmlFileName),format);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
