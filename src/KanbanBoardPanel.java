import javax.swing.*;
import java.awt.*;


public class KanbanBoardPanel extends JPanel {
    final CategoryPanel backLog = new CategoryPanel();
    final DesignedColumn bacLogD = new DesignedColumn("Back Log", backLog, this,Color.BLACK);
    final CategoryPanel toDo = new CategoryPanel();
    final DesignedColumn toDoD = new DesignedColumn("To Do", toDo, this,Color.red);
    final CategoryPanel inProgress = new CategoryPanel();
    final DesignedColumn inProgressD = new DesignedColumn("In Progress", inProgress, this,Color.BLUE);
    final CategoryPanel needReview = new CategoryPanel();
    final DesignedColumn needReviewD = new DesignedColumn("Need Review", needReview, this,Color.yellow);
    final CategoryPanel done = new CategoryPanel();
    final DesignedColumn doneD = new DesignedColumn("Done", done, this,Color.GREEN);
    int panelWidth = 160;

    public KanbanBoardPanel() {
        setLayout(null);
        setBackground(new Color(0xf7f7f7));
        int panelHeight = getHeight();

        backLog.setBounds(0, 75, panelWidth, panelHeight);
        toDo.setBounds(panelWidth, 75, panelWidth, panelHeight);
        inProgress.setBounds(panelWidth * 2, 75, panelWidth, panelHeight);
        needReview.setBounds(panelWidth * 3, 75, panelWidth, panelHeight);
        done.setBounds(panelWidth * 4, 75, panelWidth, panelHeight);


        bacLogD.setBounds(0, 0, panelWidth, 80);
        toDoD.setBounds(panelWidth, 0, panelWidth, 80);
        inProgressD.setBounds(panelWidth * 2, 0, panelWidth, 80);
        needReviewD.setBounds(panelWidth * 3, 0, panelWidth, 80);
        doneD.setBounds(panelWidth * 4, 0, panelWidth, 80);


        add(backLog);
        add(toDo);
        add(inProgress);
        add(needReview);
        add(done);


        add(bacLogD);
        add(toDoD);
        add(inProgressD);
        add(needReviewD);
        add(doneD);
        reset();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kanban Board");
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            KanbanBoardPanel kanbanBoardPanel = new KanbanBoardPanel();
            kanbanBoardPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            frame.add(kanbanBoardPanel);
            frame.setVisible(true);
        });
    }

    public void reset() {
        backLog.showTasks();
        bacLogD.numberTask.setText(String.valueOf(backLog.tasks.size()));
        toDo.showTasks();
        toDoD.numberTask.setText(String.valueOf(toDo.tasks.size()));
        inProgress.showTasks();
        inProgressD.numberTask.setText(String.valueOf(inProgress.tasks.size()));
        needReview.showTasks();
        needReviewD.numberTask.setText(String.valueOf(needReview.tasks.size()));
        done.showTasks();
        doneD.numberTask.setText(String.valueOf(done.tasks.size()));
    }

    public void addTask(TaskPanel task, CategoryPanel panel) {
        panel.addTask(task);
        reset();
    }

    public void removeTask(TaskPanel task, CategoryPanel panel) {
        panel.removeTask(task);
        reset();
    }
}
