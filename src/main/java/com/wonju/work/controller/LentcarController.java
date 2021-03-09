package com.wonju.work.controller;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wonju.work.dao.LentCarDAO;
import com.wonju.work.dto.LentcarDTO;
import com.wonju.work.util.Paging;


@RestController
@MapperScan(basePackages="com.wonju.work.dao")
public class LentcarController {
	
	
	private int blockList = 10;
	private int blockPage = 10;
	
	@Autowired
	private LentCarDAO lentcarDAO;
	
	@RequestMapping("/select_data")
	public ModelAndView load_lentcar(String cPage) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		System.out.println(cPage);
		
		int c_page = 1;
		if(cPage != null) {
			c_page = Integer.parseInt(cPage);
		}
		
		System.out.println("???");
		System.out.println(c_page);
		
		int rowTotal = lentcarDAO.totalCount();
		
		//System.out.println(rowTotal);
		
		Paging page = new Paging(c_page, rowTotal, blockList, blockPage);
		
		
		System.out.println(page.getBegin());
		System.out.println(page.getEnd());
		
		List<LentcarDTO> lentCarList = lentcarDAO.selectLentCar(page.getBegin(), page.getEnd());
		
		System.out.println(lentCarList);
		
		
		mv.addObject("lentCarList", lentCarList);
		mv.addObject("rowTotal", rowTotal);
		mv.addObject("p_code", page.getSb().toString());
		mv.addObject("blockList", blockList);
		mv.addObject("nowPage", c_page);
		
		mv.setViewName("selectdata");
		
		return mv;
		
		
	}
	
	
	@RequestMapping("/search_data")
	public ModelAndView load_search_lentcar(String cPage, String subject, String keyword) throws Exception{

		ModelAndView mv = new ModelAndView();
		
		
		int c_page = 1;
		if(cPage != null) {
			c_page = Integer.parseInt(cPage);
		}
		
		
		int rowTotal = lentcarDAO.searchtotalCount(subject, keyword);
		
		System.out.println(rowTotal);
		
		Paging page = new Paging(c_page, rowTotal, blockList, blockPage, subject, keyword);
		
		
		List<LentcarDTO> lentCarList = lentcarDAO.searchLentCar(page.getBegin(), page.getEnd(), subject, keyword);
		
		
		mv.addObject("lentCarList", lentCarList);
		mv.addObject("rowTotal", rowTotal);
		mv.addObject("p_code", page.getSb().toString());
		mv.addObject("blockList", blockList);
		mv.addObject("nowPage", c_page);
		
		mv.setViewName("selectdata");
		
		return mv;
		
		
	}
	
	
	@RequestMapping("/deletedata")
	public void delete_lentcar() throws Exception{
		
		
		int res = lentcarDAO.deleteLentCar();
		
		System.out.println(res);
		
		if(res > 0) {
			System.out.println("삭제 완료");
		}

		
		
	}
	
	
	//검증
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	//여기서 항상 몇개인지 체크해야 한다
	public String datasize() {
		String size = "";
		try {
			String url = "https://openapi.gg.go.kr/Carlendbiz?KEY=33510ea9a7b6441bb2ce226872842ed2";
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			// root tag 
			doc.getDocumentElement().normalize();
			
			Node c_node = doc.getElementsByTagName("head").item(0);
			Element pElement = (Element) c_node;
			size = getTagValue("list_total_count", pElement);

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return size;
	
	}
	

	@RequestMapping("/insertdata")
	public void lentCarsInsert(@RequestParam(value="country", defaultValue ="")String name) throws Exception{
		//데이터 insert하기
		try{

			String url = "https://openapi.gg.go.kr/Carlendbiz?KEY=33510ea9a7b6441bb2ce226872842ed2&pSize="+datasize();
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			

			doc.getDocumentElement().normalize();
			

			NodeList nList = doc.getElementsByTagName("row");
			System.out.println("갯수: "+ nList.getLength());
			
			for(int temp = 0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					
					Element eElement = (Element) nNode;
					
					LentcarDTO dto = new LentcarDTO();
					
					
					dto.setName(getTagValue("BIZPLC_NM", eElement));
					dto.setAddr(getTagValue("REFINE_LOTNO_ADDR", eElement));
					dto.setLat(getTagValue("REFINE_WGS84_LAT", eElement));
					dto.setLogt(getTagValue("REFINE_WGS84_LOGT", eElement));
					
					int res = lentcarDAO.insertLentCar(dto);
					
					if(res != 1) {
						System.out.println("insert 실패");
					}
					
				}	// for end
			}	// if end
			

		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
		
		
		/*
		 * final CityDTO param = new CityDTO(); final List<CityDTO> cityList =
		 * cityDAO.selectCities(param); System.out.println(cityList);
		 */
		
		
	}
}
