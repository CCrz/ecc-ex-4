package org.vins.exercise4.multi;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileHandler{
	protected String fileName;
	protected boolean autoSave = true;
	IOHandler io = new IOHandler();
	
	public ArrayList<String>  openFile(){
		int ctr = 0;
		int maxTries = 3;
		ArrayList<String> lines = new ArrayList<String>();
		
		while(true){			
			try{				

				String prompt = "Please input filename: ";

				this.fileName    	= io.inStr(prompt);
				
				File 	file     	= new File(this.fileName);
				
				Scanner fileReader  = new Scanner(file);

				while(fileReader.hasNextLine()){					
						lines.add(fileReader.nextLine());
				}			
				
				break;
			}catch(Exception e){
				System.out.println("File not found..."+e);
				//if(++ctr==maxTries)	throw e;

			}finally{
			}

			
		}
		
		return lines;
	}
	
	public ArrayList<String>  openFile(String inFname){
		int ctr = 0;
		int maxTries = 3;
		ArrayList<String> lines = new ArrayList<String>();
		
		
		
		while(true){			
			try{				

				String prompt = "Please input filename: ";

				if(ctr==0){
					this.fileName    	= inFname;
				}else{
				
					this.fileName    	= io.inStr(prompt);
				}
				
				File 	file     	= new File(this.fileName);
				
				Scanner fileReader  = new Scanner(file);

				while(fileReader.hasNextLine()){					
						lines.add(fileReader.nextLine());
				}			
				
				break;
			}catch(Exception e){
				System.out.println("File not found..."+e);
				//if(++ctr==maxTries)	throw e;

			}finally{
			}

			ctr++;
		}
		
		return lines;
	}
	
	public ArrayList<String>  reopenFile(){
		int ctr = 0;
		int maxTries = 3;
		ArrayList<String> lines = new ArrayList<String>();
		
		while(true){			
			try{
				
				File 	file     	= new File(this.fileName);
				
				Scanner fileReader  = new Scanner(file);

				while(fileReader.hasNextLine()){

					lines.add(fileReader.nextLine());
				}
				
				break;
			}catch(Exception e){
				System.out.println("File not found..."+e);
				//if(++ctr==maxTries)	throw e;

			}finally{
			}

			
		}
		
		return lines;
	}
	
	public void updateFile(List<String> lines){
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(this.fileName));

			for(String line: lines) {

				pw.write(line);				

				pw.println();
			}
			
			System.out.println("File updated...");
			
			pw.close();
			
		}catch(Exception ex){
			
			System.out.println("File write error...");
			
		}finally{

		}				
	}
	
	public void toggleAutoSave(){
		this.autoSave = (this.autoSave==true) ? false : true;
	}

	
}