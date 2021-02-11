/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bef;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
//import com.sun.image.codec.jpeg.*;
import static bef.Geral.imgbufferedEditada;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PainelImagem extends JPanel implements MouseMotionListener, MouseListener {

    private int mX, mY;
//    private BufferedImage mImage;
    static JFrame f = new JFrame("ImageDuplicity v1.0");
    static Graphics2D g2Image;
    private AffineTransform at;

    public PainelImagem() {
        addMouseMotionListener(this);
        addMouseListener(this);
        f.addMouseMotionListener(this);
        setSize(1000, 600);
        setVisible(true);
        setBackground(Color.black);

    }

    public static void main(String[] args) {

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // If the offscreen image is not defined, create it.   
        Dimension d = getSize();
        int w = d.width, h = d.height;

        if (Geral.imgbuffered == null) {
            /*
            criacao da imagem vazia
            */
            Geral.imgbufferedEditada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // Obtem o Graphics2D for da imagem para que se desenhe por cima dele.   
            g2Image = Geral.imgbufferedEditada.createGraphics();
            g2Image.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
// atraves do caminho criamos a bufferedImage 
            try {
                String filename = Geral.path;
                System.out.println(filename);
                //teste buffered
                File file = new File(filename);
//                BufferedImage image = ImageIO.read(file);
                Geral.imgbuffered = ImageIO.read(file);

                //             tentando rotate
                // Draw the loaded image on the offscreen image.  
                g2Image.drawImage(Geral.imgbuffered, 0, 0, w, h, null);
                g2Image.setStroke(new BasicStroke(2));
//                g2Image.rotate(Math.toRadians(45));
//                g2Image.setTransform(at);
            } catch (Exception e) {
                System.out.print(e);
            }
//        }/

        }
        // Draw some concentric ellipses.    
        Color[] colors = {Color.red, Color.blue, Color.green};

        //desnhar na imagem
        if (Geral.pintar == true) {

//            int s = 100;
            int s = Geral.tamanhoPonta;
            if (Geral.corPaint.equalsIgnoreCase("azul")) {
                g2Image.setColor(Color.blue);
            }
            if (Geral.corPaint.equalsIgnoreCase("verde")) {
                g2Image.setColor(Color.GREEN);
            }
            if (Geral.corPaint.equalsIgnoreCase("amarelo")) {
                g2Image.setColor(Color.yellow);
            }
            if (Geral.corPaint.equalsIgnoreCase("vermelho")) {
                g2Image.setColor(Color.red);
            }
            if (Geral.corPaint.equalsIgnoreCase("branco")) {
                g2Image.setColor(Color.white);
            }
            if (Geral.corPaint.equalsIgnoreCase("preto")) {
                g2Image.setColor(Color.black);
            }
            g2Image.fillOval(mX - s / 2, mY - s / 2, s, s);
        }
        // Render the offscreen image.  

        g2.drawImage(Geral.imgbufferedEditada, 0, 0, this);

        //desenhar limites do crop
        if (Geral.crop && Geral.clipping) {
            g2.setColor(Color.red);
            g2.drawRect(Geral.xInicial, Geral.yInicial, Geral.xFinal - Geral.xInicial, Geral.yFinal - Geral.yInicial);
        }
    }

    

    public void crop() {
        int w = Geral.xFinal - Geral.xInicial;
        int h = Geral.yFinal - Geral.yInicial;
        Geral.imgbufferedauxiliar = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2ImageAu = Geral.imgbufferedauxiliar.createGraphics();
        g2ImageAu.drawImage(imgbufferedEditada, 0, 0, w, h, Geral.xInicial, Geral.yInicial, Geral.xFinal, Geral.yFinal, this);

        imgbufferedEditada = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        PainelImagem.g2Image = imgbufferedEditada.createGraphics();
        PainelImagem.g2Image.drawImage(Geral.imgbufferedauxiliar, 0, 0, this);
        repaint(0);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        //mouseMoved(me);
        mX = (int) me.getPoint().getX();
        mY = (int) me.getPoint().getY();
        repaint();

        if (Geral.crop && Geral.clipping) {
            repaint(0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
//        mX = (int) me.getPoint().getX();
//        mY = (int) me.getPoint().getY();
//        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (Geral.crop) {
            System.out.println("done");
            Geral.xInicial = (int) me.getPoint().getX();
            Geral.yInicial = (int) me.getPoint().getY();
            Geral.clipping = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (Geral.crop) {
            Geral.xFinal = (int) me.getPoint().getX();
            Geral.yFinal = (int) me.getPoint().getY();
            crop();
            Geral.crop = false;
            Geral.clipping = false;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
