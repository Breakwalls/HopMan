import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;


public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] mainColor = {57,57,99}; //��ɫ��������
		int fixY = 300; //����Y��Ʒְ�
		while(true){
			try {
				CetImage(); //��ȡ��Ļͼ�񵽵���
				int distance = getDistance(mainColor,fixY); //��ȡ��ɫ��Ŀ���ľ���
				System.out.println("Distance:"+distance);
			    RunCmd("cmd /c G:/SDK/android-sdk-windows/platform-tools/adb shell input swipe 200 200 200 200 "+distance); //adbģ��������ʼ��Ծ
				Thread.sleep(1400); //�߳����ߣ��ȴ������ȶ�
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int getDistance(int[] mainColor,int fixY) throws IOException{
		File file = new File("D:/tmp.png"); //ͼƬ·��
		boolean STP = true;
		boolean ETP = true;
		int x1 = 0,y1 = 0,x2 = 0,y2 = 0;
		BufferedImage image = ImageIO.read(file); 
		int tmp = getIndex(image,0,fixY);
	    for (int i = fixY;i<1920;i++){
	        for(int j = 0;j<1080;j++){
	        	if(Math.abs(getIndex(image,j,i)-tmp)>30&&ETP){//�򵥱Ƚ�����
	        		System.out.println("Starting Point:"+j+","+i);
	        		x1 = j;
	        		y1 = i;
	        		ETP = false;
	        	}
	        	if(Arrays.equals(getRGB(image,j,i), mainColor)&&STP){
		        	System.out.println("End Point:"+j+","+i);
		        	x2 = j;
		        	y2 = i;
		        	STP = false;
	        	}
	        }
	    } 
		return (int)(Math.sqrt((Math.abs(x1 - x2)*Math.abs(x1 - x2))+(Math.abs(y1 - y2)*Math.abs(y1 - y2)))*1.2);//ͨ������������
	}
	
	public static int[] getRGB(BufferedImage image,int x,int y){ //���������ȡ����
		int[] rgb = new int [3];
		int pixel = image.getRGB(x,y);
		rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        return rgb;
	}
	
	public static int getIndex(BufferedImage image,int x,int y){ //���������ȡ����
		int[] rgb = getRGB(image,x,y);
        return rgb[0]+rgb[1]+rgb[2];
	}
	
	public static void CetImage(){
		RunCmd("cmd /c G:/SDK/android-sdk-windows/platform-tools/adb shell /system/bin/screencap -p /sdcard/tmp.png"); //����adb��ͼ�����浽sd��
		RunCmd("cmd /c G:/SDK/android-sdk-windows/platform-tools/adb pull /sdcard/tmp.png D:/tmp.png"); //����adb��ͼƬ��ֵ������ 
	}
	
	public static void RunCmd(String cmd){ //ִ������
		try { 
			Process process = null;
			process = Runtime.getRuntime().exec(cmd);
			process.waitFor(); 
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
