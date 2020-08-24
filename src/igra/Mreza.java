package igra;

import java.awt.*;
import java.awt.Panel;
import java.util.ArrayList;

public class Mreza extends Panel implements Runnable {
	private Polje[][] polja;
	private ArrayList<Novcic> novcici;
	private ArrayList<Tenk> tenkovi;
	private int brNovcica, poeni;
	private ArrayList<Igrac> igraci;
	private int dimMatrice;
	private Igra igra;
	private Thread nit;

	public Mreza(Igra igra) {
		this(17, igra);
	}

	public Mreza(int dim, Igra igra) {
		super();
		this.dimMatrice = dim;
		this.igra = igra;
		inicijalizuj();
	}

	public void pomeri(int smer, int index) {
		if (igraci.get(index) != null) {
			igraci.get(index).pomeri(smer);
			igraci.get(index).iscrtaj();
		}
	}

	private void inicijalizuj() {
		setLayout(new GridLayout(dimMatrice, dimMatrice));
		polja = new Polje[dimMatrice][dimMatrice];
		novcici = new ArrayList<>();
		tenkovi = new ArrayList<>();
		igraci = new ArrayList<>();

		for (int i = 0; i < dimMatrice; i++) {
			for (int j = 0; j < dimMatrice; j++) {
				int rand = (int) (Math.random() * 100);
				if (rand <= 20)
					polja[i][j] = new Zid(this);
				else
					polja[i][j] = new Trava(this);

				add(polja[i][j]);
			}
		}
	}

	public void promeniPolje(int tip, int i, int j) {
		remove(polja[i][j]);
		if (tip == 1)
			polja[i][j] = new Zid(this);
		else
			polja[i][j] = new Trava(this);

		add(polja[i][j], i * dimMatrice + j);
		revalidate();
	}

	public void inicijalizujFigure(int brNovcica) {
		this.brNovcica = brNovcica;
		int i = 0;
		while (i < brNovcica) {
			int randN = (int) (Math.random() * dimMatrice);
			int randM = (int) (Math.random() * dimMatrice);

			boolean postoji = true;
			while (postoji || polja[randN][randM] instanceof Zid) {
				for (Novcic novcic : novcici) {
					if (novcic.getPolje() == polja[randN][randM]) {
						postoji = true;
						break;
					} else
						postoji = false;
				}
				if (novcici.size() == 0)
					postoji = false;
				if (postoji || polja[randN][randM] instanceof Zid) {
					randN = (int) (Math.random() * dimMatrice);
					randM = (int) (Math.random() * dimMatrice);
				}
			}

			Novcic novcic = new Novcic(polja[randN][randM]);
			novcici.add(novcic);
			i++;
		}

		i = 0;
		while (i < brNovcica / 3) {
			int randN = (int) (Math.random() * dimMatrice);
			int randM = (int) (Math.random() * dimMatrice);

			boolean postojiTenk = true, postojiNovcic = true;
			while (postojiTenk || postojiNovcic || polja[randN][randM] instanceof Zid) {
				for (Tenk tenk : tenkovi) {
					if (tenk.getPolje() == polja[randN][randM]) {
						postojiTenk = true;
						break;
					} else
						postojiTenk = false;
				}
				for (Novcic novcic : novcici) {
					if (novcic.getPolje() == polja[randN][randM]) {
						postojiNovcic = true;
						break;
					} else
						postojiNovcic = false;
				}
				if (tenkovi.size() == 0)
					postojiTenk = false;
				if (postojiTenk || postojiNovcic || polja[randN][randM] instanceof Zid) {
					randN = (int) (Math.random() * dimMatrice);
					randM = (int) (Math.random() * dimMatrice);
				}

			}

			Tenk tenk = new Tenk(polja[randN][randM]);
			tenkovi.add(tenk);
			i++;
		}

		i = 0;
		while (i < 2) {
			int randN = (int) (Math.random() * dimMatrice);
			int randM = (int) (Math.random() * dimMatrice);

			boolean postojiTenk = true, postojiNovcic = true;
			while (postojiTenk || postojiNovcic || polja[randN][randM] instanceof Zid) {
				for (Tenk tenk : tenkovi) {
					if (tenk.getPolje() == polja[randN][randM]) {
						postojiTenk = true;
						break;
					} else
						postojiTenk = false;
				}
				for (Novcic novcic : novcici) {
					if (novcic.getPolje() == polja[randN][randM]) {
						postojiNovcic = true;
						break;
					} else
						postojiNovcic = false;
				}

				if (postojiTenk || postojiNovcic || polja[randN][randM] instanceof Zid) {
					randN = (int) (Math.random() * dimMatrice);
					randM = (int) (Math.random() * dimMatrice);
				}

			}

			igraci.add(new Igrac(polja[randN][randM]));
			i++;
		}
	}

	public Igra getIgra() {
		return igra;
	}

	public int getDimMatrce() {
		return dimMatrice;
	}

	public Polje[][] getPolja() {
		return polja;
	}

	public ArrayList<Novcic> getNovcici() {
		return novcici;
	}

	public ArrayList<Tenk> getTenkovi() {
		return tenkovi;
	}

	public synchronized void azurirajStanje() {
		for (int i = 0; i < novcici.size(); i++) {
			for (Igrac igrac : igraci) {
				if (igrac.equals(novcici.get(i))) {
					poeni++;
					brNovcica--;
					igra.azurirajPoene("Poeni: " + poeni);
					novcici.remove(i);
					break;
				}
			}
		}

		for (Tenk tenk : tenkovi) {
			for (Igrac igrac : igraci) {
				if (igrac.equals(tenk)) {
					igra.azurirajPoene("Poraz!");
					zaustavi();
					break;
				}
			}
		}

		if (brNovcica == 0) {
			igra.azurirajPoene("Pobeda!");
			zaustavi();
		}
	}

	public synchronized void iscrtaj() {
		if (novcici.size() > 0) {
			for (Novcic novcic : novcici)
				novcic.iscrtaj();
		}

		if (tenkovi.size() > 0) {
			for (Tenk tenk : tenkovi)
				tenk.iscrtaj();
		}

		if (igraci.size() > 0) {
			for (Igrac igrac : igraci)
				igrac.iscrtaj();
		}
	}

	public synchronized void pokreni() {
		nit = new Thread(this);
		requestFocus();

		for (Tenk tenk : tenkovi)
			tenk.pokreni();

		nit.start();

		poeni = 0;
		igra.azurirajPoene("Poeni: " + poeni);
		igra.setRadi(true);
	}

	public synchronized void zaustavi() {
		for (Tenk tenk : tenkovi)
			tenk.zaustavi();
		tenkovi.clear();

		for (Novcic novcic : novcici) {
			Polje polje = novcic.getPolje();
			novcic.izbrisi();
			polje.repaint();
		}
		novcici.clear();

		for (Igrac igrac : igraci) {
			Polje polje = igrac.getPolje();
			igrac.izbrisi();
			polje.repaint();
			igrac = null;
		}
		igraci.clear();

		if (nit != null)
			nit.interrupt();
		igra.setRadi(false);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(40);
				azurirajStanje();
				iscrtaj();
			}
		} catch (InterruptedException e) {

		}
	}
}
