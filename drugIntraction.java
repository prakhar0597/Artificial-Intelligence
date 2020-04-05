import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


class kb
{
	HashMap<String, ArrayList<String>> n_lit = new HashMap<>();
	HashMap<String, ArrayList<String>> p_lit = new HashMap<>();	
	kb(HashMap<String, ArrayList<String>> p_lit, HashMap<String, ArrayList<String>> n_lit)
	{
		this.p_lit = p_lit;
		this.n_lit = n_lit;
	}
}

class elm
{
	int val;
	String str;
	elm(int val, String str)
	{
		this.val = val;
		this.str = str;
	}
}

class subs
{
	int val;
	String new_query, new_subs;
	subs(int val, String new_query, String new_subs)
	{
		this.val = val;
		this.new_query = new_query;
		this.new_subs = new_subs;
	}
}

class test
{
	int flag;
	String qry, statement;
	test(int flag, String new_query, String new_subs)
	{
		this.flag = flag;
		this.qry = new_query;
		this.statement = new_subs;
	}
}

public class drugIntraction {
	
	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File in = new File("D:/input3.txt");
			br = new BufferedReader(new FileReader(in));
		    PrintStream fileOut = new PrintStream(new File("output.txt"));
//		    System.setOut(fileOut);
		   	int out = Integer.parseInt(br.readLine());
		   	//System.out.println(out);
		   	String value = null;
		   	String argms[] = new String[out];
//		   	String arg[];
		   	for(int i=0; i<out;i++)
	    	{
	    		value = br.readLine();
	    		argms[i] = value;
	    	}
	    	int no_kb = Integer.parseInt(br.readLine());
	    	//System.out.println(no_kb);
	    	String arr;
	    	String arr_new[] = new String[no_kb];
	    	boolean implies[] = new boolean[no_kb];
		    for(int i = 0 ; i < no_kb; i++)
		    {
		    	arr_new[i]="";
		    	arr = br.readLine();
		    	//System.out.println(arr);
		    	for(int j=0; j<arr.length(); j++)
		    	{
		    		if(arr.charAt(j)==' ')
		    		{
		    			continue;
		    		}
		    		if(arr.charAt(j)=='=' && j<arr.length()-1 && arr.charAt(j+1)=='>')
		    		{
		    			implies[i] = true;
		    		}
		    		arr_new[i] = arr_new[i] + arr.charAt(j);
		    	}
		    }

		    for(int i = 0 ; i < no_kb; i++)
		    {
		    	if(implies[i]==true)
		    	{
		    		if(arr_new[i].charAt(0)!='~')
		    		{
		    			arr_new[i] = "~" + arr_new[i]; 
		    		}
		    		else if(arr_new[i].charAt(0)=='~')
		    		{
		    			arr_new[i] = arr_new[i].substring(1);
		    		}
		    	}
		    	for(int j=0; j<arr_new[i].length(); j++)
		    	{
		    		if(arr_new[i].charAt(j)=='&' && arr_new[i].charAt(j+1)=='~' && j<arr_new[i].length()-2)
		    		{
		    			arr_new[i] = arr_new[i].substring(0,j) + "|" + arr_new[i].substring(j+2,arr_new[i].length());
		    		}
		    		else if(arr_new[i].charAt(j)=='&' && arr_new[i].charAt(j+1)!='~' && j<arr_new[i].length()-2)
		    		{
		    			arr_new[i] = arr_new[i].substring(0,j) + "|~" + arr_new[i].substring(j+1,arr_new[i].length());
		    		}
		    		if(arr_new[i].charAt(j)=='=' && arr_new[i].charAt(j+1)=='>' && j<arr_new[i].length()-2)
		    		{
		    			arr_new[i] = arr_new[i].substring(0,j) + "|" + arr_new[i].substring(j+2,arr_new[i].length());
		    		}
		    	}
		    	//System.out.println(arr_new[i]);
		    }
		    
//		    for(int i=0; i<no_kb; i++)
//		    {
//		    	System.out.println(arr_new[i]);
//		    }

		    
		    
		    
		    
