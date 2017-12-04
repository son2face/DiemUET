package Diem;

import Diem.Interface.IResultService;
import Diem.Model.ResultModel;
import Diem.Service.ResultService;
import Diem.Service.Tools;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class GetPDF {

    //    static final String LOCAL_URL = Path.getPath("grades");
    public static final String LOCAL_URL = "/webapps/result/";
    static BufferedReader in;

    static {
        Tools.disableSslVerification();
    }

    public static boolean getPDF(HashMap<String, ReaderPDF> data) {
        try {
            String urlParameters = "lstClass=823";
            byte[] postData = urlParameters.getBytes("UTF-8");
            int postDataLength = postData.length;
            String request = "http://www.coltech.vnu.edu.vn/news4st/test.php";
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/58.3.138 Chrome/52.3.2743.138 Safari/537.36");
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            Queue<String> inputLines = new LinkedList<String>();
            String key = "<a href=\"../";
            int nKey = key.length();
            while ((inputLine = in.readLine()) != null) {
                inputLines.add(inputLine);
            }
            while ((inputLine = inputLines.remove()) != null) {
                for (int i = 0; i < inputLine.length(); i++) {
                    if (inputLine.charAt(i) == key.charAt(0)) {
                        int check = 1;
                        for (int j = 1; j < nKey; j++) {
                            if (inputLine.charAt(i + j) != key.charAt(j)) {
                                check = 0;
                                break;
                            }
                        }
                        if (check == 1) {
                            i += nKey;
                            String sub = "";
                            while (inputLine.charAt(i) != '"') {
                                sub += inputLine.charAt(i);
                                i++;
                            }
                            String fileName = "";
                            for (int j = 0; j < sub.length(); j++) {
                                if (sub.charAt(j) == '/') {
                                    fileName = "";
                                } else {
                                    fileName += sub.charAt(j);
                                }
                            }
                            fileName = fileName.replace(" ", "%20");
                            sub = sub.replace(" ", "%20");
                            byte[] bytes = sub.getBytes("UTF-8");
                            sub = new String(bytes);
                            IResultService resultService = new ResultService();
                            ResultModel resultModel = resultService.get(fileName);
                            if (resultModel == null) {
                                String urlPDF = "http://www.coltech.vnu.edu.vn/";
                                System.out.println(urlPDF + sub);
                                System.out.println(fileName);
                                File t = new File(Tools.FullPath + LOCAL_URL + fileName);
                                boolean checkk = true;
                                while (checkk) {
                                    FileUtils.copyURLToFile(new URL(urlPDF + sub), t);
                                    data.put(fileName, new ReaderPDF(t));
                                    checkk = false;
                                }
                                resultService.create(fileName);
                            }
                        }
                    }
                }
            }
            return true;
        } catch (SocketTimeoutException e){
            getPDF(data);
            return true;
        }
        catch (NoSuchElementException ex) {
            return true;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        HashMap<String, ReaderPDF> data = new HashMap<String, ReaderPDF>();
        while (!GetPDF.getPDF(data)) ;
    }

}
