package foodshare;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Purpose: one shared visual theme for every screen. It adds a dark
 * header with the FoodShare logo, rounded buttons and styled fields,
 * without changing the layout made in the NetBeans designer.
 */
public class FoodShareTheme {

    //Colour palette
    public static final Color FOREST = new Color(31, 58, 46); //header, main buttons
    public static final Color FOREST_HOVER = new Color(47, 82, 66); //main buttons, mouse over
    public static final Color FOREST_PRESSED = new Color(22, 42, 33); //main buttons, clicked
    public static final Color BRASS = new Color(196, 154, 82); //accents, based on the SDG 2 gold
    public static final Color IVORY = new Color(247, 243, 234); //screen background
    public static final Color SAGE_LIGHT = new Color(226, 232, 220); //hover on outline buttons, selections
    public static final Color INK = new Color(36, 40, 37); //normal text
    public static final Color LINE = new Color(214, 206, 190); //field borders
    public static final Color TERRACOTTA = new Color(168, 74, 52); //delete button
    public static final Color TERRACOTTA_HOVER = new Color(190, 92, 68);

    //Header size
    private static final int HEADER_HEIGHT = 52;

    //Applies the theme to a whole screen.
    //Call it at the end of the constructor, after initComponents().
    public static void apply(JFrame frame) {
        UIManager.put("ComboBox.selectionBackground", SAGE_LIGHT);
        UIManager.put("ComboBox.selectionForeground", INK);

        // The content pane becomes transparent and gets empty space at the
        // top, where the header is painted
        JComponent content = (JComponent) frame.getContentPane();
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(HEADER_HEIGHT + 6, 12, 12, 12));

