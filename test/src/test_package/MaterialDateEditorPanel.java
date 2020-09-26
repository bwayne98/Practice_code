package test_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialDateEditorPanel extends JPanel {
    List<JTextField> chList = new ArrayList<>();
    List<JTextField> enList = new ArrayList<>();
    List<JTextField> percent = new ArrayList<>();
    List<JButton> removeButton = new ArrayList<>();
    int currentY = 140;
    JLabel jLabel1 = new JLabel("編號：");
    JLabel jLabel2 = new JLabel("原料廠商：");
    JLabel jLabel3 = new JLabel("原料名稱：");
    JLabel jLabel4 = new JLabel("中文名稱");
    JLabel jLabel5 = new JLabel("INCI Name");
    JLabel jLabel6 = new JLabel("占比");

    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField();

    JButton jButton1 = new JButton("讀取");

    JButton jButton2 = new JButton("更新/新增");

    JButton jButton3 = new JButton("增加欄位");
    JButton jButton4 = new JButton("清空內容");

    Font font1 = new Font("微軟正黑體",Font.PLAIN,16);




    public MaterialDateEditorPanel(MaterialDateManager materialDateManager,FormulaEditorArea formulaEditorArea){
        setLayout(null);
        jLabel1.setBounds(70,50,50,30);
        jLabel1.setFont(font1);
        jTextField1.setBounds(120,50,50,30);
        jTextField1.setFont(font1);
        jLabel2.setBounds(220,50,85,30);
        jLabel2.setFont(font1);
        jTextField2.setBounds(305,50,155,30);
        jTextField2.setFont(font1);
        jLabel3.setBounds(475,50,85,30);
        jLabel3.setFont(font1);
        jTextField3.setBounds(560,50,155,30);
        jTextField3.setFont(font1);
        jLabel4.setBounds(140,100,100,30);
        jLabel4.setFont(font1);
        jLabel5.setBounds(370,100,100,30);
        jLabel5.setFont(font1);
        jLabel6.setBounds(580,100,100,30);
        jLabel6.setFont(font1);

        jButton1.setBounds(480,470,100,30);
        jButton1.setFont(font1);
        jButton2.setBounds(590,470,120,30);
        jButton2.setFont(font1);


        jButton3.setBounds(90,470,100,30);
        jButton3.setFont(font1);
        jButton4.setBounds(200,470,100,30);
        jButton4.setFont(font1);


        add(jLabel1);
        add(jLabel2);
        add(jLabel3);
        add(jLabel4);
        add(jLabel5);
        add(jLabel6);
        add(jTextField1);
        add(jTextField2);
        add(jTextField3);
        add(jButton1);
        add(jButton2);

        add(jButton3);
        add(jButton4);

        addRow();

        //清空表格事件
        jButton4.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(null,"內容將清除？","Message",JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) return;
            while (chList.size() != 0){
                removeRow(0);
            }
            addRow();

            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
        });

        //讀取目錄事件
        jButton1.addActionListener(e -> {
            int row = 0;
            boolean exit = false;
            //抓取欄位
            String res = JOptionPane.showInputDialog(null,"輸入編號");
            if (res == null) return;
            for (row = materialDateManager.currentSheet.getFirstRowNum()+1;row <= materialDateManager.currentSheet.getLastRowNum();row++) {
                if (materialDateManager.currentSheet.getRow(row).getCell(0).getStringCellValue().isBlank()) continue;
                if (materialDateManager.currentSheet.getRow(row).getCell(0).getStringCellValue().equals(res)) {
                    exit = true;
                    break;
                }
            }
            //確認編號存在
            if (!exit) {
                JOptionPane.showMessageDialog(null,"未存在的編號");
                return;
            }
            //清空欄位
            while (chList.size() != 0){
                removeRow(0);
            }
            //填入資料
            jTextField1.setText(res);
            jTextField2.setText(materialDateManager.currentSheet.getRow(row).getCell(1).toString());
            jTextField3.setText(materialDateManager.currentSheet.getRow(row).getCell(2).toString());
            String[] ch = materialDateManager.currentSheet.getRow(row).getCell(3).toString().split("，");
            String[] en = materialDateManager.currentSheet.getRow(row).getCell(4).toString().split("，");
            String[] per = materialDateManager.currentSheet.getRow(row).getCell(5).toString().split("，");
            for (int i = 0;i < ch.length; i++){
                addRow();
            }
            for (int i = 0;i<chList.size();i++){
                chList.get(i).setText(ch[i]);
            }
            for (int i = 0;i<enList.size();i++){
                enList.get(i).setText(en[i]);
            }
            for (int i = 0;i<percent.size();i++){
                percent.get(i).setText(per[i]);
            }
        });

        //更新目錄事件
        jButton2.addActionListener(e -> {
            boolean exit = false;
            boolean empty = false;
            //空白檢查
            for (JTextField f : chList) {
                if (f.getText().isBlank()) empty = true;
            }
            for (JTextField f : enList) {
                if (f.getText().isBlank()) empty = true;
            }
            for (JTextField f : percent) {
                if (f.getText().isBlank()) empty = true;
            }

            if (jTextField1.getText().isBlank() | jTextField2.getText().isBlank() | jTextField3.getText().isBlank() | empty) {
                JOptionPane.showMessageDialog(null, "內容不可為空白");
                return;
            }


            if (materialDateManager.materialList.contains(jTextField1.getText().trim())) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "該編號已存在，資料將被複寫？", "Message", JOptionPane.YES_NO_OPTION)) {
                    exit = true;
                } else {
                    return;
                }
            }
                String num = jTextField1.getText().trim();
                String ori = jTextField2.getText().trim();
                String name = jTextField3.getText().trim();

                String ch = chList.stream().map(n -> n.getText().trim()).collect(Collectors.joining("，"));
                String en = enList.stream().map(n -> n.getText().trim()).collect(Collectors.joining("，"));
                String per = percent.stream().map(n -> n.getText().trim()).collect(Collectors.joining("，"));

                System.out.println(num);
                System.out.println(ori);
                System.out.println(name);
                System.out.println(ch);
                System.out.println(en);
                System.out.println(per);
                materialDateManager.updateMaterial(num, ori, name, ch, en, per, exit);
                formulaEditorArea.updateJcombo(materialDateManager.materialList);
        });

        //新增欄位事件
        jButton3.addActionListener(e -> {
            if (chList.size() < 8){
                addRow();
            }else{
                JOptionPane.showMessageDialog(null,"已達欄位上限");
            }
        });
    }

    //增加欄位
    public void addRow(){
        JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(90,currentY,180,30);
        jTextField1.setFont(font1);
        chList.add(jTextField1);
        add(jTextField1);
        JTextField jTextField2 = new JTextField();
        jTextField2.setBounds(300,currentY,220,30);
        jTextField2.setFont(font1);
        enList.add(jTextField2);
        add(jTextField2);
        JTextField jTextField3 = new JTextField();
        jTextField3.setBounds(575,currentY,50,30);
        jTextField3.setFont(font1);
        percent.add(jTextField3);
        add(jTextField3);
        //除除按鈕
        JButton jButton = new JButton("刪除");
        jButton.setBounds(640,currentY,80,30);
        jButton.setFont(font1);
        removeButton.add(jButton);
        add(jButton);

        jButton.addActionListener(e -> {
            if (chList.size() <= 1){
                JOptionPane.showMessageDialog(null,"欄位已達最下限");
            }else {
                removeRow(removeButton.indexOf(jButton));
            }
        });

        //限制數字輸入
        jTextField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.'
                        || e.getKeyChar() == KeyEvent.VK_DELETE ||
                        e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    jTextField3.setEditable(true);
                }else{
                    jTextField3.setEditable(false);
                }
            }
        });
        currentY += 40;
        repaint();
    }
    //移除欄位
    public void removeRow(int num){

        remove(chList.get(num));
        chList.remove(num);
        remove(enList.get(num));
        enList.remove(num);
        remove(percent.get(num));
        percent.remove(num);

        remove(removeButton.get(num));
        removeButton.remove(num);

        currentY = 140;
        for (JTextField f:chList){
            f.setBounds(90,currentY,180,30);
            currentY += 40;
        }
        currentY = 140;
        for (JTextField f:enList){
            f.setBounds(300,currentY,220,30);
            currentY += 40;
        }
        currentY = 140;
        for (JTextField f:percent){
            f.setBounds(575,currentY,50,30);
            currentY += 40;
        }
        currentY = 140;
        for (JButton f:removeButton){
            f.setBounds(640,currentY,80,30);
            currentY += 40;
        }


        repaint();
    }
}


