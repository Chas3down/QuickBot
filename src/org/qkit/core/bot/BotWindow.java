/*
 This file is a part of QuickBot.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.qkit.core.bot;

import org.qkit.core.BotInternalConstants;
import org.qkit.core.impl.accessors.Client;

import org.qkit.core.impl.accessors.GameApplet;
import org.qkit.core.impl.accessors.SuperProducer;
import org.qkit.ext.utils.logging.LogOutputStream;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


/**
 * Temporary. It will be replaced by a non-generic class.
 * @author trDna
 */
public class BotWindow extends JFrame implements Runnable{

    private static final long serialVersionUID = 1L;


    private static JPanel contentPane, gamePanel;

    protected static JList<String> logTextArea;

    private JTextField ircUserTextField;

    private JLabel lblCamX, lblCamY, lblCamZ;

    protected static ArrayList<String> values  = new ArrayList<String>();

    private volatile boolean debugCamera = true, debugPlayer = true, debugMapBase = true, debugMinimap=true;

    private static ListModel<String> list = new AbstractListModel<String>() {

        private static final long serialVersionUID = -163015257618943926L;

        public int getSize() {
            return values.size();
        }
        public String getElementAt(int index) {
            return values.get(index);
        }

    };

    private static Client theClient;

    private static GameApplet theApplet;

    private static SuperProducer canvas;

    private Object clazz;

    public BotWindow() throws Exception{
        initWindow();
        initInternal();
        Thread internalThread = new Thread(this);
        internalThread.start();
    }



