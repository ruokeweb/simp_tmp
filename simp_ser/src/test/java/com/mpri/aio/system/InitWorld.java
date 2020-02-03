package com.mpri.aio.system;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.utils.PinyinUtil;
import com.mpri.aio.common.utils.PinyinUtil.Type;
import com.mpri.aio.system.mapper.SysAreaMapper;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.service.SysAreaService;

public class InitWorld extends ApplicationTests {
	@Autowired
	SysAreaMapper sysAreaMapper;

	@Autowired
	SysAreaService sysAreaService;

	/**
	 * 初始化数据
	 */
	@Test
	public void addWorldArea() {

		try {
			File f = new File("d:/LocList.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("CountryRegion");
			
			
			PinyinUtil pu=new PinyinUtil();
			
			System.out.println(nl.getLength());
			
			for (int i = 0; i < nl.getLength(); i++) {
				
				Node countryRegion = nl.item(i);
				// 获取国家节点数据
				String valueCountry = countryRegion.getAttributes().getNamedItem("Name").getNodeValue();
				
				//save country data
				SysArea area_country=new SysArea();
				String id_country=getId(i)+"000000";
				area_country.setId(id_country);
				area_country.setParentId("0");
				area_country.setName(valueCountry);
				area_country.setPinyin(pu.getFirstSpell(valueCountry));
				area_country.setSort(Long.valueOf(id_country));
				area_country.setCode(pu.toPinYin(valueCountry, "", Type.LOWERCASE));
				area_country.setType("COUNTRY");
				area_country.setCreateDate(new Date());
				area_country.setFlag("NORMAL");
				sysAreaMapper.insert(area_country);
				
				// 国家节点数据与简拼
				// System.out.println("属性："+value+"-"+PinyinUtil.getFirstSpell(value));
				// 获取省节点
				NodeList StateList = countryRegion.getChildNodes();
				for (int j = 0; j < StateList.getLength(); j++) {
					Node ndState = StateList.item(j);

					if (ndState.getNodeType() == Node.ELEMENT_NODE) {
						String valueState = valueCountry;
						if (ndState.getAttributes().getLength() > 0) {
							// 获取省节点数据
							valueState = ndState.getAttributes().getNamedItem("Name").getNodeValue();

						}
						//save province data
						SysArea area_province=new SysArea();
						String id_province=getId(i)+getId(j)+"000";
						area_province.setId(id_province);
						area_province.setParentId(id_country);
						area_province.setName(valueState);
						area_province.setPinyin(pu.getFirstSpell(valueState));
						area_province.setSort(Long.valueOf(id_country));
						area_province.setCode(pu.toPinYin(valueState, "", Type.LOWERCASE));
						area_province.setType("PROVINCE");
						area_province.setCreateDate(new Date());
						area_province.setFlag("NORMAL");
						sysAreaMapper.insert(area_province);

						// 获取市级节点
						NodeList CityList = ndState.getChildNodes();
						for (int k = 0; k < CityList.getLength(); k++) {
							Node ndCity = CityList.item(k);

							if (ndCity.getNodeType() == Node.ELEMENT_NODE) {
								String valueCity = valueState;
								if (ndCity.getAttributes().getLength() > 0) {
									// 获取省节点数据
									valueCity = ndCity.getAttributes().getNamedItem("Name").getNodeValue();

								}
								
								//save city data
								SysArea area_city=new SysArea();
								String id_city=getId(i)+getId(j)+getId(k);
								area_city.setId(id_city);
								area_city.setParentId(id_province);
								area_city.setName(valueCity);
								area_city.setPinyin(pu.getFirstSpell(valueCity));
								area_city.setSort(Long.valueOf(id_country));
								area_city.setCode(pu.toPinYin(valueCity, "", Type.LOWERCASE));
								area_city.setType("CITY");
								area_city.setCreateDate(new Date());
								area_city.setFlag("NORMAL");
								sysAreaMapper.insert(area_city);

							}
						}
					}

				}

			}
		} catch (Exception e) {
			//// ex.printStackTrace();;
		}
	}
	
	
	//获取九位id
	public String getId(int i) {
		if (i < 10) {
			return ("00" + i);
	      } else if (i >= 100) {
	        return String.valueOf(i);
	      } else
	        return ("0" + i);

	}

}
