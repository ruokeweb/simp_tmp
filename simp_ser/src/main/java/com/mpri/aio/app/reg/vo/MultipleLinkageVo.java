package com.mpri.aio.app.reg.vo;

import java.util.List;

/**
 * @DESC 四级联动数据
 * @author syp
 *
 */
public class MultipleLinkageVo  implements Comparable<MultipleLinkageVo>{

	private String id;
	private String parentId;
	private String name;
	private List<MultipleLinkageVo>  childs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MultipleLinkageVo> getChilds() {
		return childs;
	}
	public void setChilds(List<MultipleLinkageVo> childs) {
		this.childs = childs;
	}
	public MultipleLinkageVo(String id, String parentId, String name) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}
	public MultipleLinkageVo() {
		
	}

	@Override
	public int compareTo(MultipleLinkageVo o) {
		return this.id.compareTo(o.getId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultipleLinkageVo other = (MultipleLinkageVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
