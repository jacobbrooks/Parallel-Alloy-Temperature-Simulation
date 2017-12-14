import java.awt.Color;
import java.util.ArrayList;

public class Screen {
	
	private int width, height;
	public int[] pixels;
	private Thread t;
	Control c;
	
	public Screen(int width, int height, Control c) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.c = c;
		t = new Thread(c);
		t.start();
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void render() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + (y * width)] = (int) Math.floor(c.b[y][x].getColor());
			}
		}
	}
}
