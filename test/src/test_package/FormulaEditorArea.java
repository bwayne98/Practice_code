package test_package;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
public class FormulaEditorArea extends JTable {
    DefaultTableModel TbMod = new DefaultTableModel();
    Font font1 = new Font("微軟正黑體",Font.PLAIN,16);
    public JComboBox CB = new JComboBox();
    int currentColumn;
    int currentRow;
    JTextField jTextField = new JTextField();

    public FormulaEditorArea(MaterialDateManager materialDateManager){
        /* 選單內容 */
        CB.setFont(font1);
        updateJcombo(materialDateManager.materialList);

        //比例事件

        //選單事件
        CB.addItemListener(e -> {
            System.out.println(CB.getSelectedItem());
            System.out.println("X:" + currentColumn + ", Y:" + currentRow);
            String[] materialValue = materialDateManager.findMaterialInfo(CB.getSelectedItem());
            if (getSelectedColumn() == -1) return;
            TbMod.setValueAt(materialValue[0],getSelectedRow(),getSelectedColumn()+1);
            TbMod.setValueAt(materialValue[1],getSelectedRow(),getSelectedColumn()+2);
        });

        /* 表格標題 */
        TbMod.addColumn("編號");
        TbMod.addColumn("原料名稱");
        TbMod.addColumn("INCI NAME");
        TbMod.addColumn("比例(%)");

        /* 表格設定 */
        setRowHeight(25);
        setModel(TbMod);
        setFont(font1);

        /* 初始表格 */
        TbMod.addRow(new Object[]{"請選擇","","","0"});
        /* 第一列設定為選單CB */
        getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(CB));
        getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(jTextField));
        //欄寬設定
        getColumnModel().getColumn(0).setPreferredWidth(60);
        getColumnModel().getColumn(1).setPreferredWidth(250);
        getColumnModel().getColumn(2).setPreferredWidth(300);
        getColumnModel().getColumn(3).setPreferredWidth(60);
    }
    //更新選單
    public void updateJcombo(List<String> stringList){
        CB.removeAllItems();
        for (String s:stringList) {
            CB.addItem(s);
        }

    }

}
