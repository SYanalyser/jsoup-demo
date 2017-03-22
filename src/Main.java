import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
 * @author gieey
 * @date 2017-3-22
*/
public class Main {


        private static ResourceBundle resource = ResourceBundle.getBundle("a");


        /**
         *
         * according to the image of the external address to download images to the local hard disk
         * @param filePath
         * @param imgUrl
         * @throws UnsupportedEncodingException
         *
         */
        public static void downImages(String filePath,String imgUrl) throws UnsupportedEncodingException {

            String beforeUrl = imgUrl.substring(0,imgUrl.lastIndexOf("/")+1);

            String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);

            String newFileName = URLEncoder.encode(fileName, "UTF-8");

            newFileName = newFileName.replaceAll("\\+", "\\%20");

            imgUrl = beforeUrl + newFileName;

            try {

                File files = new File(filePath);
                if (!files.exists()) {
                    files.mkdirs();
                }

                URL url = new URL(imgUrl);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                InputStream is = connection.getInputStream();

                File file = new File(filePath + fileName);

                FileOutputStream out = new FileOutputStream(file);
                int i = 0;
                while((i = is.read()) != -1){
                    out.write(i);
                }
                out.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws IOException {
            //web site address
            String resourceURL = resource.getString("resource.url");
            //after saving the resources, save the local file path
            String downloadFilePath = resource.getString("download.file.path");
            Document document = Jsoup.connect(resourceURL).get();
            Elements elements = document.getElementsByTag("a");
            for(Element element : elements){
                String imgSrc = element.attr("href");
                System.out.println("Downloading images：-----------" + imgSrc);
                downImages(downloadFilePath,resourceURL+imgSrc);
                System.out.println("Images are downloaded to complete：-----------" + imgSrc);
            }
            System.out.println("totale " + elements.size() +" file");
        }
}
