package egovframework.main.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import egovframework.main.service.MainService;

@Controller
public class MainController {
	
	@Resource(name="mainService")
	private MainService mainService;
	
	// TODO Auto-generated method stub
	@RequestMapping(value="/main/main.do",method=RequestMethod.GET)
	public String main(){
		return "main/main";
	}		

	// 데이터 서비스 테스트 화면 
	@RequestMapping(value="/dataservice/dataservice.do",method={RequestMethod.GET,RequestMethod.POST})
	public String dataService(Model model
							, @RequestParam(value="page", defaultValue="1") int page
							, @RequestParam(value="cido", required=false) String cido
							, @RequestParam(value="year", required=false) String year
							, @RequestParam(value="mm", required=false) String mm) throws Exception{		
			
		try {		       
			// 그리드 한 페이지당 개수 
			int pageSize = 10;
			int offset = (page - 1) * pageSize;
			
			
			List<Map<String, Object>> dataservice = mainService.dataservice(offset, pageSize, cido, year, mm);
			Map<String, Object> total = mainService.dataserviceTotal(cido,year,mm);		
			int totalcount = mainService.pagingTotal(cido,year,mm);
			int totalPage = (int)Math.ceil((double)totalcount/pageSize);
			
			//필터
			List<Map<String, Object>> cidos = mainService.cido();
			List<Map<String, Object>> years = mainService.year();
			List<Map<String, Object>> mms = mainService.mm();
			
			model.addAttribute("total",total);
			model.addAttribute("dataservice",dataservice);
			model.addAttribute("currentPage",page);
			model.addAttribute("totalPages",totalPage);
			model.addAttribute("totalCount",totalcount);
			
			model.addAttribute("cido",cidos);
			model.addAttribute("year",years);
			model.addAttribute("mm",mms);
			
			// 필터 고정을 위한 선택된 필터값 모델에 담기.
			model.addAttribute("selectcido",cido);
			model.addAttribute("selectyear",year);
			model.addAttribute("selectmm",mm);
			
	    } catch (Exception e) {
	        e.printStackTrace(); // 터지는 이유 로그로 확인!
	    }		
		return "testdataService/dataServiceTest";
	}
	
	
	// 데이터 서비스 테스트 화면 
	@ResponseBody
	@RequestMapping(value="/dataservice/getMonth.do",method=RequestMethod.POST)
	public String dataService(@RequestParam(value="year")String year) throws Exception{
		
		Map<String,Object> payload = new HashMap<>();
		payload.put("year", year);
		List<Map<String, Object>> getmonth = mainService.getMonth(payload);		
		// ajax 받을려면 json 변환이 필요하다...
		Gson gson = new GsonBuilder().create();
		String jsonResponse = gson.toJson(getmonth);
		
		
		return jsonResponse;
	}
	
	// 데이터서비스 엑셀 다운로드
	@RequestMapping(value="/download.do" ,method={RequestMethod.GET,RequestMethod.POST})
	public void download(HttpServletResponse response
						, @RequestParam(value="cido", required=false) String cido
						, @RequestParam(value="year", required=false) String year
						, @RequestParam(value="mm", required=false) String mm) throws Exception{
		try{
		
		System.out.println("cido : " + cido);
		System.out.println("year : " + year);
		System.out.println("mm : " + mm);
		// 엑셀파일 생성
        XSSFWorkbook wb = new XSSFWorkbook();

        // 엑셀파일 내 시트 생성
        // createSheet("시트명")
        XSSFSheet sheet = wb.createSheet("데이터서비스");
        XSSFRow row = null;
        XSSFCell cell = null;

        // Row 순서 / Cell 순서 변수 선언 및 초기화
        int rowNo = 0;
        int cellCount = 0;
        
        String[] header = {"시도","연도","월","연료"
        				  ,"차종","차량유형","용도","등록차량(대)"};
        
        row = sheet.createRow(rowNo++);
        
        for(int i=0; i<header.length; i++){
        	cell = row.createCell(i);
        	cell.setCellValue(header[i]);
        }
        
        String[] key = { "ctpv", "yymmdd" ,"mm", "fuel"
        				,"car_ty","vehicle_ty", "purps","rgn_car"};
        
		List<Map<String, Object>> dataservice = mainService.exceldataservice(cido, year, mm);
		for(Object map : dataservice){
			Map<String, Object> mapValue = (Map<String, Object>) map;
			row = sheet.createRow(rowNo++);
			for(int val=0; val<key.length; val++){
				cell = row.createCell(val); // 세 번째 열                       
                cell.setCellValue("" + mapValue.get(key[val]));
			}
		}
     // 엑셀다운로드 파일명 
        String filename="데이터서비스테스트.xlsx";
        String outputFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        
        // 컨텐츠 타입과 파일명 지정
        response.setContentType("application/vnd.ms-excel; charset=euc-kr"); 
        response.setHeader("Content-Description" , "JSP Generated Data");
        response.setHeader("Content-disposition", "attachment; filename=\"" + outputFilename + "\"");
        
        try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            wb.write(outputStream);
            outputStream.flush();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            if (wb != null) {
                wb.close();
            }
        }  
    }catch(IOException e){
    	e.printStackTrace();
    }
	
	}//download	
}//main
