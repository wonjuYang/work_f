package com.wonju.work.controller;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wonju.work.dao.LentCarDAO;
import com.wonju.work.dto.CityDTO;
import com.wonju.work.dto.LentcarDTO;


@RestController
@MapperScan(basePackages="com.wonju.work.dao")
public class LentcarController {
	
	@Autowired
	private LentCarDAO lentcarDAO;
	
	@RequestMapping("/loaddata")
	public List<LentcarDTO> lentcar() throws Exception{
		
		System.out.println("왔다");
		LentcarDTO param = new LentcarDTO();
		List<LentcarDTO> lentCarList = lentcarDAO.selectLentCar(param);
		System.out.println(lentCarList);
		
		return lentCarList;
		
		
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
	public List<CityDTO> lentCarsInsert(@RequestParam(value="country", defaultValue ="")String name) throws Exception{
		//데이터 insert하기
		int page = 1;
		int pSize = 162;
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
		return null;
		
		
	}
}
