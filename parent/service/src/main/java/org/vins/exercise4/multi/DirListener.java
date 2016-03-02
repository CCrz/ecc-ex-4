package org.vins.exercise4.multi;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.monitor.*;

//### See http://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/monitor/FileAlterationMonitor.html
//### ...for docs on how to use onChange[event] method
public class DirListener{
	
	private final long interval = TimeUnit.SECONDS.toMillis(3);
	
	private final FileAlterationMonitor monitor = new FileAlterationMonitor(this.interval);
	
	File dir;
	
	DirListener(String dir){
	
		this.dir = new File(dir);				
		
	}
	
	
	
	public void listenForAlterInFile(ListsOfMaps map, FileHandler fio){
		
		FileAlterationObserver fao = new FileAlterationObserver(this.dir);
		
		fao.addListener(new FileAlterationListenerI(map, fio));				
		
		monitor.addObserver(fao);
		
		try{
			
			monitor.start();
		
		}catch(Exception ex){
				
			System.out.println("Could not start file monitor.");
			
		}
		
		System.out.println("File monitor started.");
		
	}

	
}