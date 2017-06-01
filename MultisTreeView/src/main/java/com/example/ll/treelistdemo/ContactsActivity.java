package com.example.ll.treelistdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Liuling on 2016/12/12.
 */

public class ContactsActivity extends AppCompatActivity {

    private static final String TAG = "ContactsActivity";

    private Context context = this;
    private ListView listview;
    // -1不是选择人员界面、0单选、1多选
    protected int selecttype = 0;
    // 控制复选框
    protected boolean ckflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_act);
        initUI();
        initData();
    }

    private void initUI() {
        listview = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
        List<ContactTreeListOut> list = new ArrayList<ContactTreeListOut>();
        for (int i = 0; i < 10; i++) {
            ContactTreeListOut contactTreeListOut = new ContactTreeListOut("" + i, "张三" + i, "0");
            list.add(i, contactTreeListOut);
        }
        initTree(list);
    }

    ContactsTreeAdapter treeAdapter = null;

    private void initTree(List<ContactTreeListOut> res) {
        //**此处代码传值根据需要自定义
        initNode(context, initNodeRoot(res), ckflag, -1, -1, 0);
    }

    /**
     * @param context     响应监听的上下文
     * @param root        已经挂好树的根节点（此时列表结构还不是树形结构，但关系是树形结构）
     * @param tree_ex_id  展开图标的iconid：值为-1是使用默认的（箭头）,可传自定的iconid
     * @param tree_ec_id  收缩图标的iconid：值为-1是使用默认的（箭头）,可传自定的iconid
     * @param expandLevel 初始展开等级，例：0表示只展示到根节点，1表示展示到子节点，2表示展示到孙节点，依此类推...
     */
    public void initNode(Context context, List<Node> root, boolean hasCheckBox, int tree_ex_id, final int tree_ec_id, int expandLevel) {
        if (selecttype == 0 || selecttype == 1) {   //单选
            treeAdapter = new ContactsTreeAdapter(context, root, selecttype);
        } else {            //多选
            treeAdapter = new ContactsTreeAdapter(context, root);
        }
        // 设置整个树是否显示复选框
        treeAdapter.setCheckBox(hasCheckBox);
        // 设置展开和折叠时图标(-1是用默认的箭头)
        int tree_ex_id_ = (tree_ex_id == -1) ? R.mipmap.down : tree_ex_id;
        int tree_ec_id_ = (tree_ec_id == -1) ? R.mipmap.right : tree_ec_id;
        treeAdapter.setCollapseAndExpandIcon(tree_ex_id_, tree_ec_id_);
        // 设置默认展开级别
        treeAdapter.setExpandLevel(expandLevel);
        //为列表配置adapter
        listview.setAdapter(treeAdapter);
    }

    /**
     * 第一次的数据源
     *
     * @param res
     * @return
     */
    public List<Node> initNodeRoot(List<ContactTreeListOut> res) {  //核心代码
        ArrayList<Node> roots = new ArrayList<Node>();  //根节点列表
        for (ContactTreeListOut ct : res) {
            Node node = new Node(ct.getName(), "0".equals(ct.getIsNone()) ? "dept" : "people", "", ct.getId(), -1, ct.getIsNone());
            roots.add(node);
        }
        return roots;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
