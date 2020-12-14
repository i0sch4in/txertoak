package erietxea;

public class Fifo {
	int OK;
	int IK;
	int IM;
	int s; // Sartzeko ohearen posizioa
	int i; // Irteteko ohearen posizioa
	int k; // Okupatutako oheen posizioa
	int is;// Itxarote ilarara sartzeko posizioa
	int ii;// itxarote ilaratik atratzeko posizioa
	int ik;// Itxaroten dagoen paziente kopurua
	Panela panela;

	Pantaila p;

	public Fifo(Pantaila p, Panela panel) {
		this.p = p;
		this.OK = ErietxeApp.OK;
		this.IK = ErietxeApp.IK;
		this.IM = ErietxeApp.IM;
		this.panela = panel;

		s = i = k = is = ii = ik = 0;
	}

//	when (ik<IK)			p[PIR].iritsi[is]	 	 -> FIFO[s]       [i]       [k]  [(is+1)%IK][ii]	  [ik+1]   
	synchronized int iritsi(Pazientea pazientea) throws InterruptedException {
		while (!(ik < IK))
			wait();

		int tmp1 = is;

		is = (is + 1) % IK;
		ik += 1;

		p.inprimatu(pazientea, "irits[" + tmp1 + "]");
		notifyAll();
		return tmp1;

	}

//	when (ik>=IM && k<OK)	p[PIR].sartuTxanda[ii][s]-> FIFO[(s+1)%OK] [i] [k+1] [is]  [(ii+1)%IK] [ik-1]
	synchronized int sartuTxanda(Pazientea pazientea, int j) throws InterruptedException {
		while (!(ik >= IM && j == ii && k < OK))
			wait();

		int tmp1 = ii;
		// Itxarote ilaratik atera
		ii = (ii + 1) % IK;
		ik = ik - 1;

		int tmp2 = s;
		// Oheen ilaran sartu
		s = (s + 1) % OK;
		k += 1;

		p.inprimatu(pazientea, "sartuTx[" + tmp1 + "]" + "[" + tmp2 + "]");
		notifyAll();
		return tmp2;
	}

//	when 	 				p[PIR].irten[i] 	 	 -> FIFO[s]       [(i+1)%OK] [k-1] [is]	[ii] [ik]
	synchronized void irten(Pazientea pazientea, int x) throws InterruptedException {
		while (!(x == i))
			wait();

		i = (i + 1) % OK;
		k -= 1;

		p.inprimatu(pazientea, "irten[" + x + "]");
		notifyAll();
	}

	public String getOheak() {
		String str = "[";
		if (i < s) {
			int has = i;
			int erd = k;
			int buk = OK - has - erd;
			str += " ".repeat(has) + "*".repeat(erd) + " ".repeat(buk);
		} else {
			int has = (i + k) % OK;
			int buk = k - has;
			int erd = OK - has - buk;
			if (buk >= 0)
				str += "*".repeat(has) + " ".repeat(erd) + "*".repeat(buk);
			else
				str += " ".repeat(OK);
		}
		str += "]";
		return str;
	}

	public String getItxarote() {
		String str = "[";
		// Errore eman dezake
//		if (ii < is) {
//			int has = ii;
//			int erd = ik;
//			int buk = IK - has - erd;
//			str += " ".repeat(has) + "*".repeat(erd) + " ".repeat(buk);
//		} else {
//			int has = (ii + ik) % IK;
//			int buk = i - has;
//			int erd = IK - has - buk;
//			if (buk >= 0)
//				str += "*".repeat(has) + " ".repeat(erd) + "*".repeat(buk);
//			else
//				str += " ".repeat(IK);
//		}
//		str += "]";
		return str;
	}

}
