
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Comparable;
import java.lang.Math.*;

class st implements Comparable<st>
{
		String value;
		String parent;
		int cost;
		public st(String value, String parent, int cost) 
		{
	        this.value = value;
	        this.parent = parent;
	        this.cost = cost;
	    }
		public int compareTo(st s) 
	    { 
			
			return this.cost - s.cost;
	    } 
}


class as implements Comparable<as>
{
		String value;
		String parent;
		int cost;
		double hcost;
		double f;
		public as(String value, String parent, int cost, double hcost, double f) 
		{
	        this.value = value;
	        this.parent = parent;
	        this.cost = cost;
	        this.hcost = hcost;
	        this.f = f;
	    }
		
		public int compareTo(as a) 
	    { 
	        if (this.f < a.f)
            {
                return -1;
            }
            if ( this.f  > a.f )
            {
                return 1;
            }
            return 0;
	    } 
}

public class optimalPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		try {
			File in = new File("D:\\input1.txt");
			String algo;
		    br = new BufferedReader(new FileReader(in));
		    PrintStream fileOut = new PrintStream(new File("D:\\output.txt"));
		    System.setOut(fileOut); 
		    String value = null;
		    if((value = br.readLine()) != null)
		    {
		    	algo = value;
		    	value = br.readLine();
		    	//System.out.println(algo);
		    	String val[] = value.trim().split("\\s+");	
		    	int cols = Integer.parseInt(val[0]);
		    	int rows = Integer.parseInt(val[1]);  	
		    	//System.out.println(cols + " " + rows);
		    	value = br.readLine();
		    	String source[] = value.trim().split("\\s+");	
		    	int sx = Integer.parseInt(source[0]);
		    	int sy = Integer.parseInt(source[1]);
		    	//System.out.println(sx + " " + sy);
		    	value = br.readLine();
		    	int sz = Integer.parseInt(value);
		    	//System.out.println(sz);
		    	value = br.readLine();
		    	int num_of_out = Integer.parseInt(value);
		    	//System.out.println(num_of_out);
		    	int dx[] = new int[num_of_out];
		    	int dy[] = new int[num_of_out];
		    	String g[] = new String[num_of_out];
		    	for(int i=0; i<num_of_out;i++)
		    	{
		    		value = br.readLine();
		    		String tar[] = value.trim().split("\\s+");	
		    		dx[i] = Integer.parseInt(tar[0]);
		    		dy[i] = Integer.parseInt(tar[1]);
		    		g[i] = tar[1] + "," + tar[0];
		    		//System.out.println(dx[i] + " " + dy[i] + " Goal:" + g[i]);
		    	}
		    	int elevation[][] = new int[rows][cols];
		    	for(int i=0; i<rows;i++)
		    	{
		    		value = br.readLine();
		    		String mesh[] = value.trim().split("\\s+");	
		    		for(int j=0; j<cols;j++)
			    	{
			    		elevation[i][j] = Integer.parseInt(mesh[j]);
			    	}
		    	}
		    	/*for(int i=0; i<rows;i++)
		    	{
		    		for(int j=0; j<cols;j++)
			    	{
		    			System.out.print(elevation[i][j] + " ");
			    	}	
		    		System.out.println();
		    	}*/
		    	if(algo.equals("BFS"))
		    	{
		    		for(int k=0; k<num_of_out;k++)
		    		{		 
		    			if(k>0)
		    			{
		    				System.out.println();
		    			}
		    			//System.out.print(g[k]);
		    			Queue<String> q = new LinkedList<>();
		    			if(!(sx<0 || sy <0 || sx >= cols || sy >= rows))
		    			{
		    				String root = sy + ","+ sx;		//Check validity if given in input or not eg given -1,-1 , then not valid input
		    				q.add(root);
		    			}
		    			boolean visited[][] = new boolean[rows][cols];
		    			String parent[][] = new String[rows][cols];
		    			for(int i=0; i<rows; i++)
		    			{
		    				for(int j=0; j<cols; j++)
		    				{
		    					visited[i][j]=false;
		    				}
		    			}
		    			visited[sy][sx]=true;
		    			parent[sy][sx]="root";
		    			//System.out.print(q);
		    			String node;
		    			String arg[];
		    			ArrayList<String> list = new ArrayList<>();
		    			do
		    			{
		    				if(q.isEmpty())
		    				{
		    					System.out.print("Failure");
		    					break;
		    				}
		    				node = q.remove();
		    				if(node.equalsIgnoreCase(g[k])) //Correct the goal
		    				{
		    					//System.out.println(node);
		    					//System.out.println("Goal");
		    					String dest[] = node.split(",");
		    					int x = Integer.parseInt(dest[0]);
		    					int y = Integer.parseInt(dest[1]);
		    					String print = y + "," + x;
		    					list.add(print);
		    					//System.out.print(print);
		    					//System.out.println(parent[y][x]);
		    					while(!(parent[x][y].equals("root")))
		    					{
		    						dest = parent[x][y].split(",");
		    						x = Integer.parseInt(dest[0]);
		    						y = Integer.parseInt(dest[1]);
		    						print = y + "," + x;
		    						list.add(print);
		    						//System.out.print(" " + print);
		    						//System.out.println(parent[y][x]);
		    					}
		    					Collections.reverse(list);
		    					//System.out.print(list);
		    					for (int i = 0; i < list.size();i++) 
		    					{ 	
		    						if(i>0)
		    						{
		    							System.out.print(" ");
		    						}
		    						System.out.print(list.get(i));
		    					}   
		    					break;
		    				}
		    				arg = node.split(",");
		    				int c1;
		    				int c2;
		    				int dr[] = {-1,-1,-1,0,0,1,1,1};
		    				int dc[] = {-1,0,1,-1,1,-1,0,1};
		    				int rr=0,cc=0;
		    				for(int i=0; i<8; i++)
		    				{
		    					c1 = Integer.parseInt(arg[0]);
		    					c2 = Integer.parseInt(arg[1]);
		    					rr = c1 + dr[i];
		    					cc = c2 + dc[i];
		    					//System.out.print(rr + "," + cc);
		    					if(rr<0 || cc<0 || rr >= rows || cc >= cols || (java.lang.Math.abs(elevation[rr][cc]-elevation[c1][c2])>sz) ||  visited[rr][cc]==true)
		    					{
		    						continue;
		    					}
		    					
		    					//System.out.print(elevation[rr][cc]-elevation[c1][c2]);
		    					//System.out.print(rr);
		    					//System.out.print(cc);
		    					q.add(rr + "," + cc);
		    					parent[rr][cc] = c1 + "," + c2;
		    					//System.out.println(" Parent:" + parent[rr][cc]);
		    					visited[rr][cc]=true;
		    				}
		    			}while(true);
		    		}	
		    	}
		    	
		    	
		    	
		    	if(algo.equals("UCS"))
		    	{
		    		for(int k=0; k<num_of_out;k++)
		    		{		 
		    			if(k>0)
		    			{
		    				System.out.println();
		    			}
		    			//System.out.print(g[k]);
		    			ArrayList<st> openq = new ArrayList<>();
		    			Queue<st> closedq = new LinkedList<>();
		    			st obj[][] = new st[rows][cols];
		    			if(!(sx<0 || sy <0 || sx >= cols || sy >= rows))
		    			{
		    				String root = sy + ","+ sx;		//Check validity if given in input or not eg given -1,-1 , then not valid input
		    				String parent = "root";
		    				int cost = 0; 
		    				obj[sy][sx] = new st(root, parent, cost);
		    				openq.add(obj[sy][sx]);
		    			}
		    			boolean visited[][] = new boolean[rows][cols];
		    			for(int i=0; i<rows; i++)
		    			{
		    				for(int j=0; j<cols; j++)
		    				{
		    					visited[i][j]=false;
		    				}
		    			}
		    			visited[sy][sx]=true;
		    			//System.out.print(q);
		    			st node;
		    			String arg[];
		    			ArrayList<String> list = new ArrayList<>();
		    			do
		    			{
		    				if(openq.isEmpty())
		    				{
		    					System.out.print("Failure");
		    					break;
		    				}
		    				node = openq.remove(0);
		    				//System.out.println(node.value);
		    				if((node.value).equalsIgnoreCase(g[k])) //Correct the goal
		    				{
		    					String dest[] = node.value.split(",");
		    					int x = Integer.parseInt(dest[0]);
		    					int y = Integer.parseInt(dest[1]);
		    					String print = y + "," + x;
		    					list.add(print);
		    					
		    					
		    					//System.out.print(node.value);
		    					
		    					while(!(node.parent == "root"))
		    					{
		    						
		    						String sp[] = node.parent.split(",");
		    						int a = Integer.parseInt(sp[0]);
		    						int b = Integer.parseInt(sp[1]);
		    						node = obj[a][b];
		    						dest = node.value.split(",");
			    					x = Integer.parseInt(dest[0]);
			    					y = Integer.parseInt(dest[1]);
			    					print = y + "," + x;
			    					list.add(print);
		    					}
		    					
		    					
		    					Collections.reverse(list);
		    					//System.out.print(list);
		    					for (int i = 0; i < list.size();i++) 
		    					{ 	
		    						if(i>0)
		    						{
		    							System.out.print(" ");
		    						}
		    						System.out.print(list.get(i));
		    					} 
		    					break;
		    				}
		    				arg = node.value.split(",");
		    				int c1;
		    				int c2;
		    				int dr[] = {-1,-1,-1,0,0,1,1,1};
		    				int dc[] = {-1,0,1,-1,1,-1,0,1};
		    				int rr=0,cc=0;
		    				for(int i=0; i<8; i++)
		    				{
		    					c1 = Integer.parseInt(arg[0]);
		    					c2 = Integer.parseInt(arg[1]);
		    					rr = c1 + dr[i];
		    					cc = c2 + dc[i];
		    					if(rr<0 || cc<0 || rr >= rows || cc >= cols || (java.lang.Math.abs(elevation[rr][cc]-elevation[c1][c2])>sz))
		    					{
		    						continue;
		    					}
		    					
		    					if(!(visited[rr][cc]==true))
		    					{
		    						String root = rr + "," + cc;
		    						String parent = c1 + "," + c2;
		    						int cost;
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = obj[c1][c2].cost + 14;
			    					}
		    						else
		    						{
		    							cost = obj[c1][c2].cost + 10;
		    						}
		    						obj[rr][cc] = new st(root, parent, cost);
		    						openq.add(obj[rr][cc]);
		    						visited[rr][cc]=true;
		    						
		    					}
		    					
		    					else if(openq.contains(obj[rr][cc]))
		    					{
		    						int cost;
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = obj[c1][c2].cost + 14;
			    					}
		    						else
		    						{
		    							cost = obj[c1][c2].cost + 10;
		    						}
		    						if(cost<obj[rr][cc].cost)
		    						{
		    							obj[rr][cc].cost = cost;
		    							obj[rr][cc].parent = c1 + "," + c2;
		    						}
		    					}
		    					
		    					else if(closedq.contains(obj[rr][cc]))
		    					{
		    						int cost;
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = obj[c1][c2].cost + 14;
			    					}
		    						else
		    						{
		    							cost = obj[c1][c2].cost + 10;
		    						}
		    						if(cost<obj[rr][cc].cost)
		    						{
		    							String root = rr + "," + cc;
		    							String parent = c1 + "," + c2;
		    							closedq.remove(obj[rr][cc]);
		    							obj[rr][cc] = new st(root, parent, cost);
			    						openq.add(obj[rr][cc]);
		    						}
		    					}
		    				}
		    				closedq.add(node);
		    				//System.out.print(node.value + " " + node.cost + "| ");
		    				Collections.sort(openq);
		    			}while(true);
		    		}		
		    	}
		    	
		    	
		    	
		    	
		    	if(algo.equals("A*"))
		    	{
		    		for(int k=0; k<num_of_out;k++)
		    		{		 
		    			if(k>0)
		    			{
		    				System.out.println();
		    			}
		    			//System.out.print(g[k]);
		    			ArrayList<as> openq = new ArrayList<>();
		    			Queue<as> closedq = new LinkedList<>();
		    			as ob[][] = new as[rows][cols];
		    			if(!(sx<0 || sy <0 || sx >= cols || sy >= rows))
		    			{
		    				String root = sy + ","+ sx;		//Check validity if given in input or not eg given -1,-1 , then not valid input
		    				String parent = "root";
		    				int cost = 0; 
		    				String dest[] = g[k].split(",");
	    					int x = Integer.parseInt(dest[0]);
	    					int y = Integer.parseInt(dest[1]);
		    				double hcost =  Math.sqrt(Math.abs((sy-x)*(sy-x)-(sx-y)*(sx-y)));
		    				double f = cost + hcost;
		    				ob[sy][sx] = new as(root, parent, cost, hcost, f);
		    				openq.add(ob[sy][sx]);
		    			}
		    			boolean visited[][] = new boolean[rows][cols];
		    			for(int i=0; i<rows; i++)
		    			{
		    				for(int j=0; j<cols; j++)
		    				{
		    					visited[i][j]=false;
		    				}
		    			}
		    			visited[sy][sx]=true;
		    			//System.out.print(q);
		    			as node;
		    			String arg[];
		    			ArrayList<String> list = new ArrayList<>();
		    			do
		    			{
		    				if(openq.isEmpty())
		    				{
		    					System.out.print("Failure");
		    					break;
		    				}
		    				node = openq.remove(0);
		    				//System.out.println(node.value);
		    				if((node.value).equalsIgnoreCase(g[k])) 
		    				{
		    					
		    					String dest[] = node.value.split(",");
		    					int x = Integer.parseInt(dest[0]);
		    					int y = Integer.parseInt(dest[1]);
		    					String print = y + "," + x;
		    					list.add(print);
		    					
		    					
		    					//System.out.print(node.value);
		    					
		    					while(!(node.parent == "root"))
		    					{
		    						
		    						String sp[] = node.parent.split(",");
		    						int a = Integer.parseInt(sp[0]);
		    						int b = Integer.parseInt(sp[1]);
		    						node = ob[a][b];
		    						dest = node.value.split(",");
			    					x = Integer.parseInt(dest[0]);
			    					y = Integer.parseInt(dest[1]);
			    					print = y + "," + x;
			    					list.add(print);
		    					}
		    					
		    					
		    					Collections.reverse(list);
		    					//System.out.print(list);
		    					for (int i = 0; i < list.size();i++) 
		    					{ 	
		    						if(i>0)
		    						{
		    							System.out.print(" ");
		    						}
		    						System.out.print(list.get(i));
		    					} 
		    					break;
		    				}
		    				arg = node.value.split(",");
		    				int c1;
		    				int c2;
		    				int dr[] = {-1,-1,-1,0,0,1,1,1};
		    				int dc[] = {-1,0,1,-1,1,-1,0,1};
		    				int rr=0,cc=0;
		    				for(int i=0; i<8; i++)
		    				{
		    					c1 = Integer.parseInt(arg[0]);
		    					c2 = Integer.parseInt(arg[1]);
		    					rr = c1 + dr[i];
		    					cc = c2 + dc[i];
		    					if(rr<0 || cc<0 || rr >= rows || cc >= cols || (Math.abs(elevation[rr][cc]-elevation[c1][c2])>sz))
		    					{
		    						continue;
		    					}
		    					
		    					if(!(visited[rr][cc]==true))
		    					{
		    						String root = rr + "," + cc;
		    						String parent = c1 + "," + c2;
		    						int cost;
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = ob[c1][c2].cost + 14 + Math.abs(elevation[rr][cc]-elevation[c1][c2]);
			    					}
		    						else
		    						{
		    							cost = ob[c1][c2].cost + 10 + Math.abs(elevation[rr][cc]-elevation[c1][c2]);
		    						}
		    						String dest[] = g[k].split(",");
			    					int x = Integer.parseInt(dest[0]);
			    					int y = Integer.parseInt(dest[1]);
		    						double hcost = Math.sqrt(Math.abs((rr-x)*(rr-x)-(cc-y)*(cc-y)));
				    				double f = cost + hcost;
				    				ob[rr][cc] = new as(root, parent, cost, hcost, f);
		    						
		    						openq.add(ob[rr][cc]);
		    						visited[rr][cc]=true;
		    						
		    						
		    					}
		    					
		    					
		    					
		    					else if(openq.contains(ob[rr][cc]))
		    					{
		    						int cost;
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = ob[c1][c2].cost + 14 + java.lang.Math.abs(elevation[rr][cc]-elevation[c1][c2]);
			    					}
		    						else
		    						{
		    							cost = ob[c1][c2].cost + 10 + java.lang.Math.abs(elevation[rr][cc]-elevation[c1][c2]);
		    						}
		    						String dest[] = g[k].split(",");
			    					int x = Integer.parseInt(dest[0]);
			    					int y = Integer.parseInt(dest[1]);
		    						double hcost = Math.sqrt(Math.abs((rr-x)*(rr-x)-(cc-y)*(cc-y)));
				    				double f = cost + hcost;
		    						if(f<ob[rr][cc].f)
		    						{
		    							ob[rr][cc].f = f;
		    							ob[rr][cc].hcost = hcost;
                                        ob[rr][cc].cost = cost;
		    							ob[rr][cc].parent = c1 + "," + c2;
		    						}
		    					}
		    					
		    					else if(closedq.contains(ob[rr][cc]))
		    					{
		    						int cost;
		    						String dest[] = g[k].split(",");
			    					int x = Integer.parseInt(dest[0]);
			    					int y = Integer.parseInt(dest[1]);
		    						double hcost = Math.sqrt(Math.abs((rr-x)*(rr-x)-(cc-y)*(cc-y)));
				    				
		    						if((dr[i]==-1 && dc[i]==1) || (dr[i]==-1 && dc[i]==-1) || (dr[i]==1 && dc[i]==1) || (dr[i]==1 && dc[i]==-1))
			    					{
			    						cost = ob[c1][c2].cost + 14 + Math.abs(elevation[rr][cc]-elevation[c1][c2]);
			    					}
		    						else
		    						{
		    							cost = ob[c1][c2].cost + 10 + Math.abs(elevation[rr][cc]-elevation[c1][c2]);
		    						}
		    						double f = cost + hcost;
		    						if(f<ob[rr][cc].f)
		    						{
		    							String root = rr + "," + cc;
		    							String parent = c1 + "," + c2;
		    							closedq.remove(ob[rr][cc]);
		    							ob[rr][cc] = new as(root, parent, cost, hcost, f);
			    						openq.add(ob[rr][cc]);
		    						}
		    					}
		    				}
		    				closedq.add(node);
		    				//System.out.print(node.value + " " + node.cost + "| ");
		    				Collections.sort(openq);
		    			}while(true);
		    		}		
		    	}
		    	
	  	
		    	
		    }
		    fileOut.close();
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
