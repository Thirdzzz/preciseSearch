package spider;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.BufferedImage;

public class Histogram {
	public Histogram() {
	}
	
	public double getHistogram(int width,int totalWidth,int height,OutputStream os) {
		if(width>totalWidth) return 0;
		BufferedImage image = new BufferedImage(totalWidth,height,BufferedImage.TYPE_INT_RGB);
		
		//��ȡͼ��������
		Graphics g = image.getGraphics();
		
		//�趨������ɫ
		g.setColor(Color.white);
		g.fillRect(0,0,totalWidth,height);
		
		//���߿�
		g.setColor(Color.black);
		g.drawRect(0,0,totalWidth-1,height-1);
		
		//����״ͼ
		g.setColor(Color.blue);
		g.fillRect(0, 0, width, height);
		
		g.dispose();
		
		try{
			ImageIO.write(image,"JPEG",os);
		}catch(IOException e) {
			return 0;
		}
		
		return (double)width/totalWidth;
	}
	
}
