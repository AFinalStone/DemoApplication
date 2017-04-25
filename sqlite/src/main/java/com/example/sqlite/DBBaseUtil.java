package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.reflect.Field;

import java.util.List;
import java.util.Set;

/**
 * Created by yuanxiangbin on 2016/11/1.
 */
/**
 * @reqno:H1610190021
 * @date-designer:20161019-yuanxiangbin
 * @date-author:20161019-yuanxiangbin:代码优化
 */
public class DBBaseUtil extends SQLiteOpenHelper{
    private static final String DB_NAME = "android.db";
    private static final int DB_VERSION = 2;
    private static DBBaseUtil helper = null;

    /*** 所有的比对类型 */
    public static final Map<Class<?>, String> TYPES;
    private static List<Class<?>> model;
    static {
		/*-------------------初始化类型----------------*/
        TYPES = new HashMap<Class<?>, String>();
        TYPES.put(byte.class, "BYTE");
        TYPES.put(boolean.class, "INTEGER");
        TYPES.put(short.class, "SHORT");
        TYPES.put(int.class, "INTEGER");
        TYPES.put(long.class, "LONG");
        TYPES.put(String.class, "TEXT");
        TYPES.put(byte[].class, "BLOB");
        TYPES.put(float.class, "FLOAT"); // REAL
        TYPES.put(double.class, "DOUBLE"); // REAL
    }

    /**升级数据库**/
    public static Databases updateDB = null;
    public interface Databases {
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    }

    public static DBBaseUtil getDBBase(Context c, List<Class<?>> models, Databases db) {
        synchronized (DBBaseUtil.class) {
            model = models;
            if(db != null)
                updateDB = db;
            if (helper == null) {
                helper = new DBBaseUtil(c);
            }
        }

        return helper;
    }

    public static DBBaseUtil getDBBase(Context c) {
        synchronized (DBBaseUtil.class) {
            if (helper == null) {
                helper = new DBBaseUtil(c);
            }
        }

        return helper;
    }

    public DBBaseUtil(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = getWritableDatabase();
//		FileUtil.copyFile(db.getPath(), MobileUtil.getCacheSavePath() +"/"+ "mobileoa.db");/data/data/com.rc.mobile.oa.activity/databases/android.db/data/data/com.rc.mobile.oa.activity/databases/android.db
//		System.out.println(db.getPath());
    }

