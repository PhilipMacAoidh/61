import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import sun.applet.Main;

public class SixtyOne
  extends JFrame
  implements Runnable, MouseListener, KeyListener
{
  public static Dimension WindowSize = new Dimension(804, 837);
  private static boolean isInitialised = false;
  private BufferStrategy strategy;
  private Graphics offscreenGraphics;
  
  //TIMER
  long sTimer = 0L;
  long eTimer = 0L;
  double timer = 0.0D;
  
  //POINTS
  int points,lifeCount;
  boolean[] lives = new boolean[3];
  
  //OTHERS
  boolean completeT = false;
  boolean completeP = false;
  private boolean menu = true;
  Image Logo,vol;
  long menuBGMpos;
  Random rn = new Random();
  SFX sounds = new SFX();
  
  //CHAR MENU
  private boolean chars = false;
  Image Back,Aodan,John,Kev,Lav,Mark,Phil,Rawny,Ru;
  
  //RAWNY GAME
  private boolean rawnyGame = false;
  boolean rawnyEND = false;
  private boolean[][] mazeMap;
  int sizeX,sizeY,spriteX,spriteY,sX,sY;
  
  //KEV GAME
  private boolean kevGame = false;
  boolean falling = false;
  String[] direction = new String[4];
  Image[] noise = new Image[7];
  int noiseX,noiseY,item,dir,itemNum,noiseSpeed;
  
  //MARK GAME
  String[] pintWords = new String[10];
  String wordP,grabWord;
  boolean markGame = false;
  boolean pint = false;
  boolean handOpen = true;
  boolean handGrab = false;
  boolean handPint = false;
  boolean pintWord = false;
  int pintX,pintY,handX,handY,pwordNum,letterCount,gwordNum,release;
  double pintSpeed;
  long speakT;
  
  public SixtyOne()
  {
    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = screensize.width / 2 - WindowSize.width / 2;
    int y = screensize.height / 2 - WindowSize.height / 2;
    setBounds(x, y, WindowSize.width, WindowSize.height);
    setDefaultCloseOperation(3);
    setResizable(false);
    setVisible(true);
    setTitle("Sixty-One");
    ImageIcon icon = new ImageIcon(Main.class.getResource("/Logo-icon.png"));
    setIconImage(icon.getImage());
    
    //MENU
    icon = new ImageIcon(Main.class.getResource("/Logo.png"));
    this.Logo = icon.getImage();
    
    //CHAR SELECT
    icon = new ImageIcon(Main.class.getResource("/Chars/soon/Aodan.png"));
    this.Aodan = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/soon/John.png"));
    this.John = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/Kev.png"));
    this.Kev = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/soon/Lav.png"));
    this.Lav = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/Mark.png"));
    this.Mark = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/soon/Phil.png"));
    this.Phil = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/Rawny.png"));
    this.Rawny = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/Chars/soon/Ru.png"));
    this.Ru = icon.getImage();
    
    //KEV GAME
    icon = new ImageIcon(Main.class.getResource("/kevGame/fork.png"));
    noise[0] = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/kevGame/guitar.png"));
    noise[1] = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/kevGame/mug.png"));
    noise[2] = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/kevGame/pan.png"));
    noise[3] = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/kevGame/pot.png"));
    noise[4] = icon.getImage();
    icon = new ImageIcon(Main.class.getResource("/kevGame/spoon.png"));
    noise[5] = icon.getImage(); 
    icon = new ImageIcon(Main.class.getResource("/kevGame/glass.png"));
    noise[6] = icon.getImage();
    direction[0] = "LEFT";
    direction[1] = "RIGHT";
    direction[2] = "UP";
    direction[3] = "DOWN";
    
    //MARK GAME
    pintWords[0] = "YURT";
    pintWords[1] = "GOPNICK";
    pintWords[2] = "SLAV";
    pintWords[3] = "JOURNALISM";
    pintWords[4] = "SCHWIFTY";
    pintWords[5] = "BOOJUM";
    pintWords[6] = "SPARCH";
    pintWords[7] = "PINTS";
    pintWords[8] = "SESH";
    pintWords[9] = "GREMLIN";
    
    Thread t = new Thread(this);
    t.start();
    
    addMouseListener(this);
    addKeyListener(this);
    
    createBufferStrategy(2);
    this.strategy = getBufferStrategy();
    this.offscreenGraphics = this.strategy.getDrawGraphics();
    
    sounds.playNara();
    
    isInitialised = true;
  }
  
  public void run()
  {
    for (;;)
    {
      try
      {
        Thread.sleep(20L);
      }
      catch (InterruptedException localInterruptedException) {}
      
      repaint();
    }
  }
  
  public void paint(Graphics g)
  {
    if (!isInitialised) {
      return;
    }
    g = this.offscreenGraphics;
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WindowSize.width, WindowSize.height); 
    
    //MENU
    if (this.menu)
    {
    	//VOLUME
    	if(sounds.volume)
    	{
	    	ImageIcon icon = new ImageIcon(Main.class.getResource("/volume-on.png"));
	    	this.vol = icon.getImage();
	        g.drawImage(this.vol, 30, 40, null);
    	}
    	else
    	{
    		ImageIcon icon = new ImageIcon(Main.class.getResource("/volume-off.png"));
        	this.vol = icon.getImage();
            g.drawImage(this.vol, 30, 40, null);
    	}
    	
      //GLITCHY LOGO
      int rand = (int)(Math.random() * 500.0D);
      if (rand < 479) {
        g.drawImage(this.Logo, WindowSize.width / 2 - 100, WindowSize.height / 3 - 100, null);
      } else if ((rand >= 490) && (rand < 495)) {
        g.drawImage(this.Logo, WindowSize.width / 2 - 75, WindowSize.height / 3 - 75, null);
      } else if (rand >= 495) {
        g.drawImage(this.Logo, WindowSize.width / 2 - 125, WindowSize.height / 3 - 125, null);
      } else if ((rand >= 480) && (rand < 485)) {
        g.drawImage(this.Logo, WindowSize.width / 2 - 125, WindowSize.height / 3 - 75, null);
      } else if ((rand >= 485) && (rand < 489)) {
        g.drawImage(this.Logo, WindowSize.width / 2 - 75, WindowSize.height / 3 - 125, null);
      }
      
      //START BUTTON
      g.setColor(Color.WHITE);
      g.fillRect(315, 500, 170, 70);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.setColor(Color.BLACK);
      g.drawString("Start", 327, 555);
    }
    
    //CHAR SELECT
    if (this.chars)
    {
    	//BACK BUTTON
    	ImageIcon icon = new ImageIcon(Main.class.getResource("/Back.png"));
    	this.Back = icon.getImage();
        g.drawImage(this.Back, 30, 40, null);
    	
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.setColor(Color.WHITE);
      g.drawString("Pick a Game!", 250, 85);
      g.setFont(new Font("Showcard Gothic", 0, 18));
      int TopImgRowY = 110;
      int BotImgRowY = 475;
      int TopRowY = TopImgRowY + 350;
      int BotRowY = BotImgRowY + 355;
      
      g.drawImage(this.Aodan, 40, TopImgRowY, null);
      g.drawString("Aodan", 100, TopRowY);
      g.drawImage(this.John, 230, TopImgRowY, null);
      g.drawString("John", 290, TopRowY);
      g.drawImage(this.Kev, 420, TopImgRowY, null);
      g.drawString("Kev", 490, TopRowY);
      g.drawImage(this.Lav, 610, TopImgRowY, null);
      g.drawString("Lav", 680, TopRowY);
      
      g.drawImage(this.Mark, 40, BotImgRowY, null);
      g.drawString("Mark", 100, BotRowY);
      g.drawImage(this.Phil, 230, BotImgRowY, null);
      g.drawString("Phil", 290, BotRowY);
      g.drawImage(this.Rawny, 420, BotImgRowY, null);
      g.drawString("Rawny", 480, BotRowY);
      g.drawImage(this.Ru, 610, BotImgRowY, null);
      g.drawString("Ru", 680, BotRowY);
    }
    
    //RAWNY GAME
    if (this.rawnyGame)
    {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.drawString("GET RAWNY TO THE COUCH!", 75, 100);
      
      //DRAWING MAZE
      for (int i = 34; i < this.sizeX * 20 + 34; i += 20) {
        for (int j = 256; j < this.sizeY * 20 + 256; j += 20)
        {
          if (this.mazeMap[((i - 34) / 20)][((j - 256) / 20)] == true) {
            g.setColor(Color.white);
          } else {
            g.setColor(Color.black);
          }
          g.fillRect(i, j, 20, 20);
        }
      }
      
      //DRAWING RAWNY
      ImageIcon icon = new ImageIcon(Main.class.getResource("/rawnyGame/RawnySprite.png"));
      Image RawnySprite = icon.getImage();
      g.drawImage(RawnySprite, this.spriteX, this.spriteY, null);
      
      //DRAWING COUCH
      icon = new ImageIcon(Main.class.getResource("/rawnyGame/Couch.png"));
      Image CouchSprite = icon.getImage();
      g.drawImage(CouchSprite, 734, 596, null);
      
      //TIMER
      if (!this.completeT)
      {
        this.eTimer = System.currentTimeMillis();
        this.timer = ((this.eTimer - this.sTimer) / 1000.0D);
      }
      g.setColor(Color.WHITE);
      g.setFont(new Font("Ariel", 0, 24));
      g.drawString("Time: " + String.valueOf(this.timer), 35, 250);
    }
    
    //KEV GAME
    if (this.kevGame)
    {
    	//BACKGROUND
        ImageIcon icon = new ImageIcon(Main.class.getResource("/kevGame/kevBG.png"));
        Image kevBG = icon.getImage();
        g.drawImage(kevBG, 0, 0, null);

        if((System.currentTimeMillis()-sTimer) < 5000){
        	if((System.currentTimeMillis()-sTimer)%750 <= 325){g.setColor(Color.BLACK);}
        	else{g.setColor(Color.WHITE);}
	        g.setFont(new Font("Showcard Gothic", 0, 45));
	        g.drawString("Sssssshhhh!!!", 175, 70);
	        g.drawString("Don't wake Kev up...", 135, 120);
        }
        
        //FALLING ITEMS
        rn = new Random();
        int release = rn.nextInt(50);
        if(release == 4 && !falling)
        {
        	if(itemNum%5 == 0){noiseSpeed++;}
        	falling = true;
            item = rn.nextInt(7);
            noiseX = rn.nextInt(800-noise[item].getWidth(null));
            noiseY = noiseY - noise[item].getHeight(null);
            dir = rn.nextInt(4);
        }
        if(falling)
        {
	        g.drawImage(noise[item], noiseX, noiseY, null);
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("Ariel", Font.BOLD, 28));
	        g.drawString(direction[dir], noiseX, noiseY-20);
	        noiseY += noiseSpeed;
        }
        if(noiseY >= 835-noise[item].getHeight(null))
        {
        	falling = false;
        	noiseY = 0;
        	lifeCount++;
        	lives[lifeCount]=false;
        	
        	//SFX
        	if(item==0 || item==5){sounds.playCutlery();}
        	if(item==3 || item==4){sounds.playPot();}
        	if(item==6){sounds.playglass();}
        	if(item==1){sounds.playGuitar();}
        	if(item==2){sounds.playMug();}
        }
        
        //LIVES
        icon = new ImageIcon(Main.class.getResource("/kevGame/life.png"));
        Image life = icon.getImage();
        if (lives[0]){g.drawImage(life, 620, 40, null);}
        if (lives[1]){g.drawImage(life, 680, 40, null);}
        if (lives[2]){g.drawImage(life, 740, 40, null);}
        
        //POINTS
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ariel", 0, 24));
        g.drawString("Points: " + points, 20, 60);
        
        //GAMEOVER
        if(!lives[0] && !lives[1] && !lives[2]){
        	completeP = true;
        	sounds.playKevEnd();
        	kevGame = false;
        }
    }
    
    //MARK GAME
    if(markGame)
    {
    	//BACKGROUND
        ImageIcon icon = new ImageIcon(Main.class.getResource("/markGame/markBG.png"));
        Image markBG = icon.getImage();
        g.drawImage(markBG, 0, 0, null);

        if((System.currentTimeMillis()-sTimer) < 5000){
        	if((System.currentTimeMillis()-sTimer)%750 <= 325){g.setColor(Color.BLACK);}
        	else{g.setColor(Color.WHITE);}
        	g.setFont(new Font("Showcard Gothic", 0, 45));
	        g.drawString("DON'T DROP THE PINT", 402-g.getFontMetrics().stringWidth("DONT'T DROP THE PINT")/2, 550);
        }
        
        //POINTS
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", 0, 24));
        g.drawString("Points: " + points, 20, 60);
        
        //LIVES
        icon = new ImageIcon(Main.class.getResource("/markGame/life.png"));
        Image life = icon.getImage();
        if (lives[0]){g.drawImage(life, 620, 40, null);}
        if (lives[1]){g.drawImage(life, 680, 40, null);}
        if (lives[2]){g.drawImage(life, 740, 40, null);}
        
        if(System.currentTimeMillis()-speakT <= 3000)
        {
        	icon = new ImageIcon(Main.class.getResource("/markGame/speak.png"));
            Image speak = icon.getImage();
            g.drawImage(speak, 255, 95, null);
        }
        
        //PINTS
        release = rn.nextInt(150);
        if(release == 4 && !pint)
        {
        	pint = true;
        	pwordNum = rn.nextInt(10);
        	wordP = pintWords[pwordNum];
        }
        if(pint)
        {
        	icon = new ImageIcon(Main.class.getResource("/markGame/pint.png"));
            Image pint = icon.getImage();
            g.drawImage(pint, pintX, pintY, null);
        	g.setColor(Color.BLACK);
        	g.setFont(new Font("Showcard Gothic", 0, 28));
	        g.drawString(wordP, pintX-(g.getFontMetrics().stringWidth(wordP)/2)+25, pintY-30);
            pintX += pintSpeed;
        }
        if(pintX >= 800)
        {
        	pint = false;
        	pintX = 270;
        	sounds.playglass();
			lifeCount++;
			lives[lifeCount]=false;
        }
        
        //HAND
        if(handOpen)
        {
        	icon = new ImageIcon(Main.class.getResource("/markGame/hand-open.png"));
            Image handOpen = icon.getImage();
            g.drawImage(handOpen, handX, handY, null);
        }
        if(handGrab)
        {
        	icon = new ImageIcon(Main.class.getResource("/markGame/hand-closed.png"));
            Image handGrab = icon.getImage();
            g.drawImage(handGrab, handX, handY, null);
        }
        if(handPint)
        {
        	icon = new ImageIcon(Main.class.getResource("/markGame/hand-pint.png"));
            Image handPint = icon.getImage();
            g.drawImage(handPint, handX, handY, null);
            if(handX > 365){handX -= 10;}
            if(handX < 365){handX += 10;}
            if(handY > 660){handY -= 10;}
            if(handY < 660){handY += 10;}
        }
        
        //WORDS
        if(handPint)
        {
        	g.setColor(Color.BLACK);
        	g.setFont(new Font("Showcard Gothic", 0, 28));
	        g.drawString(grabWord, handX-(g.getFontMetrics().stringWidth(grabWord)/2)+25, handY-30);
        }
        
        //GAMEOVER
        if(!lives[0] && !lives[1] && !lives[2]){
  	      	sounds.fuckingYurt.stop();
  	      	sounds.gwan.stop();
        	sounds.playMarkEND();
        	completeP = true;
        	markGame = false;
        }
        
    }
    
    if (this.completeT)
    {
    	if(rawnyEND){
    		//BACKGROUND
            ImageIcon icon = new ImageIcon(Main.class.getResource("/rawnyGame/rawnyBG.png"));
            Image rawnyBG = icon.getImage();
            g.drawImage(rawnyBG, 0, 0, null);
    	}
      g.setColor(Color.WHITE);
      g.setFont(new Font("Showcard Gothic", 0, 86));
      g.drawString("GAMEOVER!", 402-g.getFontMetrics().stringWidth("GAMEOVER!")/2, 375);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.drawString("Time: " + String.valueOf(this.timer), 402-g.getFontMetrics().stringWidth("Time: " + String.valueOf(this.timer))/2, 450);

      g.setColor(Color.WHITE);
      g.fillRect(317, 500, 170, 70);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.setColor(Color.BLACK);
      g.drawString("Menu", 330, 555);
    }
    if (this.completeP)
    {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Showcard Gothic", 0, 86));
      g.drawString("GAMEOVER!", 402-g.getFontMetrics().stringWidth("GAMEOVER!")/2, 375);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.drawString("Points: " + points, 402-g.getFontMetrics().stringWidth("Points: " + points)/2, 450);

      g.setColor(Color.WHITE);
      g.fillRect(317, 500, 170, 70);
      g.setFont(new Font("Showcard Gothic", 0, 48));
      g.setColor(Color.BLACK);
      g.drawString("Menu", 330, 555);
    }
    this.strategy.show();
  }
  
  public void mousePressed(MouseEvent e)
  {
	  
    int x = e.getX();
    int y = e.getY();
    
    if ((this.menu)){ 
      if((x >= 315) && (x <= 485) && (y >= 500) && (y <= 570)){
	      this.menu = false;
	      this.chars = true;
	      return;
      }
      if ((x >= 30) && (x <= 80) && (y >= 40) && (y <= 90))
      {
    	  if(sounds.volume){
    		  sounds.nara.stop();
    		  menuBGMpos = sounds.nara.getMicrosecondPosition();
    		  sounds.volume = false;
    	  }
    	  else{
    		  sounds.volume = true;
    		  sounds.nara.setMicrosecondPosition(menuBGMpos);
    		  sounds.nara.start();
    	  }
        return;
      }
    }
    
    if (this.chars)
    {
    	if ((x >= 30) && (x <= 80) && (y >= 40) && (y <= 90))
        {
    		this.chars = false;
    		this.menu = true;
          return;
        }
     /* if ((x >= 40) && (x <= 196) && (y >= 110) && (y <= 435))//AODAN
      {
        this.user = "Aodan";
        this.chars = false;
        clip.stop();
        return;
      }
      if ((x >= 230) && (x <= 388) && (y >= 110) && (y <= 435))//JOHN
      {
        this.user = "John";
        this.chars = false;
        clip.stop();
        return;
      }*/
      if ((x >= 420) && (x <= 578) && (y >= 110) && (y <= 435))//KEVIN
      {
    	for(int i=0; i<3; i++){lives[i]=true;}
    	points = 0;
    	noiseSpeed = 1;
    	lifeCount = -1;
    	falling = false;
        this.chars = false;
        sounds.nara.stop();
        sounds.playKevBGM();
        sTimer = System.currentTimeMillis();
        kevGame = true;
        return;
      }
      /*if ((x >= 610) && (x <= 757) && (y >= 110) && (y <= 435))//LAVERTY
      {
        this.user = "Lav";
        this.chars = false;
        clip.stop();
        return;
      }*/
      if ((x >= 40) && (x <= 196) && (y >= 475) && (y <= 800))//MARK
      {
      	for(int i=0; i<3; i++){lives[i]=true;}
      	lifeCount = -1;
    	points = 0;
      	pintSpeed = 1;
      	handX = 365;
      	handY = 660;
      	pintX = 270;
      	pintY = 370;
      	speakT = System.currentTimeMillis();
		sTimer = System.currentTimeMillis();
      	this.handPint = false;
      	this.pintWord = false;
      	this.handGrab = false;
      	this.handOpen = true;
        this.chars = false;
        sounds.nara.stop();
        sounds.playMarkBGM();
        this.markGame = true;
        return;
      }
      /*if ((x >= 230) && (x <= 388) && (y >= 475) && (y <= 800))//PHILIP
      {
        this.user = "Phil";
        this.chars = false;
        clip.stop();
        return;
      }*/
      if ((x >= 420) && (x <= 578) && (y >= 475) && (y <= 800))//RAWNY
      {
        this.chars = false;
        Maze maze = new Maze(9);
        this.mazeMap = new boolean[maze.gridDimensionX][maze.gridDimensionY];
        this.sizeX = maze.gridDimensionX;
        this.sizeY = maze.gridDimensionY;
        this.spriteX = 54;
        this.spriteY = 276;
        this.sX = 1;
        this.sY = 1;
        this.mazeMap = maze.grid;
        this.sTimer = System.currentTimeMillis();
        this.rawnyGame = true;
        sounds.nara.stop();
        sounds.playRawnyBGM();
        return;
      }
      /*if ((x >= 610) && (x <= 757) && (y >= 475) && (y <= 800))//RU
      {
        this.user = "Ru";
        this.chars = false;
        clip.stop();
        return;
      }*/
    }
    if ((this.completeT || this.completeP) && (x >= 315) && (x <= 485) && (y >= 500) && (y <= 570))
    	    {
    	      this.chars = true;
    	      this.completeT = false;
    	      this.completeP = false;
    	      sounds.rawnyBGM.stop();
    	      sounds.kevBGM.stop();
    	      sounds.markBGM.stop();
    	      sounds.rawnyEnd.stop();
    	      sounds.playNara();
    	      return;
    	    }
  }
  
  public void mouseDragged(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  
  public void keyPressed(KeyEvent e)
  {
	  if(this.rawnyGame || this.kevGame || this.markGame)
	  {
		  if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		  {
    	      sounds.rawnyBGM.stop();
    	      sounds.kevBGM.stop();
    	      sounds.markBGM.stop();
    	      sounds.playNara();
			  this.rawnyGame = false;
			  this.kevGame = false;
			  this.markGame = false;
			  this.chars = true;
		  }
	  }
    if (this.rawnyGame)
    {
      if (e.getKeyCode() == KeyEvent.VK_UP)
      {
        if (this.mazeMap[this.sX][(this.sY - 1)] == false)
        {
          this.spriteY -= 20;this.sY -= 1;
        }
      }
      else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      {
        if (this.mazeMap[(this.sX + 1)][this.sY] == false)
        {
          this.spriteX += 20;this.sX += 1;
        }
      }
      else if (e.getKeyCode() == KeyEvent.VK_DOWN)
      {
        if (this.mazeMap[this.sX][(this.sY + 1)] == false)
        {
          this.spriteY += 20;this.sY += 1;
        }
      }
      else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && 
        (this.mazeMap[(this.sX - 1)][this.sY] == false))
      {
        this.spriteX -= 20;this.sX -= 1;
      }
      if ((this.sX == 35) && (this.sY == 17))
      {
        this.completeT = true;
        this.rawnyGame = false;
        this.rawnyEND = true;
        sounds.playRawnyEND();
      }
    }
    if (this.kevGame && falling)
    {
    	if(e.getKeyCode() == KeyEvent.VK_LEFT || 
    	   e.getKeyCode() == KeyEvent.VK_RIGHT || 
    	   e.getKeyCode() == KeyEvent.VK_UP || 
    	   e.getKeyCode() == KeyEvent.VK_DOWN){
    	//LEFT
        if (e.getKeyCode() == KeyEvent.VK_LEFT && dir == 0)
        {
      	  itemNum++;
    	  points++;
      	  falling = false;
      	noiseY = 0;
        }
        if (e.getKeyCode() != KeyEvent.VK_LEFT && dir == 0)
        {
        	falling = false;
        	noiseY = 0;
        	lifeCount++;
        	lives[lifeCount]=false;
            
        	//SFX
        	if(item==0 || item==5){sounds.playCutlery();}
        	if(item==3 || item==4){sounds.playPot();}
        	if(item==6){sounds.playglass();}
        	if(item==1){sounds.playGuitar();}
        	if(item==2){sounds.playMug();}
        }
    	//RIGHT
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && dir == 1)
        {
      	  itemNum++;
    	  points++;
      	  falling = false;
      	noiseY = 0;
        }
        if (e.getKeyCode() != KeyEvent.VK_RIGHT && dir == 1)
        {
        	falling = false;
        	noiseY = 0;
        	lifeCount++;
        	lives[lifeCount]=false;
            
        	//SFX
        	if(item==0 || item==5){sounds.playCutlery();}
        	if(item==3 || item==4){sounds.playPot();}
        	if(item==6){sounds.playglass();}
        	if(item==1){sounds.playGuitar();}
        	if(item==2){sounds.playMug();}
        }
    	//UP
      if (e.getKeyCode() == KeyEvent.VK_UP && dir == 2)
      {
    	  itemNum++;
    	  points++;
    	  falling = false;
    	  noiseY = 0;
      }
      if (e.getKeyCode() != KeyEvent.VK_UP && dir == 2)
      {
    	falling = false;
    	noiseY = 0;
      	lifeCount++;
      	lives[lifeCount]=false;
      
        //SFX
    	if(item==0 || item==5){sounds.playCutlery();}
    	if(item==3 || item==4){sounds.playPot();}
    	if(item==6){sounds.playglass();}
    	if(item==1){sounds.playGuitar();}
    	if(item==2){sounds.playMug();}
      }
    	//DOWN
      if (e.getKeyCode() == KeyEvent.VK_DOWN && dir == 3)
      {
    	  itemNum++;
    	  points++;
    	  falling = false;
    	  noiseY = 0;
      }
      if (e.getKeyCode() != KeyEvent.VK_DOWN && dir == 3)
      {
    	falling = false;
    	noiseY = 0;
      	lifeCount++;
      	lives[lifeCount]=false;
      
        //SFX
    	if(item==0 || item==5){sounds.playCutlery();}
    	if(item==3 || item==4){sounds.playPot();}
    	if(item==6){sounds.playglass();}
    	if(item==1){sounds.playGuitar();}
    	if(item==2){sounds.playMug();}
      }
    }}
    if(this.markGame)
    {
    	if(e.getKeyCode() == KeyEvent.VK_SPACE){
    		if(handX >= pintX-39 && handX <= pintX+11 && handY >= pintY-50 && handY <= pintY+75)
        	{
    			int play = rn.nextInt(4);
    			if(play == 3){sounds.playGwan();}
    			else if(play == 1){sounds.playFuckingYurt();}
    			handOpen = false;
        		handPint = true;
	        	pint = false;
	        	speakT = System.currentTimeMillis();
	        	pintX = 270;
	        	gwordNum = pwordNum;
	        	grabWord = pintWords[gwordNum];
        	}
    		else{
	    		handOpen = false;
	    		handGrab = true;
    		}
    		
    		if(handX>=345 && handX<=385 && handY>=640 && handY<=680 && handPint)
    		{
    			pintWord = true;
    			letterCount = 0;
    		}
    	}
    	
    	if(pintWord){
    		switch(gwordNum){
    		
    		case 0: //YURT
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_Y && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_U && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_R && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_T && letterCount == 3){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%10 == 0){pintSpeed += 0.25;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
        		
    		case 1: //GOPNICK
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_G && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_O && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_P && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_N && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_I && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_C && letterCount == 5){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_K && letterCount == 6){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 2: //SLAV
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_L && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_A && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_V && letterCount == 3){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 3: //JOURNALISM
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_J && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_O && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_U && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_R && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_N && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_A && letterCount == 5){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_L && letterCount == 6){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_I && letterCount == 7){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 8){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_M && letterCount == 9){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 4: //SCHWIFTY
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_C && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_H && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_W && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_I && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_F && letterCount == 5){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_T && letterCount == 6){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_Y && letterCount == 7){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 5: //BOOJUM
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_B && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_O && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_O && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_J && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_U && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_M && letterCount == 5){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 6: //SPARCH
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_P && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_A && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_R && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_C && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_H && letterCount == 5){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 7: //PINTS
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_P && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_I && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_N && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_T && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 4){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 8: //SESH
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_E && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_S && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_H && letterCount == 3){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		case 9: //GREMLIN
    			if(e.getKeyCode() == KeyEvent.VK_SPACE){}
    			else if (e.getKeyCode() == KeyEvent.VK_G && letterCount == 0){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_R && letterCount == 1){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_E && letterCount == 2){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_M && letterCount == 3){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_L && letterCount == 4){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_I && letterCount == 5){letterCount++;}
    			else if (e.getKeyCode() == KeyEvent.VK_N && letterCount == 6){letterCount++;pintWord = false;handOpen = true;handPint = false;points++;if(points%15 == 0){pintSpeed++;}}    			
    			else{pintWord=false;handOpen=true;handPint=false;lifeCount++;lives[lifeCount]=false;sounds.playglass();}
    			break;
    			
    		}
    	}

    	int handSpeed = 15;
    	//LEFT
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
      	  handX -= handSpeed;
        }    	
    	//RIGHT
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
      	  handX += handSpeed;
        }    	
    	//UP
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
      	  handY -= handSpeed;
        }    	
    	//DOWN
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
      	  handY += handSpeed;
        }
    }
  }
  
  public void keyReleased(KeyEvent e) {
	  if(this.markGame)
	    {
	    	if( e.getKeyCode() == KeyEvent.VK_SPACE && handGrab){
	    		handOpen = true;
	    		handGrab = false;
	    	}
	    	if( e.getKeyCode() == KeyEvent.VK_SPACE && handPint){
	    		if(handX>=345 && handX<=385 && handY>=640 && handY<=680){
	    			handOpen=false;
	    			handPint=true;
	    		}
	    		else{
	    		handOpen = true;
	    		handPint = false;
	    		sounds.playglass();
				lifeCount++;
				lives[lifeCount]=false;
	    		}
	    	}
	    }
  }
  public void keyTyped(KeyEvent e) {}
  
  public static void main(String[] args)
  {
    SixtyOne w = new SixtyOne();
  }
}