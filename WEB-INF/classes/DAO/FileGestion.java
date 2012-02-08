/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author babusseuil
 */
public class FileGestion {

    public FileGestion(){}
    
    
    public void removeFile(String path) {
        File f = new File(path);
        f.delete();
        //f.de
       
    }

    public void duplicationFile(File f, File fToCopy) {
        /*File dest = new File("");
        File source = new File("");*/
        
        
        FileChannel in = null; // canal d'entrée
        FileChannel out = null; // canal de sortie

        try {
          // Init
          in = new FileInputStream(f.getAbsolutePath()).getChannel();
          out = new FileOutputStream(fToCopy.getAbsolutePath()).getChannel();

          // Copie depuis le in vers le out
          in.transferTo(0, in.size(), out);
        } catch (Exception e) {
          e.printStackTrace(); // n'importe quelle exception
        } finally { // finalement on ferme
          if(in != null) {
                try {
                  in.close();
                } catch (IOException e) {}
          }
          if(out != null) {
                try {
                  out.close();
                } catch (IOException e) {}
          }
        }
    }
}
