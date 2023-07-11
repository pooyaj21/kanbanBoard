import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    private Color backgroundColor;
    private Color foregroundColor;
    private int cornerRadius;

    public RoundedButton(String text, int cornerRadius, Color backgroundColor, Color foregroundColor, int fontSize) {
        super(text);
        this.backgroundColor =backgroundColor;
        this.foregroundColor =foregroundColor;
        this.cornerRadius = cornerRadius;
        this.setFont(new Font("Arial", Font.TRUETYPE_FONT, fontSize));
        setContentAreaFilled(false);
        setFocusPainted(false);
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
        g2.setColor(foregroundColor);
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textX = (width - fontMetrics.stringWidth(getText())) / 2;
        int textY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        g2.drawString(getText(), textX, textY);
        g2.dispose();
    }

}
