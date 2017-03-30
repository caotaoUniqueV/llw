package com.linwang.uitls;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.base.Strings;

public class PicCodeUtils {
	private static Random rand = new Random(System.currentTimeMillis());
	
	private static int strLength = 4;
	private static String str = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static int width = 200;//图片宽度
	private static int height = 50;//图片高度
	private static int lineSpace = 10;//线条间距
	private static int isAddEfect = 1;// 是否添加效果
	
	
	public static String createPicStream (HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = createText();

		// 保存验证码
		request.getSession(true).setAttribute("pic_code", capText);

		// create the image with the text
		BufferedImage bi = createImage(capText);

		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		out.flush();
		out.close();
		
		return capText;
	}
	
	// 判断验证码是否正确
	public static boolean checkCodeIsEqual(HttpServletRequest request,String picCode){
        if(Strings.isNullOrEmpty(picCode)){
            return false;
        }
        HttpSession session = request.getSession();
        if (session == null) {
			return false;
		}
        String code_m = (String) session.getAttribute("pic_code");
        session.removeAttribute("pic_code");
        if (Strings.isNullOrEmpty(code_m)) {
          	 return false;
        }
        if(code_m == null || code_m.trim().isEmpty()){
            return false;
        }
        if(!picCode.trim().equalsIgnoreCase(code_m.toLowerCase())){
            return false;
        }
        return true;
    }
	
	/**
	 * 生成验证码文字
	 * @return
	 */
	public static String createText(){
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < strLength; i++)
		{
			int pos = rand.nextInt(str.length());
			text.append(str.substring(pos, pos+1));
		}

