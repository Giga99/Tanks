package igra;

import java.awt.*;

public class Trava extends Polje {

	public Trava(Mreza mreza) {
		super(mreza);
		setBackground(Color.GREEN);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return true;
	}
}
