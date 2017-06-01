package com.example.ll.treelistdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Liuling on 2016/12/12.
 */
public class ContactsTreeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Node> all = new ArrayList<Node>();// 展示
    private List<Node> cache = new ArrayList<Node>();// 缓存
    private int expandIcon = -1;// 展开图标
    private int collapseIcon = -1;// 收缩图标

    private int deptnum = 0; // 某节点下子级部门数量
    private int personnum = 0; // 某节点下所有人员数量
    private int totalNum = 0; // 某节点下部门和人员总数量

    // -1不是选择人员界面、0单选、1多选
    protected int selecttype = -1;
    private ContactsTreeAdapter tree = this;
    private Node oldNode = null;
    boolean hasCheckBox;

    private Map<String, String> totalMap = new HashMap<String, String>();

    /**
     * 构造方法
     *
     * @param rootNodes 是关系上的树形，列表本身并不是树形
     */
    public ContactsTreeAdapter(Context _context, List<Node> rootNodes) {
        this.context = _context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < rootNodes.size(); i++) {
            addNode(rootNodes.get(i));
        }
    }

    /**
     * 构造方法  单选
     *
     * @param rootNodes 是关系上的树形，列表本身并不是树形
     */
    public ContactsTreeAdapter(Context _context, List<Node> rootNodes, int selecttype) {
        this.context = _context;
        this.selecttype = selecttype;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < rootNodes.size(); i++) {
            addNode(rootNodes.get(i));
        }
    }

    public void addNode(Node node) {
        all.add(node);
        cache.add(node);
        if (node.isLeaf()) {  // 若叶子是人，则人++
            if (node.getValue().equals("people")) {
                node.setIcon(1);
            }
            return;
        }
        for (int i = 0; i < node.getChildrens().size(); i++) {
            addNode(node.getChildrens().get(i));
            node.setPersonNums(node.getPersonNums() + node.getChildrens().get(i).getPersonNums());
        }
    }

    public void addNode(int l, int c, Node node) {
        all.add(l, node);
        cache.add(c, node);
        if (node.isLeaf()) {  // 若叶子是人，则人++
            if (node.getValue().equals("people")) {
                node.setIcon(1);
            }
            return;
        }
        for (int i = 0; i < node.getChildrens().size(); i++) {
            addNode(node.getChildrens().get(i));
            node.setPersonNums(node.getPersonNums() + node.getChildrens().get(i).getPersonNums());
        }
    }

    /**
     * 一次性对某节点的所有节点进行选中or取消操作
     */
    public void checkNode(Node n, boolean isChecked) {
        checkParentNode(n, isChecked);
        for (int i = 0; i < n.getChildrens().size(); i++) {
            checkNode((Node) n.getChildrens().get(i), isChecked);
        }
    }

    public void checkParentNode(Node n, boolean isChecked) {
        n.setChecked(isChecked);
        Node parent = n.getParent();
        if (parent != null) {
            if (isChecked) {
                checkParentNode(parent, true);
            } else {
                boolean checked = false;
                for (int i = 0; i < parent.getChildrens().size(); i++) {
                    Node child = parent.getChildrens().get(i);
                    if (child.isChecked()) {
                        checked = true;
                        break;
                    }
                }
                checkParentNode(parent, checked);
            }
        }
    }

    /**
     * 设置展开收缩图标
     *
     * @param expandIcon
     * @param collapseIcon
     */
    public void setCollapseAndExpandIcon(int expandIcon, int collapseIcon) {
        this.collapseIcon = collapseIcon;
        this.expandIcon = expandIcon;
    }

    /**
     * 设置展开等级
     *
     * @param level
     */
    public void setExpandLevel(int level) {
        all.clear();
        for (int i = 0; i < cache.size(); i++) {
            Node n = cache.get(i);
            if (n.getLevel() <= level) {
                if (n.getLevel() < level)
                    n.setExplaned(true);
                else
                    n.setExplaned(false);
                all.add(n);
            }
        }
    }

    /**
     * 控制展开缩放某节点
     *
     * @param location
     */
    public void ExpandOrCollapse(final int location) {
        final Node n = all.get(location);// 获得当前视图需要处理的节点
        if (n != null)// 排除传入参数错误异常
        {
            if (n.isGetChild()) {// 已经通过网络请求获取到孩子结点 只需要做展开操作
                n.setExplaned(!n.isExplaned());// 由于该方法是用来控制展开和收缩的，所以取反即可
                filterNode(n);//**************遍历一下，将所有上级节点展开的节点重新挂上去
                notifyDataSetChanged();// 刷新视图
            } else if (!n.isLeaf() || n.getValue().equals("dept")) {
                if (n.isExplaned()) {
                    n.setExplaned(!n.isExplaned());// 由于该方法是用来控制展开和收缩的，所以取反即可
                    filterNode(n);//**************遍历一下，将所有上级节点展开的节点重新挂上去
                    notifyDataSetChanged();// 刷新视图
                    return;
                }

                List<ContactTreeListOut> list = new ArrayList<ContactTreeListOut>();

                for (int i = 0; i < 10; i++) {
                    ContactTreeListOut contactTreeListOut = new ContactTreeListOut("" + i, "李四" + i, "1");
                    list.add(i, contactTreeListOut);
                }

                totalNum = list.size();
                totalMap.put(n.getitem_id(), totalNum + "");
                ArrayList<Node> roots = new ArrayList<Node>();  //根节点列表

                for (ContactTreeListOut ct : list) {
                    Node node = new Node(ct.getName(), "0".equals(ct.getIsNone()) ? "dept" : "people", n.getitem_id(), ct.getId(), -1, ct.getIsNone());
                    n.addNode(node);
                    node.setParent(n);
                    roots.add(node);
                }
                int l = all.indexOf(n);
                int c = cache.indexOf(n);
                for (int i = 0; i < roots.size(); i++) {
                    l++;
                    c++;
                    addNode(l, c, roots.get(i));
                }
                n.setExplaned(!n.isExplaned());// 由于该方法是用来控制展开和收缩的，所以取反即可
                filterNode(n);//**************遍历一下，将所有上级节点展开的节点重新挂上去
                notifyDataSetChanged();// 刷新视图
                n.setGetChild(true);// 设置此结点获取网络请求成功 下次无需获取
            }
            // ************ 如果是叶子节点（人员），则跳转详情
            else {
                Intent intent = new Intent(context, ContactsDetailsActivity.class);
                intent.putExtra("personid", n.getitem_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        }

    }


    /**
     * 设置是否有复选框
     *
     * @param hasCheckBox
     */
    public void setCheckBox(boolean hasCheckBox) {
        this.hasCheckBox = hasCheckBox;
    }

    /*
     * ******** 清理all,从缓存中将所有父节点不为收缩状态的都挂上去 */
    public void filterNode(Node node) {
        all.clear();
        for (int i = 0; i < cache.size(); i++) {
            Node n = cache.get(i);
            if (!n.isParentCollapsed() || n.isRoot()) {// 凡是父节点不收缩或者不是根节点的都挂上去
                if (n.getParent() == node) {     //********若需要展开某节点时，其下的所有节点也都展开，则注释本条件语句。反之，则使用本条件语句
                    n.setExplaned(false);
                }
                all.add(n);
            }
        }
    }

    @Override
    public int getCount() {
        return all.size();
    }

    @Override
    public Object getItem(int location) {
        return all.get(location);
    }

    @Override
    public long getItemId(int location) {
        return location;
    }


    @Override
    public View getView(final int location, View _view, ViewGroup viewgroup) {

        ViewItem viewItem = null;
        if (_view == null) {
            _view = layoutInflater.inflate(R.layout.tree_list_item_can_scroll, null);
            viewItem = new ViewItem();

            viewItem.flagIcon = (ImageView) _view.findViewById(R.id.ivec);
            viewItem.tv = (TextView) _view.findViewById(R.id.itemvalue); // 文字
            viewItem.icon = (ImageView) _view.findViewById(R.id.ivicon);
            viewItem.cb = (CheckBox) _view.findViewById(R.id.cb); // Checkbutton

            viewItem.deptnum = (TextView) _view
                    .findViewById(R.id.list_item_deptnum_tv);
            viewItem.personnum = (TextView) _view
                    .findViewById(R.id.list_item_personnum_tv);
            //侧滑
            viewItem.tv_add_contact = (TextView) _view.findViewById(R.id.tv_add_contact);

            viewItem.item_click = (RelativeLayout) _view.findViewById(R.id.item_click);

            viewItem.line = _view.findViewById(R.id.line);

            viewItem.cb.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (selecttype == 0) {   //单选
                        if (!((CheckBox) v).isChecked()) {  //取消选中

                            Node node1 = (Node) v.getTag();
                            checkNode(node1, ((CheckBox) v).isChecked());
                        } else {    //选中
                            if (oldNode != null) {
                                checkNode(oldNode, false);
                            }

                            Node node1 = (Node) v.getTag();
                            oldNode = node1;
                            checkNode(node1, ((CheckBox) v).isChecked());
                        }
                    } else {     //多选
                        Node node1 = (Node) v.getTag();
                        checkNode(node1, ((CheckBox) v).isChecked());
                        tree.notifyDataSetChanged();
                    }
                    tree.notifyDataSetChanged();
                }
            });
            _view.setTag(viewItem);
        } else {
            viewItem = (ViewItem) _view.getTag();
        }

        if (all.size() == location + 1) {
            viewItem.line.setVisibility(View.GONE);
        } else {
            viewItem.line.setVisibility(View.VISIBLE);
        }
        viewItem.item_click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处是判断点击列表时，列表展开或收缩
                ExpandOrCollapse(location);
            }
        });

        Node node2 = all.get(location);
        if (node2 != null) {
            viewItem.cb.setTag(node2);
            viewItem.cb.setChecked(node2.isChecked());
            // 叶节点不显示展开收缩图标
            if (node2.isLeaf() && node2.getValue().equals("people")) {
                viewItem.flagIcon.setVisibility(View.GONE);
            } else {
                viewItem.flagIcon.setVisibility(View.VISIBLE);
                if (node2.isExplaned()) {
                    if (expandIcon != -1) {
                        viewItem.flagIcon.setImageResource(expandIcon);
                    }
                } else {
                    if (collapseIcon != -1) {
                        viewItem.flagIcon.setImageResource(collapseIcon);
                    }
                }
            }
            if (node2.isLeaf() && node2.getValue().equals("people")) {
                viewItem.tv_add_contact.setVisibility(View.VISIBLE);
            } else {
                viewItem.tv_add_contact.setVisibility(View.GONE);
            }

            // 设置是否显示复选框  //**
            if (selecttype == 0 || selecttype == 1) {             //单选情况下，如果是叶子节点，则显示checkbox；不是叶子节点，不显示checkbox
                if (node2.getValue().equals("people")) {         //单选下，可以点击人，不能点击部门
                    viewItem.cb.setVisibility(View.VISIBLE);
                    viewItem.cb.setClickable(true);
                } else if (node2.getValue().equals("dept")) {
                    viewItem.cb.setVisibility(View.GONE);
                    viewItem.cb.setClickable(false);
                } else {
                    viewItem.cb.setVisibility(View.GONE);
                    viewItem.cb.setClickable(false);
                }
            } else {
                if (hasCheckBox) {
                    viewItem.cb.setVisibility(View.VISIBLE);
                } else {
                    viewItem.cb.setVisibility(View.GONE);
                }
            }

            // 设置是否显示头像图标
            if (node2.getIcon() != -1) {
                viewItem.icon.setVisibility(View.VISIBLE);
            } else {
                viewItem.icon.setVisibility(View.GONE);
            }
            // 显示文本
            viewItem.tv.setText(node2.getDeptkey());
            if (node2.getValue().equals("people")) {
                // 控制缩进
                _view.setPadding(50 + 20 * node2.getLevel(), 3, 3, 3);
            } else {
                // 控制缩进
                _view.setPadding(20 * node2.getLevel(), 3, 3, 3);
            }

            personnum = 0;
            deptnum = 0;
            getPresonNum(node2);

            // *****显示部门、人员数量
            if (node2.isLeaf()) {
                if (node2.getValue().equals("people")) {           //叶子节点是人
                    viewItem.deptnum.setVisibility(View.GONE);
                    viewItem.personnum.setVisibility(View.GONE);
                } else {                                           //叶子节点是部门
                    viewItem.deptnum.setVisibility(View.GONE);
                    viewItem.personnum.setVisibility(View.GONE);
                }
            } else {                                             //非叶子节点（只可能是部门）
                viewItem.deptnum.setVisibility(View.VISIBLE);
                viewItem.personnum.setVisibility(View.VISIBLE);
                viewItem.deptnum.setText("子部门(" + deptnum + ")"); // 某节点下子级部门数量
                viewItem.personnum.setText("人员(" + personnum + ")"); // 某节点下所有人员数量
            }
        }
        return _view;
    }


    // ****
    public void addNum(Node node) {
        if (node.isLeaf() && node.getValue().equals("people")) {
            personnum++;
            return;
        }
        deptnum++; // 列表中某节点下，子节点中，有几个部门（有子节点的就是部门）
        for (int i = 0; i < node.getChildrens().size(); i++) {
            getPresonNum(node.getChildrens().get(i));
        }
    }

    // ****获取该部门下所有人员数量
    private void getPresonNum(Node node2) {
        for (int i = 0; i < node2.getChildrens().size(); i++) {
            addNum((Node) node2.getChildrens().get(i));
        }
    }


    public class ViewItem {
        private CheckBox cb;
        private ImageView icon;
        private ImageView flagIcon;
        private TextView tv;
        // ****
        private TextView deptnum; // 部门数量
        private TextView personnum; // 人员数量

        private TextView tv_add_contact;
        private RelativeLayout item_click;
        private View line;
    }
}
