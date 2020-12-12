package erietxea;

import java.awt.Image;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
// Timer Imports
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Panela extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Image boy, nurse, bed, bg, crt;
	private Timer timer;

	// Checkpoint grafikoak zehaztu
	public final int bsize = 32; // Block size = mapako karratu bakoitza
	public final int[] start = { bsize * 12 + 25, bsize * 9 };
	public final int[] exit = { 800 - 25, 720 };
	public final int[][] bedPos = { { 300, 350 }, { 500, 350 }, { 300, 500 }, { 500, 500 }, { 300, 650 }, { 500, 650 } };
	public final int lefthall = bedPos[0][0] - 50;
	public final int righthall = bedPos[1][0] + 50;

	private int[][] boyPos;
	private int[][] nursePos;
	private int OK = bedPos.length;
	private int PK;
	private int EK;

	public Panela() {
		// Pazienteen posizioen sorrera "dinamikoa"
		PK = ErietxeApp.PK;
		boyPos = new int[PK][2];
		int initialX = 100;
		int initialY = 40;
		int sepX = 80;
		for (int i = 0; i < PK; i++) {
			boyPos[i][0] = initialX;
			boyPos[i][1] = initialY;
			initialX += sepX;
		}

		// Erizainen posizioen sorrera "dinamikoa"
		EK = ErietxeApp.PK;
		nursePos = new int[EK][2];
		initialX = 200;
		initialY = 800;
		sepX = 200;
		for (int i = 0; i < EK; i++) {
			nursePos[i][0] = initialX;
			nursePos[i][1] = initialY;
			initialX += sepX;
		}

		// hemen oheen sortze dinamikoa
		//

		ImageIcon ii = new ImageIcon(this.getClass().getResource("boy.png"));
		boy = ii.getImage();
		ImageIcon ii2 = new ImageIcon(this.getClass().getResource("nurse.png"));
		nurse = ii2.getImage();
		ImageIcon ii3 = new ImageIcon(this.getClass().getResource("bed.png"));
		bed = ii3.getImage();
		ImageIcon iibg = new ImageIcon(this.getClass().getResource("background.png"));
		bg = iibg.getImage();
		ImageIcon iicu = new ImageIcon(this.getClass().getResource("curtain.png"));
		crt = iicu.getImage();

		this.setBackground(Color.white);

		timer = new Timer(10, this); // 10ms-ro actionPerformed metodoari deitzen dio
		timer.start();
	}

	public void setBoyXY(int boy, int x, int y) {
		this.boyPos[boy][0] = x;
		this.boyPos[boy][1] = y;
	}

	public void setBoyX(int boy, int x) {
		this.boyPos[boy][0] = x;
	}
	
	public void setBoyY(int boy, int y) {
		this.boyPos[boy][1] = y;
	}
	
	public void paint(Graphics g) {
		super.paint(g);

		// draw background
		g.drawImage(bg, 0, 0, this);

		// draw beds
		for (int i = 0; i < OK; i++) {
			g.drawImage(bed, bedPos[i][0], bedPos[i][1], this);
		}

		// draw boys
		for (int i = 0; i < PK; i++) {
			g.drawImage(boy, boyPos[i][0], boyPos[i][1], this);
		}

		// draw nurses
		for (int i = 0; i < EK; i++) {
			g.drawImage(nurse, nursePos[i][0], nursePos[i][1], this);
		}

		// draw curtain
		g.drawImage(crt, 320, 150, this);
	}

	public void actionPerformed(ActionEvent e) {
		repaint(); // panela bir-margotu (re-paint)
	}

	public int[] getBoyXY(int boy) {
		return this.boyPos[boy];
	}

//	public void goToStart(int boy) {
//		int startpx = 32 * 13;
//		int startpy = 32 * 7 + 10;
//
//		while (boyPos[boy][0] < startpx) {
//			boyPos[boy][0] += 1;
//		}
//
//		while (boyPos[boy][1] < startpy) {
//			boyPos[boy][1] += 1;
//		}
//
//	}

}