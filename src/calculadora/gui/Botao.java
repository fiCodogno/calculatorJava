package calculadora.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Filipe
 */
public class Botao extends JButton{
    
    public Botao(String texto, Color corFundo){
        
        setText(texto);
        setOpaque(true);
        setBackground(corFundo);
        setForeground(Color.WHITE);
        setFont(new Font("Courier", Font.PLAIN, 20));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
}
