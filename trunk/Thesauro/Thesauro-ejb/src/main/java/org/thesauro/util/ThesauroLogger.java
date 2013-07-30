/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matheus
 */
public class ThesauroLogger {
    
    private final Logger logger;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private Class<?> clazz;
    private boolean generateHeader = false;
    private static boolean debugMode = false;
    

    private ThesauroLogger(Logger logger, Class<?> clazz){
        this.logger = logger;
        this.clazz = clazz;
    }

    private String getHeader(){
        if(generateHeader){
            String time = sdf.format(new Date());
            return time + " ["+clazz.getSimpleName()+"]: ";
        }
        return "";
    }

    public boolean isGenerateHeader() {
        return generateHeader;
    }

    public void setGenerateHeader(boolean generateHeader) {
        this.generateHeader = generateHeader;
    }

    public static ThesauroLogger getLogger(Class<?> clazz) {
        return new ThesauroLogger(Logger.getLogger(clazz.getName()),clazz);
    }
    
    public void setDebugMode(){
        debugMode = true;
    }
    
    public void unsetDebugMode(){
        debugMode = false;
    }

    public void debug(Object object){
        if(debugMode){
            logger.log(Level.INFO,object==null?"null":getHeader()+object.toString());
        }
        else{
            logger.log(Level.FINE,object==null?"null":getHeader()+object.toString());
        }
    }

    public void info(Object object){
        logger.log(Level.INFO,object==null?"null":getHeader()+object.toString());
    }

    public void error(Object object){
        logger.log(Level.SEVERE,object==null?"null":getHeader()+object.toString());
    }

    public void error(Object object, Throwable e){
        logger.log(Level.SEVERE,object==null?"null":getHeader()+object.toString(),e);
    }
    
}
