import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskPanel extends JPanel {
    private final String task;
    private CategoryPanel currentColumn;
    private Point offset;

    public TaskPanel(String task ,KanbanBoardPanel kanbanBoardPanel) {
        this.task = task;
        currentColumn = kanbanBoardPanel.backLog;
        setBorder(BorderFactory.createEtchedBorder());
        add(new JLabel(task));



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
}
