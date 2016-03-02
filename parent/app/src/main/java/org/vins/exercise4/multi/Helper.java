package org.vins.exercise4.multi;

import java.util.*;
import java.net.URL;

import org.apache.commons.io.monitor.*;

public class Helper{
	ListsOfMaps mL = new ListsOfMaps();
	IOHandler   io  = new IOHandler();
	FileHandler fio = new FileHandler();
	String [] args;
	DirListener dirListener;
	
	public Helper(String [] frCli){
		this.args = frCli;
	}
	
	public int getMaxList(List <String> list){
		int max=0;
		
		for(String str : list){
			max = str.length()>max ? str.length() : max;
		}
		
		return max;
	}
	
	public void printMenu(){
		ArrayList<String> menuDo    =   new ArrayList<String>();
        
        //add menu items
        menuDo.add("[File To Map]");
        menuDo.add("[MENU] ");
        menuDo.add("----------------------------------");
        menuDo.add("1.)  Search");
        menuDo.add("2.)  Edit Cell Value");
        menuDo.add("3.)  Edit Cell Key");
        menuDo.add("4.)  Add Cell");
        menuDo.add("5.)  Add Row");
        menuDo.add("6.)  Delete Cell");
        menuDo.add("7.)  Delete Row ");
        menuDo.add("8.)  Display Map");
        menuDo.add("9.)  Update Map");
        menuDo.add("10.)  Update File");        
        menuDo.add("11.) Open File");
		if(fio.autoSave==true){
			menuDo.add("12.) Toggle Autosave:[ON]");
		}else{
        	menuDo.add("12.) Toggle Autosave:[OFF]");
		}
		if(mL.asc==true){
			menuDo.add("13.) Sort by Row Toggle:[ASC]");
		}else{
        	menuDo.add("13.) Sort by Row Toggle:[DESC]");
		}
		
        
		//print topside
		System.out.print("*");
		for(int i=0; i<menuDo.get(2).length(); i++){
			System.out.print("-");
		}
		System.out.println("*");
		
		//print body
		for(int i=0; i<menuDo.size(); i++){
            int width   =   this.getMaxList(menuDo);
			System.out.print("|");
            if(menuDo.get(i).length()>width){
                width--;
            }
			for(int w=0; w<width; w++){
				if(w==0){
					System.out.print(menuDo.get(i));
                    width=width-menuDo.get(i).length()+1;
				}else{
					System.out.print(" ");
				}
				
			}
            if(menuDo.get(i).length()>width){
                width++;
            }
			System.out.println("|");
            
		}
		
		//print bottomside
		System.out.print("*");
		for(int i=0; i<menuDo.get(2).length(); i++){
			System.out.print("-");
		}
		System.out.println("*");
	}
	
	public String dirOneUp(String thisLoc){
		
		int endIndex = thisLoc.lastIndexOf("/");
		String thisLoc1Up = "";
		if (endIndex != -1)  
		{
			thisLoc1Up = thisLoc.substring(0, endIndex);
		}
		
		return thisLoc1Up;
		
	}
	
	public void loadMap(){			
		
		this.printMenu();
		mL.setMap();
		mL.setMap(fio.openFile(fio.fileName));
		mL.displayMap();
			
	}
	
