package Diem.Controller;

import Diem.DiemThi;
import Diem.Interface.IStudentSubcribeService;
import Diem.Interface.ISubcribeService;
import Diem.Service.StudentSubcribeService;
import Diem.Service.SubcribeService;
import org.json.simple.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("subcribe")
public class SubcribeController {

    private static final int TTL = 255;
    private IStudentSubcribeService studentSubcribeService;
    private ISubcribeService subcribeService;
    private DiemThi diemThi;

    public SubcribeController() {
        studentSubcribeService = new StudentSubcribeService();
        diemThi = new DiemThi();
        subcribeService = new SubcribeService();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public String doLink(StudentSubcrib studentSubcrib) {
        JSONObject obj = new JSONObject();
        JSONObject obj1 = new JSONObject();
        obj1.put("success", true);
        obj.put("data", obj1);
//        System.out.println(studentSubcrib.mssv);
//        System.out.println(studentSubcrib.endpoint);
//        System.out.println(studentSubcrib.key);
//        System.out.println(studentSubcrib.auth);
        studentSubcribeService.create(studentSubcrib.mssv, studentSubcrib.endpoint);
        subcribeService.create(studentSubcrib.endpoint, studentSubcrib.auth, studentSubcrib.key);
        return obj.toString();
    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{id}")
//    public String doSearch(@Context HttpHeaders headers, @PathParam("id") String mssv) throws IOException {
//        return diemThi.score(mssv).toString();
//    }
//
//    @GET
//    @Path("{id}")
//    public void sendAll(@PathParam("id") String mssv) throws Exception {
//        String diemthi = diemThi.score(mssv).toString();
//        System.out.println(diemthi);
//        byte[] payload = diemthi.getBytes("UTF-8");
//        List<StudentSubcribeModel> studentSubcribeModels = studentSubcribeService.get(Integer.valueOf(mssv));
//        for (StudentSubcribeModel studentSubcribeModel : studentSubcribeModels) {
//            SubcribeModel subcribeModel = subcribeService.get(studentSubcribeModel.endpoint);
////            System.out.println("-------------");
////            System.out.println(subcribeModel.auth);
////            System.out.println(subcribeModel.endpoint);
////            System.out.println(subcribeModel.key);
//            sendPushMessage(subcribeModel, payload);
//        }
//    }
//
//    public void sendPushMessage(SubcribeModel sub, byte[] payload) throws Exception {
//        // Figure out if we should use GCM for this notification somehow
//        Notification notification;
//        PushService pushService;
//        Security.addProvider(new BouncyCastleProvider());
//        // Or create a GcmNotification, in case of Google Cloud Messaging
//        notification = new Notification(
//                sub.getEndpoint(),
//                sub.getUserPublicKey(),
//                sub.getAuthAsBytes(),
//                payload,
//                TTL
//        );
//        // Instantiate the push service with a GCM API key
//        pushService = new PushService("AAAAHbckDFM:APA91bEnZoeSw596JHdn17Guy_BBsJ-YvHqZM6S9dYMMWaKifYZ4FTlfSS4wJxFuQyzhBAFid7izxj9lIoB5HohLoJJQLJkf16AXKO6Ot4n4sXi0xqe3D7uGZIjOheoqvUz1aJkMSYSd");
//        // Send the notification
//        pushService.sendAsync(notification);
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("")
//    public String doSearch(@Context HttpHeaders headers, @DefaultValue("") @QueryParam("number") String number, @DefaultValue("20") @QueryParam("limit") int limit, @QueryParam("date") String dateS) {
//        SearchLegalInfoModel searchLegalInfoModel = new SearchLegalInfoModel();
//        searchLegalInfoModel.number = number;
//        searchLegalInfoModel.take = limit;
//        if (dateS == null || "".equals(dateS.trim())) {
////            searchLegalInfoModel.dateCreated
//        } else searchLegalInfoModel.dateCreated = Date.valueOf(dateS);
//        return legalInfoService.get(searchLegalInfoModel).toString();
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("")
//    public String doSearch(@Context HttpHeaders headers, @DefaultValue("") @QueryParam("number") String number, @DefaultValue("20") @QueryParam("limit") int limit, @QueryParam("date") String dateS) {
//        SearchLegalInfoModel searchLegalInfoModel = new SearchLegalInfoModel();
//        searchLegalInfoModel.number = number;
//        searchLegalInfoModel.take = limit;
//        if (dateS == null || "".equals(dateS.trim())) {
////            searchLegalInfoModel.dateCreated
//        } else searchLegalInfoModel.dateCreated = Date.valueOf(dateS);
//        return legalInfoService.get(searchLegalInfoModel).toString();
//    }
}

class StudentSubcrib {
    public Integer mssv;
    public String endpoint;
    public String auth;
    public String key;
}

