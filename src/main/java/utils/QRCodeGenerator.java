package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class QRCodeGenerator {
    public static boolean generateQrCode(String text,int id){
        String path="C:\\PiJava-gestion_users\\src\\main\\resources\\qrcode\\Product"+id+".png";
        int size=90;
        String filetype="png";
        File myFile=new File(path);
        Map<EncodeHintType,Object> hintMap=new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hintMap.put(EncodeHintType.MARGIN,0);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix byteMatrix= null;
        try {
            byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,size,size,hintMap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        int crunchifyWidth=byteMatrix.getWidth();
        BufferedImage image=new BufferedImage(crunchifyWidth,crunchifyWidth,BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics2D= (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,crunchifyWidth,crunchifyWidth);
        graphics2D.setColor(Color.BLACK);
        for(int i=0;i<crunchifyWidth;i++){
            for(int j=0;j<crunchifyWidth;j++){
                if(byteMatrix.get(i,j)){
                    graphics2D.fillRect(i,j,1,1);
                }
            }
        }
        try {
            ImageIO.write(image,filetype,myFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
