package org.POS.frontend.src.raven.application.form.other;


import net.miginfocom.swing.MigLayout;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.open_cash.OpenCash;
import org.POS.backend.open_cash.OpenCashDAO;
import org.POS.backend.open_cash.OpenCashType;
import org.POS.backend.string_checker.StringChecker;
import org.POS.backend.user.User;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;
import org.POS.frontend.src.raven.application.Application;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class AfterPOS extends JPanel {

    private CodeGeneratorService codeGeneratorService;

    public AfterPOS() {
        codeGeneratorService = new CodeGeneratorService();
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("al center center"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new org.POS.frontend.src.raven.application.form.PanelLogin();
        lbTitle1 = new JLabel();
        lbTitle = new JLabel();
        lbUser = new JLabel();
        txtUser = new JTextField();
        lbUser1 = new JLabel();
        txtUser1 = new JTextField();
        cmdLogin = new JButton();
        lbTitle2 = new JLabel();

        lbTitle1.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle1.setIcon(new ImageIcon(getClass().getResource("/png/logogroup.png"))); // NOI18N
        panelLogin1.add(lbTitle1);

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle.setText("Close Cash ");
        panelLogin1.add(lbTitle);

        lbUser.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbUser.setText("Final Cash");
        panelLogin1.add(lbUser);

        txtUser.setMinimumSize(new java.awt.Dimension(64, 40));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        panelLogin1.add(txtUser);

        lbUser1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbUser1.setText("Note");
        panelLogin1.add(lbUser1);

        txtUser1.setMinimumSize(new java.awt.Dimension(64, 40));
        txtUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser1ActionPerformed(evt);
            }
        });
        panelLogin1.add(txtUser1);

        cmdLogin.setBackground(new java.awt.Color(235, 161, 132));
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cmdLogin.setForeground(new java.awt.Color(255, 255, 255));
        cmdLogin.setText("Save");
        cmdLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdLogin);

        lbTitle2.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogin1.add(lbTitle2);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(panelLogin1, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(141, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(panelLogin1, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginActionPerformed
        try {
            BigDecimal balance = new BigDecimal(txtUser.getText());
            String note = txtUser1.getText();

            if (!StringChecker.isNumericOnly(String.valueOf(balance.setScale(2, RoundingMode.HALF_UP)))) {
                JOptionPane.showMessageDialog(null, "Any letter or special character is not allowed");
                return;
            }

            // save here
            UserDAO userDAO = new UserDAO();
            OpenCashDAO openCashDAO = new OpenCashDAO();
            User user = userDAO.getUserById(CurrentUser.id);
            OpenCash openCash = new OpenCash();
            openCash.setCash(balance);
            openCash.setNote(note);
            openCash.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.OPEN_CASH_PREFIX));
            openCash.setType(OpenCashType.OUT);
            openCash.setDateTime(LocalDateTime.now());
            user.addOpenCash(openCash);

            UserLog userLog = new UserLog();
            userLog.setCode(openCash.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.CLOSE_CASH_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            openCashDAO.add(openCash, userLog);
            CurrentUser.isPosLoginSetup = false;
            Application.logout();

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please Enter Final Cash");
        }
    }//GEN-LAST:event_cmdLoginActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton cmdLogin;
    private JLabel lbTitle;
    private JLabel lbTitle1;
    private JLabel lbTitle2;
    private JLabel lbUser;
    private JLabel lbUser1;
    private org.POS.frontend.src.raven.application.form.PanelLogin panelLogin1;
    private JTextField txtUser;
    private JTextField txtUser1;
    // End of variables declaration//GEN-END:variables
}