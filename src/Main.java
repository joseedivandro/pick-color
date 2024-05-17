import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("src/santos.png")); 
            Color predominantColor = getPredominantColor(image);
            System.out.println("Cor predominante: " + predominantColor);
        } catch (IOException e) {
            System.err.println("Erro ao ler a imagem: " + e.getMessage());
        }
    }

    private static Color getPredominantColor(BufferedImage image) {
        Map<Integer, Integer> colorMap = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();
    
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff; 
                if (alpha != 0) { 
                    colorMap.merge(rgb, 1, Integer::sum);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> sortedEntries = colorMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            Color color = new Color(entry.getKey());
            if (!(color.equals(Color.BLACK) || color.equals(Color.WHITE))) {
                return color;
            }
        }

        return Color.BLACK; 
    }
}
