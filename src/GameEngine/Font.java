package GameEngine;

public class Font {
    public static final Font STANDARD = new Font("/Resources/font.png");
    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path) {
        fontImage = new Image(path);
        offsets = new int[59];
        widths = new int[59];
        int unicode = 0;
        for (int i = 0; i < fontImage.getWidth(); ++i) {
            if (fontImage.getPixels()[i] == 0xff0000ff) {
                offsets[unicode] = i;//salveaza la ce pixel incepe litera respectiva
            }
            if (fontImage.getPixels()[i] == 0xffffff00) {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }
    public int[] getOffsets() {
        return offsets;
    }
    public int[] getWidths() {
        return widths;
    }
}
