package igra;

import java.awt.*;
import java.awt.Graphics;

public class Igrac extends Figura {
	private Polje staroPolje;

	public Igrac(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		Graphics graphics = getPolje().getGraphics();
		int x = getPolje().getWidth() / 2;
		int y = getPolje().getHeight() / 2;
		graphics.setColor(Color.RED);
		graphics.drawLine(x, 0, x, getPolje().getHeight());
		graphics.drawLine(0, y, getPolje().getWidth(), y);
	}
	
	public void pomeri(int smer) {
		staroPolje = getPolje();
		int i =0, j = 0;
		
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
			Polje novoPolje = getPolje().dohvPoljeZaPomeraj(i, j);
			if (novoPolje.dozvoljenaFigura(this)) {
				pomeriFiguru(novoPolje);
				staroPolje.repaint();
				iscrtaj();
			}
		}
	}
}