		return text.toString();
	}
	/**
	 * 获取验证码图像
	 * @param text
	 * @return
	 */
	public static BufferedImage createImage(String text){
		BufferedImage bi = renderWord(text,width,height);
		if (isAddEfect == 1) {
			bi = addEfect(bi);
		}
		bi = addBackground(bi);
		return bi;
	}
	/**
	 * 添加背景色
	 * @param baseImage
	 * @return
	 */
	private static BufferedImage addBackground(BufferedImage baseImage){
//		Color colorFrom = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
//		Color colorTo = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		Color colorFrom = new Color(255, 255, 255, 255);
		Color colorTo = new Color(255, 255, 255, 255);

		int width = baseImage.getWidth();
		int height = baseImage.getHeight();

		// create an opaque image
		BufferedImage imageWithBackground = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graph = (Graphics2D) imageWithBackground.getGraphics();
		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY));
		hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));

		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));

		graph.setRenderingHints(hints);

		GradientPaint paint = new GradientPaint(0, 0, colorFrom, width, height,
				colorTo);
		graph.setPaint(paint);
		graph.fill(new Rectangle2D.Double(0, 0, width, height));

		// draw the transparent image over the background
		graph.drawImage(baseImage, 0, 0, null);

		return imageWithBackground;
	}
	/**
	 * 添加效果
	 * @param baseImage
	 * @return
	 */
	private static BufferedImage addEfect(BufferedImage baseImage){
		Graphics2D graph = (Graphics2D) baseImage.getGraphics();
		int imageHeight = baseImage.getHeight();
		int imageWidth = baseImage.getWidth();

		// want lines put them in a variable so we might configure these later
		int horizontalLines = imageHeight / 7;
		int verticalLines = imageWidth / 7;

		// calculate space between lines
		int horizontalGaps = imageHeight / (horizontalLines + 1) + lineSpace;
		int verticalGaps = imageWidth / (verticalLines + 1) + lineSpace;

		// draw the horizontal stripes
		for (int i = horizontalGaps; i < imageHeight; i = i + horizontalGaps)
		{
			Color color = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			graph.setColor(color);
			graph.drawLine(0, i, imageWidth, i);

		}

		// draw the vertical stripes
		for (int i = verticalGaps; i < imageWidth; i = i + verticalGaps)
		{
			Color color = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			graph.setColor(color);
			graph.drawLine(i, 0, i, imageHeight);

		}

		// create a pixel array of the original image.
		// we need this later to do the operations on..
		int pix[] = new int[imageHeight * imageWidth];
		int j = 0;

		for (int j1 = 0; j1 < imageWidth; j1++)
		{
			for (int k1 = 0; k1 < imageHeight; k1++)
			{
				pix[j] = baseImage.getRGB(j1, k1);
				j++;
			}

		}

		double distance = ranInt(imageWidth / 4, imageWidth / 3);

		// put the distortion in the (dead) middle
		int widthMiddle = baseImage.getWidth() / 2;
		int heightMiddle = baseImage.getHeight() / 2;

		// again iterate over all pixels..
		for (int x = 0; x < baseImage.getWidth(); x++)
		{
			for (int y = 0; y < baseImage.getHeight(); y++)
			{

				int relX = x - widthMiddle;
				int relY = y - heightMiddle;

				double d1 = Math.sqrt(relX * relX + relY * relY);
				if (d1 < distance)
				{

					int j2 = widthMiddle
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (x - widthMiddle));
					int k2 = heightMiddle
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (y - heightMiddle));
					baseImage.setRGB(x, y, pix[j2 * imageHeight + k2]);
				}
			}

		}

		return baseImage;
	}
	private static int ranInt(int i, int j)
	{
		double d = Math.random();
		return (int) ((double) i + (double) ((j - i) + 1) * d);
	}
	private static double fishEyeFormula(double s)
	{
		if (s < 0.0D)
			return 0.0D;
		if (s > 1.0D)
			return s;
		else
			return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
	}
	/**
	 * 绘画验证码文字
	 * @param word
	 * @param width
	 * @param height
	 * @return
	 */
	private static BufferedImage renderWord(String word, int width, int height){
		int fontSize = 40;
		int[] fontStyles = new int[]{Font.BOLD,Font.ITALIC,Font.PLAIN};
		Font[] fonts = new Font[]{
			new Font("Arial", fontStyles[rand.nextInt(fontStyles.length)], fontSize - rand.nextInt(10)), 
			new Font("Courier", fontStyles[rand.nextInt(fontStyles.length)], fontSize - rand.nextInt(10))
		};
		
		int charSpace = (width - (width/strLength)*strLength)/strLength;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = image.createGraphics();
		

		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));
		g2D.setRenderingHints(hints);

		FontRenderContext frc = g2D.getFontRenderContext();
		Random random = new Random();

		int startPosY = (height - fontSize) / 5 + fontSize;

		char[] wordChars = word.toCharArray();
		Font[] chosenFonts = new Font[wordChars.length];
		int [] charWidths = new int[wordChars.length];
		int widthNeeded = 0;
		for (int i = 0; i < wordChars.length; i++)
		{
			chosenFonts[i] = fonts[random.nextInt(fonts.length)];

			char[] charToDraw = new char[]{
				wordChars[i]
			};
			GlyphVector gv = chosenFonts[i].createGlyphVector(frc, charToDraw);
			charWidths[i] = (int)gv.getVisualBounds().getWidth();
			if (i > 0)
			{
				widthNeeded = widthNeeded + 2;
			}
			widthNeeded = widthNeeded + charWidths[i];
		}
		
		int startPosX = (width - widthNeeded) / 2;
//		Color color = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		Color color = new Color(100 , 100, 100, 255);
		g2D.setColor(color);
		for (int i = 0; i < wordChars.length; i++)
		{
			g2D.setFont(chosenFonts[i]);
			char[] charToDraw = new char[] {
				wordChars[i]
			};
			g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, startPosY);
			startPosX = startPosX + (int) charWidths[i] + charSpace;
		}

		return image;
	}
	public int getStrLength() {
		return strLength;
	}
	public void setStrLength(int strLength) {
		this.strLength = strLength;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getLineSpace() {
		return lineSpace;
	}
	public void setLineSpace(int lineSpace) {
		this.lineSpace = lineSpace;
	}
	public int getIsAddEfect() {
		return isAddEfect;
	}
	public void setIsAddEfect(int isAddEfect) {
		this.isAddEfect = isAddEfect;
	}
}
