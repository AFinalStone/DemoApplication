package com.example.ll.treelistdemo;

import java.io.Serializable;

/**
 * @reqno:H1612210007
 * @date-designer:20161222-liuling
 * @date-author:20161222-liuling:根据UI图完成树形通讯录设计，实现各层级数据实时获取，并且人员可以实现左滑添加常用联系人操作；常用联系人可以实现左滑取消常用联系人；然后实现其他模块可以调用通讯录获取相关数据，支持搜索。
 */
public class ContactTreeListOut implements Serializable {
	//主键id
	private String id;
	//名称
	private String name;
	//是否是部门，0是/1否
	private String isNone;

	public ContactTreeListOut(String id, String name, String isNone) {
		this.id = id;
		this.name = name;
		this.isNone = isNone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsNone() {
		return isNone;
	}

	public void setIsNone(String isNone) {
		this.isNone = isNone;
	}

}