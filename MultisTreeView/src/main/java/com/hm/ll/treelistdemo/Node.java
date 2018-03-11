package com.hm.ll.treelistdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuling on 2016/12/12.
 */

public class Node {

    //**接下来这几个字段是与服务器返回数据相关，若需要用本控件，需要对应调整这几个字段及其结构
    private String parentkey = null;  //父节点id
    private String item_id = null;     //自己id
    private String simpleName;//节点显示文字

    private int personNums;//子节点中人员数量

    private Node parent = null; // 父节点对象
    private List<Node> childrens = new ArrayList<Node>(); //子节点对象列表

    //后面这几个字段是为了客户端逻辑所增加，与服务器返回数据无关
    private String value;//节点显示值
    private int icon = -1; //icon(R.drawable的id)
    private boolean isChecked = false; //是否被选中
    private boolean isExpand = true;//是否处于扩展状态
    private boolean hasCheckBox = true;//是否有复选框
    private boolean isGetChild = false;// 是否通过网络获取到孩子结点
    private String isNone;

    public int getPersonNums() {
        return personNums;
    }

    public void setPersonNums(int personNums) {
        this.personNums = personNums;
    }

    /**
     * 设置节点值
     *
     * @param parentkey 父节点id
     * @param item_id   自己id
     * @param isNone    是否是部门，1是/0否
     */
    public Node(String simlpleName, String value, String parentkey, String item_id, int iconId, String isNone) {
        this.simpleName = simlpleName;
        this.value = value;
        this.parentkey = parentkey;
        this.icon = iconId;
        this.item_id = item_id;
        this.isNone = isNone;
    }


    public boolean isGetChild() {
        return isGetChild;
    }

    public void setGetChild(boolean isGetChild) {
        this.isGetChild = isGetChild;
    }

    public String getIsNone() {
        return isNone;
    }

    public void setIsNone(String isNone) {
        this.isNone = isNone;
    }

    /**
     * 得到父节点
     *
     * @return
     */
    public Node getParent() {
        return parent;
    }

    /**
     * 设置父节点
     *
     * @param parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 得到子节点
     *
     * @return
     */
    public List<Node> getChildrens() {
        return childrens;
    }

    /**
     * 是否根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null ? true : false;
    }

    /**
     * 是否隐藏图标
     *
     * @return
     */
    public int getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * 是否被选中
     *
     * @return
     */
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    /**
     * 是否是展开状态
     *
     * @return
     */
    public boolean isExplaned() {
        return isExpand;
    }

    /**
     * 设置展开状态
     *
     * @param isExplaned
     */
    public void setExplaned(boolean isExplaned) {
        this.isExpand = isExplaned;
    }

    /**
     * 是否有复选框
     *
     * @return
     */
    public boolean hasCheckBox() {
        return hasCheckBox;
    }

    /**
     * 设置是否有复选框
     *
     * @param hasCheckBox
     */
    public void setHasCheckBox(boolean hasCheckBox) {
        this.hasCheckBox = hasCheckBox;
    }

    /**
     * 得到节点标题
     *
     * @return
     */
    public String getDeptkey() {
        return simpleName;
    }


    /**
     * 得到节点值
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置节点值
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 增加一个子节点
     *
     * @param node
     */
    public void addNode(Node node) {
        if (!childrens.contains(node)) {
            childrens.add(node);
        }
    }

    /**
     * 移除一个子节点
     *
     * @param node
     */
    public void removeNode(Node node) {
        if (childrens.contains(node))
            childrens.remove(node);
    }

    /**
     * 移除指定位置的子节点
     *
     * @param location
     */
    public void removeNode(int location) {
        childrens.remove(location);
    }

    /**
     * 清除所有子节点
     */
    public void clears() {
        childrens.clear();
    }

    /**
     * 判断给出的节点是否当前节点的父节点
     *
     * @param node
     * @return
     */
    public boolean isParent(Node node) {
        if (parent == null) return false;
        if (parent.equals(node)) return true;
        return parent.isParent(node);
    }

    /**
     * 递归获取当前节点级别
     *
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 父节点是否处于折叠的状态
     *
     * @return
     */
    public boolean isParentCollapsed() {
        if (parent == null) return false;
        if (!parent.isExplaned()) return true;
        return parent.isParentCollapsed();
    }

    /**
     * 是否叶节点（没有展开下级的几点）
     *
     * @return
     */
    public boolean isLeaf() {
        return childrens.size() < 1 ? true : false;
    }

    /**
     * 返回自己的id
     *
     * @return
     **/
    public String getitem_id() {
        return item_id;
    }

    /**
     * 返回的父id
     *
     * @return
     **/
    public String getParentkey() {
        return parentkey;
    }
}
