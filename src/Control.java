import java.util.concurrent.CyclicBarrier;

public class Control implements Runnable{

	public Region[][] a;
	public Region[][] b;
	private int height;
	
	private double c1;
	private double c2;
	private double c3;
	
	private int threshold;
	
	private Jacobi j;
	
	public Control(int height, double c1, double c2, double c3, double s, double t, int steps) {
		this.height = height;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		threshold = (height * (height * 2)) / 4;
		initMatrices(s,t);
		j = new Jacobi(a, b, 0, height - 1, 0, (height * 2) - 1, steps, threshold);
	}
	
	private void initMatrices(double s, double t) {
		a = new Region[height][height * 2];
		b = new Region[height][height * 2];
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				a[i][j] = new Region(c1,c2,c3,s,t,0,i,j,height);
				b[i][j] = new Region(c1,c2,c3,s,t,0,i,j,height);
			}
		}
		
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				if(i - 1 >= 0) {
					a[i][j].setNeighbor(0, a[i - 1][j]);
					b[i][j].setNeighbor(0, b[i - 1][j]);
				}
				if(i + 1 < height) {
					a[i][j].setNeighbor(1, a[i + 1][j]);
					b[i][j].setNeighbor(1, b[i + 1][j]);
				}
				if(j - 1 >= 0) {
					a[i][j].setNeighbor(2, a[i][j - 1]);
					b[i][j].setNeighbor(2, b[i][j - 1]);
				}
				if(j + 1 < height * 2) {
					a[i][j].setNeighbor(3, a[i][j + 1]);
					b[i][j].setNeighbor(3, b[i][j + 1]);
				}
				a[i][j].calculateNeighborCount();
				b[i][j].calculateNeighborCount();
			}
		}
		
		a[0][0].setTemperature(s);
		b[0][0].setTemperature(t);
		a[height - 1][(height * 2) - 1].setTemperature(t);
		b[height - 1][(height * 2) - 1].setTemperature(t);
	}
	
	public void go() {
		j.invoke();
	}
	
	public void printAlloy() {
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; j++) {
				System.out.print("|" + a[i][j].getTemperature() + "|");
			}
			System.out.println();
		}
	}

	@Override
	public void run() {
		go();
	}
	
	
}
