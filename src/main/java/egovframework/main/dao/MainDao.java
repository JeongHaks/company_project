package egovframework.main.dao;

import java.util.*;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("MainDAO")
public class MainDao extends EgovAbstractMapper  {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 그리드 표출 전체 데이터
	public List<Map<String, Object>> dataservice(int offset, int pageSize, String cido, String year, String mm) throws Exception{
		Map<String, Object> payload = new HashMap<>();
		payload.put("offset", offset);
		payload.put("pageSize", pageSize);
		payload.put("cido", cido);
		payload.put("year", year);
		payload.put("mm", mm);
		System.out.println("payload : " + payload);
		return sqlSession.selectList("egovframework.main.service.MainMapper.dataservice", payload);
	}
	
	// 전체 개수 
	public Map<String, Object> dataserviceTotal(String cido, String year, String mm) throws Exception{
		Map<String, Object> payload = new HashMap<>();
		payload.put("cido", cido);
		payload.put("year", year);
		payload.put("mm", mm);
		return sqlSession.selectOne("egovframework.main.service.MainMapper.dataserviceTotal", payload);
	}
	
	// 기본 페이징을 위한 전체 개수
	public int pagingTotal(String cido, String year, String mm) throws Exception{
		Map<String, Object> payload = new HashMap<>();
		payload.put("cido", cido);
		payload.put("year", year);
		payload.put("mm", mm);
		return sqlSession.selectOne("egovframework.main.service.MainMapper.pageingTotal",payload);
	}
	
	// 필터 시도
	public List<Map<String,Object>> cido() throws Exception{
		return sqlSession.selectList("egovframework.main.service.MainMapper.cido");
	}
	// 필터 연도
	public List<Map<String,Object>> year() throws Exception{
		return sqlSession.selectList("egovframework.main.service.MainMapper.year");
	}
	// 필터 월
	public List<Map<String,Object>> mm() throws Exception{
		return sqlSession.selectList("egovframework.main.service.MainMapper.mm");
	}
	
	// 필터 연계
	public List<Map<String, Object>> getmonth(Map<String, Object> payload) throws Exception {
		return sqlSession.selectList("egovframework.main.service.MainMapper.getmonth",payload);
	}
	
	// 엑셀 다운로드
	// 그리드 표출 전체 데이터
	public List<Map<String, Object>> exceldataservice(String cido, String year, String mm) throws Exception{
		Map<String, Object> payload = new HashMap<>();
		payload.put("cido", cido);
		payload.put("year", year);
		payload.put("mm", mm);
		System.out.println("payload : " + payload);
		return sqlSession.selectList("egovframework.main.service.MainMapper.exceldataservice", payload);
	}
}
