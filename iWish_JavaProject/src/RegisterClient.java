
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Mahmoud
 */
public class RegisterClient extends javax.swing.JFrame {

    /*
     Connection con = null;
     PreparedStatement pst = null;
     PreparedStatement pstID = null;
     ResultSet rsID = null;
     int ID = 0;
     */
    Socket server;
    DataInputStream dataInputStream;
    PrintStream printStream;
    JOptionPane jop;
    String userName = null;

    @Override

    public void dispose() {
        System.out.println("Closing All Open Resources ...");
        try {
            // CLOSE ALL Sockets and Resources
            if (Objects.nonNull(printStream)) {
                System.out.println("Closing Print Stream ...");
                printStream.close();
            }
            if (Objects.nonNull(dataInputStream)) {
                System.out.println("Closing Data Input Stream ...");
                dataInputStream.close();
            }
            if (Objects.nonNull(server) && !server.isClosed()) {
                System.out.println("Closing Socket Server ...");
                server.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dispose();
    }

    public RegisterClient() {
        initComponents();
        this.setTitle("Iwish Project");
        try {
            this.setLocationRelativeTo(null);
            server = new Socket("127.0.0.1", 5005);
            dataInputStream = new DataInputStream(server.getInputStream());
            printStream = new PrintStream(server.getOutputStream());
        } catch (ConnectException ex) {
            jop = new JOptionPane("sssssss");
            jop.showMessageDialog(this, "server is slept");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(RegisterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*Thread t = new Thread(new Runnable() {

         @Override
         public void run() {
         try {
         while (true) {
         // revceived data from server
         // String str = dis.readLine();
         //UserDTO data = new Gson().fromJson(str, UserDTO.class);
         /*
         textArea.append("- " + data.id + "\n");
         textArea.append("- " + data.username + "\n");
         textArea.append("- " + data.pass + "\n");
                        
         }
         } catch (SocketException ex) {
         pos.close();
         if (Thread.activeCount() > 0) {
         Thread.currentThread().stop();
         }
         } catch (IOException ex) {
         ex.printStackTrace();
         }
         }
         });
         t.start();*/
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wlcPnl = new javax.swing.JPanel();
        suLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPnl = new javax.swing.JPanel();
        usrLbl = new javax.swing.JLabel();
        usrRgstTxtfield = new javax.swing.JTextField();
        pwdrgstLbl = new javax.swing.JLabel();
        emailLbl = new javax.swing.JLabel();
        emailRgstTxtfield = new javax.swing.JTextField();
        fnameLbl = new javax.swing.JLabel();
        fnameTxtfield = new javax.swing.JTextField();
        lnameLbl = new javax.swing.JLabel();
        lnametxtField = new javax.swing.JTextField();
        pwdrgstTxtfield = new javax.swing.JPasswordField();
        subtnsPnl = new javax.swing.JPanel();
        rgstBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 710));
        getContentPane().setLayout(new java.awt.BorderLayout(0, 70));

        wlcPnl.setLayout(new java.awt.BorderLayout(0, 80));

        suLbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        suLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        suLbl.setText("Signup");
        suLbl.setPreferredSize(new java.awt.Dimension(70, 50));
        suLbl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        wlcPnl.add(suLbl, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/images.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE)
        );

        wlcPnl.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(wlcPnl, java.awt.BorderLayout.PAGE_START);

        usrLbl.setText("User Name");

        pwdrgstLbl.setText("Password");

        emailLbl.setText("Email");

        fnameLbl.setText("First Name");

        fnameTxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameTxtfieldActionPerformed(evt);
            }
        });

        lnameLbl.setText("Last Name");

        pwdrgstTxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwdrgstTxtfieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bodyPnlLayout = new javax.swing.GroupLayout(bodyPnl);
        bodyPnl.setLayout(bodyPnlLayout);
        bodyPnlLayout.setHorizontalGroup(
            bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPnlLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(usrLbl)
                        .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lnameLbl)
                            .addComponent(fnameLbl)
                            .addComponent(emailLbl)))
                    .addGroup(bodyPnlLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(pwdrgstLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailRgstTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnametxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(usrRgstTxtfield, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fnameTxtfield, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pwdrgstTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        bodyPnlLayout.setVerticalGroup(
            bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usrRgstTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usrLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fnameTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fnameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lnametxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailRgstTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwdrgstTxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwdrgstLbl))
                .addGap(78, 78, 78))
        );

        getContentPane().add(bodyPnl, java.awt.BorderLayout.CENTER);

        subtnsPnl.setPreferredSize(new java.awt.Dimension(100, 70));

        rgstBtn.setText("Register");
        rgstBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rgstBtn.setPreferredSize(new java.awt.Dimension(160, 40));
        rgstBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rgstBtnActionPerformed(evt);
            }
        });
        subtnsPnl.add(rgstBtn);

        getContentPane().add(subtnsPnl, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rgstBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rgstBtnActionPerformed
  try {/*
             String qry = "insert into usr(USRID,USRNAME,PASSWORD, EMAIL, FNAME, LNAME) values(?,?,?,?,?,?)";
             //DriverManager.registerDriver(new OracleDriver());
             //con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "iwish", "iwish");
             pst = con.prepareStatement(qry);

             String qryID = ("select nvl(max(usrid),0)+1 from usr");
             pstID = con.prepareStatement(qryID, rsID.TYPE_SCROLL_INSENSITIVE, rsID.CONCUR_UPDATABLE);
             rsID = pstID.executeQuery();
             while (rsID.next()) {
             ID = rsID.getInt(1);
             System.out.println(ID);
             }*/
            //System.out.println(qryID);
            //System.out.println(rsID);
            //System.out.println(pstID);
            /*    
             pst.setInt(1, ID);
             pst.setString(2, usrRgstTxtfield.getText());
             pst.setString(3, pwdrgstTxtfield.getText());
             pst.setString(4, emailRgstTxtfield.getText());
             pst.setString(5, fnameTxtfield.getText());
             pst.setString(6, lnametxtField.getText());
            
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "register completed !");*/

            JSONObject jsonObject = new JSONObject();

            userName = usrRgstTxtfield.getText();
            String password = pwdrgstTxtfield.getText();
            String email = emailRgstTxtfield.getText();
            String fname = fnameTxtfield.getText();
            String lname = lnametxtField.getText();
            if (!userName.equals("") & !password.equals("") & !email.equals("")
                    & !fname.equals("") & !lname.equals("")) {

                jsonObject.put("msg", "register");
                jsonObject.put("username", userName);
                jsonObject.put("password", password);
                jsonObject.put("email", email);
                jsonObject.put("fname", fname);
                jsonObject.put("lname", lname);
                printStream.println(jsonObject.toString());
                printStream.flush();

            } else {
                JOptionPane.showMessageDialog(null, "Please Enter All Required Data...");
            }

            Thread th = new Thread(new Runnable() {

                @Override
                public void run() {
                    //while (true) {
                    try {
                        String str = dataInputStream.readLine();
                        UserDTO userDto = new Gson().fromJson(str, UserDTO.class);
                        if (str != null) {
                            if (userName.equals(userDto.usrname)) {

                                if (userDto.regResult.equals("failded")) {
                                    JOptionPane.showMessageDialog(null, "Username or Email already exist");
                                } else {
                                    System.out.println(userDto.usrname + "respond");

                                    JOptionPane.showMessageDialog(null, "user registered with username " + userDto.usrname);
                                    closeFrame();

                                }

                            }
                        }

                        //UserDTO userDto = new Gson().fromJson(str, UserDTO.class);
                        //System.out.println("aaaa" + str);

                        //if (userDto.key.equals("register")) {
                        //JOptionPane.showMessageDialog(null, "user registered with ID:" + userDto.usrID);
                        //}
                        else {
                            System.out.println("else");
                            close_frame();
                            //JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                            //break;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //}

                }
            });
            th.start();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_rgstBtnActionPerformed

    private void pwdrgstTxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwdrgstTxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwdrgstTxtfieldActionPerformed

    private void fnameTxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameTxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameTxtfieldActionPerformed
    public void closeFrame() {
        //this.dispose();
        LoginClient login = new LoginClient();
        login.setVisible(true);
        this.setVisible(false);

    }
 public void close_frame(){
    this.dispose();
    }
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
            java.util.logging.Logger.getLogger(RegisterClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterClient().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPnl;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField emailRgstTxtfield;
    private javax.swing.JLabel fnameLbl;
    private javax.swing.JTextField fnameTxtfield;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lnameLbl;
    private javax.swing.JTextField lnametxtField;
    private javax.swing.JLabel pwdrgstLbl;
    private javax.swing.JPasswordField pwdrgstTxtfield;
    private javax.swing.JButton rgstBtn;
    private javax.swing.JLabel suLbl;
    private javax.swing.JPanel subtnsPnl;
    private javax.swing.JLabel usrLbl;
    private javax.swing.JTextField usrRgstTxtfield;
    private javax.swing.JPanel wlcPnl;
    // End of variables declaration//GEN-END:variables
}

class UserDTO {

    public String key;
    public int senderId;
    public int receiverId;
    public int usrID;
    public String usrname;
    public String password;
    public String email;
    public String fname;
    public String lname;
    public String regResult;
}
