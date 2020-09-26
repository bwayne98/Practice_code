package test_package;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PanelFomulaEditor extends JPanel {
    FormulaEditorArea formulaEditorArea;
    JScrollPane sp;

    Date now = new Date();
    FileCreator fileCreator = new FileCreator();
    SimpleDateFormat dZone = new SimpleDateFormat("yyyyMMdd");
    JButton outPut = new JButton("輸出表單");
    JButton reset = new JButton("重置頁面");
    JButton save = new JButton("儲存紀錄");
    JButton load = new JButton("讀取紀錄");
    JButton newRow = new JButton("增加欄位");
    JButton deletRow = new JButton("刪除欄位");
    JTextField name = new JFormattedTextField();
    JTextField num1 = new JFormattedTextField();
    JTextField num2 = new JFormattedTextField();

    JLabel nameDescription = new JLabel("主件名稱：");
    JLabel num1Description = new JLabel("主件品號：");
    JLabel num2Description = new JLabel("批號：" + dZone.format(now) + "-");
    JLabel percent = new JLabel("0%");

    Font font1 = new Font("微軟正黑體",Font.PLAIN,16);

    public PanelFomulaEditor(MaterialDateManager materialDateManager,FileCreator fileCreator){
        formulaEditorArea = new FormulaEditorArea(materialDateManager);
        sp = new JScrollPane(formulaEditorArea);

        /* 元件大小、位置 */
        sp.setBounds(50,60,670,350);
        outPut.setBounds(620,470,100,30);
        outPut.setFont(font1);
        reset.setBounds(510,470,100,30);
        reset.setFont(font1);
        save.setBounds(400,470,100,30);
        save.setFont(font1);
        load.setBounds(290,470,100,30);
        load.setFont(font1);
        newRow.setBounds(50,420,100,30);
        newRow.setFont(font1);
        deletRow.setBounds(170,420,100,30);
        deletRow.setFont(font1);
        percent.setBounds(670,410,100,30);
        percent.setFont(font1);
        nameDescription.setBounds(50,20,80,30);
        nameDescription.setFont(font1);
        name.setBounds(130,20,150,30);
        name.setFont(font1);
        num1Description.setBounds(300,20,80,30);
        num1Description.setFont(font1);
        num1.setBounds(380,20,100,30);
        num1.setFont(font1);
        num2Description.setBounds(500,20,130,30);
        num2Description.setFont(font1);
        num2.setBounds(630,20,50,30);
        num2.setFont(font1);
        /* 版面配置 */
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        //元件配置
        add(outPut);
        add(reset);
        add(save);
        add(load);
        add(newRow);
        add(deletRow);
        add(percent);
        add(nameDescription);
        add(name);
        add(num1Description);
        add(num1);
        add(num2Description);
        add(num2);
        add(sp);

        //表格事件
        formulaEditorArea.jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.'
                        || e.getKeyChar() == KeyEvent.VK_DELETE ||
                        e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    formulaEditorArea.jTextField.setEditable(true);
                    System.out.println(e.getKeyChar());
                }else{
                    formulaEditorArea.jTextField.setEditable(false);
                }
            }
