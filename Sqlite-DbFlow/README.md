### DBFlow的设置

### 一、配置DBFlow插件

- **1.在项目的build.gradle中添加**

```gradle

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.1'
        //添加下面这行代码
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

- **2.在当前model的build.gradle中添加**

```gradle
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'//这行代码

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"

    defaultConfig {
        applicationId "com.example.dbflow"
        minSdkVersion 16
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'


    //下面这三行代码
    apt 'com.raizlabs.android:DBFlow-Compiler:2.2.1'
    compile "com.raizlabs.android:DBFlow-Core:2.2.1"
    compile "com.raizlabs.android:DBFlow:2.2.1"

}
```

- **3 初始化**
```java
//自己定义MyApplication，在其onCreate方法中调用FlowManager.init(this)进行初始化
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}

```

```xml
    <application
        android:name=".MyApplication"/>
```

### 二、使用

- 1 创建数据库
```java

@Database(name = MyDbflowData.NAME, version = MyDbflowData.VERSION)
public class MyDbflowData {
    //数据库名称
    public static final String NAME = "MyDbflowData";
    //数据库版本号
    public static final int VERSION = 1;
}

```
- 2 创建javabean对象

在DBFlow中，每个与数据库连接的ORM对象类都必须继承Model接口。这是为了确保这些对象都能有相同的一些方法。通常为了方便我们继承BaseModel类，这个类也是Model的标准实现。

一个正确的数据表类需要以下几项：

    对类添加@Table注解
    声明所连接的数据库类，这里是ColonyDatabase。
    定义至少一个主键。
    这个类和这个类中数据库相关列的修饰符必须是包内私有或者public。
    这样生成的_Adapter类能够访问到它。

NOTE: 列（Column）属性可以是private，但这样就必须指定公有public的getter和setter方法。

```java

/**
 * 必须继承BaseModel，BaseModel包含了基本的数据库操作
 * （save、delete、update、insert、exists），可以发现这个表是关联上面定义的数据库
 * ，UserModel 的id是自增的id（autoincrement ）。
 * Created by afinalstone on 17-4-24.
 */
@ModelContainer
@Table(database = MyDbflowData.class)
public class UserBean extends BaseModel {
    //用户ID编号
    @Column
    @PrimaryKey(autoincrement = true)
    public long userId;
    //用户姓名
    @Column
    public String userName;
    //用户年龄
    @Column
    public int age;
    //用户性别,男为true
    @Column
    public boolean sex;

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
```
创建userBean之后，我们Make project，如果有错误会提示我们，如果没有错误编译成功，将会自动创建一些数据库文件

- 3 具体增删改差

```java
public class MainActivity extends AppCompatActivity {
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            //添加
            case R.id.singObjectAdd:
                userBean = new UserBean();
                userBean.userName = "李白";
                userBean.age = (int) (Math.random() % 100);
                userBean.sex = true;
                userBean.save();
                break;
            //修改
            case R.id.singObjectUpdate:
                if (userBean != null) {
                    userBean.userName = "李小画";
                    userBean.age = 22;
                    userBean.sex = false;
                    userBean.update();
                }
                break;
             //查询
            case R.id.singObjectSelect:
                if (userBean != null) {
                    UserBean bean = new Select().from(UserBean.class).querySingle();
                    System.out.println(bean);
                }
                break;
             //删除   
            case R.id.singObjectDelete:
                if (userBean != null) {
                    userBean.delete();
                }
                break;
             //多对象查询   
            case R.id.mulObjectSelect:
                List<UserBean> list = new Select().from(UserBean.class).queryList();
                System.out.println(list);
                break;
             //多对象条件查询   
            case R.id.mulObjectSelectByWhere:
                List<UserBean> list2 = new Select().from(UserBean.class).where(
                        Condition.column(new NameAlias("userName")).is("李小画"),
                        Condition.column(new NameAlias("sex")).is(false)).queryList();
                System.out.println(list2);
                break;
        }
    }
}
```

### 三、官方文档

本文只是对dbflow的简单介绍和使用，写的并不完整，附上官方文档介绍：[点我开始传送！](https://yumenokanata.gitbooks.io/dbflow-tutorials/content/transaction_manager.html)
