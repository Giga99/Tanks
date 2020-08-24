package igra;

public abstract class Figura {
	private Polje polje;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}

	public Polje getPolje() {
		return polje;
	}
	
	public void izbrisi() {
		polje = null;
	}
	
	public void pomeriFiguru(Polje p) {
		if(p.dozvoljenaFigura(this)) polje = p;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Figura)) return false;
		if(obj == this) return true;
		Figura figura = (Figura) obj;
		return polje == figura.polje;
	}
	
	public abstract void iscrtaj();
}
