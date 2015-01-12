/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.Controller;

/**
 *
 * @author Dan
 */
public class StartAll {
    
    public static void main(String[] args)
    {
        MainInterface mainInterface = new MainInterface();
        Controller controller = new Controller (mainInterface);
        mainInterface.setVisible(true);
    }
}
