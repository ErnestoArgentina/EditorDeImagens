/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bef;

import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ERNESTO ARGENTINA
 */
public class Geral {

    public static String path;
    public static BufferedImage imgbuffered;
    public static BufferedImage imgbufferedEditada;
    public static BufferedImage imgbufferedauxiliar;
    public static String corPaint="amarelo";
    public static boolean pintar = false;
    public static boolean crop = false;
    static int tamanhoPonta = 10;
    static int xInicial;
    static int xFinal;
    static int yInicial;
    static int yFinal;
    static boolean clipping = false;
    static int xTranslacao = 0;
    static int yTranslacao = 0;
    static int largura = 0;
    static int comprimento = 0;
    

    public static boolean salvar(BufferedImage image) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "png", "gif");
        fileChooser.addChoosableFileFilter(filter);
        imgbuffered = image;
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            try {
                File file = new File(path);
                ImageIO.write(imgbuffered, "jpg", file);
                return true;
            } catch (Exception e) {

            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Nenhum ficheiro seleccionado");
            return false;
        }
        return false;
//            lbImagem.setText("");
////            lbImagem.setIcon(ResizeImage(path));
//            lbImagem.setIcon(ResizeImageBuffered(path));
    }
}
