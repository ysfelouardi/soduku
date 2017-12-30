package sudoku;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Sudoku extends JFrame{
	
	int grid[][] ;
	int initcells[][];
	JPanel jpsudoku;
	JPanel jpoperations;
	JPanel jpinfos;
	JButton btnsolve;
	JButton btnsave;
	JButton btnload;
	JButton btnclear;
	JButton btngeneratenbrs;
	JLabel  infolbl;
	JButton btns[][];
	JComboBox algocmbx;
	boolean solveisclicked = false;
	static int nbrsteps = 0;
	
	public Sudoku(){
		grid = new int[9][9];
		initcells = new int[9][9];
		
	      grid[0][0] = 9 ;
	      grid[0][4] = 2 ;
	      grid[0][6] = 7 ;
	      grid[0][7] = 5 ;

	      grid[1][0] = 6 ;
	      grid[1][4] = 5 ;
	      grid[1][7] = 4 ;

	      grid[2][1] = 2 ;
	      grid[2][3] = 4 ;
	      grid[2][7] = 1 ;

	      grid[3][0] = 2 ;
	      grid[3][2] = 8 ;

	      grid[4][1] = 7 ;
	      grid[4][3] = 5 ;
	      grid[4][5] = 9 ;
	      grid[4][7] = 6 ;

	      grid[5][6] = 4 ;
	      grid[5][8] = 1 ;

	      grid[6][1] = 1 ;
	      grid[6][5] = 5 ;
	      grid[6][7] = 8 ;

	      grid[7][1] = 9 ;
	      grid[7][4] = 7 ;
	      grid[7][8] = 4 ;

	      grid[8][1] = 8 ;
	      grid[8][2] = 2 ;
	      grid[8][4] = 4 ;
	      grid[8][8] = 6 ;
	      
	      
	      initcells[0][0] = 9 ;
	      initcells[0][4] = 2 ;
	      initcells[0][6] = 7 ;
	      initcells[0][7] = 5 ;

	      initcells[1][0] = 6 ;
	      initcells[1][4] = 5 ;
	      initcells[1][7] = 4 ;

	      initcells[2][1] = 2 ;
	      initcells[2][3] = 4 ;
	      initcells[2][7] = 1 ;

	      initcells[3][0] = 2 ;
	      initcells[3][2] = 8 ;

	      initcells[4][1] = 7 ;
	      initcells[4][3] = 5 ;
	      initcells[4][5] = 9 ;
	      initcells[4][7] = 6 ;

	      initcells[5][6] = 4 ;
	      initcells[5][8] = 1 ;

	      initcells[6][1] = 1 ;
	      initcells[6][5] = 5 ;
	      initcells[6][7] = 8 ;

	      initcells[7][1] = 9 ;
	      initcells[7][4] = 7 ;
	      initcells[7][8] = 4 ;

	      initcells[8][1] = 8 ;
	      initcells[8][2] = 2 ;
	      initcells[8][4] = 4 ;
	      initcells[8][8] = 6 ;
	      init();
	}
	

	   

	public void init(){
		jpoperations = new JPanel();
		jpsudoku = new JPanel();
		jpinfos = new JPanel();
		btns = new JButton[9][9];
		btnsolve = new JButton("Solve");
		btnclear = new JButton("Clear");
		btnsave = new JButton("Save");
		btnload = new JButton("Load");
		btngeneratenbrs = new JButton("Generate");
		
		
		ImageIcon img = new ImageIcon(new ImageIcon("asset/painticon.png").getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT));
		JButton iconbtn = new JButton();
		
		iconbtn.setMargin(new Insets(0, 0, 0, 0));
		iconbtn.setBorder(null);
		iconbtn.setFocusPainted(true);
		iconbtn.setContentAreaFilled(false);
		iconbtn.setIcon(img);
		iconbtn.setToolTipText("Repaint");
		

		
		String[] algos = { "Backtracking", "ForwardChecking", "MinConflict" ,"MRV" ,"LCV" ,"AC3" };
		algocmbx = new JComboBox(algos);
		
		
		infolbl = new JLabel("		");
		jpinfos.add(infolbl);
		jpsudoku.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		jpsudoku.setDoubleBuffered(false);
		jpsudoku.setRequestFocusEnabled(false);
		
		jpoperations.add(algocmbx);
		jpoperations.add(btnsolve);
		jpoperations.add(btnclear);
		jpoperations.add(btngeneratenbrs);
		jpoperations.add(btnsave);
		jpoperations.add(btnload);
		jpoperations.add(iconbtn);
		

	      
		iconbtn.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
		        repaint();
		    }
		});
		
		
		algocmbx.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
		        initiate();//pour affecter les valeurs initiaux au grid du sudoku 
	    		infolbl.setText("");
		        repaint();
		    }
		});
		
	      btnsolve.addActionListener(new ActionListener() { 
      		 
	    	  public void actionPerformed(ActionEvent e) {
	    		  String selected = (String) algocmbx.getSelectedItem();
	    		  nbrsteps = 0;
	    		  boolean solved = false;
	      		  long startTime = System.currentTimeMillis();
	    		  if(selected.equals("Backtracking")){
		      		  solved = backtracking(0,0);	
	    		  }else if(selected.equals("ForwardChecking")){
	    			  solved = forwardchecking(0,0);
	    		  }else if(selected.equals("MinConflict")){
	    			  solved = minConflict();
	    		  }else if(selected.equals("MRV")){
	    			  solved = MRV();
	    		  }else if(selected.equals("LCV")){
	    			  solved = LCV();
	    		  }
	      		  long stopTime = System.currentTimeMillis();
	      		  //System.out.println("start time = " + startTime );
	      		  //System.out.println("stop time = " + stopTime);
	      	      long elapsedTime = (stopTime - startTime);
	      	      //System.out.println(elapsedTime);
	      	      String str = "execution time = " + elapsedTime + " ms"+"     number of steps = " + nbrsteps;
	      	      System.out.println(str);
	      	      infolbl.setText(str);
	      		  if(solved)
	      		  {
	      			  solveisclicked = true;
	      			  repaint();
	      		  }else{
	      			JOptionPane.showMessageDialog(null, "Pas De Solutions!", "InfoBox: " + "Info", JOptionPane.INFORMATION_MESSAGE);
	      			repaint();
	      		  }
	      	  } 
	      	} );

	      btnclear.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		  infolbl.setText("");
	      		  clear();
	      		  repaint();   		  
	      	  } 
	      	} );
	      
	      btngeneratenbrs.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		  infolbl.setText("");
	      		  generate();
	      		  repaint();   		  
	      	  } 
	      	} );
	      btnsave.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		  save();   		  
	      	  } 
	      	} );
	      btnload.addActionListener(new ActionListener() { 
	      	  public void actionPerformed(ActionEvent e) { 
	      		  infolbl.setText("");
	      		  load();
	      		  repaint();   		  
	      	  } 
	      	} );
	    jpsudoku.setBackground(Color.white);
	    //this.setContentPane(new JLabel(new ImageIcon("./asset/background.jpg")));
		this.add(jpoperations,BorderLayout.NORTH);
		this.add(jpsudoku,BorderLayout.CENTER);
		this.add(jpinfos,BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setVisible(true);
		this.setSize(600, 660);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		repaint();
	}
	
	
	public void paint(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		for(int i=0;i<9;i++)
		{
			 g2.setStroke(new BasicStroke(3));
			 g2.setColor(Color.blue);
			 if (i % 3 == 0)
			 {
			     g2.drawRect(35 + i * 60,75, 180,180); 
			 }
			for(int j=0;j<9;j++)
			{
				 g2.setStroke(new BasicStroke(3));
				 g2.setColor(Color.blue);
				 if (j % 3 == 0)
				 {
					 g2.drawRect(35,75 + j *60, 180,180); 
				 }
				 if(j % 3 == 0 && i % 3 == 0)
				 {
					 g2.drawRect(35 + i * 60,75 + j *60, 180,180);
				 }
				g2.setStroke(new BasicStroke(1));
				g2.setColor(Color.black);
				g2.drawRect(40 + i * 60,80 + j * 60, 50, 50);
				
				g2.setFont(new Font("Comic Sans Ms", Font.PLAIN, 25));
				//g2.setFont(new Font("Jokerman", Font.PLAIN, 25));
				if(solveisclicked)
				{
					//System.out.println("initcells " + i + "," + j + " = " + initcells[i][j]);
					//System.out.println("grid " + i + "," + j + " = " + grid[i][j]);
					if(initcells[i][j] != grid[i][j])
					{						
						g2.setColor(Color.red);
					}else{
						g2.setColor(Color.black);
					}
					
					g2.drawString(String.valueOf(grid[i][j]), 60 + i * 60,108 + j * 60);
					
				}else{
					g2.setColor(Color.black);
					if( initcells[i][j] != 0 ){
						//g2.setFont(new Font("Jokerman", Font.BOLD, 25));
						//g2.drawString(String.valueOf(initcells[i][j]), 63 + i * 35,87 + j * 35);
						g2.drawString(String.valueOf(grid[i][j]), 60 + i * 60,108 + j * 60);
					}
				}
				
			}

		}
		
//		g.setColor(Color.red);
//		g.drawRect(45,65, 320, 320); 
		
		solveisclicked = false;
		  
	  }
	
	public void save(){
	      try {
	          FileOutputStream fileOut = new FileOutputStream("data/grid.ser");
	          
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          
	          out.writeObject(grid);
	          
	          out.close();
	          
	          fileOut.close();
	          
	          System.out.printf("Serialized data is saved in /data/grid.ser");
	       }catch(IOException i) {
	          i.printStackTrace();
	       }
	}
	
	public void load(){
		
		 try {
	         FileInputStream fileIn = new FileInputStream("data/grid.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         grid = (int[][]) in.readObject();
	 		for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					initcells[i][j] = grid[i][j] ;
				}
			}
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         c.printStackTrace();
	         return;
	      }			
	}
	
	
	public void clear(){
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				grid[i][j] = 0;
				initcells[i][j] = 0;
			}
		}
	}
	
	//pour affecter les valeurs initiaux au grid du sudoku 
	public void initiate(){
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				grid[i][j] = initcells[i][j];
			}
		}
	}
	public void generate(){
		clear();
		Random rand = new Random();
		int row = 0;
		int col = 0;
		int val = 0;
		for(int i=0;i<9;i++)
		{
			
			if(i % 3 == 0){
				col = rand.nextInt(9);
				val = rand.nextInt(9) + 1;
				if(legal(i,col,val))
				{
					grid[i][col] = val;
					initcells[i][col] = val;
				}
				
			}
			for(int j=0;j<9;j++)
			{
				if(j%3 == 0){
					row = rand.nextInt(9);
					val = rand.nextInt(9) + 1;
					if(legal(row,j,val))
					{
						grid[row][j] = val;
						initcells[row][j] = val;
					}
				}
				if(i%3 == 0 && j%3 == 0){
					val = rand.nextInt(9) + 1;
					if(legal(i,j,val))
					{
						grid[i][j] = val;
						initcells[i][j] = val;
					}
				}
			}
		}
	     
	}

	
    public boolean backtracking(int i, int j) {

    	if (i == 9) {
            i = 0;
            if (++j == 9) 
            {
            	return true; // on est arrivé a la derniere case 
            }
                
        }
        if (grid[i][j] != 0) // Si la case n'est pas vide, on passe à la suivante (appel récursif)
        {
        	return backtracking(i+1,j);
        }
            

        for (int val = 1; val <= 9; ++val) {
            nbrsteps++;
            if (legal(i,j,val)) {
                grid[i][j] = val;
                if (backtracking(i+1,j))
                {
                	return true;
                }
                    
            }
        }
        grid[i][j] = 0; // reset on backtrack
        return false;
    }
  
    
    //pour le forward checking
    public ArrayList<Integer> getPossibleVals(int i,int j){
    	ArrayList<Integer> vals = new ArrayList<Integer>();

    	for (int k = 1; k <= 9; ++k) 
    	{
    		nbrsteps++;
    		if(legal(i,j,k)){
    			//System.out.println(" i = " + i + " j = " + j +" possible & legal value  = "+ k);
                vals.add(k);
    		}
    	}



        return vals; 
    	
    }
 
    public boolean forwardchecking(int i,int j){
    	

    	if (i == 9) {
            i = 0;
            if (++j == 9)
            {
            	return true; // on est arrivé a la derniere case 
            }
                
        }

	    
	    if (grid[i][j] != 0) // Si la case n'est pas vide, on passe à la suivante (appel récursif)
	        return forwardchecking(i + 1,j);

	    for (int k : getPossibleVals(i,j))
	    {
	    	nbrsteps++;
	    	System.out.println("nb steps = " + nbrsteps);
            grid[i][j] = k;
            if(forwardchecking(i+1,j))
                return true;

	    }
	    grid[i][j] = 0; //reseter en backtracking

	    return false;
    }

    //fonction qui compte les conflits d'une celulle
    public int Check(int i, int j)
    {
    	int conflict = 0 , penalty = 10;
    	
        int boxRowOffset = (i / 3)*3;
        int boxColOffset = (j / 3)*3;
        for (int k = 0; k < 3; ++k) // box
            for (int m = 0; m < 3; ++m)
            	if(m!=j || k!=i)
    			{
	                if (grid[i][j] == grid[boxRowOffset+k][boxColOffset+m])
	                {
	                	conflict = conflict + penalty;
	                }else{
	                	++conflict;
	                }
    			}
                   
        
    	for (int k = 0; k < 9; ++k)  // row
            if ( k != i && grid[i][j] == grid[k][j])
            {
            	conflict = conflict + penalty;
            }else{
            	++conflict;
            }
                

        for (int k = 0; k < 9; ++k) // col
            if ( k!= j && grid[i][j] == grid[i][k])
            {
            	conflict = conflict + penalty;
            }else{
            	++conflict;
            }
               

 
        
    	return conflict;
    }
  //fonction qui compte les conflits de toutes les celulles
    public int gridWideConflict(){
    	int conflict = 0;
    	for (int i = 0; i < 9; i++) 
    		for (int j = 0; j < 9; j++)
    				conflict += Check(i, j);
    	
    	return conflict;
    }
    //tester la validitée de la gird du sudoku
    public boolean isvalid(){

    	for (int i = 0; i < 9; i++) 
    		for (int j = 0; j < 9; j++)
    		{
    			
    			if(!legal(i,j,grid[i][j])){
    			
    				return false;
    			} 
    			

    		}
    	
    	return true;
    }
    
    
	int same_value = 5;
    public boolean minConflict() {
    	
    	int MinConflict = 2000;
    	Random rand = new Random();

    	
    	for (int i = 0; i < 9; i++) 
    		for (int j = 0; j < 9; j++)
    			if(grid[i][j] == 0){
    				grid[i][j] = rand.nextInt(9) + 1;
    			}
    			
    	
    	int conflict = gridWideConflict();
    	int newvalue = 0;
    	
    	boolean testing;
    	boolean spike = false;
    	int prev_conflict = 0 , prev_value;
    	int count = 0;

    	
    	while(conflict > 0){

    		int r_row = rand.nextInt(9);
    		int r_col = rand.nextInt(9);
    		while(grid[r_row][r_col] == initcells[r_row][r_col])
    		{
    			//System.out.println("gird[" + r_row+  "]["+r_col+"] = " + grid[r_row][r_col]);
    			r_row = rand.nextInt(9);
    			r_col = rand.nextInt(9);
    			
    		}
        	

    		int r_conflict = Check(r_row,r_col);

    		if (r_conflict > 0 && legal(r_row,r_col,grid[r_row][r_col]) == false )
    		{
    			
    			if(r_conflict==prev_conflict)
    				++count;
    			else
    				count = 0;
    			prev_conflict = r_conflict;
    			prev_value = grid[r_row][r_col];
    			
    			if(count>same_value)
    				spike = true;
    			for (int i=1; i<=9 && spike==false ;i++)		// Trouver la valeur de la cellule qui générerait le moins de conflits
    			{
    				grid[r_row][r_col] = i;					//Mettre à jour la grille [c] [r] vers une nouvelle valeur d'essai
    				int CellConflict = Check(r_row,r_col);		//Obtenir la valeur de conflit de la valeur d'essai
    				//Find the value that results in minimum conflict
    				if (CellConflict < MinConflict)						//Si la valeur d'essai résulte des conflits plus faibles
    				{
    					MinConflict = CellConflict;
    					newvalue = i;
    				}
    			}
    			
    			grid[r_row][r_col] = newvalue;
    			conflict = gridWideConflict(); //Mettre à jour la valeur globale du conflit	
    			MinConflict = 2000;		//Réinitialiser la valeur de MinConflict pour la boucle suivante
    			++nbrsteps;				//incementer le nbr d"iterations
    			

        		System.out.println("row = " + r_row  + " col = " +r_col +" grid = " +grid[r_row][r_col]+ " r_conflict = " + r_conflict + " conflict =" + conflict + " nbrsteps " + nbrsteps);
    			


    		} 
    		
    		if(spike==true)
    		{
    			int new_rand = rand.nextInt(9) + 1;
    			grid[r_row][r_col]= new_rand;
    			spike=false;
    			count = 0;
    		}
    		

    		if(isvalid()){
	      		//System.out.println(isvalid());
    			return true;
    		}
    			
    	}
    	
    	
    	
    	return false;
    			

    }

    public boolean LCV(){
        int i=0,j=0;
        int max=0;
        int tmp;
        for (int k = 0; k < 9; k++) {
            for (int m = 0; m < 9; m++) {
                nbrsteps++;
                if(grid[k][m]==0){
                    tmp = getPossibleVals(k, m).size();                 
                        if(tmp > max){
                            max=tmp;
                            i=k;j=m;
                            System.out.println("tmp = " + tmp + " i = " + k + " j = " + m + " gird =" + grid[k][m] + " possible vals "  + getPossibleVals(k,m));
                            //System.out.println("max = " + max + " i = " + i + " j = " + j);
                        }
                }
            }
        }
        
        //System.out.println("max = " + max + " i = " + i + " j = " + j + " gird =" + grid[i][j] + " possible vals" + getPossibleVals(i,j));
        if(max == 0){
            return true;
        }
        for(int k : getPossibleVals(i,j))
        {
            nbrsteps++;
            grid[i][j] = k;
            
            System.out.println(" i = " + i + " j = " + j + " gird =" + grid[i][j]);
			if( LCV() ){
				return true;
			}else
			{
				grid[i][j] = 0;
			}
				
        }
		grid[i][j] = 0;
		return false;
   } 
    
    public boolean MRV(){
        int i=0,j=0;
        int min=10;
        int tmp;
        for (int k = 0; k < 9; k++) {
            for (int m = 0; m < 9; m++) {
                nbrsteps++;
                if(grid[k][m]==0){
                    tmp = getPossibleVals(k, m).size();
                        if(tmp < min){
                            min=tmp;
                            i=k;j=m;
                            System.out.println("min = " + min + " i = " + i + " j = " + j);
                        }
                }
            }
        }
        if(min==10){
            return true;
        }
        for(int k : getPossibleVals(i,j))
        {
            nbrsteps++;
            grid[i][j] = k;
			if( MRV() ){
				return true;
			}else
			{
				grid[i][j] = 0;
			}
				
        }
		grid[i][j] = 0;
		return false;
   }
    

    public boolean legal(int i, int j, int val) {
       	//nbrsteps++;
    	for (int k = 0; k < 9; ++k)  // row
            if (val == grid[k][j] && k!= i)
            {
            	return false;
            }
                

        for (int k = 0; k < 9; ++k) // col
            if (val == grid[i][k] && k!= j)
            {
            	 return false;
            }
               

        int boxRowOffset = (i / 3)*3;
        int boxColOffset = (j / 3)*3;
        for (int k = 0; k < 3; ++k) // box
            for (int m = 0; m < 3; ++m)
                if (val == grid[boxRowOffset+k][boxColOffset+m] && boxRowOffset+k!= i && boxRowOffset+m != j)
                {
                	 return false;
                }
                   

        return true; // no violations, so it's legal
    }

 
    
    
    
    public boolean arcreduce(int xi,int xj,int yi,int yj)
    { 
    	  ArrayList<Integer> l1 = getPossibleVals(xi,xj);
    	  ArrayList<Integer> l2 = getPossibleVals(yi,yj);

    	  int one,two;
    	  boolean found=false, change=false;

    	  for(int a=0;a<l1.size();a++){
    	     one = (int) l1.get(a);
    	     found = false;            

    	     for(int b=0;b<l2.size();b++){
    	     two = (int) l2.get(b);

    	         if(one==two && xi==yi) //same int same line
    	             found=true;
    	         if(one==two && xj==yj) //same int same column
    	             found=true;

    	     }
    	     if(found==false){
 	    	     l1.remove(a);
 	    	     change=true;
    	     }

    	  }
    	  return change;
    }
   
