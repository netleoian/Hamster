import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.applet.*;
import java.net.*;
//想想還有沒有其他的東西要import
public class final_program extends JFrame implements KeyListener,ActionListener,MouseMotionListener,MouseListener
{
	Container c;
	//宣告UI元件
	JMenuItem bot,stop,rebot,rule,musicStar,musicStop;
	JLabel lab1,lab2,hightscore;
	JPanel jpane;
	//設定共用的變數與類別
	java.util.Random rg;
	int []x={0,200,300,400,200,300,400,200,300,400};
	int []y={0,200,200,200,300,300,300,400,400,400};
	//int x1,y1,x2,y2,x3,y3,x4,x5,x6,x7,x8,x9,y4,y5,y6,y7,y8,y9,mode,ms;//mt時間,ms分數
	int mode,ms,hscore;
	double mt;
	int tmp;//隨意用
	javax.swing.Timer t;
	String musicfile1="music.wav";
	String musicfile2="facebook_ringtone_pop.wav";
	AudioClip audio1,audio2;
	Boolean []point={false,false,false,false,false,false,false,false,false,false} ;
	public final_program() //建構元，名稱改一改
	{
		super("final_program 打地鼠"); 
		//初始化共用變數
		mode=0;
		rg=new Random();
		
		//取得ContentPane
		c=getContentPane();
		c.setLayout(new FlowLayout());//設定版面設定
		c.setBackground(Color.gray);
		
		//初始化UI元件
		lab1=new JLabel("剩下:60秒");
		lab2=new JLabel("成績:"+ms);
		JMenuItem bot=new JMenuItem("開始(Q)",KeyEvent.VK_Q);
		JMenuItem stop=new JMenuItem("暫停(W)",KeyEvent.VK_W);
		JMenuItem rebot=new JMenuItem("繼續(E)",KeyEvent.VK_E);
		JMenuItem rule=new JMenuItem("規則(R)",KeyEvent.VK_R);
		JMenuItem musicStar=new JMenuItem("開始(T)",KeyEvent.VK_T);
		JMenuItem musicStop=new JMenuItem("停止(Y)",KeyEvent.VK_Y);
		hightscore=new JLabel("最高分:"+hscore);
		
		//設定 jmenu
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);
		JMenu file1 = new JMenu("功能(F)");
		file1.setMnemonic(KeyEvent.VK_F);
		file1.add(bot);file1.add(stop);file1.add(rebot);file1.add(rule);
		JMenu file2 = new JMenu("音樂(G)");
		file2.setMnemonic(KeyEvent.VK_G);
		file2.add(musicStar);file2.add(musicStop);
		jmb.add(file1);jmb.add(file2);
		bot.setActionCommand("bot");
		stop.setActionCommand("stop");
		rebot.setActionCommand("rebot");
		rule.setActionCommand("rule");
		musicStar.setActionCommand("musicStar");
		musicStop.setActionCommand("musicStop");
		

		//載入音樂
		try{
			URL musicURL1=new URL("file:"+System.getProperty("user.dir")+"/"+musicfile1);
			URL musicURL2=new URL("file:"+System.getProperty("user.dir")+"/"+musicfile2);
			audio1 = Applet.newAudioClip(musicURL1);
			audio2=Applet.newAudioClip(musicURL2);
		}
		catch ( MalformedURLException e ) { }
		
		
		//將UI元件加入ContentPane
		c.add(lab1);c.add(lab2);c.add(hightscore);
		//設定UI元件與滑鼠的事件觸發傾聽者
		bot.addKeyListener(this);
		bot.addActionListener(this);
		stop.addActionListener(this);
		rebot.addActionListener(this);
		rule.addActionListener(this);
		musicStar.addActionListener(this);
		musicStop.addActionListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		t=new javax.swing.Timer(800,this);
		//設定size，顯示出去 set jframe
		//setSize(640,480);
		setBounds(500,300,650,500);
		setVisible(true);
	}
