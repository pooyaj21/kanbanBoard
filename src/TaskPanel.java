import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class TaskPanel extends JPanel {
    private final JLabel title = new JLabel();
    private final JLabel theText = new JLabel();
    private final JPanel settingPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            Shape shape = new RoundRectangle2D.Double(0, 0, width, height, 30, 30);
            g2.setColor(Color.white);
            g2.fill(shape);
            g2.setColor(Color.BLACK);
            g2.dispose();
        }
    };
    private CategoryPanel currentColumn;
    private Point offset;
    private int currentTask = 0;
    private boolean isSettingOpen = false;
    private boolean isEditable = false;


    public TaskPanel(CategoryPanel categoryPanel, KanbanBoardPanel kanbanBoardPanel) {
        currentColumn = categoryPanel;
        setBackground(Color.white);
        setLayout(null);
        setTitle("the title");//test
        setText("the text that is so big you cant handel it so you must handel it but you cant you must handel it  you must do it but you cant but you  should");//test


        add(settingPanel);
        settingPanel.setVisible(false);
        settingPanel.setLayout(null);

        RoundedButton editButton = new RoundedButton("Edit", 0, getBackground(), getForeground(), 13);
        editButton.setBounds(0, 0, 100, 30);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingPanel.setVisible(false);
                isSettingOpen = false;
                isEditable = true;
            }
        });
        settingPanel.add(editButton);

        RoundedButton removeButton = new RoundedButton("Remove", 0, getBackground(), getForeground(), 13);
        removeButton.setBounds(0, 30, 100, 30);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskPanel.this.setVisible(false);
                currentColumn.removeTask(TaskPanel.this);
                kanbanBoardPanel.reset();
            }
        });
        settingPanel.add(removeButton);


        title.setBounds(10, 30, 120, 10);
        title.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.BOLD, 13));
        add(title);

        RoundedButton setting = new RoundedButton("⋮", 30, this.getBackground(), Color.BLACK, 22);
        setting.setBounds(120, 5, 15, 18);
        setting.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.BOLD, 22));
        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isSettingOpen) {
                    Point buttonLocation = setting.getLocationOnScreen();
                    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                    int mouseX = mouseLocation.x - buttonLocation.x;
                    int mouseY = mouseLocation.y - buttonLocation.y;

                    settingPanel.setBounds(mouseX + 20, mouseY, 100, 60);
                    settingPanel.setVisible(true);
                    isSettingOpen = true;
                } else {
                    settingPanel.setVisible(false);
                    isSettingOpen = false;
                }
            }
        });


        add(setting);

        theText.setBounds(10, 40, 120, 80);
        theText.setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, 10));
        theText.setHorizontalAlignment(SwingConstants.LEFT);
        add(theText);


        RoundedButton taskType = new RoundedButton(TaskTypes.values()[currentTask].getName(), 17, TaskTypes.values()[currentTask].getColor(), Color.WHITE, 11);
        taskType.setBounds(10, 5, 50, 20);
        taskType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTask == 3) currentTask = 0;
                else currentTask++;
                taskType.setBackgroundColor(TaskTypes.values()[currentTask].getColor());
                taskType.setText(TaskTypes.values()[currentTask].getName());
            }
        });
        add(taskType);

        setComponentZOrder(settingPanel, 0);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE); // Set background color to white
        setOpaque(false); // Make the panel background transparent


        kanbanBoardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                SwingUtilities.convertPointToScreen(clickPoint, kanbanBoardPanel);
                SwingUtilities.convertPointFromScreen(clickPoint, TaskPanel.this);
                if (!TaskPanel.this.contains(clickPoint)) {
                    settingPanel.setVisible(false);
                    isSettingOpen = false;
                    isEditable = false;
                }
            }
        });

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

    public void setText(String text) {
        int lineLength = 24;
        int currentIndex = 0;
        int textLength = text.length();
        StringBuilder sb = new StringBuilder(text);

        while (currentIndex < textLength) {
            int nextIndex = currentIndex + lineLength;

            if (nextIndex < textLength) {
                int spaceIndex = sb.lastIndexOf(" ", nextIndex);
                if (spaceIndex != -1) {
                    sb.insert(spaceIndex + 1, "<br>");
                    currentIndex = spaceIndex + 1;
                } else {
                    sb.insert(nextIndex + 1, "<br>");
                    currentIndex = nextIndex + 1;
                }
            } else {
                break;
            }
        }
        text = sb.toString();
        theText.setText("<html>" + "<br>" + text + "</html>");
    }
}


