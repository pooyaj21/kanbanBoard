import java.awt.*;

public enum TaskTypes {
    NOTHING("",new Color(0xf7f7f7)),
    ANDROID("Android",new Color(0x78C257)),
    IOS("IOS", new Color(0x555555)),
    BACK_END("BackEnd",new Color(0x004e98));

    private final String name;
    private final Color color;

    TaskTypes(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
