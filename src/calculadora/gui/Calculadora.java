package calculadora.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Filipe
 */
public class Calculadora extends JFrame{
    
    public Calculadora(){
        
        organizarLayout();
        
        setSize(260, 340);
        setMinimumSize(new Dimension(260, 340));
        setVisible(true);
        setTitle("Calculadora");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    public static void main(String[] args) {       
        new Calculadora();
    }

    private void organizarLayout() {
        setLayout(new BorderLayout());
        
        Display display = new Display();
        display.setPreferredSize(new Dimension(260, 60));
        add(display, BorderLayout.NORTH);
        
        Teclado teclado = new Teclado();
        add(teclado, BorderLayout.CENTER);
    }
}
