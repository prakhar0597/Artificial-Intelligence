import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;



class test
{
	double eval;
	String move;
	test(double eval, String move)
	{
		this.eval = eval;
		this.move = move;
	}
}


public class Halma {

	//static ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> sol = new ArrayList<>();
	
	static void get_path(String show, String square[][])
	{
		String source[] = show.split(",");	
    	int sx = Integer.parseInt(source[0]);
    	int sy = Integer.parseInt(source[1]);
    	int dx = Integer.parseInt(source[2]);
    	int dy = Integer.parseInt(source[3]);
		
//		int sx = 9;
//    	int sy = 9;
//    	int dx = 13;
//    	int dy = 13;
		
		int dr[] = {-1,-1,-1,0,0,1,1,1};
		int dc[] = {-1,0,1,-1,1,-1,0,1};
		
		String goal = dx + "," + dy;
		
		int flag = 0;
		if(flag==0)
		{
			int rr=0,cc=0;
			for(int i=0; i<8; i++)
			{
				rr = sx + dr[i];
				cc = sy + dc[i];
				if(rr==dx && cc==dy)
				{
					System.out.print("E" + " " + sy +"," + sx + " " + dy + "," + dx);
					flag = 1;
					break;
				}
			}
			
		}
		if(flag==0) 
		{
				
			
			Queue<String> q = new LinkedList<>();
			boolean visited[][] = new boolean[16][16];
			String parent[][] = new String[16][16];
			for(int i=0; i<16; i++)
			{
				for(int j=0; j<16; j++)
				{
					visited[i][j]=false;
				}
			}
			
			if(!(sx<0 || sy <0 || sx >= 16 || sy >= 16))
			{
				String root = sx + ","+ sy;		
				q.add(root);
				visited[sx][sy]=true;
				parent[sx][sy]="root";
				//System.out.println(parent[sx][sy]);
			}
                  	
		
			String node;
			String arg[];
			ArrayList<String> list = new ArrayList<>();
			do
			{
				if(q.isEmpty())
				{
					break;
				}
				node = q.remove();
				if(node.equalsIgnoreCase(goal)) 
				{
					
					String dest[] = node.split(",");
					int x = Integer.parseInt(dest[0]);
					int y = Integer.parseInt(dest[1]);
					String print = y + "," + x;
					list.add(print);
					while(!(parent[x][y].equals("root")))
					{
						dest = parent[x][y].split(",");
						x = Integer.parseInt(dest[0]);
						y = Integer.parseInt(dest[1]);
						print = y + "," + x;
						list.add(print);
					}
					Collections.reverse(list);
					for (int i = 0; i < list.size()-1;i++) 
					{ 	
						if(i>0)
						{
							System.out.println();
						}	
						System.out.print("J " + list.get(i) + " " + list.get(i+1));
					}   
					break;
				}
				arg = node.split(",");
				int c1;
				int c2;
				
				//int dr[] = {-1,-1,-1,0,0,1,1,1};
				//int dc[] = {-1,0,1,-1,1,-1,0,1};
				int rr=0,cc=0;
				//System.out.println("h");
				for(int i=0; i<8; i++)
				{
					c1 = Integer.parseInt(arg[0]);
					c2 = Integer.parseInt(arg[1]);
					//System.out.println("*******" + c1 + "*******" + c2);
					rr = c1 + dr[i];
					cc = c2 + dc[i];
					//System.out.println("*******" + rr + "*******" + cc);
					if(rr<0 || cc<0 || rr >= 16 || cc >= 16 || visited[rr][cc]==true)
					{
						continue;
					}
					if(square[rr][cc].equals("."))
					{
						continue;
					}
					
					rr = c1 + dr[i] + dr[i];
					cc = c2 + dc[i] + dc[i];
					//System.out.println("h");
					if(rr<0 || cc<0 || rr >= 16 || cc >= 16 || visited[rr][cc]==true)
					{
						continue;
					}
					//System.out.println("h");
					if(!square[rr][cc].equals("."))
					{
						continue;
					}
					//System.out.println("h");
					q.add(rr + "," + cc);
					parent[rr][cc] = c1 + "," + c2;
					visited[rr][cc]=true;
					//System.out.println(visited[rr][cc] + ""+cc +""+ rr);
				}
			}while(true);
		}
	}
	
	
	static double eval_func(String [][] square, int box[][], String max, String min)
	{
		
		double value = 0;
		for(int a=0; a<16; a++)
		{
			for(int b=0; b<16; b++)
			{
				//If Computer coin is black and is in home.
				if(max.equals("B") && square[a][b].equals("B"))
				{
					//System.out.println("Hi");
					int k = 0;
					double dist = -10000;
				   	for(int i=10; i<16; i++)
				   	{
				   		for(int j=15-k; j<16; j++)
				   		{
				   			if(i==10 && j==15 || j==10 && i==15)
				   			{	
				   					continue;
				   			}
				   			if(square[i][j].equalsIgnoreCase("."))
				   			{
				   				dist = Math.max(dist, eucledian(a,b,i,j));
				   				//System.out.println(dist);
				   			}
				   		}
				   		k++;
				   	}

				   	if(dist==-10000)
						value = value + 40;
					else
						value = value - dist;
				}
				//  If Computer coin is black and is outside.				
				else if(max.equals("B") && square[a][b].equals("W"))
				{
					double dist = -10000;
					for(int i=0; i<5; i++)
					{
						for(int j=5-i; j>=0; j--)
						{
							if(i==0 && j==5 || j==0 && i==5)
							{
								continue;
							}
							if(square[i][j].equalsIgnoreCase("."))
				   			{
								dist = Math.max(dist, eucledian(a,b,i,j));
								//System.out.println(dist);
				   			}
						}
					}

				   	if(dist==-10000)
						value = value + 40;
					else
						value = value + dist;
				}
				//If Computer coin is white and is in home.
				if(max.equals("W") && square[a][b].equals("W"))
				{
					double dist = -10000;
					for(int i=0; i<5; i++)
					{
						for(int j=5-i; j>=0; j--)
						{
							if(i==0 && j==5 || j==0 && i==5)
							{
								continue;
							}
							
							if(square[i][j].equals("."))
				   			{
								dist = Math.max(dist, eucledian(a,b,i,j));
//								System.out.println(dist);
				   			}
							
						}
					}
					if(dist==-10000)
						value = value + 40;
					else
						value = value - dist;
//					for(int i=0; i<5; i++)
//					{
//						for(int j=5-i; j>=0; j--)
//						{
//							if(square[a][b].equals("W"))
//							{
//								value = value + 750;
//							}
//						}
//					}
					//System.out.println(value);
				}
				//If Computer coin is white and is outside.
				else if(max.equals("W") && square[a][b].equals("B"))
				{
					int k = 0;
					double dist = -10000;
				   	for(int i=10; i<16; i++)
				   	{
				   		for(int j=15-k; j<16; j++)
				   		{
				   			if(i==10 && j==15 || j==10 && i==15)
				   			{	
				   					continue;
				   			}
				   			if(square[i][j].equals("."))
				   			{
				   				dist = Math.max(dist, eucledian(a,b,i,j));
				   				//System.out.println(dist);
				   			}
				   		}
				   		k++;
				   	}
				   	if(dist==-10000)
						value = value + 40;
					else
						value = value + dist;
				}
			}
		}
		
		return value;
	}
		
	
	private static double eucledian(int x, int y, int i, int j) {
		
		double dist = Math.sqrt((i - x)*(i - x) + (j - y)*(j - y));
		
		return dist;
	}
	
