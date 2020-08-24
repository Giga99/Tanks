package igra;

import java.awt.*;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Mreza mreza;
	private boolean rezimIzmene;
	private Label poeniLabela = new Label("Poeni: 0");
	private CheckboxGroup group;
	private Checkbox trava, zid;
	private boolean radi = false;
	
	public Igra() {
		super("Igra");
		setSize(500, 500);
		
		mreza = new Mreza(this);
		rezimIzmene = true;
	
		Panel podPanel = new Panel(new GridLayout(1, 0));
		Label podloga = new Label("Podloga: ");
		podPanel.add(podloga);
		
		Panel panelPodloga = new Panel(new GridLayout(2, 1));
		group = new CheckboxGroup();
		trava = new Checkbox("Trava", true, group);
		trava.setBackground(Color.GREEN);
		zid = new Checkbox("Zid", false, group);
		zid.setBackground(Color.LIGHT_GRAY);
		panelPodloga.add(trava);
		panelPodloga.add(zid);
		podPanel.add(panelPodloga);
		
		add(podPanel, BorderLayout.EAST);
		
		Panel donjiDeo = new Panel(new GridLayout(1, 4));
		Panel rez = new Panel();
		Label nov = new Label("Novcici: ");
		TextField brNovcica = new TextField(3);
		rez.add(nov);
		rez.add(brNovcica);
		donjiDeo.add(rez);
		
		Panel poeni = new Panel();
		poeni.add(poeniLabela);
		donjiDeo.add(poeni);
		
		Panel button = new Panel();
		Button pocni = new Button("Pocni");
		pocni.setEnabled(false);
		button.add(pocni);
		donjiDeo.add(button);
		
		add(donjiDeo, BorderLayout.SOUTH);
		
		add(mreza, BorderLayout.CENTER);
		
		Menu rezim = new Menu("Rezim");
		MenuItem rezimIzmena = new MenuItem("Rezim izmena");
		MenuItem rezimIgranje = new MenuItem("Rezim igranje");
		rezim.add(rezimIzmena);
		rezim.add(rezimIgranje);
		
		rezimIzmena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rezimIzmene = true;
				pocni.setEnabled(false);
				panelPodloga.setEnabled(true);
				mreza.zaustavi();
			}
		});
		
		rezimIgranje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rezimIzmene = false;
				pocni.setEnabled(true);
				panelPodloga.setEnabled(false);
			}
		});
		
		MenuBar menuBar = new MenuBar();
		menuBar.add(rezim);
		setMenuBar(menuBar);
		
		pocni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int novcici = Integer.parseInt(brNovcica.getText().toString());
				if(radi) mreza.zaustavi();
				mreza.inicijalizujFigure(novcici);
				mreza.pokreni();
				radi = true;
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(radi) mreza.zaustavi();
				dispose();
			}
		});
		
		mreza.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {// 0-LEVO, 1-DESNO, 2-GORE, 3-DOLE
					case KeyEvent.VK_A: 
						mreza.pomeri(0, 0) ;
						break;
					case KeyEvent.VK_D: 
						mreza.pomeri(1, 0);
						break;
					case KeyEvent.VK_W: 
						mreza.pomeri(2, 0); 
						break;
					case KeyEvent.VK_S: 
						mreza.pomeri(3, 0); 
						break;
						
					case KeyEvent.VK_LEFT: 
						mreza.pomeri(0, 1) ;
						break;
					case KeyEvent.VK_RIGHT: 
						mreza.pomeri(1, 1);
						break;
					case KeyEvent.VK_UP: 
						mreza.pomeri(2, 1); 
						break;
					case KeyEvent.VK_DOWN: 
						mreza.pomeri(3, 1); 
						break;
				}
			}
		});
		
		setVisible(true);
	}
	
	public void setRadi(boolean radi) {
		this.radi = radi;
	}
	
	public void azurirajPoene(String tekst) {
		poeniLabela.setText(tekst);
	}
	
	public boolean isRezimIzmene() {
		return rezimIzmene;
	}
	
	//0-trava, 1-zid
	public int promenaTipa() {
		if(group.getSelectedCheckbox().getLabel().equals("Trava")) return 0;
		else return 1;
	}

	public static void main(String[] args) {
		new Igra();
	}
}
