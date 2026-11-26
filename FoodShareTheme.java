package foodshare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Purpose: gives every screen of the app the same look (colours, header,
 * buttons and fields), so the whole project has a consistent design.
 *
 * How it is used: each screen calls these methods at the end of its
 * constructor, after initComponents(). For example:
 *
 *     FoodShareTheme.apply(this);  //colours the whole screen
 *     FoodShareTheme.styleTitle(titleLBL); //styles the page title
 *     FoodShareTheme.styleSecondary(backBTN);  //outline style for Back
 *     FoodShareTheme.styleDanger(deleteBTN);   //red style for Delete or Exit
 *
 * All methods are static, so we never need to create a FoodShareTheme object.
 * We call them directly with the class name, like Math.max().
 */
public class FoodShareTheme {

    
    //1. COLOURS
    //Each colour is created once and reused by every screen.
       
    public static final Color FOREST = new Color(31, 58, 46); //dark green: header and main buttons
    public static final Color BRASS = new Color(196, 154, 82); //gold, based on the SDG 2 colour: details
    public static final Color IVORY = new Color(247, 243, 234); //light cream: screen background
    public static final Color INK = new Color(36, 40, 37); //dark grey: normal text
    public static final Color LINE = new Color(214, 206, 190); //beige: borders of fields
    public static final Color TERRACOTTA = new Color(168, 74, 52); //red: Delete and Exit buttons

    
    //2. APPLY THE THEME TO A WHOLE SCREEN
    //Called once per screen. It adds the header, colours the background
    //and then styles every component on the screen.
    
    public static void apply(JFrame frame) {

        //Step 1: keep the screen made in the NetBeans designer.
        //getContentPane() returns the panel that holds all the components.
        Container screen = frame.getContentPane();
        screen.setBackground(IVORY);

        //Step 2: create the header, a label with the app name.
        JLabel header = new JLabel("FoodShare");
        header.setFont(new Font("Georgia", Font.BOLD, 20));
        header.setForeground(IVORY);
        header.setBackground(FOREST);
        header.setOpaque(true);   //a label is transparent by default; this makes its background visible
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, BRASS),     //gold line under the header
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));      //space around the text

        //Step 3: build a new main panel with BorderLayout.
        //NORTH = the header at the top, CENTER = the original screen.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(screen, BorderLayout.CENTER);
        frame.setContentPane(mainPanel);

        //Step 4: style every component on the original screen.
        styleComponents(screen);

        //Step 5: resize the window to fit the header and centre it.
        frame.pack();
        frame.setLocationRelativeTo(null);

        //Step 6: resize once more, just after the window appears on screen.
        //Some sizes are only final when the window is visible, so without this
        //the bottom of a few screens could be cut off by a few pixels.
        //invokeLater() means "run this as soon as the window has opened".
        javax.swing.SwingUtilities.invokeLater(() -> {
            frame.pack();
            frame.setLocationRelativeTo(null);
        });
    }

    
    //3. STYLE EVERY COMPONENT
    //Goes through the components one by one. "instanceof" checks the
    //type of each component, so each type gets its own style.
    
    private static void styleComponents(Container container) {
        for (Component c : container.getComponents()) {

            if (c instanceof JButton) {
                stylePrimary((JButton) c); //every button starts green; Back and Delete are changed later

            } else if (c instanceof JLabel) {
                c.setForeground(INK); //dark text

            } else if (c instanceof JTextField) {
                styleField((JTextField) c); //white field with a beige border

            } else if (c instanceof JComboBox) {
                c.setBackground(Color.WHITE); //white drop-down list

            } else if (c instanceof JRadioButton || c instanceof JCheckBox) {
                c.setForeground(INK);
                ((JComponent) c).setOpaque(false); //transparent, so the cream background shows

            } else if (c instanceof JScrollPane) {
                styleScrollPane((JScrollPane) c); //text areas and tables live inside a scroll pane
            }
        }
    }

    
    //4. STYLES THE SCREENS CALL DIRECTLY
    

    //Page title: dark green text with a gold line underneath.
    public static void styleTitle(JLabel label) {
        label.setForeground(FOREST);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, BRASS), //the gold line
                BorderFactory.createEmptyBorder(0, 2, 6, 2))); //space between text and line
    }

    //Main buttons (Save, Search, menu buttons): green with light text.
    public static void stylePrimary(JButton button) {
        styleButton(button, FOREST, IVORY);
    }

    //Back and Clear buttons: light background with a green outline,
    //so they look less important than the main action.
    public static void styleSecondary(JButton button) {
        styleButton(button, IVORY, FOREST);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FOREST),               //green outline
                BorderFactory.createEmptyBorder(5, 11, 5, 11)));      //space inside the button
    }

    //Delete and Exit buttons: red, to warn the user.
    public static void styleDanger(JButton button) {
        styleButton(button, TERRACOTTA, IVORY);
    }

    
    // 5. HELPER METHODS (private: only used inside this class)
    

    //Shared settings for every button. Only the colours change.
    private static void styleButton(JButton button, Color background, Color text) {
        button.setBackground(background);
        button.setForeground(text);
        button.setOpaque(true); //makes the background colour visible
        button.setFocusPainted(false); //removes the dotted box around the clicked button
        button.setBorderPainted(false); //flat button, without the default 3D border
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //hand cursor over the button
    }

    //Text fields: white background, beige border and some inner space.
    private static void styleField(JTextField field) {
        field.setBackground(Color.WHITE);
        field.setForeground(INK);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                BorderFactory.createEmptyBorder(3, 6, 3, 6)));
    }

    //Scroll panes hold either a text area (Donations) or a table
    //(Food Banks and Requests). getViewport().getView() returns that component.
    private static void styleScrollPane(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(LINE));
        Component inside = scroll.getViewport().getView();

        if (inside instanceof JTextArea) {
            JTextArea area = (JTextArea) inside;
            area.setLineWrap(true); //long lines continue on the next line
            area.setWrapStyleWord(true); //lines break between words, not in the middle of a word
            area.setMargin(new java.awt.Insets(8, 10, 8, 10)); //space around the text
        }

        if (inside instanceof JTable) {
            JTable table = (JTable) inside;
            table.setRowHeight(24); //taller rows, easier to read
            table.setGridColor(LINE); //beige lines between cells
            table.getTableHeader().setBackground(FOREST); //green column titles
            table.getTableHeader().setForeground(IVORY);
        }
    }
}