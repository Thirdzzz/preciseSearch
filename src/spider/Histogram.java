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
		
		//获取图形上下文
		Graphics g = image.getGraphics();
		
		//设定背景颜色
		g.setColor(Color.white);
		g.fillRect(0,0,totalWidth,height);
		
		//画边框
		g.setColor(Color.black);
		g.drawRect(0,0,totalWidth-1,height-1);
		
		//画柱状图
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
