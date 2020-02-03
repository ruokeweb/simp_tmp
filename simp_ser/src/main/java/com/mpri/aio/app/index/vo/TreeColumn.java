package com.mpri.aio.app.index.vo;

/**
 * 栏目
 */
public class TreeColumn {

	private String name;//栏目名称
	
	private int treeid;//栏目id
	
	private int viewid;//组件id
	
	private int owner;//站点ID 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getViewid() {
		return viewid;
	}

	public void setViewid(int viewid) {
		this.viewid = viewid;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getTreeid() {
		return treeid;
	}

	public void setTreeid(int treeid) {
		this.treeid = treeid;
	}
	
	
}
