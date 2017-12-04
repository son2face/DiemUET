import Diem.DiemThi;
import Diem.GetPDF;
import Diem.Service.Tools;
import manager.Entity.DatabaseEntity;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;

public class InitClass extends ResourceConfig implements ServletContextListener  {

    public void contextInitialized(ServletContextEvent arg0) {
        DatabaseEntity.setFileDir("database.txt");
        DatabaseEntity.loadData();
        File file = new File(Tools.FullPath + GetPDF.LOCAL_URL);
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        DiemThi.makeData(file);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            DatabaseEntity.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}