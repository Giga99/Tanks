package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		if (getPolje() != null) {
			Graphics graphics = getPolje().getGraphics();
			graphics.setColor(Color.YELLOW);
			graphics.fillOval(getPolje().getWidth() / 4, getPolje().getHeight() / 4, getPolje().getWidth() / 2, getPolje().getHeight() / 2);
		}
	}
}
