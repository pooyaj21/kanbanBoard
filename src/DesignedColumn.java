import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesignedColumn extends JPanel {
    CircularLabel numberTask = new CircularLabel("", 60, Color.white, Color.BLACK, 16);
    TaskPanel taskPanel;

    public DesignedColumn(String name, CategoryPanel categoryPanel, KanbanBoardPanel kanbanBoardPanel, Color colorDot) {
        JLabel header = new JLabel(name);
        JLabel theDot = new JLabel("•");
        header.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 15));
        header.setHorizontalAlignment(SwingConstants.LEFT);
        theDot.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 18));
        theDot.setForeground(colorDot);
        theDot.setHorizontalAlignment(SwingConstants.LEFT);
        setBackground(null);
        setLayout(null);
        theDot.setBounds(0, 0, 10, 50);
        header.setBounds(15, 7, 180, 40);


        numberTask.setBounds(name.length()*5+50, 12, 35, 30);
        RoundedButton addTask = new RoundedButton("+ Add New Task", 12, Color.white, new Color(0x4a60b5), 12);
        addTask.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.BOLD, 12));
        addTask.setBounds(5, 47, 190, 32);
        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskPanel = new TaskPanel(categoryPanel, kanbanBoardPanel);
                kanbanBoardPanel.add(taskPanel);
                kanbanBoardPanel.addTask(taskPanel, categoryPanel);
                kanbanBoardPanel.repaint(); // Repaint the container
                kanbanBoardPanel.reset();
            }
        });

        add(numberTask);
        add(header);
        add(theDot);

        add(addTask);
        setVisible(true);
    }
}
