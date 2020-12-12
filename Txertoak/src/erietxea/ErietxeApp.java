package erietxea;

import erietxea.Framea;

public class ErietxeApp {
	// Parametroak zehaztu
	public final static int OK = 6; // Ohe kopurua
	public final static int PK = 15; // Paziente kopurua
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
			kanalak[i] = new Kanala(i, p, panela);
			erizainak[i] = new Erizaina(i, kanalak[i], panela);
		}
		for (int i = 0; i < PK; i++) {
			pazienteak[i] = new Pazientea(i, f, kanalak, panela);
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

		/* ----------------------------------    TEST	-----------------------------------------	*/

	}

}