		    ArrayList<String> simplify = new ArrayList<String>();
		    for(String i :  arr_new)
		    {
//		    	
		    	int flag=1;
		    	if(i.contains("|"))
				{
					flag=0;
				}
		    	String param = i.split("\\(")[1];
				param = param.split("\\)")[0];
				String constant[] = param.split(",");
				for(String j : constant)
				{
					if(Character.isUpperCase(j.charAt(0)))
						continue;
					else
						flag=0;
				}
		    	if(flag==1)
					simplify.add(i);
		    }
		    //System.out.println(simplify);
		    
		    kb obj = def_kb(arr_new);
		    
		    String query="";
		    for(String i : argms)
		    {
		    	if(i.charAt(0)=='~')
		    	{
		    		query = i.substring(1);
		    	}
		    	else
		    	{
		    		query = "~" + i;
		    		//System.out.println(query);
		    		
		    	}
		    	if(unification(query, query, obj.p_lit, obj.n_lit, simplify, 0))
	    		{
	    			//System.out.println("Hi");
	    			System.out.println("TRUE");
	    		}
	    		else
	    			System.out.println("FALSE");
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
		catch(StringIndexOutOfBoundsException e)
		{
			System.out.println("FALSE");
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

	private static boolean unification(String query, String remain, HashMap<String, ArrayList<String>> p_lit, HashMap<String, ArrayList<String>> n_lit, ArrayList<String> simplify, int loop) {
		
		if(loop < 1000) {
			ArrayList<String> value = new ArrayList<String>();

			if(query.charAt(0)=='~')
			{
				//System.out.println("Data passed in Unification" + query + remain + p_lit + n_lit + simplify );

				String to_map = query.split("\\(")[0];
				if(p_lit.containsKey(to_map.substring(1)))
					value = p_lit.get(to_map.substring(1));
				else
					return false;
				elm obj1, obj2;
				for(String i : value)
				{
					String temp_query = query;
					String left_out = remain;
					if(simplify.contains(i))
					{
						//System.out.println("Enter if*****");
						obj1 = eliminate(left_out,  "~" + i);
						obj2 = new elm(1, "");
					}
					else
					{
						//System.out.println("Enter else*****");
						obj1 = eliminate(left_out, temp_query);
						//System.out.println(i);
						//System.out.println(temp_query.substring(1));
						//System.out.println("*****");
						obj2 = eliminate(i, temp_query.substring(1));
					}
					if(obj1.val == 0 || obj2.val == 0)
					{
						continue;
					}
					else 
					{
						//System.out.println(obj1.str);
						//System.out.println(obj2.str);
						if(obj1.str.equals("") && obj2.str.equals(""))
						{
							//System.out.println("No1");
							left_out = "";
						}
						else if(!(obj1.str.equals("")) && obj2.str.equals(""))
						{
							//System.out.println("No2");
							left_out = obj1.str;
						}
						else if(obj1.str.equals("") && !(obj2.str.equals("")))
						{
							//System.out.println("No3");
							left_out = obj2.str;
						}	
						else
						{
							//System.out.println("No4");
							left_out = obj2.str + "|" + obj1.str;
						}
						if(left_out.equals(""))
						{
							return true;
						}	
						else
						{
							//System.out.println(left_out+ p_lit+n_lit+simplify);
							if(left_out.contains("|"))
							{
								String data[] = left_out.split("\\|");
								for(String j : data)
								{
									j = j.trim();
									//System.out.println("Enter.1.recursion.unification");
									loop++;
									int pra = 0;
									if(!(unification(j,left_out,p_lit,n_lit,simplify,loop)))
										break;
									else if(pra!=0)
										return false;
									else
										return true;
								}			
							}
							else
							{
								loop++;
								int pra = 0;
								//System.out.println("Enter.2.recursion.unification");
								if(!(unification(left_out,left_out,p_lit,n_lit,simplify, loop)))
									continue;
								else if(pra!=0)
									return false;
								else
									return true;
							}
						}
					}	
				}
				return false;
			}
			else
			{
				String to_map = query.split("\\(")[0];
			
				if(n_lit.containsKey(to_map))
					value = n_lit.get(to_map);
				else
					return false;
				elm obj1, obj2;
				for(String i : value)
				{
					String left_out = remain;
					String temp_query = query;
					if(simplify.contains(i))
					{
						obj1 = eliminate(left_out, i.substring(1));
						obj2 = new elm(1, "");
					}	
					else
					{
						obj1 = eliminate(left_out, temp_query);
						obj2 = eliminate(i, "~" + temp_query);
					}
					if(obj1.val == 0 || obj2.val == 0)
					{
						continue;
					}
					else 
					{
						if(obj1.str.equals("") && obj2.str.equals(""))
						{
							left_out = "";
						}
						else if(!(obj1.str.equals("")) && obj2.str.equals(""))
						{
							left_out = obj1.str;
						}
						else if(obj1.str.equals("") && !(obj2.str.equals("")))
						{
							left_out = obj2.str;
						}	
						else
						{
							left_out = obj2.str + "|" + obj1.str;
						}
						if(left_out.equals(""))
						{
							return true;
						}
						else
						{
							if(left_out.contains("|"))
							{	
								String data[] = left_out.split("\\|");
								for(String j : data)
								{
									j = j.trim();
									int pra = 0;
									loop++;
									if(!(unification(j,left_out,p_lit,n_lit,simplify,loop)))
										break;
									else if(pra!=0)
										return false;
									else	
										return true;
								}		
							}
							else
							{
								int pra = 0;
								loop++;
								if(!(unification(left_out,left_out,p_lit,n_lit,simplify,loop)))
									continue;
								else if(pra!=0)
									return false;
                        		else
                        			return true;
							}
						}
					}	
				}
				return false;
			}
		}
		else
		{
			return false;
		}
	}



	static kb def_kb(String[] arr_new) 
	{

		HashMap<String, ArrayList<String>> n_lit = new HashMap<>();
		HashMap<String, ArrayList<String>> p_lit = new HashMap<>();		
		for(String i : arr_new)
		{
			
			String pred[] = i.trim().split("\\|");
			for(String j : pred)
			{
				ArrayList<String> arr = new ArrayList<String>();
				j = j.trim();
				if(j.charAt(0)!='~')
				{
					j = j.split("\\(")[0];
					if(p_lit.containsKey(j))
					{
						arr = p_lit.get(j);
						arr.add(i);
						p_lit.put(j, arr); 
					}
					else
					{
						arr.add(i);
						p_lit.put(j, arr);
					}
				}
				else if(j.charAt(0)=='~')
				{
					j = j.substring(1);
					j = j.split("\\(")[0];
					if(n_lit.containsKey(j))
					{
						arr = n_lit.get(j);
						arr.add(i);
						n_lit.put(j, arr); 
					}
					else
					{
						arr.add(i);
						n_lit.put(j, arr); 
					} 
				}
			}
		}
		kb obj;
		obj = new kb(p_lit, n_lit);
		return obj;
		
	}
	static elm eliminate(String k, String query) 
	{
		//System.out.println("//////el//////");
		subs sb;
		sb = substitute(k, query);
		
		int i = sb.val;
		String new_query=sb.new_query, new_subs=sb.new_subs;
		String new2, remv;
		int start, end;
		
		if(i == 1)
		{
			String new1;
			int f=0;
			while(f<1)
			{
				new1 = "";
				f++;
			}
			int token=0;
			if(new_subs.contains(new_query))
			{
				
				//System.out.println("Enter elm...if");
				//System.out.println(new_subs);
				//System.out.println(new_query);
				//new1 = new_subs.replaceAll(new_query, "");
				
				new1=new_subs;
				while(new1.contains(new_query))
				{
					int start1 = new1.indexOf(new_query);
					int end1 = start1 + new_query.length();
					new1 = new1.substring(0, start1) + new1.substring(end1, new1.length());
				}
			}
			else if(new_subs.contains(new_query) && token != 0)
			{
				new1=new_subs;
				while(new1.contains(new_query))
				{
					int start1 = new1.indexOf(new_query);
					int end1 = start1 + new_query.length();
					new1 = new1.substring(0, start1) + new1.substring(end1, new1.length());
				}
			}
			else
			{
				//System.out.println("Enter elm...else");
				//System.out.println("Check0");
				start = new_subs.indexOf(query.split("\\(")[0]);
				//System.out.println("Check1");
				end = new_subs.indexOf(")", start);
				//System.out.println("Check2");
				remv = new_subs.substring(start, end+1);
				new1=new_subs;
				while(new1.contains(remv))
				{
					int start1 = new1.indexOf(remv);
					int end1 = start1 + remv.length();
					new1 = new1.substring(0, start1) + new1.substring(end1, new1.length());
				}
				
				//System.out.println("Check3");
				//System.out.println(remv);
				//new1 = new_subs.replaceAll(remv, "");
			}
			//System.out.println(new1);
			if(new1.contains("||"))
			{
				new2 = new1.replace("||", "|");
				elm obj= new elm(1, new2);
				return obj;
			}
			else if(new1.length() > 0)
			{
				if(new1.substring(new1.length()-1).equals("|"))
				{
					new2 = new1.substring(0, new1.length()-1);
					elm obj= new elm(1, new2);
					return obj;
				}
				else if(new1.substring(0, 1).equals("|"))
				{
					new2 = new1.substring(1);
					elm obj= new elm(1, new2);
					return obj;
				}
				
				else
				{
					elm obj= new elm(1, new1);
					return obj;
				}
			}
			else
			{
				elm obj= new elm(1, new1);
				return obj;
			}
			//System.out.print(new2);
		}
		else //check after deleting whether results are same or not
		{
			elm obj= new elm(0, new_subs);
			return obj;
		}
	}

	private static test get(String data[], String pred, String cons_ary[], String statement, String qry)
	{
		int flag = 0;
		int count = 0;
		for(String i : data)
		{
			String p = i.split("\\(")[0];
			p= p.trim();
			if(p.equals(pred))
			{
				String param = i.split("\\(")[1];
				param = param.split("\\)")[0];
				param = param.trim();
				//System.out.println(param);
				String var_list[] = param.split(",");
//				for(String j : var_list)
//				{
//					System.out.println(j);
//				}
				for(String j : var_list)
				{
					if(Character.isLowerCase(cons_ary[count].charAt(0)) && Character.isUpperCase(j.charAt(0)))
					{
						//System.out.println("Enter Test11111");
						qry = qry.replaceAll("\\b" + cons_ary[count] + "\\b", j);
//						System.out.println(qry);
						count++;
						flag = 1;
					}
					else if(Character.isUpperCase(cons_ary[count].charAt(0)) && Character.isUpperCase(j.charAt(0)))
					{
						//System.out.println("Enter Test22222");
						if(j.equals(cons_ary[count]))
						{
							flag = 1;
						}
						else
						{
							flag = 0;
							break;
						}
						count++;
					}
					else if(Character.isLowerCase(cons_ary[count].charAt(0)) && Character.isLowerCase(j.charAt(0)))
					{
						//System.out.println("Enter Test33333");
						if(j.equals(cons_ary[count]))
						{
							flag = 1;
						}
						else
						{
							statement = statement.replaceAll("\\b" + cons_ary[count]  + "\\b", cons_ary[count] + "1");
							statement = statement.replaceAll("\\b" + j + "\\b", cons_ary[count]);
							flag = 1;
						}
						count++;
					}
					else if(Character.isUpperCase(cons_ary[count].charAt(0)) && Character.isLowerCase(j.charAt(0)))
					{
						//System.out.println("Enter Test44444");
						statement = statement.replaceAll("\\b" + j + "\\b", cons_ary[count]);
						count++;
						flag = 1;
					}
					else
					{
						qry=qry;
						statement = statement;
					}
					
					
				}
				if(flag==1)
					break;
			}
		}
		test obj = new test(flag , qry, statement);
		return obj;
	}
	
	private static subs substitute(String statement, String qry) {

		//System.out.println("Enter Substitute-----");
		String data[] = statement.split("\\|");
//		int flag = 0;
//		int count = 0;
		String pred = qry.split("\\(")[0];
		//String cons = extract_const(qry);
		String cons = qry.split("\\(")[1];
		cons = cons.split("\\)")[0];
		String cons_ary[] = cons.split(",");
//		for(String i : cons_ary)
//		{
//			System.out.println(i);
//		}
		//System.out.println("q:" + qry);
		test ob = get(data, pred, cons_ary, statement, qry);
		//System.out.println("q:" + ob.qry);
		//System.out.println("s:" + ob.statement);
		
		if(ob.flag == 0)
		{
			subs obj = new subs(0, ob.qry, ob.statement);
	        return obj;
		}
	    else
	    {
	    	subs obj = new subs(1, ob.qry, ob.statement);
	        return obj;
	    }
		
	}
}