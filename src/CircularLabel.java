import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class CircularLabel extends JLabel {
    private Color backgroundColor;
    private int cornerRadius;

    public CircularLabel(String text, int cornerRadius, Color backgroundColor, Color foregroundColor, int fontSize) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.cornerRadius = cornerRadius;
        this.setFont(new Font("Arial", Font.TRUETYPE_FONT, fontSize));
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(foregroundColor);
        setOpaque(false);
        setForeground(foregroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.setColor(backgroundColor);
        g2.fill(shape);
        g2.setColor(backgroundColor);
        g2.draw(shape);

        super.paintComponent(g2);
        g2.dispose();
    }
}
