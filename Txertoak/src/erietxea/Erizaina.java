package erietxea;

public class Erizaina extends Thread implements Tab {
	private Kanala k;
	private int id;

	public Erizaina(int id, Kanala k) {
		this.id = id;
		this.k = k;
	}

	public void run() {
		while (true) {
			try {
				itxaron(1000);
				k.eskaeraJaso(this);

				itxaron(1000);
				k.txertoaJarri(this);

			} catch (Exception e) {
			}
		}
	}

	public int getTab() {
		return this.id;
	}

	private void itxaron(int denb) throws InterruptedException {
		sleep((long) (Math.random() * denb));
	}
}
