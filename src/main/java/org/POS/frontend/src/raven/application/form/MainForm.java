package org.POS.frontend.src.raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.open_cash.OpenCash;
import org.POS.backend.open_cash.OpenCashDAO;
import org.POS.backend.open_cash.OpenCashType;
import org.POS.backend.user.User;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.application.form.other.*;
import org.POS.frontend.src.raven.menu.Menu;
import org.POS.frontend.src.raven.menu.MenuAction;
import org.POS.frontend.src.zeusled.gui.Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Raven
 */
public class MainForm extends JLayeredPane {

    public MainForm() {
        init();
    }

    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        menu = new Menu();
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            switch (index) {
                case 0:
                    Application.showForm(new Dashboard());
                    break;
                case 1:
                    if (subIndex == 1) {
                        Application.showForm(new Expenses_Category());
                    } else if (subIndex == 2) {
                        Application.showForm(new Expenses_Sub_Category());
                    } else if (subIndex == 3) {
                        Application.showForm(new Expenses_List());
                    } else {
                        action.cancel();
                    }
                    break;
                case 2:
                    if (subIndex == 1) {
                        Application.showForm(new Purchases_List());
                    } else {
                        action.cancel();
                    }
                    break;
                case 3:
                    if (subIndex == 1) {
                        Application.showForm(new Quotation_List());
                    } else if (subIndex == 2) {
                        Application.showForm(new Invoice_List());
                    } else if (subIndex == 3) {
                        Application.showForm(new Order_List());
                    } else if (subIndex == 4) {
                        Application.showForm(new Return_List());
                    } else if (subIndex == 5) {
                        Application.showForm(new Cash_Transaction());
                    } else if (subIndex == 6) {
                        if (CurrentUser.isPosLoginSetup) {
                            Application.showForm(new POS());
                        } else {
                            Application.showForm(new BeforePOS());
                        }
                    } else {
                        action.cancel();
                    }
                    break;
                case 4:
                    Application.showForm(new Customer_List());
                    break;
                case 5:
                    Application.showForm(new Suppliers_List());
                    break;
                case 6:
                    Application.showForm(new User_List());
                    break;
                case 7:
                    if (subIndex == 1) {
                        Application.showForm(new Products_Category());
                    } else if (subIndex == 2) {
                        Application.showForm(new ProductList());
                    } else {
                        action.cancel();
                    }
                    break;
                case 8:
                    if (subIndex == 1) {
                        Application.showForm(new Inventory());
                    } else if (subIndex == 2) {
                        Application.showForm(new InventoryAdjustment());
                    } else {
                        action.cancel();
                    }
                    break;
                case 9:
                    Application.showForm(new Sales_Report());
                    break;
                case 10:
                    Application.showForm(new ProfitLoss_Report());
                    break;
                case 11:
                    Application.showForm(new Expense_Report());
                    break;
                case 12:
                    Application.showForm(new CollectionByUser_Report());
                    break;
                case 13:
                    Application.showForm(new Inventory_Report());
                    break;
                case 14:
                    Application.showForm(new CurrentValueandStock_Report());
                    break;
                case 15:
                    Application.showForm(new Settings());
                    break;
                case 16:
                    if (CurrentUser.isPosLoginSetup) {
                        try {
                            String result = JOptionPane.showInputDialog(null, "Close the Register for Final Cash", "Confirmation", JOptionPane.WARNING_MESSAGE);
                            double cashOut = Double.parseDouble(result);

                            CodeGeneratorService codeGeneratorService = new CodeGeneratorService();
                            // save here
                            UserDAO userDAO = new UserDAO();
                            OpenCashDAO openCashDAO = new OpenCashDAO();
                            User user = userDAO.getUserById(CurrentUser.id);

                            OpenCash openCash = new OpenCash();
                            openCash.setCash(BigDecimal.valueOf(cashOut));
                            openCash.setNote("Log Out");
                            openCash.setCode(codeGeneratorService.generateProductCode(GlobalVariable.OPEN_CASH_PREFIX));
                            openCash.setType(OpenCashType.OUT);
                            openCash.setDateTime(LocalDateTime.now());
                            openCash.setUser(user);

                            UserLog userLog = new UserLog();
                            userLog.setCode(openCash.getCode());
                            userLog.setDate(LocalDate.now());
                            userLog.setAction(UserActionPrefixes.OPEN_CASH_ACTION_LOG_PREFIX);
                            userLog.setUser(user);

                            openCashDAO.add(openCash, userLog);
                            CurrentUser.isPosLoginSetup = false;
                            Application.logout();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Make sure to Enter Number only.");
                        }
                    } else {
                        Application.logout();
                    }
                    break;
                default:
                    action.cancel();
                    break;
            }
        });
    }


    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) throws IOException {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}
