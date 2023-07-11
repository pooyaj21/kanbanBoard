import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class CircularLabel extends JLabel {
    private Color backgroundColor;
    private int cornerRadius;

    public CircularLabel(String text, int cornerRadius, Color backgroundColor, Color foregroundColor, int fontSize) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        setForeground(foregroundColor);
        setOpaque(false);
        setFont(new Font("assets/Montserrat-ExtraLight.ttf", Font.PLAIN, fontSize));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Shape shape = new Ellipse2D.Double(0, 0, width, height);

        g2.setColor(backgroundColor);
        g2.fill(shape);
        Stroke borderStroke = new BasicStroke(0f);
        g2.setStroke(borderStroke);
        g2.setColor(getForeground());
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        int diameter = Math.max(super.getPreferredSize().width, super.getPreferredSize().height);
        return new Dimension(diameter, diameter);
    }
}
