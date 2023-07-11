import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class CategoryPanel extends JPanel {
    ArrayList<TaskPanel> tasks;
    int lowestY = 10;
    public CategoryPanel() {
        tasks = new ArrayList<>();
        setVisible(true);
        setLayout(null);
    }

    public void addTask(TaskPanel task) {
        tasks.add(task);
    }

    public void removeTask(TaskPanel task) {
        tasks.remove(task);
    }

    public void showTasks() {
        lowestY = getY()+10;
        int categoryPanelX = getX();

        for (TaskPanel task : tasks) {
            task.setBounds(categoryPanelX + 10, lowestY + 5, 140, 130);
            lowestY += task.getHeight() + 5;
        }
        repaint();
    }



    @Override
    public Dimension getPreferredSize() {
        int width = super.getPreferredSize().width;
        int height = calculateTotalHeight();
        return new Dimension(width, height);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 30, 30);

        g2.setColor(Color.pink);
        g2.fill(shape);
        g2.setColor(Color.pink);
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();
    }
    private int calculateTotalHeight() {
        int totalHeight = 10;
        for (TaskPanel task : tasks) {
            totalHeight += task.getHeight() + 5; // Adjust the spacing between tasks
        }
        return totalHeight;
    }
}
