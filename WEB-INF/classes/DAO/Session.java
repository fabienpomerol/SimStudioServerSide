/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;
/**
 *
 * @author babusseuil
 */
public class Session {
    int i;
    public Session(){
    }
     public void saveSessionAttribute(String key, Object value) {
        FlexSession fs = FlexContext.getFlexSession();
        fs.setAttribute(key, value);
    }
 
    /**
     * Return the session attribute associated to the key parameter
     * @param key the attribute name
     * @return the attribute value, null if not set before
     */
    public Object getSessionAttribute(String key) {
        FlexSession fs = FlexContext.getFlexSession();
        return fs.getAttribute(key);
    }
}