    public void run(){
        while (!getClient().isLoggedIn()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }



    public void initInternal() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException {

        JFrame.setDefaultLookAndFeelDecorated(true);

        setAlwaysOnTop(true);
        setVisible(true);

        ClassLoader cl = new URLClassLoader(new URL[]{new URL("file:" + BotInternalConstants.INJ_JAR_PATH)});

        clazz = cl.loadClass("client").newInstance();
        Applet gameApplet = (Applet) clazz;

//        GameApplet ga = (GameApplet) cl.loadClass("RSApplet").newInstance();
        //      realGraphics = ga.getRealGraphics();

        gameApplet.setPreferredSize(new Dimension(768, 560));
        gameApplet.setVisible(true);
        gameApplet.init();
        gameApplet.start();

        theClient = (Client) clazz;
        theApplet = (GameApplet) clazz;

        getGamePanel().add(gameApplet);
        getGamePanel().setVisible(true);

    }

    public GameApplet getGameApplet(){
        return theApplet;
    }

    public void btnPlayScriptClickPerformed(ActionEvent e){

    }

    public Client getClient(){
        return theClient;
    }

    public JPanel getGamePanel(){
        return gamePanel;
    }

    public void initWindow(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(BotWindow.class.getResource("images/logo.png")));
        setResizable(false);
        setTitle("qBot - Development Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1290, 725);

        JMenuBar menuBar = new JMenuBar();

        JMenuItem fileMenu = new JMenuItem("File");
        menuBar.add(fileMenu);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 24, 256, 650);
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setForeground(Color.WHITE);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(1021, 24, 263, 650);
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JTabbedPane rightInfoPane = new JTabbedPane(JTabbedPane.TOP);

        JLabel lblLoggedInAs = new JLabel("Logged in as: DEFAULT ");
        lblLoggedInAs.setForeground(Color.LIGHT_GRAY);
        lblLoggedInAs.setFont(new Font("Arial", Font.BOLD, 15));

        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setIcon(new ImageIcon(BotWindow.class.getResource("images/cross.png")));
        btnSignOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            }
        });

        JButton btnGetPremium = new JButton("Donate!");
        btnGetPremium.setIcon(new ImageIcon("images/login.png"));
        btnGetPremium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        GroupLayout gl_rightPanel = new GroupLayout(rightPanel);
        gl_rightPanel.setHorizontalGroup(
                gl_rightPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_rightPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_rightPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(rightInfoPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, gl_rightPanel.createSequentialGroup()
                                                .addGap(10)
                                                .addComponent(lblLoggedInAs))
                                        .addGroup(Alignment.TRAILING, gl_rightPanel.createSequentialGroup()
                                                .addComponent(btnGetPremium, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                                .addGap(18)
                                                .addComponent(btnSignOut, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_rightPanel.setVerticalGroup(
                gl_rightPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_rightPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(rightInfoPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblLoggedInAs)
                                .addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addGroup(gl_rightPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnSignOut)
                                        .addComponent(btnGetPremium))
                                .addContainerGap())
        );

        JPanel ircPane = new JPanel();
        ircPane.setBackground(Color.CYAN);
        rightInfoPane.addTab("IRC", null, ircPane, null);

        JScrollPane scrollPane = new JScrollPane();

        JLabel lblparabot = new JLabel("#jhacking - Rizon IRC");
        lblparabot.setFont(new Font("Tahoma", Font.BOLD, 14));

        JButton button = new JButton("Send Message");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JScrollPane scrollPane_3 = new JScrollPane();
        GroupLayout gl_ircPane = new GroupLayout(ircPane);
        gl_ircPane.setHorizontalGroup(
                gl_ircPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_ircPane.createSequentialGroup()
                                .addGroup(gl_ircPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_ircPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(gl_ircPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblparabot, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(gl_ircPane.createSequentialGroup()
                                                .addGap(34)
                                                .addComponent(button, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_ircPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(224, Short.MAX_VALUE))
        );
        gl_ircPane.setVerticalGroup(
                gl_ircPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_ircPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblparabot, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(button, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        JTextArea ircTextArea = new JTextArea();
        scrollPane.setViewportView(ircTextArea);
        ircTextArea.setEditable(false);

        ircUserTextField = new JTextField();
        scrollPane_3.setViewportView(ircUserTextField);
        ircUserTextField.setColumns(10);
        ircPane.setLayout(gl_ircPane);

        JPanel notePadPane = new JPanel();
        notePadPane.setBackground(Color.MAGENTA);
        rightInfoPane.addTab("Notepad", null, notePadPane, null);

        JScrollPane scrollPane_2 = new JScrollPane();

        JButton btnQuickSave = new JButton("Quick Save");
        btnQuickSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton btnSaveAs = new JButton("Save As..");
        btnSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        GroupLayout gl_notePadPane = new GroupLayout(notePadPane);
        gl_notePadPane.setHorizontalGroup(
                gl_notePadPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_notePadPane.createSequentialGroup()
                                .addGroup(gl_notePadPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_notePadPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_notePadPane.createSequentialGroup()
                                                .addGap(26)
                                                .addComponent(btnSaveAs)
                                                .addGap(18)
                                                .addComponent(btnQuickSave)))
                                .addContainerGap(242, Short.MAX_VALUE))
        );
        gl_notePadPane.setVerticalGroup(
                gl_notePadPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_notePadPane.createSequentialGroup()
                                .addGap(27)
                                .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                                .addGap(18)
                                .addGroup(gl_notePadPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnSaveAs)
                                        .addComponent(btnQuickSave))
                                .addContainerGap())
        );

        JTextArea notePadTextArea = new JTextArea();
        scrollPane_2.setViewportView(notePadTextArea);
        notePadPane.setLayout(gl_notePadPane);
        rightPanel.setLayout(gl_rightPanel);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        JLabel lblHello = new JLabel("");
        lblHello.setIcon(new ImageIcon(BotWindow.class.getResource("images/gplv3.png")));

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(BotWindow.class.getResource("images/poweredbyasm.gif")));
        GroupLayout gl_leftPanel = new GroupLayout(leftPanel);
        gl_leftPanel.setHorizontalGroup(
                gl_leftPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_leftPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_leftPanel.createSequentialGroup()
                                                .addComponent(lblHello)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(label, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(10, Short.MAX_VALUE))
        );
        gl_leftPanel.setVerticalGroup(
                gl_leftPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_leftPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblHello)
                                        .addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addGap(57))
        );

        JPanel botInfoPanel = new JPanel();
        botInfoPanel.setBackground(new Color(255, 0, 0));
        tabbedPane.addTab("Bot Information", null, botInfoPanel, null);

        JLabel lblVersion = new JLabel("Version 2.0 ALPHA");
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 13));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setValue(-1);

        JLabel lblCheckingForUpdates = new JLabel("Bot In Sync");
        lblCheckingForUpdates.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        lblCamX = new JLabel("CamX:");
        lblCamX.setFont(new Font("Tahoma", Font.BOLD, 13));

        lblCamY = new JLabel("CamY:");
        lblCamY.setFont(new Font("Tahoma", Font.BOLD, 13));

        lblCamZ = new JLabel("CamZ:");
        lblCamZ.setFont(new Font("Tahoma", Font.BOLD, 13));
        GroupLayout gl_botInfoPanel = new GroupLayout(botInfoPanel);
        gl_botInfoPanel.setHorizontalGroup(
                gl_botInfoPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_botInfoPanel.createSequentialGroup()
                                .addGap(53)
                                .addComponent(lblVersion)
                                .addContainerGap(65, Short.MAX_VALUE))
                        .addGroup(gl_botInfoPanel.createSequentialGroup()
                                .addContainerGap(74, Short.MAX_VALUE)
                                .addComponent(lblCheckingForUpdates, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(gl_botInfoPanel.createSequentialGroup()
                                .addContainerGap(19, Short.MAX_VALUE)
                                .addGroup(gl_botInfoPanel.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCamZ, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                        .addComponent(lblCamY, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblCamX, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28))
        );
        gl_botInfoPanel.setVerticalGroup(
                gl_botInfoPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_botInfoPanel.createSequentialGroup()
                                .addGap(12)
                                .addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblCheckingForUpdates, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                .addGap(29)
                                .addComponent(lblCamX)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblCamY)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblCamZ)
                                .addContainerGap(424, Short.MAX_VALUE))
        );
        botInfoPanel.setLayout(gl_botInfoPanel);
        leftPanel.setLayout(gl_leftPanel);
        contentPane.add(leftPanel);
        contentPane.add(rightPanel);

        contentPane.setSize(768, 1024);

        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(253, 0, 942, 24);
        contentPane.add(toolBar);

        Component horizontalGlue = Box.createHorizontalGlue();
        toolBar.add(horizontalGlue);

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(0, -1, 136, 25);
        contentPane.add(lblNewLabel);

        JCheckBox checkBox = new JCheckBox("Stay on Top");
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        checkBox.setBounds(153, 4, 94, 15);
        contentPane.add(checkBox);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(255, 583, 769, 91);
        contentPane.add(scrollPane_1);

        JList<String> logTextArea = new JList<String>();
        scrollPane_1.setViewportView(logTextArea);
        logTextArea.setFont(new Font("Tahoma", Font.BOLD, 11));
        logTextArea.setModel(new AbstractListModel<String>() {
            private static final long serialVersionUID = -163015257618943926L;
            String[] values = new String[] {"I am a log area :D", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "a"};
            public int getSize() {
                return values.length;
            }
            public String getElementAt(int index) {
                return values[index];
            }
        });
        logTextArea.setForeground(Color.WHITE);

        JButton btnStopScript = new JButton("");
        btnStopScript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnStopScript.setIcon(new ImageIcon(BotWindow.class.getResource("images/stop.png")));
        btnStopScript.setEnabled(false);
        btnStopScript.setBounds(1251, 0, 23, 22);
        contentPane.add(btnStopScript);

        JButton btnPauseScript = new JButton("");
        btnPauseScript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnPauseScript.setIcon(new ImageIcon(BotWindow.class.getResource("images/pause.png")));
        btnPauseScript.setEnabled(false);
        btnPauseScript.setBounds(1228, 0, 23, 22);
        contentPane.add(btnPauseScript);

        JButton btnPlayScript = new JButton("");
        btnPlayScript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                btnPlayScriptClickPerformed(arg0);
            }
        });
        btnPlayScript.setIcon(new ImageIcon(BotWindow.class.getResource("images/run.png")));
        btnPlayScript.setBounds(1205, 0, 23, 22);
        contentPane.add(btnPlayScript);

        JTabbedPane botTabPane = new JTabbedPane(JTabbedPane.TOP);
        botTabPane.setBounds(253, 24, 771, 560);
        contentPane.add(botTabPane);

        gamePanel = new JPanel();
        botTabPane.addTab("Bot 1", null, gamePanel, null);
        gamePanel.setPreferredSize(new Dimension(764, 503));
        gamePanel.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        botTabPane.addTab("New tab", null, panel, null);
        System.setOut(new LogOutputStream(System.out, values, logTextArea));

        setJMenuBar(menuBar);


    }

    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()){
                    showMenu(e);
                }
            }
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    showMenu(e);
                }
            }
            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }



}