//            @Override
//            public void keyPressed(KeyEvent e1){
//                if (e1.getKeyCode() == KeyEvent.VK_ENTER){
//                    try{
//                        Double total = 0d;
//                        Double curr;
//                        for (int i = 0; i < formulaEditorArea.TbMod.getRowCount() ; i++) {
//                            curr = Double.parseDouble(formulaEditorArea.getValueAt(i, 3).toString());
//                            formulaEditorArea.setValueAt(curr,formulaEditorArea.getSelectedRow(),3);
//                            total += curr;
//                        }
//                        percent.setText(total + "%");
//                    }catch(Exception notNumber){
//                        formulaEditorArea.setValueAt("0",formulaEditorArea.getSelectedRow(),3);
//                        JOptionPane.showMessageDialog(null,"請輸入數字");
//                    }
//                }
//            }
        });

        //計算比例
        formulaEditorArea.TbMod.addTableModelListener(e -> {
            if (formulaEditorArea.getSelectedColumn() == 3) {
                try {
                    Double total = 0d;
                    Double curr;
                    for (int i = 0; i < formulaEditorArea.TbMod.getRowCount(); i++) {
                        curr = Double.parseDouble(formulaEditorArea.getValueAt(i, 3).toString());
                        total += curr;
                    }
                    percent.setText(total + "%");
                } catch (Exception notNumber) {
                    //formulaEditorArea.setValueAt("0", formulaEditorArea.getSelectedRow(), 3);
                    JOptionPane.showMessageDialog(null, "請輸入數字");
                }
            }
        });

        /* 按鈕事件 */
        reset.addActionListener(e -> {
            if (name.getText().isBlank() & num1.getText().isBlank() &
                    num2.getText().isBlank() & formulaEditorArea.TbMod.getRowCount() == 1){
                return;
            }
            int res = JOptionPane.showConfirmDialog(
                    null,"確定要清空資料嗎?",
                    null,JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION){
                name.setText("");
                num1.setText("");
                num2.setText("");
                while(formulaEditorArea.TbMod.getRowCount() > 0)
                {
                    System.out.println(formulaEditorArea.getModel().getValueAt(0,0));
                    formulaEditorArea.TbMod.removeRow(0);
                }
                formulaEditorArea.TbMod.addRow(new Object[]{"請選擇","","","0"});
                formulaEditorArea.getColumnModel().getColumn(0).setPreferredWidth(60);
                formulaEditorArea.getColumnModel().getColumn(1).setPreferredWidth(250);
                formulaEditorArea.getColumnModel().getColumn(2).setPreferredWidth(300);
                formulaEditorArea.getColumnModel().getColumn(3).setPreferredWidth(60);
            }
        });
        outPut.addActionListener(e -> {
            if (name.getText().isBlank() | num1.getText().isBlank() | num2.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "表格不可為空白");
                return;
            }
            //移除空白格子
            String s = num1.getText().trim();
            String t = num2.getText().trim();
            //確認品號及批號字數
            if (s.length() != 8 | t.length() != 2) {
                JOptionPane.showMessageDialog(null,
                        "品號為8碼數字，批號為2碼數字");
                return;
            }
            //轉換為文字為數字
            int a, b;
            try {
                a = Integer.parseInt(num1.getText().trim());
                b = Integer.parseInt(num2.getText().trim());
            } catch (Exception isNumCheck) {
                JOptionPane.showMessageDialog(null, "品號與批號只可為整數數字");
                return;
            }
            //確認輸出
            int res = JOptionPane.showConfirmDialog(
                    null, "確定要輸出表格嗎?",
                    name.getText() + "-" + a, JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                fileCreator.newFile("測試檔案",formulaEditorArea);
            }

        });
        newRow.addActionListener(e -> formulaEditorArea.TbMod.addRow(new Object[]{"請選擇","","","0"}));
        deletRow.addActionListener(e -> {
            if (formulaEditorArea.TbMod.getRowCount() == 1){
                return;
            }
            try {
                //輸入欄位
                String deletnum = JOptionPane.showInputDialog(null,"請輸入欄位1~"+ formulaEditorArea.TbMod.getRowCount(),"Message",JOptionPane.QUESTION_MESSAGE);
                if (deletnum == null) {return;}
                else {
                    //再次確認
                    if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null,
                            "確定要刪除："+ formulaEditorArea.getModel().getValueAt((Integer.parseInt(deletnum)-1),0) + "？",
                            "Message",JOptionPane.YES_NO_OPTION)){
                      return;
                    }
                    //刪除欄位
                    formulaEditorArea.TbMod.removeRow(Integer.parseInt(deletnum)-1);
                }
            } catch (Exception overnum) {
                JOptionPane.showMessageDialog(null,"輸入錯誤，請輸入數字1~" + formulaEditorArea.TbMod.getRowCount());
            }
        });
        /* 編輯紀錄生成.讀取 */
        save.addActionListener(e -> {
            if (name.getText().isBlank() | num1.getText().isBlank()){
                JOptionPane.showMessageDialog(null,
                        "名稱與品號不可為空白");
                return;
            }
            if (num1.getText().length() != 10){
                JOptionPane.showMessageDialog(null,
                        "品號格式錯誤");
                return;
            }
            fileCreator.newSave(name.getText(),num1.getText(),
                    num2Description.getText() + num2.getText(),
                    formulaEditorArea);
        });
        load.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(
                    null,"當前編輯內容將被清空?",
                    null,JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION){
                name.setText("");
                num1.setText("");
                num2.setText("");
                while(formulaEditorArea.TbMod.getRowCount() > 0)
                {
                    System.out.println(formulaEditorArea.getModel().getValueAt(0,0));
                    formulaEditorArea.TbMod.removeRow(0);
                }
                formulaEditorArea.TbMod.addRow(new Object[]{"請選擇","","","0"});
                formulaEditorArea.getColumnModel().getColumn(0).setPreferredWidth(60);
                formulaEditorArea.getColumnModel().getColumn(1).setPreferredWidth(250);
                formulaEditorArea.getColumnModel().getColumn(2).setPreferredWidth(300);
                formulaEditorArea.getColumnModel().getColumn(3).setPreferredWidth(60);
            }
            try {
                fileCreator.loadSave(this);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null,"讀取失敗");
            }
        });
    }
}