    public void start(){				
		
        //INITIALIZE
		int option = 1;
        int position=0;
        int row=0;
        String col="";	
		
		ArrayList<String> linesInFile = new  ArrayList<String>();
		
        this.printMenu();
		
		try{			
			
			linesInFile = fio.openFile(args[0]);	
			
		}catch(Exception ex){
			
			linesInFile = fio.openFile();	
		}
						
		mL.setMap(linesInFile);
        mL.displayMap();					 
				
		String dir = this.dirOneUp(Helper.class.getProtectionDomain().getCodeSource().getLocation().getFile());
		System.out.println("Listener will listen to: "+dir);
		dirListener = new DirListener(dir);
		dirListener.listenForAlterInFile(mL, fio);
		
        //MAIN LOOP
        while(option!=0){
            option  =   io.inInt("---->");
            switch(option){
                    case 1: //SEARCH
                        String query = io.inStr("Search Query: ");
                    
                        //--redisplay--
                        this.printMenu();
                        mL.displayMap();
                        System.out.println("searching for..."+query);
                        mL.search(query);
                    break;
                    
                    case 2: //EDIT CELL VAL
                        row     = io.inInt("Please enter existing [row] number of cell: ");
						while(row<=0 || row>mL.getMap().size()){
                            row     = io.inInt("Please enter existing [row] number of cell: ");
                        }
                    
						if(mL.getMap().get(row-1).size()>0){
							
							col     = io.inStr("Please enter [key] of cell: ");
							while(col=="" || !mL.getMap().get(row-1).containsKey(col)){
								col     = io.inStr("Please enter existing [key] of cell: ");
							}

							String  newStr  = io.inStr("Please enter new value of cell: ");

							mL.editCellVal(row-1, col, newStr);
							
							//--redisplay--
							this.printMenu();
							mL.displayMap();
							
						}else{
							//--redisplay--
							this.printMenu();														
							mL.displayMap();
							System.out.println("Row has no columns!");
						}
								
                    
                        
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
                    break;
					
					case 3: //EDIT CELL KEY
						row     = io.inInt("Please enter existing [row] number of cell: ");
						while(row<=0 || row>mL.getMap().size()){
                            row     = io.inInt("Please enter existing [row] number of cell: ");
                        }
                    
						if(mL.getMap().get(row-1).size()>0){
							
							col     = io.inStr("Please enter [key] of cell: ");
							while(col=="" || !mL.getMap().get(row-1).containsKey(col)){
								col     = io.inStr("Please enter existing [key] of cell: ");
							}

							String  newStr  = io.inStr("Please enter new key of cell: ");

							mL.editCellKey(row-1, col, newStr);
							
							//--redisplay--
							this.printMenu();
							mL.displayMap();
							
						}else{
							//--redisplay--
							this.printMenu();														
							mL.displayMap();
							System.out.println("Row has no columns!");
						}
					
						mL.sortByKeyRow();
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
					break;
                    
                    case 4: //ADD CELL        
                        row         = io.inInt("Please enter existing [row] number to insert cell into: ");
                        System.out.println("Size: "+mL.getMap().size());
						while(row<=0 || row>mL.getMap().size()){
                            row         = io.inInt("Please enter existing [row] number to insert cell into: ");
                        }
                    
                        col         = io.inStr("Please enter non-existing [key] for new cell: ");
                        while(col==null || col=="" || mL.getMap().get(row-1).containsKey(col)){
                            col         = io.inStr("Please enter non-existing [key] for new cell: ");
                        }
                    
                        String initStr  = io.inStr("Please enter initial value of the cell: ");                        
                        mL.addCell(row-1, col, initStr);
                        
                        //--redisplay--
                        this.printMenu();
                        mL.displayMap();
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
                    break;
                    
                    case 5: //ADD ROW
					
                        mL.addRow();
                        
                        //--redisplay--
                        this.printMenu();
                        mL.displayMap();
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
                    break;
                    
                    case 6: //DELETE CELL
                        row         = io.inInt("Please enter existing [row] number of cell to delete: ");
                        while(row<=0 || row>mL.getMap().size()){
                            row         = io.inInt("Please enter existing [row] number of cell to delete: ");
                        }
                    	if(mL.getMap().get(row-1).size()>0){
							col         = io.inStr("Please enter existing [key] of cell to delete: ");
							while(col=="" || !mL.getMap().get(row-1).containsKey(col)){
								col         = io.inStr("Please enter existing [key] number of cell to delete: ");
							}

							mL.delCell(row-1, col);

							//--redisplay--
							this.printMenu();
							mL.displayMap();                    
						}else{
							//--redisplay--
							this.printMenu();							
							mL.displayMap();                    
							System.out.println("Row has no columns!");
						}
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
                    break;
                    
                    case 7: //DELETE ROW
					
                        position = io.inInt("Please enter existing [row] number of row to delete: ");
                        while(position<=0 || position>mL.getMap().size()){
                            position = io.inInt("Please enter existing [row] number of row to delete: ");
                        }
                    
                        mL.delRow(position-1);
                        
                    
                        //--redisplay--
                        this.printMenu();
                        mL.displayMap();
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
                    break;
                    
                    case 8: //DISPLAY MAP
					
                        this.printMenu();
                        mL.displayMap();
                    break;                    
					
					case 9:	//UPDATE Map
						this.loadMap();
					break;
					
					case 10: //UPDATE FILE
						this.printMenu();
						fio.openFile(fio.fileName);
						mL.displayMap();
						fio.updateFile(mL.stringifyMap());
					break;
					
					case 11: //OPEN OTHER FILE				
						mL.setMap();
						mL.setMap(fio.openFile());
						this.printMenu();
						mL.displayMap();					
					break;
					
					case 12: //TOGGLE AUTOSAVE
						
						fio.toggleAutoSave();
						this.printMenu();
						mL.displayMap();
					break;
					
					case 13: //SORT BY ROW ASC/DESC TOGGLE						
												
						mL.sortByKeyRow();
						this.printMenu();
						mL.displayMap();
					
						if(fio.autoSave==true){
							fio.updateFile(mL.stringifyMap());
						}
					break;
                    
            }
        }
        
    }
}