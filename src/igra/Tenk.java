package igra;

import java.awt.*;

public class Tenk extends Figura implements Runnable {
	private Thread nit;
	private Polje staroPolje;

	public Tenk(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		if (getPolje() != null) {
			Graphics graphics = getPolje().getGraphics();
			graphics.setColor(Color.BLACK);
			graphics.drawLine(0, 0, getPolje().getWidth(), getPolje().getHeight());
			graphics.drawLine(getPolje().getWidth(), 0, 0, getPolje().getHeight());
		}
	}

	public synchronized void pokreni() {
		nit = new Thread(this);
		nit.start();
	}

	public synchronized void zaustavi() {
		if (nit != null)
			nit.interrupt();
	}

	public synchronized void pomeri() {
		staroPolje = getPolje();
		Polje novoPolje;

		while (true) {
			int smer = (int) (Math.random() * 4);// 0-LEVO, 1-DESNO, 2-GORE, 3-DOLE
			int i = 0, j = 0;
			switch (smer) {
			case 0:
				j--;
				break;
			case 1:
				j++;
				break;
			case 2:
				i--;
				break;
			case 3:
				i++;
				break;
			}

			if (getPolje().dohvPoljeZaPomeraj(i, j) != null) {
				novoPolje = getPolje().dohvPoljeZaPomeraj(i, j);
				if (novoPolje.dozvoljenaFigura(this))
					break;
			}
		}

		pomeriFiguru(novoPolje);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(500);
				pomeri();
				staroPolje.repaint();
				iscrtaj();
			}
		} catch (InterruptedException e) {
			Polje polje = getPolje();
			izbrisi();
			polje.repaint();
		}
	}
}
