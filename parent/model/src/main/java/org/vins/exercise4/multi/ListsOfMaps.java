/**abstract class for table using arraylist
*
*/

package org.vins.exercise4.multi;

import java.util.*;

public class ListsOfMaps{
    protected List<LinkedHashMap<String,String>> mapList = new ArrayList<LinkedHashMap<String,String>>();
    public boolean asc = true;
    String keyValDelim = ",";
    String entryDelim = "░";
	
    public List<LinkedHashMap<String,String>> getMap(){
    
        return mapList;
    }    
	
    public void setMap(List<String> lines){

        int ltr = 0;                
        
        int dud = 0;
        
        for(String line : lines){           
            
                mapList.add(new LinkedHashMap<String, String>());                        

                for(String pair : line.split(entryDelim)){

                    String [] keyVal = pair.split(keyValDelim);

                    if(keyVal.length>0){

                        try{
                            mapList.get(ltr).put(keyVal[0], keyVal[1]);                             
                        }catch(Exception ex){
                            //skip bad entry
                            ++dud;
                        }
                    }    

                }                        

                System.out.println();

                ltr++;
        }
        
        System.out.println( dud>0 ? "Map list has been set ... with "+dud+" bad entry/ies" : "Map list has been set ...");
    
    }
    
    public void setMap(){
        
        this.mapList = new ArrayList<LinkedHashMap<String,String>>();
    }
    
    public List<String> stringifyMap(){
        
        List<String> lines = new ArrayList<String>();
        
        for(LinkedHashMap<String,String> map : mapList){
            
            String line = "";
            
            Set<Map.Entry<String,String>> entrySet = map.entrySet();

            for(Map.Entry<String,String> entry : entrySet){
                
                line += entry.getKey() + keyValDelim + entry.getValue() + entryDelim;
            }
            
            lines.add(line);
        }
        
        return lines;
    }
    
    public void search(String query){		
        
        int oC=0;
        int oCtr=0;
        int rowCtr=1;
        int colCtr=1;
        
        System.out.println("Occurence/s\tRow\tCol\tCell");
        
        for(LinkedHashMap<String,String> map : mapList){                        
            
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            
            for(Map.Entry<String,String> entry : entrySet){
                
                colCtr=1;
                
                String combi = entry.getKey() + entry.getValue();
                
                oC = countOc(combi, query);
                
                if(oC > 0){
                    ++oCtr; 
                    System.out.println("\t"+oC+"\t"+rowCtr+"\t"+colCtr+"\t"+entry.getKey()+""+entry.getValue());
                }
                
                colCtr++;
            }
            
            rowCtr++;
        }
        
        System.out.println("Total:"+oCtr);
	}
    
    public int countOc(String str, String find) {
        int index = 0, count = 0;
        do {
                index  = str.indexOf(find, index);
                if ( index == -1 ) {
                        return count;
                }
                index += find.length();
                count++;
        } while( true );
    }
    
    public void displayMap(){
        int rowCtr=0;

        for(LinkedHashMap<String,String> map : mapList){
            
            ++rowCtr;
            
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            
            System.out.print(rowCtr+"\t");
            
            for(Map.Entry<String,String> entry : entrySet){
                
                String combi = entry.getKey() +"=>"+ entry.getValue();
                
                System.out.print(combi+"\t");
            }
            
            System.out.println();
        }
    }
    
    public int getMaxNumOfCol(){
        int maxCol=0;
        
        for(LinkedHashMap<String,String> map : this.mapList){           
            
           maxCol = (map.size() > maxCol) ? map.size() : maxCol;
            
            //System.out.println(map.size());
        }
        
        return maxCol;
    }
	
    public void editCellVal(int row, String key, String val){
        mapList.get(row).put(key, val);
    }
    
    public void editCellKey(int row, String key, String newKey){
       
       String value = mapList.get(row).get(key);        
       mapList.get(row).put(newKey, value);
       this.delCell(row, key);
    }
    
    public void addCell(int row, String key, String val){
        mapList.get(row).put(key, val);
    }
    
    public void addRow(){
        mapList.add(new LinkedHashMap<String,String>());
    }
    
    public void delCell(int row, String key){
        mapList.get(row).remove(key);
    }
    
    public void delRow(int row){
        mapList.remove(row);
    }
    
    public void sortByKeyRow(){
        asc = (asc==true) ? false : true;
        int ltr=0;
        
        if(asc==true){
            for(Map<String,String> map : mapList){
                
                Map<String,String> treeMap = new TreeMap<String,String>(map);
                mapList.set(ltr, new LinkedHashMap<String,String>(treeMap));            
                ltr++;    
            }
        }else{
            for(Map<String,String> map : mapList){
                
                TreeMap<String,String> treeMap = new TreeMap<String,String>(map);                
                mapList.set(ltr, new LinkedHashMap<String,String>(treeMap.descendingMap()));            
                ltr++;    
            }
        }                                        
    }

    
	
}

