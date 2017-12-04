package Diem.Service;

import Diem.Interface.IReaderPDF;
import Diem.Model.StudentModel;
import com.asprise.ocr.Ocr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Son on 6/15/2017.
 */
public class ReaderPDFService implements IReaderPDF {
    List<StudentModel> studentModelList = new ArrayList<>();
    public String Data;

    ReaderPDFService(byte[] data) {
        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
//        ocr.recognize(new File[] {new File("5.png")},
//                Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PDF,
//                Ocr.PROP_PDF_OUTPUT_FILE, "ocr-result.pdf",
//                Ocr.PROP_PDF_OUTPUT_TEXT_VISIBLE, true);
// ocr more images here ...
        String s = ocr.recognize("1.pdf", 1, -1, -1, -1, -1,
                Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PDF,
                Ocr.PROP_PDF_OUTPUT_FILE, "11.pdf",
                Ocr.PROP_PDF_OUTPUT_TEXT_VISIBLE, true);
        System.out.println(s);
        ocr.stopEngine();
    }

    ReaderPDFService(String path) {
        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
//        ocr.recognize(new File[] {new File("5.png")},
//                Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PDF,
//                Ocr.PROP_PDF_OUTPUT_FILE, "ocr-result.pdf",
//                Ocr.PROP_PDF_OUTPUT_TEXT_VISIBLE, true);
// ocr more images here ...
        Data = ocr.recognize(path, 1, -1, -1, -1, -1,
                Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PDF,
                Ocr.PROP_PDF_OUTPUT_FILE, "11.pdf",
                Ocr.PROP_PDF_OUTPUT_TEXT_VISIBLE, true);
        ocr.stopEngine();
    }

    public static void main(String[] args) {
        ReaderPDFService readerPDFService = new ReaderPDFService("H:\\apache-tomcat-9.0.0.M21\\webapps\\result2\\chvtc_ema3015-1.pdf");
        System.out.println(readerPDFService.Data);
    }
}
