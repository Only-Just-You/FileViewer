package org.example.filesee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileViewer {
    private final JFrame frame;
    private final JList<Object> list;
    private final JTextArea textArea;
    private File dir;
    private File[] fileList;
    private final JComboBox<Object> comboBox;

    private final JFileChooser jFileChooser1 = new JFileChooser();
    private File file = null;

    public FileViewer(){
        frame = new JFrame("文件内容查看器");
        Container containerPane = frame.getContentPane();
        containerPane.setLayout(new BorderLayout());
        JMenuBar jMenuBar1 = new JMenuBar();
        frame.setJMenuBar(jMenuBar1);
        JMenu jMenu1 = new JMenu();
        jMenu1.setText("文件");
        JMenuItem jMenuItem9 = new JMenuItem();
        jMenuItem9.setText("打开");
        jMenuItem9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str;
                jFileChooser1.showOpenDialog(frame);
                file = jFileChooser1.getSelectedFile();
                if(file == null) return;
                FileReader frd = null;
                BufferedReader bfrd = null;
                try {
                    frd = new FileReader(file);
                    bfrd = new BufferedReader(new FileReader(file));
                    while (true){
                        str = bfrd.readLine();
                        if(str == null) break;
                        textArea.append(str + "\n");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    try {
                        if(frd != null) frd.close();
                        if(bfrd != null) bfrd.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        JMenuItem jMenuItem2 = new JMenuItem();
        jMenuItem2.setText("退出");
        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jMenuBar1.add(jMenu1);
        jMenu1.add(jMenuItem9);
        jMenu1.add(jMenuItem2);

        File[] s = File.listRoots();
        assert s != null;
        comboBox = new JComboBox<Object>(s);
        containerPane.add(comboBox, BorderLayout.NORTH);

        list = new JList<>();
        textArea = new JTextArea();
        textArea.setFont(new Font("宋体",Font.PLAIN,14));
        JScrollPane jScrollPane = new JScrollPane(list);
        JScrollPane jScrollPaneTxt = new JScrollPane(textArea);
        JSplitPane jsp = new JSplitPane(1,true, jScrollPane, jScrollPaneTxt);
        containerPane.add(jsp, BorderLayout.CENTER);

        JLabel jLabel = new JLabel("文件信息");
        JPanel panel = new JPanel();
        panel.add(jLabel);
        containerPane.add(panel, BorderLayout.SOUTH);

        frame.setSize(450, 450);
        frame.setVisible(true);
        frame.setLocation(200,260);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = comboBox.getSelectedItem() + "\\";
                try {
                    dir = new File(str);
                    fileList = dir.listFiles();
                    list.setListData(fileList);
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String fileStr = list.getSelectedValue().toString();
                File file = new File(fileStr);
                FileReader fin = null;
                BufferedReader bin = null;
                try {
                    if(!file.isDirectory()){
                        fin = new FileReader(file);
                        bin = new BufferedReader(fin);
                        StringBuilder txt = new StringBuilder();
                        String strTemp;
                        while (true){
                            strTemp = bin.readLine();
                            if(strTemp == null) break;
                            txt.append(strTemp).append("\n\r");
                        }
                        textArea.setText(txt.toString());
                    }
                }catch (IOException ioex){
                    ioex.printStackTrace();
                }finally {
                    try {
                        if(bin != null) bin.close();
                        if(fin != null) fin.close();
                    }catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new FileViewer();
    }
}