//    public boolean AC3() {
// 		
//    	
//    	for(int i=0;i<9;i++)
//    		for(int j=0;j<9;j++)
//    		{
//    			nbrsteps++;
//    			if(i < 8 && j < 8){
//    				if(!arcreduce(i,j,i+1,j+1)) {
//        				continue;
//        			}
//    			}
//    			
//    			
//    			int assignedRow = i;
//    			int assignedCol = j;
//    			int otherRow = i+1;
//    			int otherCol = j+1;
//    			
//    			if (getPossibleVals(assignedRow, assignedCol).size() == 0) {
//    				return false;
//    			}
//    			
//    			int startRow = (assignedRow / 3) * 3;
//    			int startCol = (assignedCol / 3) * 3;
//    			int endRow = startRow + 3;
//    			int endCol = startCol + 3;
//    			
//
//    			for (int row = startRow; row < endRow; row++) {
//    				for (int col = startCol; col < endCol; col++) {
//    					if (row != assignedRow && col != assignedCol && row != otherRow && col != otherCol) {
//    						//arcs.enqueue(new Index[] { new Index(row, col), new Index(assignedRow, assignedCol) });
//    						grid[row][col] = getPossibleVals(assignedRow, assignedCol).get(0);
//    					}
//    				}
//    			}
// 
//    			for (int row = 0; row < 9; row++) {
//    				if (row != assignedRow && row != otherRow) {
//    					//arcs.enqueue(new Index[] { new Index(row, assignedCol), new Index(assignedRow, assignedCol) });
//    					grid[row][assignedCol] = getPossibleVals(assignedRow, assignedCol).get(0);
//    				}
//    			}
//
//    			/* Queue squares in current column */
//    			for (int col = 0; col < 9; col++) {
//    				if (col != assignedCol && col != otherCol) {
//    					//arcs.enqueue(new Index[] { new Index(assignedRow, col), new Index(assignedRow, assignedCol) });
//    					grid[assignedRow][col] = getPossibleVals(assignedRow, assignedCol).get(0);
//    				}
//    			}
//    		}
//    		
// 	
//
// 		return true;
// 	}
    
    


    
    public static void main(String[] args) {
        Sudoku sd = new Sudoku();

    }

}
