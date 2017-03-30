import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;
import com.linwang.redis.JedisPoolManager;
import com.linwang.uitls.PdfUitls;


public class test {

	public static void main(String[] args) throws MalformedURLException, IOException, DocumentException {
//		JedisPoolManager jpm=new JedisPoolManager();
//		jpm.set("a","asd");
		PdfUitls.createdPdf();
	}

}
