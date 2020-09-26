package test_package;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCreator {
    int x=0;
    int y=0;
    public FileCreator(){
    }
    public void newSave(String name,String number,String Lot,FormulaEditorArea formulaEditorArea){
        try{
            Files.createDirectories(Path.of(".//Date"));
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"路徑讀取失敗");
        }
        try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                        new File(".//Date//配方表編輯紀錄 - " + name + "-" + number + "-" + Lot +".txt")));
                bufferedWriter.write(name);
                bufferedWriter.newLine();
                bufferedWriter.write(number);
                for (int i = 0 ; i < formulaEditorArea.getModel().getRowCount(); i++){
                    bufferedWriter.newLine();
                    bufferedWriter.write(formulaEditorArea.getValueAt(i,0).toString() + "," + formulaEditorArea.getValueAt(i,1).toString()
                     + "," + formulaEditorArea.getValueAt(i,2).toString() + "," + formulaEditorArea.getValueAt(i,3).toString());
                }
                bufferedWriter.close();
        }catch (Exception NotAllowPath ){

            JOptionPane.showMessageDialog(null,"讀取失敗");

        }
    }

    public void loadSave (PanelFomulaEditor jPanel) throws IOException {
        JFileChooser jFileChooser = new JFileChooser(".//Date");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("文件檔案","txt"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        int res = jFileChooser.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
            String str = bufferedReader.readLine();
            jPanel.name.setText(str);
            str = bufferedReader.readLine();
            jPanel.num1.setText(str);
            int count = 0;
            str = bufferedReader.readLine();
            while(str != null){
                jPanel.formulaEditorArea.TbMod.addRow(new Object[]{"請選擇","","","0".toString()});
                String[] temp = str.split(",");
                jPanel.formulaEditorArea.setValueAt(temp[0],count,0);
                jPanel.formulaEditorArea.setValueAt(temp[1],count,1);
                jPanel.formulaEditorArea.setValueAt(temp[2],count,2);
                jPanel.formulaEditorArea.setValueAt(temp[3],count,3);
                str = bufferedReader.readLine();
                count++;
                try{
                    double tatolPercent = 0;
                    for (int ex = 0;ex < jPanel.formulaEditorArea.getRowCount();ex++){
                        double cur = Double.parseDouble(jPanel.formulaEditorArea.getValueAt(ex,3).toString());
                        tatolPercent += cur;
                    }
                    jPanel.percent.setText(tatolPercent + "%");
                }catch(Exception notNumber){
                    JOptionPane.showMessageDialog(null,"請輸入數字","Message",JOptionPane.ERROR_MESSAGE);
                }
            }
            jPanel.formulaEditorArea.TbMod.removeRow(count);
        }

    }

    public void newFile(String fileName, FormulaEditorArea formulaEditorArea) {

        try{
            Files.createDirectories(Path.of(".//Output"));
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"路徑讀取失敗");
        }

        //新增檔案
        XSSFWorkbook wb = new XSSFWorkbook();
        //內容字形
        XSSFFont xssfFont1 = wb.createFont();
        xssfFont1.setFontHeight(16);
        xssfFont1.setFontName("微軟正黑體");
        XSSFCellStyle xssfCellStyle1 = wb.createCellStyle();
        xssfCellStyle1.setFont(xssfFont1);
        //標題字形
        XSSFFont xssfFont2 = wb.createFont();
        xssfFont2.setFontHeight(20);
        xssfFont2.setFontName("新細明體");
        XSSFCellStyle xssfCellStyle2 = wb.createCellStyle();
        xssfCellStyle2.setFont(xssfFont2);
        xssfCellStyle2.setAlignment(HorizontalAlignment.CENTER); //左右置中
        xssfCellStyle2.setBorderBottom(BorderStyle.THIN);
        //下框線
        XSSFCellStyle xssfCellStyle3 = wb.createCellStyle();
        xssfCellStyle3.setFont(xssfFont1);
        xssfCellStyle3.setBorderBottom(BorderStyle.THIN);


        //新增分頁
        XSSFSheet sheet = wb.createSheet("新的分頁");
        //設定欄寬
        sheet.setColumnWidth(0,3*256);
        sheet.setColumnWidth(1,4*256);
        sheet.setColumnWidth(2,6*256);
        sheet.setColumnWidth(3,43*256);
        sheet.setColumnWidth(4,7*256);
        sheet.setColumnWidth(5,2*256);
        sheet.setColumnWidth(6,3*256);
        sheet.setColumnWidth(7,8*256);
        sheet.setColumnWidth(8,2*256);
        sheet.setColumnWidth(9,3*256);
        sheet.setColumnWidth(10,8*256);
        sheet.setColumnWidth(11,3*256);

        //新增行
        XSSFRow currRow = sheet.createRow(1);

        //新增儲存格
        XSSFCell currCell;
        //設定內容
        for (int rw = 0;rw <= 11;rw++) {
            currCell = currRow.createCell(rw);
            currCell.setCellStyle(xssfCellStyle2);
        }
        currCell = sheet.getRow(1).getCell(0);
        currCell.setCellValue("打樣配方單");
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,11));
        currRow.setHeight((short)(28*20));

        //設定字形
        currRow = sheet.createRow(2);
        currCell = currRow.createCell(0);
        currCell.setCellValue(516);
        currCell = currRow.createCell(2);
        currCell.setCellValue("跳躍");
        currCell.setCellStyle(xssfCellStyle1);

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(".//Output//" + fileName + ".xlsx"));
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch (Exception errorPath){
            System.out.println("路徑錯誤");
        }
    }

}