	private static double man(int x, int y, int i, int j) {
		
		double dist = Math.abs((i - x)) + Math.abs((j - y));
		
		return dist;
	}


	static ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> next_move(String square[][], int box[][], String max)
	{
		ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> arr = new ArrayList<>();
		
		ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> out = new ArrayList<>();		
		
		ArrayList<ArrayList<Integer>> blockage = new ArrayList<>();
		int flag = 0;
		if(max.equalsIgnoreCase("B"))
		{
			
			for(int i=0; i<5; i++)
			{
				for(int j=5-i; j>=0; j--)
				{
					if(i==0 && j==5 || j==0 && i==5)
					{
						continue;
					}	
					if(square[i][j].equals("B"))
					{
						flag=1;
						//System.out.println("HI");
						tile_move_new(i, j, square, box, max, arr, blockage, out);
						
					}
				}
			}
			//System.out.println(blockage);
			//System.out.println(arr);
			if(flag==1 && arr.isEmpty())
			{
				//System.out.println(arr);
				for(int i=0; i<16; i++)
				{
					for(int j=0; j<16; j++)
					{
						if((i==0 && (j==0 || j==1 || j==2 || j==3 || j==4)) || (i==1 && (j==0 || j==1 || j==2 || j==3 || j==4)) || (i==2 && (j==0 || j==1 || j==2 || j==3)) || (i==3 && (j==0 || j==1 || j==2)) || (i==4 && (j==0 || j==1)))
						{
							continue;
						}	
						if(square[i][j].equals("B"))
						{
							for (int s = 0; s < blockage.size(); s++) 
							{
								int bx = blockage.get(s).get(0);
								int by = blockage.get(s).get(1);
								tile_move(bx, by, square, box, max, arr);
							}
						}
					}
				}
			}
			if(flag==0 || arr.isEmpty())
			{
				for(int i=0; i<16; i++)
				{
					for(int j=0; j<16; j++)
					{
						if(square[i][j].contentEquals("B"))
						{
							tile_move(i, j, square, box, max, arr);
						}
					}
				}
			}
		}
		//System.out.println(arr);
		
		if(max.equalsIgnoreCase("W"))
		{
			
			int k = 0;
		   	for(int i=10; i<16; i++)
	   		{
	   			for(int j=15-k; j<16; j++)
		   		{
	   				if(i==10 && j==15 || j==10 && i==15)
	   				{
						continue;
					}	
					if(square[i][j].equals("W"))
					{
						flag=1;
						tile_move_new(i, j, square, box, max, arr, blockage, out);
					}
				}
	   			k++;
			}
			//System.out.println(arr);
		   	
			if(flag==1 && arr.isEmpty())
			{
				//System.out.println(arr);
				for(int i=0; i<16; i++)
				{
					for(int j=0; j<16; j++)
					{
						if((i==15 && (j==15 || j==14 || j==13 || j==12 || j==11)) || (i==14 && (j==15 || j==14 || j==13 || j==12 || j==11)) || (i==13 && (j==15 || j==14 || j==13 || j==12)) || (i==12 && (j==15 || j==14 || j==13)) || (i==11 && (j==15 || j==14)))
						{
							continue;
						}	
						if(square[i][j].equals("W"))
						{
							for (int s = 0; s < blockage.size(); s++) 
							{
								int bx = blockage.get(s).get(0);
								int by = blockage.get(s).get(1);
								tile_move(bx, by, square, box, max, arr);
							}
						}
					}
				}
			}
			if(flag==0 || arr.isEmpty())
			{
				for(int i=0; i<16; i++)
				{
					for(int j=0; j<16; j++)
					{
						if(square[i][j].contentEquals("W"))
						{
							tile_move(i, j, square, box, max, arr);
						}
					}
				}
			}
		}
		if(!out.isEmpty())
			return out;
		//System.out.println(arr);
		return arr;
	}
	
	
	static ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> tile_move_new(int x, int y, String square[][], int box[][], String c, ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> arr, ArrayList<ArrayList<Integer>> blockage, ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> out)
	{
		int player=0;
		int g1=15, g2=15;
		if(c.equals("B"))
		{
				player = 1;
				g1=15;
				g2=15;
		}
		if(c.equals("W"))
		{
				player = 2;
				g1=0;
				g2=0;
		}
		ArrayList<Integer> valid = new ArrayList<>();
		//Arraylist<ArrayList<Integer>>
		valid.add(0);
		valid.add(1);
		valid.add(2);
		if(box[x][y] != player)
		{
			valid.remove(Integer.valueOf(player));
		}
		if(box[x][y] != 0 && box[x][y] != player)
		{
			valid.remove(Integer.valueOf(0));
		}
		
		int dx[] = {-1,-1,-1,0,0,1,1,1};
		int dy[] = {-1,0,1,-1,1,-1,0,1};
		int xx=0, yy=0;
		for(int i=0; i<8; i++)
		{
			xx = x + dx[i];
			yy = y + dy[i];
			if(xx<0 || yy<0 || xx >= 16 || yy >= 16)
			{
				continue;
			}
			if(!valid.contains(box[xx][yy]))
			{
				continue;
			}
			if(!square[xx][yy].equals("."))
			{
				if(g1==0 && g2==0)
				{
					if(!((xx==15 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==14 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==13 && (yy==15 || yy==14 || yy==13 || yy==12)) || (xx==12 && (yy==15 || yy==14 || yy==13)) || (xx==11 && (yy==15 || yy==14))))
					{
						ArrayList<Integer> scold = new ArrayList<>();
						scold.add(xx);
						scold.add(yy);
						blockage.add(scold);
					}
				}
				if(g1==15 && g2==15)
				{
					if(!((xx==0 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==1 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==2 && (yy==0 || yy==1 || yy==2 || yy==3)) || (xx==3 && (yy==0 || yy==1 || yy==2)) || (xx==4 && (yy==0 || yy==1))))
					{
						ArrayList<Integer> scold = new ArrayList<>();
						scold.add(xx);
						scold.add(yy);
						blockage.add(scold);
					}
				}
			}
			if(square[xx][yy].equals("."))
			{	
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				if(g1==0 && g2==0)
				{
					if((xx==15 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==14 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==13 && (yy==15 || yy==14 || yy==13 || yy==12)) || (xx==12 && (yy==15 || yy==14 || yy==13)) || (xx==11 && (yy==15 || yy==14)))
					{
						if(man(xx, yy, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				if(g1==15 && g2==15)
				{
					if((xx==0 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==1 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==2 && (yy==0 || yy==1 || yy==2 || yy==3)) || (xx==3 && (yy==0 || yy==1 || yy==2)) || (xx==4 && (yy==0 || yy==1)))
					{
						if(man(xx, yy, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				key.add(x);
				key.add(y);
				value.add(xx);
				value.add(yy);
				hm.put(key, value);
				arr.add(hm);
				if(g1==15 && g2==15)
				{
					if(!((xx==0 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==1 && (yy==0 || yy==1 || yy==2 || yy==3 || yy==4)) || (xx==2 && (yy==0 || yy==1 || yy==2 || yy==3)) || (xx==3 && (yy==0 || yy==1 || yy==2)) || (xx==4 && (yy==0 || yy==1))))
					{
						out.add(hm);
					}
				}
				
				if(g1==0 && g2==0)
				{
					if(!((xx==15 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==14 && (yy==15 || yy==14 || yy==13 || yy==12 || yy==11)) || (xx==13 && (yy==15 || yy==14 || yy==13 || yy==12)) || (xx==12 && (yy==15 || yy==14 || yy==13)) || (xx==11 && (yy==15 || yy==14))))
					{
						out.add(hm);
					}
				
				}
				
				continue;
			}
			else if(square[xx][yy].equals("B") || square[xx][yy].equals("W"))
			{
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				int aa = xx + dx[i];
				int bb = yy + dy[i];
				if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
				{
					continue;
				}
				if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
				{
					continue;
				}
				if(!valid.contains(box[aa][bb]))
				{
					continue;
				}
				if(g1==0 && g2==0)
				{
					if((aa==15 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==14 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==13 && (bb==15 || bb==14 || bb==13 || bb==12)) || (aa==12 && (bb==15 || bb==14 || bb==13)) || (aa==11 && (bb==15 || bb==14)))
					{
						if(man(aa, bb, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				if(g1==15 && g2==15)
				{
					if((aa==0 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==1 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==2 && (bb==0 || bb==1 || bb==2 || bb==3)) || (aa==3 && (bb==0 || bb==1 || bb==2)) || (aa==4 && (bb==0 || bb==1)))
					{
						if(man(aa, bb, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				key.add(x);
				key.add(y);
				value.add(aa);
				value.add(bb);

				hm.put(key, value);
				arr.add(hm);
				
				if(g1==0 && g2==0)
				{
					if(!((aa==15 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==14 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==13 && (bb==15 || bb==14 || bb==13 || bb==12)) || (aa==12 && (bb==15 || bb==14 || bb==13)) || (aa==11 && (bb==15 || bb==14))))
					{
						out.add(hm);
					}
				}
				if(g1==15 && g2==15)
				{
					if(!((aa==0 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==1 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==2 && (bb==0 || bb==1 || bb==2 || bb==3)) || (aa==3 && (bb==0 || bb==1 || bb==2)) || (aa==4 && (bb==0 || bb==1))))
					{
						out.add(hm);
					}
				}
				
				jump_new(aa, bb, valid, box, square, x, y, arr, dx, dy, player, out);
			}
		}
		return arr;
		
	}
			
	static ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> tile_move(int x, int y, String square[][], int box[][], String c, ArrayList<HashMap<ArrayList<Integer>,ArrayList<Integer>>> arr)
	{
		int player=0;
		if(c.equals("B"))
				player = 1;
		if(c.equals("W"))
				player = 2;
		ArrayList<Integer> valid = new ArrayList<>();
		//Arraylist<ArrayList<Integer>>
		valid.add(0);
		valid.add(1);
		valid.add(2);
		if(box[x][y] != player)
		{
			valid.remove(Integer.valueOf(player));
		}
		if(box[x][y] != 0 && box[x][y] != player)
		{
			valid.remove(Integer.valueOf(0));
		}
		
		
				
		int dx[] = {-1,-1,-1,0,0,1,1,1};
		int dy[] = {-1,0,1,-1,1,-1,0,1};
		int xx=0, yy=0;
		for(int i=0; i<8; i++)
		{
			xx = x + dx[i];
			yy = y + dy[i];
			if(xx<0 || yy<0 || xx >= 16 || yy >= 16)
			{
				continue;
			}
			if(!valid.contains(box[xx][yy]))
			{
				continue;
			}
			if(square[xx][yy].equals("."))
			{	
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				key.add(x);
				key.add(y);
				value.add(xx);
				value.add(yy);
				hm.put(key, value);
				arr.add(hm);
				continue;
			}
			else if(square[xx][yy].equals("B") || square[xx][yy].equals("W"))
			{
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				int aa = xx + dx[i];
				int bb = yy + dy[i];
				if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
				{
					continue;
				}
				if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
				{
					continue;
				}
				if(!valid.contains(box[aa][bb]))
				{
					continue;
				}
				
				key.add(x);
				key.add(y);
				value.add(aa);
				value.add(bb);

				hm.put(key, value);
				arr.add(hm);
				jump(aa, bb, valid, box, square, x, y, arr, dx, dy, player);
			}
		}
		return arr;
	}
	
	static void jump_new(int xx, int yy, ArrayList<Integer> valid, int box[][], String square[][], int x, int y, ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> arr, int dx[], int dy[], int player, ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> out)
	{
		int aa, bb, g1 = 0, g2 = 0;
		ArrayList<Integer> valid1 = new ArrayList<Integer>(valid);
		if(player==1)
		{
			g1 = 15;
			g2 = 15;
		}
		if(player==2)
		{
			g1 = 0;
			g2 = 0;
		}
		for(int j=0; j<8; j++)
		{
			aa = xx + dx[j];
			bb = yy + dy[j];
			if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
			{
				continue;
			}
			if(box[aa][bb] != 0 && box[aa][bb] != player)
			{
				valid1.remove(Integer.valueOf(0));
			}
			if(!valid1.contains(box[aa][bb]))
			{
				continue;
			}
			if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
			{
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				aa = aa + dx[j];
				bb = bb + dy[j];
				if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
				{
					continue;
				}
				if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
				{
					continue;
				}
				if(!valid1.contains(box[aa][bb]))
				{
					continue;
				}
				if(g1==0 && g2==0)
				{
					if((aa==15 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==14 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==13 && (bb==15 || bb==14 || bb==13 || bb==12)) || (aa==12 && (bb==15 || bb==14 || bb==13)) || (aa==11 && (bb==15 || bb==14)))
					{
						if(man(aa, bb, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				if(g1==15 && g2==15)
				{
					if((aa==0 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==1 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==2 && (bb==0 || bb==1 || bb==2 || bb==3)) || (aa==3 && (bb==0 || bb==1 || bb==2)) || (aa==4 && (bb==0 || bb==1)))
					{
						if(man(aa, bb, g1, g2) >= man(x, y, g1, g2)) 
						{
							continue;
						}
					}
				}
				key.add(x);
				key.add(y);
				value.add(aa);
				value.add(bb);

				hm.put(key, value);
				if(arr.contains(hm))
					continue;
				arr.add(hm);
				
				if(g1==0 && g2==0)
				{
					if(!((aa==15 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==14 && (bb==15 || bb==14 || bb==13 || bb==12 || bb==11)) || (aa==13 && (bb==15 || bb==14 || bb==13 || bb==12)) || (aa==12 && (bb==15 || bb==14 || bb==13)) || (aa==11 && (bb==15 || bb==14))))
					{
						out.add(hm);
					}
				}
				if(g1==15 && g2==15)
				{
					if(!((aa==0 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==1 && (bb==0 || bb==1 || bb==2 || bb==3 || bb==4)) || (aa==2 && (bb==0 || bb==1 || bb==2 || bb==3)) || (aa==3 && (bb==0 || bb==1 || bb==2)) || (aa==4 && (bb==0 || bb==1))))
					{
						out.add(hm);
					}
				}
				
				jump_new(aa, bb, valid1, box, square, x, y, arr, dx, dy, player, out);
			}
		}
		
	}
	
	static void jump(int xx, int yy, ArrayList<Integer> valid, int box[][], String square[][], int x, int y, ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> arr, int dx[], int dy[], int player)
	{
		int aa, bb;
		ArrayList<Integer> valid1 = new ArrayList<Integer>(valid);
		for(int j=0; j<8; j++)
		{
			aa = xx + dx[j];
			bb = yy + dy[j];
			if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
			{
				continue;
			}
			if(box[aa][bb] != 0 && box[aa][bb] != player)
			{
				valid1.remove(Integer.valueOf(0));
			}
			if(!valid1.contains(box[aa][bb]))
			{
				continue;
			}
			if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
			{
				HashMap<ArrayList<Integer>, ArrayList<Integer>> hm = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();
				ArrayList<Integer> key = new ArrayList<>();
				ArrayList<Integer> value = new ArrayList<>();
				aa = aa + dx[j];
				bb = bb + dy[j];
				if(aa<0 || bb<0 || aa >= 16 || bb >= 16)
				{
					continue;
				}
				if(square[aa][bb].equals("B") || square[aa][bb].equals("W"))
				{
					continue;
				}
				if(!valid1.contains(box[aa][bb]))
				{
					continue;
				}

				key.add(x);
				key.add(y);
				value.add(aa);
				value.add(bb);

				hm.put(key, value);
				if(arr.contains(hm))
					continue;
				arr.add(hm);
				jump(aa, bb, valid1, box, square, x, y, arr, dx, dy, player);
			}
		}
		
	}
	static int flag = 0;
	static test minimax(int depth, String max, double beta, double alpha, boolean maxing, String[][] square, int[][] box, String min, long startTime)
	{
		//System.out.println(depth);
		ArrayList<HashMap<ArrayList<Integer>, ArrayList<Integer>>> arr = new ArrayList<>();
		test pair = null;
		String best_move = null;
		boolean terminal = terminal_state(square, max);
		if(terminal)
		{
			
			double eval = eval_func(square, box, max, min);
			pair = new test(eval, null);
			//System.out.println(pair.move);
			return pair;
		}
		long endTime = System.currentTimeMillis();
   		long time = (endTime - startTime)/1000;
   		
		if(depth == 0 || time > 11.9)
		{
			//System.out.println(time);
			double eval = eval_func(square, box, max, min);
			pair = new test(eval, null);
			return pair;
		}
		
		double value;
		if(maxing)
		{
			value = Double.NEGATIVE_INFINITY;
			arr = next_move(square, box, max);
			//System.out.println(arr +"*********"+depth);
		}
		else
		{
			value = Double.POSITIVE_INFINITY;
			arr = next_move(square, box, min);
			//System.out.println(arr +"*********"+depth);
		}	
		
		int x, y;
		int x1, y1;
		for(int i=0; i<arr.size(); i++)
		{
			for (ArrayList<Integer> key : arr.get(i).keySet()) 
			{
				
				
				x = key.get(0);
				y = key.get(1);
				ArrayList<Integer> value1;
				value1 = (arr.get(i)).get(key);
				x1 = value1.get(0);
				y1 = value1.get(1);
				
				
				square[x1][y1] = square[x][y];
				square[x][y] = ".";
				
				
								
				test temp = minimax(depth-1, max, beta, alpha, !maxing, square, box, min,startTime);
				
				
				
				square[x][y] = square[x1][y1];
				square[x1][y1] = ".";
				
				
				
				
				if(maxing)
				{
					if(temp.eval >= value)
					{
						value = temp.eval;
						best_move =  x + "," + y + "," + x1 + "," + y1;
						pair = new test(value, best_move);
						alpha = Math.max(alpha, value);
					}
					
				}
				
				if(maxing==false)
				{	
					if(temp.eval < value)
					{
						value = temp.eval;
						best_move =  x + "," + y + "," + x1 + "," + y1;
						pair = new test(value, best_move);
						beta = Math.min(beta, value);
					}
				}
				if(beta<=alpha)
					return pair;
			}
			if(flag==1)
			{
				break;
			}
		}
		return pair;     
	}
	

	
	static boolean terminal_state(String[][] square, String max) {
		
		if(max.equalsIgnoreCase("B"))
		{
			int k = 0;
		   	for(int i=10; i<16; i++)
	   		{
	   			for(int j=15-k; j<16; j++)
		   		{
	   				if(i==10 && j==15 || j==10 && i==15)
	   				{
	   					continue;
	   				}
	   				if(!square[i][j].equalsIgnoreCase("B"))
	   					return false;
		   		}
   				k++;
		   	}
		}
		else
		{
			for(int i=0; i<5; i++)
	   		{
	   			for(int j=5-i; j>=0; j--)
		   		{
	   				if(i==0 && j==5 || j==0 && i==5)
	   				{
	   					continue;
	   				}
	   				if(!square[i][j].equalsIgnoreCase("W"))
	   					return false;
		   		}
		   	}
		}
		return true;
	}


	public static void main(String[] args) {
		
		
		BufferedReader br = null;
		try {
			long startTime = System.currentTimeMillis();
			int x=16, y=16;
			File in = new File("D:/input2.txt");
			br = new BufferedReader(new FileReader(in));
		    PrintStream fileOut = new PrintStream(new File("D:/output2.txt"));
		    System.setOut(fileOut);
		    String 	chance = br.readLine();
		   	String color = br.readLine();
		   	double time = Double.parseDouble(br.readLine());
		   	String square[][] = new String[x][y];
		   	int box[][] = new int[x][y];
		   	
		   	String line;
		   	for(int i=0; i<x; i++)
		   	{
		   		line = br.readLine();
		   		String mesh[] = line.trim().split("");	
		   		for(int j=0; j<y; j++)
		   		{
		   			square[i][j] = mesh[j];
		   		}
		   	}
		   	int yes = 0;
		   	for(int i=0; i<5; i++)
	   		{
	   			for(int j=5-i; j>=0; j--)
		   		{
	   				if(i==0 && j==5 || j==0 && i==5)
	   				{
	   					continue;
	   				}
	   				box[i][j] = 1;
	   				if(color.equals("WHITE"))	
	   			   	{
	   					if(square[i][j].equals("W"))
	   					{
	   						yes = yes +1;
	   					}
	   				}
		   		}
		   	}
		   	
		   	int k = 0;
		   	for(int i=10; i<16; i++)
	   		{
	   			for(int j=15-k; j<16; j++)
		   		{
	   				if(i==10 && j==15 || j==10 && i==15)
	   				{
	   					continue;
	   				}
	   				box[i][j] = 2;
	   				if(color.equals("BLACK"))	
	   			   	{
	   					if(square[i][j].equals("B"))
	   					{
	   						yes = yes +1;
	   					}
	   				}
		   		}
   				k++;
		   	}
		   	int depth = 3;
		   	if(chance.equals("SINGLE"))
		   		depth = 3;
		   	if(chance.equals("GAME"))
		   	{		   		
		   		if(time >= 14.1 && time < 60.1)
		   			depth =2;
		   		else if(time< 14.1)
		   			depth =1;
		   		if(yes > 14 && time > 50)
		   			depth =3;
		   		if(yes > 17)
		   			depth =2;
		   	}
			double beta=Double.POSITIVE_INFINITY, alpha=Double.NEGATIVE_INFINITY;
			boolean maxing = true;
					
			
		   	if(color.equals("BLACK"))	
		   	{
		   		test print = minimax(depth, "B", beta, alpha, maxing, square, box, "W", startTime);
		   		get_path(print.move, square);
		   		//long endTime = System.currentTimeMillis();
		   		//System.out.println("That took " + (endTime - startTime) + " milliseconds");
		   	}
		   	
		   	else if(color.equals("WHITE"))
		   	{
		   		test print = minimax(depth, "W", beta, alpha, maxing, square, box, "B", startTime);
		   		//System.out.println(print.move);
		   		get_path(print.move, square);
		   		
		   		//long endTime = System.currentTimeMillis();
		   		//System.out.println("That took " + (endTime - startTime) + " milliseconds");
		   	}
		   	
		   			   	
		}
		catch (FileNotFoundException e)
		{
		   e.printStackTrace();
		} 
		catch (IOException e)
		{
		   e.printStackTrace();
		}
		finally 
		{
		    try
		    {
		        if (br != null)
		        {
		            br.close();
		        }
		    } 
		    catch (IOException e) 
		    {
		    	e.printStackTrace();
		    }
		}
	}
}