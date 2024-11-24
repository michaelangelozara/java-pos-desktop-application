package org.POS.frontend.src.raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.user.LoginRequestDto;
import org.POS.backend.user.UserService;
import org.POS.frontend.src.raven.application.Application;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginForm extends javax.swing.JPanel {

    public LoginForm() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new org.POS.frontend.src.raven.application.form.PanelLogin();
        lbTitle1 = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();
        lbTitle2 = new javax.swing.JLabel();

        lbTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/logogroup.png"))); // NOI18N
        panelLogin1.add(lbTitle1);

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Login");
        panelLogin1.add(lbTitle);

        lbUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbUser.setText("Username");
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbPass.setText("Password");
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        cmdLogin.setBackground(new java.awt.Color(235, 161, 132));
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cmdLogin.setForeground(new java.awt.Color(255, 255, 255));
        cmdLogin.setText("Login");
        cmdLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdLogin);

        lbTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelLogin1.add(lbTitle2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginActionPerformed
        // for default after user logs out
        String username = this.txtUser.getText();
        String password = this.txtPass.getText();
        LoginRequestDto dto = new LoginRequestDto(username, password);
        UserService userService = new UserService();

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    cmdLogin.setText("Logging in ...");
                    cmdLogin.setEnabled(false);
                    txtUser.setEnabled(false);
                    txtPass.setEnabled(false);
                });
                return userService.authenticate(dto);
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    cmdLogin.setEnabled(true);
                    cmdLogin.setText("Login");
                    txtUser.setEnabled(true);
                    txtPass.setEnabled(true);
                });
                try {
                    boolean isAuthenticate = get();

                    if (isAuthenticate) {
                        JOptionPane.showMessageDialog(null, GlobalVariable.USER_LOGGED_IN, "Login Successful", JOptionPane.PLAIN_MESSAGE);
                        Application.login();
                    } else {
                        JOptionPane.showMessageDialog(null, GlobalVariable.USER_INVALID_CREDENTIAL, "Login Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred during login.", "Login Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        worker.execute();
    }//GEN-LAST:event_cmdLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdLogin;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTitle2;
    private javax.swing.JLabel lbUser;
    private org.POS.frontend.src.raven.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