        // A background panel is placed in a layer BEHIND the screen's components
        BackgroundPanel background = new BackgroundPanel();
        JLayeredPane layers = frame.getLayeredPane();
        layers.add(background, Integer.valueOf(JLayeredPane.FRAME_CONTENT_LAYER - 1));
        layers.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                background.setBounds(0, 0, layers.getWidth(), layers.getHeight());
            }
        });

        styleAll(content);
        resize(frame, background);

        //Some sizes are only final once the window is on screen, so the window is resized again just after it opens
        javax.swing.SwingUtilities.invokeLater(() -> resize(frame, background));
    }

    private static void resize(JFrame frame, BackgroundPanel background) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        JLayeredPane layers = frame.getLayeredPane();
        background.setBounds(0, 0, layers.getWidth(), layers.getHeight());
    }

    //Goes through every component and styles it according to its type.
    
    private static void styleAll(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                stylePrimary((JButton) c);
            } else if (c instanceof JLabel) {
                c.setForeground(INK);
            } else if (c instanceof JTextField) {
                styleField((JTextField) c);
            } else if (c instanceof JComboBox) {
                styleCombo((JComboBox<?>) c);
                continue;   // its inner arrow button must not become a green button
            } else if (c instanceof JRadioButton || c instanceof JCheckBox) {
                c.setForeground(INK);
                ((JComponent) c).setOpaque(false);
            } else if (c instanceof JScrollPane) {
                styleScroll((JScrollPane) c);
                continue;   //the scroll bar is styled separately
            }
            if (c instanceof Container) {
                styleAll((Container) c);   //components inside panels
            }
        }
    }

    //Public methods used by each screen

    //Title in forest green with a short brass line underneath
    public static void styleTitle(JLabel label) {
        label.setForeground(FOREST);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, BRASS),
                BorderFactory.createEmptyBorder(0, 2, 6, 2)));
    }

    //Main actions (Save, Search, menu buttons): solid forest green
    public static void stylePrimary(JButton button) {
        styleButton(button, new RoundedButtonUI(FOREST, FOREST_HOVER, FOREST_PRESSED, null), IVORY);
    }

    //Back buttons: only an outline, so they look less important than the main action
    public static void styleSecondary(JButton button) {
        styleButton(button, new RoundedButtonUI(IVORY, SAGE_LIGHT, LINE, FOREST), FOREST);
    }

    //Delete buttons: terracotta, to warn the user
    public static void styleDanger(JButton button) {
        styleButton(button, new RoundedButtonUI(TERRACOTTA, TERRACOTTA_HOVER, FOREST_PRESSED, null), IVORY);
    }

    //Private helpers

    private static void styleButton(JButton button, RoundedButtonUI ui, Color text) {
        button.setUI(ui);
        button.setForeground(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static void styleField(JTextField field) {
        field.setBackground(Color.WHITE);
        field.setForeground(INK);
        field.setCaretColor(FOREST);
        field.setBorder(fieldBorder(LINE));
        //The border turns green while the user is typing in the field
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(fieldBorder(FOREST));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(fieldBorder(LINE));
            }
        });
    }

    private static Border fieldBorder(Color colour) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colour),
                BorderFactory.createEmptyBorder(3, 6, 3, 6));
    }

    private static void styleCombo(JComboBox<?> combo) {
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                // Small arrow in forest green on an ivory background
                JButton arrow = new BasicArrowButton(SwingConstants.SOUTH, IVORY, IVORY, FOREST, IVORY);
                arrow.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, LINE));
                return arrow;
            }
        });
        combo.setBackground(Color.WHITE);
        combo.setForeground(INK);
        combo.setBorder(BorderFactory.createLineBorder(LINE));
    }

    private static void styleScroll(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(LINE));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUI(new ThinScrollBarUI());
        scroll.getHorizontalScrollBar().setUI(new ThinScrollBarUI());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        Component inside = scroll.getViewport().getView();
        if (inside instanceof JTextArea) {
            JTextArea area = (JTextArea) inside;
            area.setBackground(Color.WHITE);
            area.setForeground(INK);
            area.setLineWrap(true);        //long lines continue on the next line
            area.setWrapStyleWord(true);   //and break between words
            area.setMargin(new java.awt.Insets(10, 12, 10, 12));
        }
        if (inside instanceof JTable) {
            styleTable((JTable) inside);
        }
    }

    //Tables: forest green header, taller rows and a light green selection
    private static void styleTable(JTable table) {
        table.setRowHeight(24);
        table.setGridColor(LINE);
        table.setSelectionBackground(SAGE_LIGHT);
        table.setSelectionForeground(INK);
        table.getTableHeader().setBackground(FOREST);
        table.getTableHeader().setForeground(IVORY);
        table.getTableHeader().setOpaque(true);
    }

    //Turns on smooth edges for shapes and text
    private static Graphics2D smooth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    //Inner classes

    //Paints a button as a rounded rectangle. The colour changes when the
    //mouse is over the button (rollover) and when it is clicked (pressed).
    private static class RoundedButtonUI extends BasicButtonUI {
        private final Color normal;
        private final Color hover;
        private final Color pressed;
        private final Color outline;   // null means no outline

        RoundedButtonUI(Color normal, Color hover, Color pressed, Color outline) {
            this.normal = normal;
            this.hover = hover;
            this.pressed = pressed;
            this.outline = outline;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Graphics2D g2 = smooth(g);

            Color fill = normal;
            if (model.isPressed()) {
                fill = pressed;
            } else if (model.isRollover()) {
                fill = hover;
            }
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 12, 12);

            if (outline != null) {
                g2.setColor(outline);
                g2.setStroke(new BasicStroke(1.4f));
                g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 12, 12);
            }
            g2.dispose();
            super.paint(g, c);   //the parent class draws the button text
        }
    }

    //Paints the background and the header behind the components
    private static class BackgroundPanel extends JPanel {

        BackgroundPanel() {
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = smooth(g);
            int w = getWidth();
            int h = getHeight();

            //Ivory background
            g2.setColor(IVORY);
            g2.fillRect(0, 0, w, h);

            //Dark header with a thin brass line at the bottom
            g2.setColor(FOREST);
            g2.fillRect(0, 0, w, HEADER_HEIGHT);
            g2.setColor(BRASS);
            g2.fillRect(0, HEADER_HEIGHT - 3, w, 3);

            //Logo: wheat icon and the app name on the left
            drawWheat(g2, 22, HEADER_HEIGHT / 2 - 2);
            g2.setColor(IVORY);
            g2.setFont(new Font("Georgia", Font.BOLD, 18));
            FontMetrics fm = g2.getFontMetrics();
            int textY = (HEADER_HEIGHT - 3 + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString("FoodShare", 40, textY);

            g2.dispose();
        }

        //Draws a simple wheat ear: a stem with grains on both sides
        private void drawWheat(Graphics2D g2, int x, int y) {
            g2.setColor(BRASS);
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawLine(x, y + 12, x, y - 10);
            for (int i = 0; i < 4; i++) {
                int gy = y - 6 + i * 5;
                drawGrain(g2, x - 3.5, gy, -0.6);
                drawGrain(g2, x + 3.5, gy, 0.6);
            }
            drawGrain(g2, x, y - 11, 0);
        }

        private void drawGrain(Graphics2D g2, double cx, double cy, double angle) {
            AffineTransform old = g2.getTransform();
            g2.rotate(angle, cx, cy);
            g2.fill(new Ellipse2D.Double(cx - 2, cy - 3.5, 4, 7));
            g2.setTransform(old);
        }
    }

    //A slim scroll bar without arrow buttons
    private static class ThinScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = LINE;
            trackColor = Color.WHITE;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return emptyButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return emptyButton();
        }

        private JButton emptyButton() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0, 0));
            return b;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, java.awt.Rectangle r) {
            if (r.isEmpty()) {
                return;
            }
            Graphics2D g2 = smooth(g);
            g2.setColor(thumbColor);
            g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 6, 6);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, java.awt.Rectangle r) {
            g.setColor(trackColor);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
}
