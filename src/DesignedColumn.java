import javax.swing.*;
import java.awt.*;

public class DesignedColumn extends JPanel {
    CircularLabel numberTask = new CircularLabel("", 60, Color.white, Color.BLACK, 16);

    public DesignedColumn(String name) {
        JLabel header = new JLabel(name);
        header.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 16));
        setBackground(null);
        header.setBounds(20, 20, name.length() * 5, 40);
        numberTask.setBounds(header.getX() + header.getWidth(), 20, 100, 10);
        RoundedButton addTask = new RoundedButton("+ Add New Task", 12, Color.WHITE, new Color(0x4a60b5), 13);
        addTask.setBounds(20, 80, getWidth(), 40);
        //TODO: add fun to Button


        add(header);
        add(numberTask);
        add(addTask);
        setVisible(true);
    }
}