public void paint(Graphics g) 
{
	super.paint(g);//畫出元件
	for(int i=1;i<10;i++)
	g.drawOval(x[i]-50,y[i]-50,100,100);
	//額外的畫圖程式寫在這裡
	
	for(int i=1;i<10;i++){
		if(point[i])
			g.fillOval(x[i]-50,y[i]-50,100,100);
	}
	
}
public void mouseDragged(MouseEvent xxx) { };
public void mouseMoved(MouseEvent e){ };
//UI元件事件處理類別寫在這裡
public void actionPerformed(ActionEvent e)
{
	if(e.getSource() instanceof JMenuItem){
		menuItemFun(e);
	}
	if (e.getSource()==t)
	{
		if(mt!=0.0){
			mt=mt-0.5;
			mode=rg.nextInt(999)+1;
			tmp=mode;
			while(tmp>0){
    if(point[tmp%10]==true)
      point[tmp%10]=false;
    else point[tmp%10]=true;
    tmp/=10;
   }
			lab1.setText("剩下:"+mt+"秒");
			repaint();
		}else if(mt==0.0){
			if(ms>hscore){
				hscore=ms;
				hightscore.setText("最高分:"+hscore);
			}
		}
	}
}
public void menuItemFun(ActionEvent e){
	if(e.getActionCommand().equalsIgnoreCase("bot")){
		mt=60;
		ms=0;
		t.start();
	}
	if(e.getActionCommand().equalsIgnoreCase("stop")){
		t.stop();
	}
	if(e.getActionCommand().equalsIgnoreCase("rebot")){
		t.start();
	}
	if(e.getActionCommand().equalsIgnoreCase("rule")){
		JOptionPane.showMessageDialog(jpane,
		"遊戲時間為60秒，當有黑色圖示出現時敲擊",
		"遊戲說明",
		JOptionPane.QUESTION_MESSAGE);
	}
	if(e.getActionCommand().equalsIgnoreCase("musicStar")){
		audio1.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("musicStop")){
		audio1.stop();
	}
}
public void mouseReleased(MouseEvent e){ };
public void mouseClicked(MouseEvent e){ };
public void mouseEntered(MouseEvent e){ }; 
public void mouseExited(MouseEvent e){ }; 
public void mousePressed(MouseEvent e)
{
	
	audio2.play();
	int mx,my;
	mx=e.getX();my=e.getY();
	for(int i=1;i<10;i++){
		if(point[i]&&(x[i]-mx)*(x[i]-mx)+(y[i]-my)*(y[i]-my)<2500){
			point[i]=false;
			ms++;
		}
		else if(!point[i]&&(x[i]-mx)*(x[i]-mx)+(y[i]-my)*(y[i]-my)<2500){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
	}

};
public void  keyPressed(KeyEvent e){
	audio2.play();
  int key=e.getKeyCode();
  switch(key){
  case KeyEvent.VK_NUMPAD7:
	if(point[1]){
			point[1]=false;
			ms++;
		}
		else if(!point[1]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD8:
  if(point[2]){
			point[2]=false;
			ms++;
		}
		else if(!point[2]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD9:
  if(point[3]){
			point[3]=false;
			ms++;
		}
		else if(!point[3]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD4:
  if(point[4]){
			point[4]=false;
			ms++;
		}
		else if(!point[4]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD5:
  if(point[5]){
			point[5]=false;
			ms++;
		}
		else if(!point[5]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD6:
  if(point[6]){
			point[6]=false;
			ms++;
		}
		else if(!point[6]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD1:
  if(point[7]){
			point[7]=false;
			ms++;
		}
		else if(!point[7]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD2:
  if(point[8]){
			point[8]=false;
			ms++;
		}
		else if(!point[8]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
  break;
  case KeyEvent.VK_NUMPAD3:
  if(point[9]){
			point[9]=false;
			ms++;
		}
		else if(!point[9]){
		ms--;}
		lab2.setText("成績:"+ms);
		repaint();
break;}
};
public void keyReleased(KeyEvent e){};
public void keyTyped(KeyEvent e){};
//滑鼠事件處理類別寫在這裡
/***主程式***/
	public static void main(String args[]) //程式起點
	{
		//final_program exapp =new final_program(); 前頁畫面
		final_program app=new final_program(); //名稱改一改，啟動UI元件
		//app.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){System.exit(0);}}); //處理視窗關閉要求
		app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}