package test_package;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class MaterialDateManager {
    XSSFWorkbook materialDate;
    List<String> materialList = new ArrayList<>();
    XSSFSheet currentSheet;

    public MaterialDateManager(){
        renewList();
    }


    //從編號讀取原料名稱
    public String[] findMaterialInfo(Object numbering) {
        for (int rw = currentSheet.getFirstRowNum()+1; rw <= currentSheet.getLastRowNum();rw++) {
            if (currentSheet.getRow(rw).getCell(0).getStringCellValue().isBlank()) continue;
            if (currentSheet.getRow(rw).getCell(0).getStringCellValue().equals(numbering)) {
                currentSheet.getRow(rw).getCell(1).setCellType(CellType.STRING);
                currentSheet.getRow(rw).getCell(2).setCellType(CellType.STRING);
                currentSheet.getRow(rw).getCell(3).setCellType(CellType.STRING);
                currentSheet.getRow(rw).getCell(4).setCellType(CellType.STRING);
                currentSheet.getRow(rw).getCell(5).setCellType(CellType.STRING);
                return new String[]{currentSheet.getRow(rw).getCell(3).getStringCellValue(),
                        currentSheet.getRow(rw).getCell(4).getStringCellValue()};
            }
        }
        return new String[]{"",""};
    }


    //更新清單
    public void renewList(){
        materialList.clear();
        try{
            FileInputStream fileInputStream = new FileInputStream(".//Date//MaterialDate.xlsx");
            materialDate = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();
            //讀取原料清單
            currentSheet = materialDate.getSheetAt(0);
            System.out.println(currentSheet.getFirstRowNum()+1);
            for (int rw = currentSheet.getFirstRowNum()+1; rw <= currentSheet.getLastRowNum();rw++) {
                materialList.add(currentSheet.getRow(rw).getCell(0).getStringCellValue());
            }
            //materialDate.close(); 關閉後會無法寫入
        }catch (Exception dateLost){
            JOptionPane.showMessageDialog(null,"遺失資料庫：MaterialDate.xlsx","Message",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }

    //更新原料
    public void updateMaterial(String num,String ori,String name,String ch,String en,String per,boolean exit){
        XSSFRow row = currentSheet.getRow(currentSheet.getLastRowNum());
        if (exit){
            for (int rw=currentSheet.getFirstRowNum()+1;rw<currentSheet.getLastRowNum();rw++){
                if (currentSheet.getRow(rw).getCell(0).getStringCellValue().equals(num)){
                    row = currentSheet.getRow(rw);
                    break;
                }
            }
        }else{
            row = currentSheet.createRow(currentSheet.getLastRowNum()+1);
        }
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(num);
        cell = row.createCell(1);
        cell.setCellValue(ori);
        cell = row.createCell(2);
        cell.setCellValue(name);
        cell = row.createCell(3);
        cell.setCellValue(ch);
        cell = row.createCell(4);
        cell.setCellValue(en);
        cell = row.createCell(5);
        cell.setCellValue(per);
        try {
            File file = new File(".//Date//MaterialDate.xlsx");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            materialDate.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        renewList();
        System.out.println("成功");
    }

}
