package erietxea;

public class Pazientea extends Thread implements Tab {
	private Kanala[] k;
	private Fifo fifo;
	private Panela panela;
	private int s; // Sartu den ohearen posizioa
	private int x; // Kanalaren indizea
	private int id;
	private int j; // Itxarote-ilarako posizioa
	private final int speed = 8;

	public Pazientea(int id, Fifo l, Kanala[] k, Panela p) {
		this.id = id;
		this.k = k;
		this.fifo = l;
		this.x = 0;
		this.panela = p;
	}

	/*
	 * PAZIENTE = (iritsi[j:ISR] -> sartuTxanda[j][s:OSR] -> BEGIRATU[1][s]),	 
	 */
	public void run() {
		while (true) {
			try {
				itxaron(4000);
				panela.setEntryPos(id);
				panela.showBoy(id);

				itxaron(1000);
				j = fifo.iritsi(this);
				goToQueue();

				itxaron(1000);
				s = fifo.sartuTxanda(this, j);
				goToStart();
				goToBed();

				/*		BEGIRATU[x:EIR][s:OSR] = (k[x].begiratu[okup:Bool] ->
				 * 		if(okup==0) then SEGI[x][s]
										else(k[x].desblokeatu -> if(x<EK) then BEGIRATU[x+1][s]
										 			 	   		   		  else BEGIRATU[1]  [s]) ),*/
				Boolean okup = k[x].begiratu(this);
				while (okup) {
					itxaron(1000);
					k[x].desblokeatu(this);

					if (x < k.length - 1)
						x++;
					else
						x = 0;

					itxaron(1000);
					okup = k[x].begiratu(this);
				}

//				SEGI[x:EIR][s:OSR] = (k[x].eskuratu -> k[x].askatu -> irten[s] -> PAZIENTE).
				itxaron(1000);
				k[x].eskuratu(this, s);

				itxaron(1000);
				k[x].askatu(this);

				itxaron(1000);
				fifo.irten(this, s);
				goToExit();
				panela.hideBoy(id);

			} catch (Exception e) {
			}
		}
	}

	private void goToQueue() throws InterruptedException {
		int start_x = panela.entry[0];

		int pos_x = panela.start[0] - (this.j * 50);

		// SET X position
		goToX(start_x, pos_x);

	}

	private void goToStart() throws InterruptedException {
		itxaron(500);
		int[] current = panela.getBoyXY(id);
		int start_x = panela.start[0];
		int start_y = panela.start[1];

		// SET Y position -> get out of Queue
		goToY(current[1], (start_y + current[1]) / 4);

		// SET X position
		goToX(current[0], start_x);

		// SET Y position
		goToY(current[1], start_y);
	}

	private void goToBed() throws InterruptedException {
		int bed = this.s;
		int[] current = panela.getBoyXY(id);
		int bed_x = panela.bedPos[bed][0];
		int bed_y = panela.bedPos[bed][1];
		int hall;

		if (bed % 2 == 0) {
			hall = panela.leftHall;
		} else {
			hall = panela.rightHall;
		}

		// Set X position
		goToX(current[0], hall);

		// Set Y position
		goToY(current[1], bed_y);

		panela.setBoyXY(id, bed_x + 4, bed_y + 2);

	}

	private void goToExit() throws InterruptedException {
		int bed = this.s;
		int bed_y = panela.bedPos[bed][1];
		int[] exit = panela.exit;
		int hall;

		if (bed % 2 == 0) {
			hall = panela.leftHall;
		} else {
			hall = panela.rightHall;
		}

		int current_x = hall;
		int current_y = bed_y + 30;

		itxaron(500);

		panela.setBoyXY(id, current_x, current_y);

		// Set Y position
		goToY(current_y, exit[1]);

		// Set X position
		goToX(current_x, exit[0]);

	}

	public void goToX(int current_x, int goal_x) throws InterruptedException {
		if (current_x <= goal_x) {
			// Go right
			for (int i = current_x; i < goal_x + 1; i++) {
				panela.setBoyX(id, i);
				sleep(speed);
			}
		} else {

			// Go left
			for (int i = current_x; i > goal_x - 1; i--) {
				panela.setBoyX(id, i);
				sleep(speed);
			}
		}
	}

	public void goToY(int current_y, int goal_y) throws InterruptedException {
		if (current_y <= goal_y) {
			// Go bottom
			for (int i = current_y; i < goal_y + 1; i++) {
				panela.setBoyY(id, i);
				sleep(speed);
			}
		} else {
			// Go top
			for (int i = current_y; i > goal_y - 1; i--) {
				panela.setBoyY(id, i);
				sleep(speed);
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
