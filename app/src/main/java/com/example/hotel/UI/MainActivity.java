package com.example.hotel.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel.Bean.Announcement;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Person;
import com.example.hotel.Bean.Room;
import com.example.hotel.Bean.Traveller;
import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Mine.MineFragment;
import com.example.hotel.UI.Order.OrderFragment;
import com.example.hotel.UI.Room.RoomFragment;
import com.example.hotel.UI.Room.RoomModel;
import com.example.hotel.UI.Room.RoomPresenter;
import com.example.hotel.UI.Room.RoomViewInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

//import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    private Fragment[] fragments;

    private int lastFragmentIndex = 0;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Hotel);
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplicationContext(),"f6017516ea38b947a8214fa98dbec40f");
        mContext = getApplicationContext();
//        try {
//
////            roomModel.testData();
//            RoomPresenter roomPresenter = new RoomPresenter();
//            roomPresenter.getRoomsPresenter(new RoomViewInterface() {
//                @Override
//                public void getRoomsSucceed(List<Room> rooms) {
//                    System.out.println("room's size = " + rooms.size());
//                    EditText editText = findViewById(R.id.edit_query);
//                    editText.setHint(rooms.get(1).getType());
//
//                }
//
//                @Override
//                public void getRoomError() {
//                    System.out.println("getRoomError!");
//                }
//
//
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        setContentView(R.layout.activity_main);
//        link();
//        Bmob.initialize(getApplicationContext(),"f6017516ea38b947a8214fa98dbec40f");
//        insert();
//        searchById();
//        updateById();
//        deleteById();
//        searchAll();
//        searchByCondition();
//        queryByBql();
//        signUp();
//        login();
//        getMsg();
//        isLogin1();
//        addRoom();
//        addOrder();
//        addPA();
    }

    private void addPA() {
        Announcement announcement = new Announcement("第一个公告","这是我第一次创建公告");
        announcement.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.i("TAG", "添加公告成功，返回objectId为：" + s);
                }else {
                    Log.e("TAG", "创建公告失败：" + e.getMessage());
                }
            }
        });
    }


    private void addOrder(){
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            List<Traveller> list = new ArrayList<>();
            list.add(new Traveller(user.getUsername(),user.getIDcard()));
            list.add(new Traveller("杨桓","123"));
            Order order = new Order();
            order.setUser(user);
            order.setRoomId("201");

            Date start = new Date();
            Date end = new Date();
            String startAt = "2022-04-05 12:30:00";
            String endAt = "2022-04-07 09:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                start = sdf.parse(startAt);
                end = sdf.parse(endAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            order.setStartTime(new BmobDate(start));
            order.setEndTime(new BmobDate(end));
            order.setPrice(150.0);
            order.setIsPay(0);
            order.setUserMassage("希望能提供多一个枕头.");
            order.setMsgId(user.getObjectId());
            order.setTravellerList(list);
            order.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.i("TAG", "添加订单成功，返回objectId为：" + s);
                    }else {
                        Log.e("TAG", "创建订单失败：" + e.getMessage());
                    }
                }
            });
        }
        else {
            Log.e("TAG", "addOrder: 尚未登录");
        }
    }

    private void addRoom() {
        List<String> list = new ArrayList<>();
        list.add("隔音好");
        list.add("这跟双人房有什么区别");
        list.add("就这还贵25块？");
        Room room = new Room("401","情侣双人房",175.0,
                "空闲",1.0,4,list);
        room.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.i("TAG", "添加房间成功，返回objectId为：" + s);
                }else {
                    Log.e("TAG", "创建房间失败：" + e.getMessage());
                }
            }
        });
    }

    private void getMsg(){
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.i("TAG", "获取控制台最新数据成功：" + s);
                }else {
                    Log.e("TAG", "获取控制台最新数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void isLogin1(){
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            Log.i("TAG", "isLogin1: 已经登录，" + user.getUsername());
            BmobUser.logOut();
        }
        else {
            Log.e("TAG", "isLogin1: 尚未登录");
        }
    }

    //登录
    private void login(){
//        final User user = new User();
//        //此处替换为你的用户名
//        user.setUsername("刘粤鼎");
//        //此处替换为你的密码
//        user.setPassword("123");
//        user.login(new SaveListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null){
//                    Log.i("TAG", "登录成功:" + user.getIDcard());
//                }else {
//                    Log.e("TAG", "登录失败," + e.getMessage());
//                }
//            }
//        });
        BmobUser.loginByAccount("刘粤鼎", "123", new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    Log.i("TAG", "登录成功:" + user.getIDcard());
                }else {
                    Log.e("TAG", "登录失败," + e.getMessage());
                }
            }
        });
    }
    //注册
    private void signUp(){
        final User user = new User("1","男",22,"123456789");
//        final User user = new User();
        user.setUsername("刘粤鼎");
//        user.setUsername("Manager" + "Admin");
        user.setPassword("123");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    Log.i("TAG", "注册成功:" + user.getUsername());
                }else {
                    Log.e("TAG", "注册失败," + e.getMessage());
                }
            }
        });
    }
    //注意，不能用模糊查询
    private void queryByBql() {

//        String bql = "select * from Person where address = '湖南工业大学'";
//        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//
//        bmobQuery.setSQL(bql);
//        bmobQuery.doSQLQuery(new SQLQueryListener<Person>() {
//            @Override
//            public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
//                if(e ==null){
//                    List<Person> list = (List<Person>) bmobQueryResult.getResults();
//                    if(list!=null && list.size()>0){
//                        for (int i = 0; i < list.size(); i++) {
//                            Log.i("search by sql", "name: " + list.get(i).getName() + ",objId = " + list.get(i).getObjectId());
//                        }
//                    }else{
//                        Log.i("smile", "查询成功，无数据返回");
//                    }
//                }else{
//                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
//                }
//            }
//        });


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
        p2.setName("杨桓");
        p2.setAddress("湖南工业大学");
        p2.setObjectId("1");
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

//        Person p2 = new Person();
//        Person p3 = new Person();
//        p2.setName("cjh");
//        p2.setAddress("湖南工业大学");
//        p2.setName("lh");
//        p2.setAddress("吉林大学");
//        List<Person> list = new ArrayList<>();
//        list.add(p2);
//        list.add(p3);
//        MysqlTest mysqlTest = new MysqlTest();
//        mysqlTest.setId(1);
//        mysqlTest.setName("my test");
//        mysqlTest.setPersonList(list);
//        mysqlTest.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Log.i("TAG", "done: " + s);
//                }else{
//                    Log.e("TAG", "error: " + e.getMessage());
//                }
//            }
//        });
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


//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message message) {
//            switch (message.what){
//                case 10:{
//                    System.out.println("yesyesyes");
////                    EditText editText = findViewById(R.id.edit_query);
//                    List<Room> theList = (List<Room>) message.obj;
//                    for (int i = 0; i < theList.size(); i++) {
//                        System.out.println(theList.get(i).getType());
//                    }
//                }
//            }
//            return false;
//        }
//    });




}