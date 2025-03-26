package egovframework.main.service;

import java.util.*;


public interface MainService {	
	public List<Map<String, Object>> dataservice(int offset, int pageSize, String cido, String year, String mm) throws Exception;
	public Map<String, Object> dataserviceTotal(String cido, String year, String mm) throws Exception;
	public int pagingTotal(String cido, String year, String mm) throws Exception;
	public List<Map<String, Object>> cido() throws Exception;
	public List<Map<String, Object>> year() throws Exception;
	public List<Map<String, Object>> mm() throws Exception;
	
	// 필터 연계
	public List<Map<String,Object>> getMonth(Map<String,Object> payload) throws Exception;
	// 엑셀 다운로드
	public List<Map<String, Object>> exceldataservice(String cido, String year, String mm) throws Exception;
}
