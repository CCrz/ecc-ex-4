package org.vins.exercise4.multi;

import java.io.File;
import java.util.Date;
import org.apache.commons.io.monitor.*;
 
/**
 *
 * @author John Yeary
 * @version 1.0
 */
public class FileAlterationListenerI implements FileAlterationListener{
    
    ListsOfMaps map;
	
	FileHandler fio;
    
    FileAlterationListenerI(ListsOfMaps map, FileHandler fio){
        
        this.map = map;
        this.fio = fio;				
        
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(final FileAlterationObserver observer) {
        
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDirectoryCreate(final File directory) {
        
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDirectoryChange(final File directory) {
      
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDirectoryDelete(final File directory) {
      
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onFileCreate(final File file) {
      
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onFileChange(final File file) {
        
		map.setMap();
		map.setMap(fio.openFile(fio.fileName));
		map.displayMap();
        
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onFileDelete(final File file) {
        
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onStop(final FileAlterationObserver observer) {
        
    }
}