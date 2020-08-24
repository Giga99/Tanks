package igra;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Polje extends Canvas {
	private Mreza mreza;

	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(mreza.getIgra().isRezimIzmene()) {
					int tip = mreza.getIgra().promenaTipa();
					mreza.promeniPolje(tip, pozicijaUMrezi()[0], pozicijaUMrezi()[1]);
				}
			}
		});
	}

	public Mreza getMreza() {
		return mreza;
	}
	
	public abstract boolean dozvoljenaFigura(Figura figura);
	
	public int[] pozicijaUMrezi() {
		Polje[][] polja = mreza.getPolja();
		int[] pozicija = new int[2];
		
	labela:for(int i = 0; i < mreza.getDimMatrce(); i++) {
			for(int j = 0; j < mreza.getDimMatrce(); j++) {
				if(polja[i][j] == this) {
					pozicija[0] = i;
					pozicija[1] = j;
					break labela;
				}
			}
		}
		
		return pozicija;
	}
	
	public Polje dohvPoljeZaPomeraj(int i, int j) {
		int[] pozicija = pozicijaUMrezi();
		if(pozicija[0] + i < 0 || pozicija[0] + i >= mreza.getDimMatrce() 
				|| pozicija[1] + j < 0 || pozicija[1] + j >= mreza.getDimMatrce()) return null;
		return mreza.getPolja()[pozicija[0] + i][pozicija[1] + j];
	}
}
