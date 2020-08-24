package igra;

import java.awt.Color;

public class Zid extends Polje {

	public Zid(Mreza mreza) {
		super(mreza);
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return false;
	}
}
