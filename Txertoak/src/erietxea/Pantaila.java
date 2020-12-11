package erietxea;

import java.util.Arrays;

public class Pantaila {
	final String sep = "\t\t";
	private Fifo lifo;
	int PK, OK, EK;
	private Kanala[] k;

	public Pantaila() {
		this.PK = ErietxeApp.PK;
		this.OK = ErietxeApp.OK;
		this.EK = ErietxeApp.EK;
	}

	public void inprimatuBurukoa() {
		for (int i = 0; i < PK; i++)
			System.out.print("p[" + i + "]" + sep);

		System.out.print("[ ");
		for (int i = 0; i < EK; i++)
			System.out.print(i + " ");
		System.out.print("]\t");

		for (int i = 0; i < EK; i++)
			System.out.print("e[" + i + "]" + sep);

		System.out.print("Oheak" + sep);
		System.out.println("ItxaroteIlara" + sep);
		

		// separator
		System.out.println("=".repeat((PK + EK + 3)*16));

	}

	public void setValues(Fifo l, Kanala[] k) {
		this.lifo = l;
		this.k = k;
	}

	synchronized void inprimatu(Tab elem, String text) {
		int oheakPos = PK + EK + 1;
		int itxPos = oheakPos + 1;
		int chPos = PK;
		int currentPos = elem.getTab();
		int luzera = PK + EK + 3; // PertsonaKop + ErizainKop
								  //+ Kanalak + Oheak + ItxaroteIlara

		String[] str = new String[luzera];
		Arrays.fill(str, "");
		str[currentPos] = text;
		str[oheakPos] = lifo.getOheak();
		str[itxPos] = lifo.getItxarote();
		str[chPos] = channels();

		for (int i = 0; i < luzera; i++)
			System.out.print(String.format("%-9s\t", str[i]));
		System.out.println();
	}

	private String channels() {
		String str = "[ ";
		for (int i = 0; i < k.length; i++)
			str += k[i].toString() + " ";
		str += "]";

		return str;
	}

}
