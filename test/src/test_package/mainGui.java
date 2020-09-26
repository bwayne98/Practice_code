package test_package;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class mainGui{

    public static void main(String[] args){
        FileCreator fileCreator = new FileCreator();
        MaterialDateManager materialDateManager = new MaterialDateManager();
        JFrame window = new JFrame();
        PanelFomulaEditor panel1 = new PanelFomulaEditor(materialDateManager,fileCreator);
        MaterialDateEditorPanel panel2 = new MaterialDateEditorPanel(materialDateManager, panel1.formulaEditorArea);
        JTabbedPane jTabbedPane = new JTabbedPane();
        Font jTabbedPaneFont = new Font("微軟正黑體",Font.BOLD,16);

//        JMenuBar jMenuBar = new JMenuBar();
//        JMenu jMenuFile = new JMenu("檔案管理");
//        JMenuItem jMenuItemSave = new JMenuItem("儲存紀錄");
//        JMenuItem jMenuItemLoad = new JMenuItem("讀取紀錄");

        //彈出視窗字體
        UIManager.put("OptionPane.messageFont",jTabbedPaneFont);

        /* set Frame */
        window.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4),
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4),
                800,620);
        window.setResizable(false);
        window.setTitle("配方表編輯工具");
        window.setVisible(true);

        //檔案管理

//        jMenuBar.add(jMenuFile);
//        jMenuFile.setFont(jTabbedPaneFont);
//        jMenuFile.add(jMenuItemSave);
//        jMenuFile.add(jMenuItemLoad);
//        window.setJMenuBar(jMenuBar);
//        jMenuBar.setFont(jTabbedPaneFont);
//        jMenuItemLoad.setFont(jTabbedPaneFont);
//        jMenuItemSave.setFont(jTabbedPaneFont);

        /* 增加分頁 */
        window.add(jTabbedPane);
        jTabbedPane.setFont(jTabbedPaneFont);
        jTabbedPane.add("主頁",panel1);
        jTabbedPane.add("原料清單管理",panel2);

        /* 關閉視窗設定 */
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null,"確定要結束程式？","Message",JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) System.exit(0);
            }
        });
    }
}
