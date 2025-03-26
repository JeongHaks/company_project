package egovframework.main.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.main.dao.MainDao;
import egovframework.main.service.MainService;

@Service("mainService")
public class MainServiceImpl implements MainService{

	@Resource(name="MainDAO")
	private MainDao mainDao;
	
	// 전체 데이터 (그리드)
	@Override
	public List<Map<String, Object>> dataservice(int offset, int pageSize, String cido, String year, String mm) throws Exception {
		return mainDao.dataservice(offset, pageSize, cido, year, mm);
	}
	
	// 전체 개수
	@Override
	public Map<String, Object> dataserviceTotal(String cido, String year, String mm) throws Exception {
		// TODO Auto-generated method stub
		return mainDao.dataserviceTotal(cido, year, mm);	
	}

	// 기본 페이징을 위한 전체 개수
	@Override
	public int pagingTotal(String cido, String year, String mm) throws Exception {
		return mainDao.pagingTotal(cido, year, mm);
	}
	
	// 필터 시도
	@Override
	public List<Map<String, Object>> cido() throws Exception {
		return mainDao.cido();
	}

	// 필터 연도
	@Override
	public List<Map<String, Object>> year() throws Exception {
		return mainDao.year();
	}
	// 필터 월
	@Override
	public List<Map<String, Object>> mm() throws Exception {
		return mainDao.mm();
	}
	// 필터 연계 연도 - 월
	@Override
	public List<Map<String, Object>> getMonth(Map<String,Object> payload) throws Exception {
		return mainDao.getmonth(payload);
	}

	// 엑셀 다운로드
	@Override
	public List<Map<String, Object>> exceldataservice(String cido, String year, String mm) throws Exception {
		return mainDao.exceldataservice(cido,year,mm);
	}
}
