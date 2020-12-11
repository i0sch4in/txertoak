package erietxea;

public class Kanala {
	Boolean b, o, e, t;
	Pantaila p;
	private int id;

	public Kanala(int id, Pantaila p) {
		this.id = id;
		this.p = p;
		o = b = e = t = false;
	}

//	when (b==0)			p[PIR].begiratu[o]	-> KANALA[1][o][e][t]
	synchronized Boolean begiratu(Pazientea pazientea) throws InterruptedException {
		while (!(b == false))
			wait();

		b = true;

		String tmp = o.booleanValue() ? "1" : "0";
		p.inprimatu(pazientea, id() + "beg[" + tmp + "]");
		notifyAll();
		return o;
	}

//						p[PIR].desblokeatu	-> KANALA[0][o][e][t]
	synchronized void desblokeatu(Pazientea pazientea) {
		b = false;

		p.inprimatu(pazientea, id() + "desb");
		notifyAll();
	}

//	when (o==0)			p[PIR].eskuratu		-> KANALA[0][1][e][t]
	synchronized void eskuratu(Pazientea pazientea) throws InterruptedException {
		while (!(o == false))
			wait();

		b = false;
		o = true;

		p.inprimatu(pazientea, id() + "eskur");
		notifyAll();
	}

//	when (o==1 && t==0)	e[EIR].eskaeraJaso	-> KANALA[b][o][1][t]	
	synchronized void eskaeraJaso(Erizaina erizaina) throws InterruptedException {
		while (!(o == true && t == false))
			wait();

		e = true;

		p.inprimatu(erizaina, id() + "eskJaso");
		notifyAll();
	}

//	when (e==1)			e[EIR].txertoaJarri	-> KANALA[b][o][e][1]
	synchronized void txertoaJarri(Erizaina erizaina) throws InterruptedException {
		while (!(e == true))
			wait();

		t = true;

		p.inprimatu(erizaina, id() + "txJarri");
		notifyAll();
	}

//	when (t==1)			p[PIR].askatu		-> KANALA[b][0][0][0]
	synchronized void askatu(Pazientea pazientea) throws InterruptedException {
		while (!(t == true))
			wait();

		o = false;
		e = false;
		t = false;

		p.inprimatu(pazientea, id() + "askatu");
		notifyAll();
	}

	public String toString() {
		String str = " ";
		if (this.o)
			str = "X";
		return str;
	}

	public String id() {
		return "k" + id + ".";
	}
}
