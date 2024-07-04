package clientchat;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Whitsip
 */
public class MainForm extends javax.swing.JFrame {

    String username;
    String host;
    int port;
    Socket socket;
    DataOutputStream dos;
    private boolean isConnected = false;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        MyInit();
    }

    public void initFrame(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Chat Room " + username);
        //Menghubungkan
        connect();
    }

    void MyInit() {
        setLocationRelativeTo(null);
    }

    public void connect() {
        appendMessage(" Menghubungkan....", "Status", Color.DARK_GRAY, Color.DARK_GRAY);
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            // Implementasi Username
            dos.writeUTF("CMD_JOIN " + username);
            appendMessage(" Terhubung", "Status", Color.BLACK, Color.BLACK);
            appendMessage("Selamat Datang " + username + "!" , "Pesan Spesial ", Color.ORANGE, Color.ORANGE);

            // Mulai Thread Client
            new Thread(new ClientThread(socket, this)).start();
            jButton1.setEnabled(true);
            // Koneksi
            isConnected = true;

        } catch (IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Tidak dapat terhubung ke server, coba lagi nanti!", "Koneksi gagal!", JOptionPane.ERROR_MESSAGE);
            appendMessage("[IOException]: " + e.getMessage(), "KESALAHAN", Color.RED, Color.RED);
        }
    }

    /*
        Koneksi
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /*
    Menampilkan Pesan
     */
    public void appendMessage(String msg, String header, Color headerColor, Color contentColor) {
        jTextPane1.setEditable(true);
        getMsgHeader(header, headerColor);
        getMsgContent(msg, contentColor);
        jTextPane1.setEditable(false);
    }

    /*
        Pesan dalam chat
     */
    public void appendMyMessage(String msg, String header) {
        jTextPane1.setEditable(true);
        getMsgHeader(header, Color.GREEN);
        getMsgContent(msg, Color.BLACK);
        jTextPane1.setEditable(false);
    }

    /*
        Judul pesan
     */
    public void getMsgHeader(String header, Color color) {
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jTextPane1.replaceSelection(header + ":");
    }

    /*
        Isi Pesan
     */
    public void getMsgContent(String msg, Color color) {
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jTextPane1.replaceSelection(msg + "\n\n");
    }

    public void appendOnlineList(Vector list) {
        sampleOnlineList(list);
    }

    /*
        Menampilkan daftar pengguna online
     */
    public void showOnLineList(Vector list) {
        try {
            txtpane2.setEditable(true);
            txtpane2.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            Iterator it = list.iterator();
            sb.append("<html><table>");
            while (it.hasNext()) {
                Object e = it.next();
                URL url = getImageFile();
                Icon icon = new ImageIcon(this.getClass().getResource("/images/online.png"));
                sb.append("<tr><td><b>></b></td><td>").append(e).append("</td></tr>");
                System.out.println("Online: " + e);
            }
            sb.append("</table></body></html>");
            txtpane2.removeAll();
            txtpane2.setText(sb.toString());
            txtpane2.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
      ************************************  Menampilkan daftar online  *********************************************
     */
    private void sampleOnlineList(Vector list) {
        txtpane2.setEditable(true);
        txtpane2.removeAll();
        txtpane2.setText("");
        Iterator i = list.iterator();
        while (i.hasNext()) {
            Object e = i.next();
            /*  Nama Pengguna Online   */
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.white);

            Icon icon = new ImageIcon(this.getClass().getResource("/images/online.png"));
            JLabel label = new JLabel(icon);
            label.setText(" " + e);
            panel.add(label);
            int len = txtpane2.getDocument().getLength();
            txtpane2.setCaretPosition(len);
            txtpane2.insertComponent(panel);
            /*  Append Lanjutan   */
            sampleAppend();
        }
        txtpane2.setEditable(false);
    }

    private void sampleAppend() {
        int len = txtpane2.getDocument().getLength();
        txtpane2.setCaretPosition(len);
        txtpane2.replaceSelection("\n");
    }

    /*
      ************************************  Show Online Sample  *********************************************
     */
 /*
        Get image file path
     */
    public URL getImageFile() {
        URL url = this.getClass().getResource("/images/online.png");
        return url;
    }

    /*
        Set myTitle
     */
    public void setMyTitle(String s) {
        setTitle(s);
    }


    /*
        Phương thức get host
     */
    public String getMyHost() {
        return this.host;
    }

    /*
        Phương thức get Port
     */
    public int getMyPort() {
        return this.port;
    }

    /*
        Phương thức nhận My Username
     */
    public String getMyUsername() {
        return this.username;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtpane2 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        LogoutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Whitspi Multiclient-ChatRoom");
        setBackground(new java.awt.Color(204, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextPane1.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 11)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 437, 361));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 373, 437, 42));

        jButton1.setBackground(new java.awt.Color(0, 102, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("KIRIM");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 376, 152, 37));

        txtpane2.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        txtpane2.setForeground(new java.awt.Color(120, 14, 3));
        txtpane2.setAutoscrolls(false);
        txtpane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(txtpane2);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 118, 167, 249));

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Daftar User Online");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 96, 127, 16));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 415, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo mini.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 14, 69, 76));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg main.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 420));

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/check.png"))); // NOI18N
        jMenu2.setText("Akun");

        LogoutMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loggoff.png"))); // NOI18N
        LogoutMenu.setText("Keluar");
        LogoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutMenuActionPerformed(evt);
            }
        });
        jMenu2.add(LogoutMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutMenuActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin untuk logout?");
        if (confirm == 0) {
            try {
                socket.close();
                setVisible(false);
                /**
                 * Login Form *
                 */
                new LoginForm().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_LogoutMenuActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        try {
            String content = username + " " + evt.getActionCommand();
            dos.writeUTF("CMD_CHATALL " + content);
            appendMyMessage(" " + evt.getActionCommand(), username);
            jTextField1.setText("");
        } catch (IOException e) {
            appendMessage(" Tidak dapat mengirim pesan sekarang, tidak dapat terhubung ke Server saat ini, coba lagi nanti atau mulai ulang aplikasi ini.!", "KESALAHAN", Color.RED, Color.RED);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            String content = username + " " + jTextField1.getText();
            dos.writeUTF("CMD_CHATALL " + content);
            appendMyMessage(" " + jTextField1.getText(), username);
            jTextField1.setText("");
        } catch (IOException e) {
            appendMessage(" Tidak dapat mengirim pesan sekarang, tidak dapat terhubung ke Server saat ini, coba lagi nanti atau mulai ulang aplikasi ini.!", "KESALAHAN", Color.RED, Color.RED);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem LogoutMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane txtpane2;
    // End of variables declaration//GEN-END:variables
}
