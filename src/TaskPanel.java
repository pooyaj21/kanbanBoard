import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class TaskPanel extends JPanel {
    private CategoryPanel currentColumn;
    private Point offset;
    private final JLabel title = new JLabel();

    public TaskPanel(CategoryPanel categoryPanel, KanbanBoardPanel kanbanBoardPanel) {
        currentColumn = categoryPanel;
        setLayout(null);
        title.setBounds(10, 45, 140, 13);
        title.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.BOLD, 13));
        add(title);
        JLabel setting = new JLabel("⋮");
        title.setBounds(130, 15, 140, 13);
        title.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 13));
        add(setting);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE); // Set background color to white
        setOpaque(false); // Make the panel background transparent

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
                getParent().setComponentZOrder(TaskPanel.this, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                double mouseX = e.getXOnScreen(); // Get the mouse X coordinate on the screen
                double panelX = kanbanBoardPanel.getLocationOnScreen().getX(); // Get the panel's X coordinate on the screen
                double columnWidth = kanbanBoardPanel.getWidth() / 5.0; // Divide the width into 5 equal parts
                double whichColumn = (mouseX - panelX) / columnWidth; // Calculate which column the mouse is released in

                if (currentColumn != null) {
                    kanbanBoardPanel.removeTask(TaskPanel.this, currentColumn);
                }

                CategoryPanel newColumn = null;

                if (whichColumn < 1) {
                    newColumn = kanbanBoardPanel.backLog;
                } else if (whichColumn >= 1 && whichColumn < 2) {
                    newColumn = kanbanBoardPanel.toDo;
                } else if (whichColumn >= 2 && whichColumn < 3) {
                    newColumn = kanbanBoardPanel.inProgress;
                } else if (whichColumn >= 3 && whichColumn < 4) {
                    newColumn = kanbanBoardPanel.needReview;
                } else if (whichColumn >= 4 && whichColumn < 5) {
                    newColumn = kanbanBoardPanel.done;
                }

                if (newColumn != null) {
                    currentColumn = newColumn;
                    kanbanBoardPanel.addTask(TaskPanel.this, currentColumn);
                }

                kanbanBoardPanel.reset();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = getX() + e.getX() - offset.x;
                int y = getY() + e.getY() - offset.y;
                setLocation(x, y);
                getParent().repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 40, 40);

        g2.setColor(getBackground());
        g2.fill(shape);
        Stroke borderStroke = new BasicStroke(0f);
        g2.setStroke(borderStroke);
        g2.setColor(getForeground());
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();
    }

    public void setTitle(String theTitle) {
        title.setText(theTitle);
    }
}
