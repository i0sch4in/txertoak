package erietxea;

public class Pazientea extends Thread implements Tab {
	private Kanala[] k;
	private Fifo fifo;
	private int s;
	private int x;
	private int id;
	private int j;

	public Pazientea(int id, Fifo l, Kanala[] k) {
		this.id = id;
		this.k = k;
		this.fifo = l;
		this.x = 0;
	}

	/*
	 * PAZIENTE = (iritsi[j:ISR] -> sartuTxanda[j][s:OSR] -> BEGIRATU[1][s]),	 
	 */
	public void run() {
		while (true) {
			try {
				itxaron(1000);
				j = fifo.iritsi(this);
				
				itxaron(1000);
				s = fifo.sartuTxanda(this, j);
				
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
				k[x].eskuratu(this);

				itxaron(1000);
				k[x].askatu(this);

				itxaron(1000);
				fifo.irten(this, s);

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
