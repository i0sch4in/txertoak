package erietxea;

import java.util.concurrent.ThreadLocalRandom;

public class Erizaina extends Thread implements Tab {
	private Kanala k;
	public int id;
	private Panela panela;
	private final int speed = 5;

	public Erizaina(int id, Kanala k, Panela p) {
		this.id = id;
		this.k = k;
		this.panela = p;
	}

	public void run() {
		while (true) {
			try {
				itxaron(1000);
				int bed = k.eskaeraJaso(this);
				goToBed(bed);

				panela.showVac(id);
				// 1 eta 4 segundu bitarte itxaron, bakuna "txertatzeko"
				itxaron(1, 4);
				k.txertoaJarri(this);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void goToBed(int bed) throws InterruptedException {
		int[] current = panela.getNurseXY(id);
		int bed_y = panela.bedPos[bed][1];
		int hall;

		if (bed % 2 == 0) {
			hall = panela.leftCorridor;
		} else {
			hall = panela.rightCorridor;
		}

		// Set X position
		goToX(current[0], hall);

		// Set Y position
		goToY(current[1], bed_y);

	}

	public void goToX(int current_x, int goal_x) throws InterruptedException {
		if (current_x <= goal_x) {
			// Go right
			for (int i = current_x; i < goal_x + 1; i++) {
				panela.setNurseX(id, i);
				sleep(speed);
			}
		} else {

			// Go left
			for (int i = current_x; i > goal_x - 1; i--) {
				panela.setNurseX(id, i);
				sleep(speed);
			}
		}
	}

	public void goToY(int current_y, int goal_y) throws InterruptedException {
		if (current_y <= goal_y) {
			// Go bottom
			for (int i = current_y; i < goal_y + 1; i++) {
				panela.setNurseY(id, i);
				sleep(speed);
			}
		} else {
			// Go top
			for (int i = current_y; i > goal_y - 1; i--) {
				panela.setNurseY(id, i);
				sleep(speed);
			}
		}
	}

	public int getTab() {
		return this.id + ErietxeApp.PK + 1;
	}

	private void itxaron(int denb) throws InterruptedException {
		sleep((long) (Math.random() * denb));
	}

	private void itxaron(int min, int max) throws InterruptedException {
		// wait random seconds between min and max
		int random = ThreadLocalRandom.current().nextInt(min, max + 1);
		sleep((long) random * 1000);
	}
}