    public void cleanTable(String tablename, String where, String[] whereArgs) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(tablename, where, whereArgs);
        }
    }

    public synchronized void cleanAllTables() {
        try {
            synchronized (DBBaseUtil.class) {
                SQLiteDatabase db = getWritableDatabase();
                db.beginTransaction();// 通过事务

                if (model != null && model.size() > 0) {
                    String sql = "";
                    for (int i = 0; i < model.size(); i++) {
                        sql = model.get(i).getSimpleName();
                        db.delete(sql, null, null);
                    }
                }

                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(model,db);
    }

    public void createTable(List<Class<?>> model){
        if (model != null && model.size() > 0) {
            String sql = "";
            for (int i = 0; i < model.size(); i++) {
                sql = getTableBuildingSQL(model.get(i));
                execSQL(sql);
            }
        }
    }

    public void createTable(List<Class<?>> model,SQLiteDatabase db){
        if (model != null && model.size() > 0) {
            String sql = "";
            for (int i = 0; i < model.size(); i++) {
                sql = getTableBuildingSQL(model.get(i));
                db.execSQL(sql);
            }
        }
    }

    /** 根据类结构构造表。 */
    private String getTableBuildingSQL(Class<?> clazz) {
        StringBuilder strBuilder = new StringBuilder(
                "create table if not exists ");
        strBuilder.append(clazz.getSimpleName());
        strBuilder.append("(");
        // getDeclaredFields():只获取该类文件中声明的字段
        // getFields():获取该类文件、其父类、接口的声明字段
        Field[] arrField = clazz.getDeclaredFields();
        for (int i = arrField.length - 1; i >= 0; i--) {
            Field f = arrField[i];
            String type = TYPES.get(f.getType());
            if (type == null) {
                continue;
            } else {
                if (i != arrField.length - 1) {
                    strBuilder.append(",");
                }

                strBuilder.append("[" + f.getName() + "]" + " " + "[" + type
                        + "]");
                if (f.getName().equals("_id")) {
                    strBuilder.append(" PRIMARY KEY AUTOINCREMENT");
                }
            }
        }
        strBuilder.append(");");
        return strBuilder.toString();
    }

    public int insert(String tablename, ContentValues values) {
        int strid = 0;

        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues newValues = null;

            if (values != null) {
                newValues = new ContentValues();
                Set<Entry<String, Object>> set = values.valueSet();
                Iterator<Entry<String, Object>> it = set.iterator();

                while (it.hasNext()) {
                    Entry<String, Object> entry = it.next();
                    newValues.put("[" + entry.getKey() + "]", entry.getValue()
                            + "");
                }

                newValues.remove("[_id]");
            }

            db.insert(tablename, null, newValues);

            Cursor cursor = db.rawQuery("select last_insert_rowid() from "
                    + tablename, null);

            if (cursor.moveToFirst())
                strid = cursor.getInt(0);

            cursor.close();
        }

        return strid;
    }

    public void del(String tablename) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(tablename, null, null);
        }
    }

    public void del(String tablename, int id) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(tablename, "_id=?", new String[] { String.valueOf(id) });
        }
    }

    public void del(String tablename, String whereClause, String[] whereArgs) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(tablename, whereClause, whereArgs);
        }

    }

    public String[] createUpdateColumnSql(ContentValues values, StringBuffer sb) {
        int index = 0;
        String[] objs = new String[values.size()];

        Set<Entry<String, Object>> set = values.valueSet();
        Iterator<Entry<String, Object>> it = set.iterator();

        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (index == 0) {
                sb.append("[" + entry.getKey() + "] = ?");
            } else {
                sb.append(" ,  [" + entry.getKey() + "] = ?");
            }

            if (entry.getValue() == null)
                objs[index] = "";
            else
                objs[index] = entry.getValue().toString();
            index++;
        }

        return objs;
    }

    public String[] createDeleteColumnSql(ContentValues values, StringBuffer sb) {
        int index = 0;
        String[] objs = new String[values.size()];

        Set<Entry<String, Object>> set = values.valueSet();
        Iterator<Entry<String, Object>> it = set.iterator();

        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (index == 0) {
                sb.append("[" + entry.getKey() + "] = ?");
            } else {
                sb.append(" and  [" + entry.getKey() + "] = ?");
            }

            if (entry.getValue() == null)
                objs[index] = "";
            else
                objs[index] = entry.getValue().toString();
            index++;
        }

        return objs;
    }

    /**
     * 修改
     *
     * @author 王力杨
     */
    public void update(String tablename, ContentValues columns) {
        if (columns == null)
            return;

        StringBuffer cols = new StringBuffer();
        String[] objs = createUpdateColumnSql(columns, cols);

        String whe = "where _id = " + columns.get("_id");

        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("update " + tablename + " set " + cols.toString() + " "
                    + whe, objs);
        }
    }

    public void update(String tablename, ContentValues columns,
                       ContentValues wheres) {
        if (columns == null)
            return;

        StringBuffer cols = new StringBuffer();
        String[] objs = createUpdateColumnSql(columns, cols);

        StringBuffer where = new StringBuffer();
        String[] wherearray = createWhereColumnSql(wheres, where);

        List<String> listcols = new ArrayList<String>();
        if (objs != null) {
            for (String value : objs) {
                listcols.add(value);
            }
        }
        if (wherearray != null) {
            for (String value : wherearray) {
                listcols.add(value);
            }
        }

        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("update " + tablename + " set " + cols.toString() + " "
                    + where.toString(), listcols.toArray());
        }
    }

    public void update(String tablename, String cols, Object[] objs) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("update " + tablename + " set " + cols, objs);
        }

    }

    public String[] createWhereColumnSql(ContentValues values,
                                         StringBuffer where) {
        String[] objs = null;

        if (values != null && values.size() > 0) {
            where.append("where ");

            int index = 0;
            objs = new String[values.size()];

            Set<Entry<String, Object>> set = values.valueSet();
            Iterator<Entry<String, Object>> it = set.iterator();

            while (it.hasNext()) {
                Entry<String, Object> entry = it.next();
                if (index == 0) {
                    where.append("[" + entry.getKey() + "] = ? ");
                } else {
                    where.append(" and  [" + entry.getKey() + "] = ? ");
                }

                if (entry.getValue() == null)
                    objs[index] = "";
                else
                    objs[index] = entry.getValue().toString();
                index++;
            }
        }

        return objs;
    }

    public String[] createWhereLikeColumnSql(ContentValues values,
                                             StringBuffer where) {
        String[] objs = null;

        if (values != null && values.size() > 0) {
            where.append("where ");

            int index = 0;
            objs = new String[values.size()];

            Set<Entry<String, Object>> set = values.valueSet();
            Iterator<Entry<String, Object>> it = set.iterator();

            while (it.hasNext()) {
                Entry<String, Object> entry = it.next();
                if (index == 0) {
                    where.append("[" + entry.getKey() + "] like ? ");
                } else {
                    where.append(" and  [" + entry.getKey() + "] like ? ");
                }

                if (entry.getValue() == null)
                    objs[index] = "";
                else
                    objs[index] = entry.getValue().toString();
                index++;
            }
        }

        return objs;
    }

    /**
     * 查询
     *
     * @author 王力杨
     */
    public Cursor search(String tablename, ContentValues values,
                         String groupby, String orderby) {
        String sql = "select * from " + tablename;

        String group = "";
        String order = "";
        String[] objs = null;

        StringBuffer where = new StringBuffer();
        objs = createWhereColumnSql(values, where);

        if (groupby != null && !"".equals(groupby)) {
            group = " GROUP BY " + groupby;
        }

        if (orderby != null && !"".equals(orderby)) {
            order = " order by " + orderby;
        }

        sql = sql + " " + where.toString() + group + order;
        return findBySql(sql, objs);
    }

    /**
     * 自定义where查询
     *
     * @author 王力杨
     */
    public Cursor customWhereSearch(String tablename, String where,
                                    String groupby, String orderby) {
        String sql = "select * from " + tablename;

        String group = "";
        String order = "";

        if (groupby != null && !"".equals(groupby)) {
            group = " GROUP BY " + groupby;
        }

        if (orderby != null && !"".equals(orderby)) {
            order = " order by " + orderby;
        }

        sql = sql + " " + "where " + where + group + order;
        return findBySql(sql, null);
    }

    /**
     * 模糊查询
     *
     * @author 王力杨
     */
    public Cursor fuzzySearch(String tablename, ContentValues values,
                              String groupby, String orderby) {
        String sql = "select * from " + tablename;
        String group = "";
        String order = "";
        String[] objs = null;

        StringBuffer where = new StringBuffer();
        objs = createWhereLikeColumnSql(values, where);

        if (groupby != null && !"".equals(groupby)) {
            group = " GROUP BY " + groupby;
        }

        if (orderby != null && !"".equals(orderby)) {
            order = " order by " + orderby;
        }

        sql = sql + " " + where + group + order;
        return findBySql(sql, objs);
    }

    /**
     * 删除
     *
     * @author 王力杨
     */
    public void delete(String tablename, ContentValues values) {
        if (values == null) {// 删除全部
            del(tablename);
        } else {// 有Where条件
            StringBuffer cols = new StringBuffer();
            String[] objs = createDeleteColumnSql(values, cols);

            del(tablename, cols.toString(), objs);
        }
    }

    public Cursor qurey(boolean distinct, String tablename, String[] columns,
                        String sel, String[] selArgs, String groupby, String orderby) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(distinct, tablename, columns, sel,
                    selArgs, groupby, null, orderby, null);

            return cursor;
        }
    }

    public Cursor findAll(String tablename) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(tablename, null, null, null, null, null,
                    null);

            return cursor;
        }

    }

    public Cursor find(String tablename, int id) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + tablename
                    + " where _id = ?", new String[] { String.valueOf(id) });

            return cursor;
        }
    }

    public Cursor findBySql(String sql, String[] args) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, args);
            return cursor;
        }
    }

    /**
     * 分页查询数据
     *
     * @param start
     *            分页开始记录数
     * @param end
     *            分页结束记录数
     * @return 查询结果集
     */
    public Cursor page(String tablename, int start, int end) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db
                    .rawQuery(
                            "select * from " + tablename
                                    + " order by _id limit ?,?",
                            new String[] { String.valueOf(start),
                                    String.valueOf(end) });

            return cursor;
        }

    }

    /**
     * 获取表记录总数
     *
     * @return
     */
    public long getCount(String tablename) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cur = db.rawQuery("select count(*) from " + tablename, null);
            cur.moveToFirst();

            long count = cur.getLong(0);

            cur.close();

            return count;
        }
    }

    public long getCount(String tablename, String where, String[] whereArgs) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cur = db.rawQuery("select count(*) from " + tablename
                    + " where " + where, whereArgs);
            cur.moveToFirst();

            long count = cur.getLong(0);

            cur.close();

            return count;
        }
    }

    public long getCount(SQLiteDatabase db, String tablename, String where,
                         String[] whereArgs) {
        Cursor cur = db.rawQuery("select count(*) from " + tablename
                + " where " + where, whereArgs);
        cur.moveToFirst();

        long count = cur.getLong(0);

        cur.close();

        return count;
    }

    public boolean exist(String tablename, String where, String[] whereArgs) {
        return getCount(tablename, where, whereArgs) > 0;
    }

    public boolean exist(String tablename, String idname, String idvalue) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cur = db.rawQuery("select count(*) from " + tablename
                    + " where " + idname + " = ?", new String[] { idvalue });
            cur.moveToFirst();

            long count = cur.getLong(0);

            cur.close();

            return count > 0;
        }
    }

    /**
     * 判断某张表是否存在
     *
     * @param tabName
     *            表名
     * @return
     */
    public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
                    + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 执行事务
     */
    public void transaction(List<String> sqls) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            for (int i = 0; i < sqls.size(); i++) {
                String sql = sqls.get(i);
                db.execSQL(sql);
            }

            db.setTransactionSuccessful();
            // 事务默认有commit、rollback，默认为False，即非提交状态，需要设置为commit
        } finally {
            db.endTransaction();
        }

    }

    public SQLiteDatabase beginTransaction() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        return db;
    }

    public void endTransaction(SQLiteDatabase db) {
        try {
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void execSQL(String sql) {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
        }
    }

    public void close() {
        synchronized (DBBaseUtil.class) {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null)
                db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(updateDB != null){
            updateDB.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
