package com.zz.eshuo.as30;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.afa.tourism.greendao.gen.UserDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GreenDaoDemo extends AppCompatActivity {

    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_age)
    EditText etAge;
    @InjectView(R.id.et_sex)
    EditText etSex;
    @InjectView(R.id.et_salary)
    EditText etSalary;
    @InjectView(R.id.bt_in)
    Button btIn;
    @InjectView(R.id.bt_de)
    Button btDe;
    @InjectView(R.id.bt_up)
    Button btUp;
    @InjectView(R.id.bt_qu)
    Button btQu;
    @InjectView(R.id.bt_all)
    Button btAll;
    @InjectView(R.id.lv_view)
    ListView lvView;
    @InjectView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    @InjectView(R.id.tv_info)
    TextView tvInfo;
    @InjectView(R.id.rv_view)
    RecyclerView mRecyclerView;

    private UserDao dao;
    private String name;
    private String age;
    private String sex;
    private String salary;
    private DaoSession mDaoSession;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        ButterKnife.inject(this);

        setDatabase();

        dao = mDaoSession.getUserDao();

//        dao.insertOrReplace(new User());

//        setupDatabase();

//        String sql = "insert into user values (null,'111')";
//        AFaApplication.getApplication().getDaoSession().getDatabase().execSQL(sql);


////设置布局管理器
//        RecyclerView.LayoutManager layout = null;
//        mRecyclerView.setLayoutManager(layout);
////设置adapter
//        mRecyclerView.setAdapter(adapter);
////设置Item增加、移除动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
////添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                this, DividerItemDecoration.HORIZONTAL_LIST));

        initData();
        initView();

    }

    private void initData() {
        data = getData();
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter();
    }

    private void initView() {

        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 实例化展示的view
            View v = LayoutInflater.from(GreenDaoDemo.this).inflate(R.layout.item_rv, parent, false);
            // 实例化viewholder
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 绑定数据
            holder.mTv.setText(data.get(position));
        }

        public void remove(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }

        public void add(String text, int position) {
            data.add(position, text);
            notifyItemInserted(position);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(R.id.item_tv);
            }
        }
    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }


    private static DaoSession daoSession;

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }


    //增
    private void insertData() {
        name = etName.getText().toString().trim();
        age = etAge.getText().toString();
        sex = etSex.getText().toString().trim();
        salary = etSalary.getText().toString().trim();
        User insertData = new User(null, name, age, sex, salary);
        dao.insert(insertData);
    }


    //删
    private void deleteDate(int id) {
        dao.deleteByKey((long) id);
    }

    //改
    private void updateData(int i) {
        name = etName.getText().toString().trim();
        age = etAge.getText().toString();
        sex = etSex.getText().toString().trim();
        salary = etSalary.getText().toString().trim();
        User updateData = new User((long) 1, name, age, sex, salary);
        dao.update(updateData);
    }


    //查
    private void queryData() {
        List<User> users = dao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
//            userName += users.get(i).getName() + ",";

            userName += users.get(i).toString();
        }
        tvInfo.setText(userName);
        Toast.makeText(this, "查询全部数据==>" + userName, Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.bt_in, R.id.bt_de, R.id.bt_up, R.id.bt_qu, R.id.bt_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_in:
//                insertData();
                data.add(0, "www.lyf.xyz");
                mAdapter.notifyItemChanged(0);
                mRecyclerView.scrollToPosition(0);
                mAdapter.notifyDataSetChanged();

                break;
            case R.id.bt_de:
//                deleteDate(1);
                data.remove(0);
                mAdapter.notifyItemChanged(0);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_up:
//                updateData(1);
                mAdapter.notifyItemMoved(1,2);
                break;
            case R.id.bt_qu:
                queryData();
                break;
            case R.id.bt_all:
                break;
            default:
                break;
        }
    }
}
