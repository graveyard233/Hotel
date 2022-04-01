package com.example.hotel.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Data;
import com.example.hotel.Bean.Person;
import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Mine.MineFragment;
import com.example.hotel.UI.Order.OrderFragment;
import com.example.hotel.UI.Room.RoomFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

//import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment[] fragments;

    private int lastFragmentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Hotel);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        link();
        Bmob.initialize(this,"f6017516ea38b947a8214fa98dbec40f");
//        insert();
//        searchById();
//        updateById();
//        deleteById();
//        searchAll();
//        searchByCondition();
        queryByBql();
    }

    private void queryByBql() {//注意，不能用模糊查询
        String bql = "select * from Person where name = '刘粤鼎'";
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();

        bmobQuery.setSQL(bql);
        bmobQuery.doSQLQuery(new SQLQueryListener<Person>() {
            @Override
            public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
                if(e ==null){
                    List<Person> list = (List<Person>) bmobQueryResult.getResults();
                    if(list!=null && list.size()>0){
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("search by sql", "name: " + list.get(i).getName());
                        }
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }


    private void searchByCondition() {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//        bmobQuery.addWhereEqualTo("name","刘粤");//相等的
//        bmobQuery.addWhereNotEqualTo("name","刘粤");//不等的
        String[] names = {"刘粤鼎","lucky"};
        bmobQuery.addWhereContainedIn("name", Arrays.asList(names));//多项查询
//        Date cad = new Date();
//        String createAt = "2022-04-02 00:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            cad = sdf.parse(createAt);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        BmobDate bmobDate = new BmobDate(cad);
//        bmobQuery.addWhereLessThan("createdAt",bmobDate);//查询某时间之间创建的数据

        bmobQuery.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if (!list.equals("[]")) {
//                    Log.i("TAG", "done: " + list.get(0).getAddress());
//                    Log.i("TAG", "done: " + list.get(0).getName());
                    for (int i = 0; i < list.size(); i++) {
                        Log.i("TAG", "done: " + list.get(i).getAddress());
                    }
                } else {
                    Log.e("BMOB", "error");
                }
            }
        });
    }

    private void searchAll() {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if (e == null) {
                    Log.i("TAG", "done: " + list.get(0).getAddress());
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    private void deleteById() {
        Person p2 = new Person();
        p2.setObjectId("6269230407");
        p2.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("TAG", "done: "+p2.getUpdatedAt());
                }else{
                    Log.e("TAG", "删除失败," + e.getMessage());
                }
            }

        });
    }

    private void updateById() {
        Person p2 = new Person();
        p2.setAddress("北京zy");
        p2.update("6269230407", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("TAG", "done: " + p2.getAddress());
                }else{
                    Log.e("TAG", "更新失败," + e.getMessage());
                }
            }

        });
    }

    private void searchById() {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject("c88e4286d1", new QueryListener<Person>() {
            @Override
            public void done(Person person, BmobException e) {
                if (e == null){
                    Log.i("TAG", "done: " + person.getAddress());
                }else {
                    Log.i("TAG", "done: 查询失败," + e.getMessage());
                }
            }
        });
    }

    private void insert() {
        Person p2 = new Person();
        p2.setName("刘粤鼎");
        p2.setAddress("广州黄埔");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initViews() {
        BottomNavigationView bottomNavigationView =
                findViewById(R.id.main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);



        fragments = new Fragment[] {
                new RoomFragment(),
                new OrderFragment(),
                new MineFragment()
        };

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame,fragments[0])
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.bottom_room:
                switchFragment(0);
                break;
            case R.id.bottom_order:
                switchFragment(1);
                break;
            case R.id.bottom_mine:
                switchFragment(2);
                break;

        }
        return false;
    }

    private void switchFragment(int to){
        if (lastFragmentIndex == to){
            return;
        }
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        if (!fragments[to].isAdded()){//没添加过就添加进来
            fragmentTransaction.add(R.id.main_frame,fragments[to]);
        } else {//添加过就显示
            fragmentTransaction.show(fragments[to]);
        }
        fragmentTransaction.hide(fragments[lastFragmentIndex])
                .commitAllowingStateLoss();

        lastFragmentIndex = to;
    }

    private void link() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("haha");
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("https://tenapi.cn/wether/?city=广州")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        Retrofit retrofit;
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://tenapi.cn")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RoomService roomService = retrofit.create(RoomService.class);
//        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
//        roomService.getForecast().enqueue(new Callback<BaseBean<Data>>() {
//            @Override
//            public void onResponse(Call<BaseBean<Data>> call, Response<BaseBean<Data>> response) {
//                Log.i("TAG", "onResponse: " + response.body().getData());
//            }
//
//            @Override
//            public void onFailure(Call<BaseBean<Data>> call, Throwable t) {
//
//            }
//        });

//        roomService.getData().subscribe(new Consumer<BaseBean<Data>>() {
//            @Override
//            public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
//                System.out.println(dataBaseBean.toString());
//            }
//        });
    }


}