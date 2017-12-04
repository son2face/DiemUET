package Diem.Controller;

import Diem.DiemThi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("diemthi")
public class DiemController {
    private DiemThi diemThi;

    public DiemController() {
        diemThi = new DiemThi();
    }

//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        this.out = response.getWriter();
//        this.request = request.getParameter("cmd");
//        if ("getscore".equals(this.request)) {
//            score(request.getParameter("mssv"));
//            Cookie ms = new Cookie("cookie", request.getParameter("mssv"));
//            ms.setMaxAge(60 * 60 * 24 * 7);
//            response.addCookie(ms);
//        }
//        if ("report".equals(this.request)) {
//            report(request.getParameter("mssv"), request.getParameter("url"));
//        }
//        if ("cookie".equals(this.request)) {
//            Cookie[] coo = request.getCookies();
//            if (coo != null) {
//                out.write("{\"mssv\":\"" + coo[0].getValue() + "\"}");
//            } else {
//
//            }
//        }
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response doSearch(@Context HttpHeaders headers, @PathParam("id") String mssv) throws IOException {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(diemThi.score(mssv).toString()).build();
    }
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