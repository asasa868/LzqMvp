package com.shuyi.lzqmvp.greenDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * created by Lzq
 * on 2021/2/26 0026
 * Describe ：
 */
public class DbController {


    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private GreenDaoBeanDao getPersonInforDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        getPersonInforDao = mDaoSession.getGreenDaoBeanDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "person.db", null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param personInfor
     */
    public void insertOrReplace(GreenDaoBean personInfor) {
        getPersonInforDao.insertOrReplace(personInfor);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param personInfor
     */
    public long insert(GreenDaoBean personInfor) {
        return getPersonInforDao.insert(personInfor);
    }

    /**
     * 更新数据
     *
     * @param personInfor
     */
    public void update(GreenDaoBean personInfor) {
        GreenDaoBean mOldPersonInfor = getPersonInforDao.queryBuilder().where(GreenDaoBeanDao.Properties.Id.eq(personInfor.getId())).build().unique();//拿到之前的记录
        if (mOldPersonInfor != null) {
            mOldPersonInfor.setName("张三");
            getPersonInforDao.update(mOldPersonInfor);
        }
    }

    /**
     * 按条件查询数据
     */
    public List<GreenDaoBean> searchByWhere(String wherecluse) {
        List<GreenDaoBean> personInfors = (List<GreenDaoBean>) getPersonInforDao.queryBuilder().where(GreenDaoBeanDao.Properties.Name.eq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 查询所有数据
     */
    public List<GreenDaoBean> searchAll() {
        List<GreenDaoBean> personInfors = getPersonInforDao.queryBuilder().list();
        return personInfors;
    }

    /**
     * 删除数据
     */
    public void delete(String wherecluse) {
        getPersonInforDao.queryBuilder().where(GreenDaoBeanDao.Properties.Name.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

}

//用法

//  mDbController = DbController.getInstance(this);
//private void similateData() {
//    personInfor1 = new PersonInfor(null,"001","王大宝","男");
//    personInfor2 = new PersonInfor(null,"002","李晓丽","女");
//    personInfor3 = new PersonInfor(null,"003","王麻麻","男");
//    personInfor4 = new PersonInfor(null,"004","王大锤","女");
//}
//
//    private void Envent() {
//
//        Add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Add
//                mDbController.insertOrReplace(personInfor1);
//
//                mDbController.insertOrReplace(personInfor2);
//
//                mDbController.insertOrReplace(personInfor3);
//
//                mDbController.insertOrReplace(personInfor4);
//
//                showDataList();
//            }
//        });
//        Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Delete
//                mDbController.delete("王麻麻");
//
//                showDataList();
//            }
//        });
//
//        Update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Update
//                mDbController.update(personInfor1);
//
//                showDataList();
//            }
//        });
//
//        Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Search
//                showDataList();
//            }
//        });
//    }
//
//    private void showDataList() {
//        StringBuilder sb = new StringBuilder();
//        List<PersonInfor>personInfors = mDbController.searchAll();
//        for(PersonInfor personInfor:personInfors){
//            // dataArea.setText("id:"+p);
//            sb.append("id:").append(personInfor.getId())
//                    .append("perNo:").append(personInfor.getPerNo())
//                    .append("name:").append(personInfor.getName())
//                    .append("sex:").append(personInfor.getSex())
//                    .append("\n");
//        }
//        dataArea.setText(sb.toString());
//    }
