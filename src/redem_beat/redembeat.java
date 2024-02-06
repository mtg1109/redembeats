///////////////////////////////////
//
// redem Beat 소스코드
// 
// setComponentZOrder의 999999는 맨뒤를 의미
// setComponentZOrder의 0은 맨앞을 의미
//
//
///////////////////////////////////

package redem_beat;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class redembeat extends JFrame {
	//////////////////
	// init
	//////////////////

	// 그래픽
	private Image screenImage;
	private Graphics screenGraphic;

	// 백그라운드
	Random random = new Random();

	private JLabel introBackground = new JLabel(new ImageIcon(Main.class.getResource("../img/background/emu/emu2.jpg")));
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../img/menuBar.png")));
	// 버튼
	// 호버
	private ImageIcon PlayButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../img/Buttons/PlayButtonEntered.png"));

	// 일반
	private ImageIcon PlayButtonImage = new ImageIcon(Main.class.getResource("../img/Buttons/PlayButton.png"));
	private ImageIcon SelectMusicButtonImage = new ImageIcon(
			Main.class.getResource("../img/Buttons/selectMusicButton.png"));

	// 버튼 생성
	private JButton playButton = new JButton(PlayButtonImage);

	// 사운드

	private String InGameMusicName = MusicList.InGameMusicList[random.nextInt(3)];
	private Music BackgroundMusic = new Music("../musicsrc/music/" + InGameMusicName + ".mp3", true);

	// 게임 선택
	private ImageIcon selectedImage = new ImageIcon(
			Main.class.getResource("../musicsrc/img/" + InGameMusicName + ".png"));
	private ImageIcon selectedBackgroundImage = new ImageIcon(
			Main.class.getResource("../musicsrc/backgroundimg/selectScreen.png"));

	// 화면전환
	private boolean isSelectingScreen = false;

	// 마우스
	private int mouseX, mouseY;

	///////////////////////////////////////

	public redembeat() {

		//////////////////////////////
		// 창.init
		//////////////////////////////

		setUndecorated(false);
		setTitle("Redem Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);

		///////////////////////////////
		// 백그라운드 이미지
		///////////////////////////////

		img.setLayout(null);
		img.setBounds(0, 0, 1920, 1080);

		///////////////////////////////
		// 메뉴바
		///////////////////////////////

		menuBar.setBounds(0, 0, 1920, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY - 25);
			}
		});

		////////////////////////////
		// 플레이 버튼
		////////////////////////////

		playButton.setBounds(1602, 900, 300, 100);
		playButton.setBorderPainted(false);
		playButton.setContentAreaFilled(false);
		playButton.setFocusPainted(false);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playButton.setIcon(PlayButtonEnteredImage);
				Music buttonEnteredMusic = new Music("../soundFX/buttonEntered.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				playButton.setIcon(PlayButtonImage);
				Music buttonExitedMusic = new Music("../soundFX/buttonEntered.mp3", false);
				buttonExitedMusic.start();
			}

			public void mousePressed(MouseEvent e) {
				////////////////////////////
				// 게임화면 전환
				////////////////////////////

				// sound
				Music buttonEnteredMusic = new Music("../soundFX/buttonClicked.mp3", false);
				buttonEnteredMusic.start();
				BackgroundMusic.close();

				// 전환
				inGame();
			}
		});

		////////////////////////////
		// 요소 추가
		////////////////////////////

		add(playButton);
		add(menuBar);
		add(img);

		BackgroundMusic.start();

	}

	////////////////////////////
	// 요소 그리기
	////////////////////////////

	public JPanel img = new JPanel() {

		public void paint(Graphics g) {

			////////////////////////////
			// 폰트
			////////////////////////////
			Font generalFont = new Font("Toppan Bunkyu Midashi Gothic", Font.BOLD, 21);
			g.setFont(generalFont);

			////////////////////////////////
			// g.그리기
			////////////////////////////////

			g.drawImage(introBackground, 0, 0, null);
			g.drawString(InGameMusicName, 900, 23);

			if (isSelectingScreen) {
				////////////////////////////
				// g.그리기
				///////////////////////////
				g.drawImage(selectedBackgroundImage.getImage(), 125, 100, null);
				g.drawImage(selectedImage.getImage(), 150, 150, null);
				g.drawString(InGameMusicName, 150, 450);

			}
		}
	};
	////////////////////////
	// 플레이화면 세팅
	////////////////////////

	private void inGame() {

		// init
		repaint();
		
		isSelectingScreen = true;
		playButton.setVisible(false);

		Music BackgroundMusic = new Music("../musicsrc/musicHighLight/" + InGameMusicName + "-HighLight" + ".mp3",true);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("../img/Buttons/selectMusicButton.png"));
		
		JButton MusicListButton = new JButton(icon);
		
		MusicListButton.setText("Click Me");
		MusicListButton.setHorizontalTextPosition(JButton.CENTER);
		MusicListButton.setVerticalTextPosition(JButton.CENTER);
		MusicListButton.setBounds(1182, 500, 700, 100);

		
		setVisible(true);
		
		
		// 추가
        add(MusicListButton);
		setComponentZOrder(MusicListButton,0);
		
		
		// 사운드

		BackgroundMusic.start();
		
	};

}