package erietxea;

import javax.swing.JPanel;

import erietxea.Framea;

public class ErietxeApp {
	// Parametroak zehaztu
	public final static int OK = 4; // Ohe kopurua
	public final static int PK = 8; // Paziente kopurua
	public final static int EK = 3; // Erizain kopurua
	public final static int IK = 4; // Itxarote-gelaren tamaina
	public final static int IM = 2; // Itxarote-gelaren egon behar den paziente-kopuru minimoa

	public static void main(String[] args) throws InterruptedException {

//		System.out.println("ERIETXEA: return sakatu hasteko");
//		try {
//			System.in.read();
//		} catch (Exception ex) {
//		}

		// framea eta pantaila hasieratu
		Framea framea = new Framea();
		Panela panela = framea.panela;
		framea.setVisible(true);
		Pantaila p = new Pantaila();

		// monitoreak hasieratu
		Fifo f = new Fifo(p);
		Kanala[] kanalak = new Kanala[EK];
		p.setValues(f, kanalak);

		Erizaina[] erizainak = new Erizaina[EK];
		Pazientea[] pazienteak = new Pazientea[PK];

		// harien arrayak hasieratu
		for (int i = 0; i < EK; i++) {
			kanalak[i] = new Kanala(i, p);
			erizainak[i] = new Erizaina(i + PK + 1, kanalak[i]);
		}
		for (int i = 0; i < PK; i++) {
			pazienteak[i] = new Pazientea(i, f, kanalak);
		}

		// hariak hasi
		for (int i = 0; i < EK; i++) {
			erizainak[i].start();
		}

		for (int i = 0; i < PK; i++) {
			pazienteak[i].start();
		}

		// burukoa inprimatu
		p.inprimatuBurukoa();
		
		
		// begizta x=getX tik x=32*13 arte
		// eta 	   y=getYtik y=32*7+10 arte
		int startpx = 32 * 13;
		int startpy = 32 * 7 + 10;
		int boy = 0;
		int[] current = panela.getBoyXY(boy);
		int currentx = current[0];
		int currenty = current[1];
		
		while (currentx < startpx) {
			System.out.print("x:" + currentx);
			panela.setBoyXY(0, currentx, currenty);
			currentx++;
			Thread.sleep(10);
		}
		
		
		
		//panela.setBoyXY(0, 32*13, 32*7+10);
		//framea.panela.goToStart(0);

	}

}
