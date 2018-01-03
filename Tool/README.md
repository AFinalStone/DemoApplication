
### 常用工具类：

#### [SmoothCheckBox](https://github.com/andyxialm/SmoothCheckBox)

![SmoothCheckBox](pic/smoothcb.gif)

#### Attrs 属性
```
attr	format	description
duration	integer	动画持续时间
stroke_width	dimension	未选中时边框宽度
color_tick	color	对勾颜色
color_checked	color	选中时填充颜色
color_unchecked	color	未选中时填充颜色
color_unchecked_stroke	color	未选中时边框颜色

```
#### Usage 使用
```java

    setChecked(boolean checked);                   // 默认不带动画，若需要动画 调用重载方法
    setChecked(boolean checked, boolean animate);  // 参数: animate 是否显示动画
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final SmoothCheckBox scb = (SmoothCheckBox) findViewById(R.id.scb);
        scb.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                Log.d("SmoothCheckBox", String.valueOf(isChecked));
            }
        });
    }
```

- **CreateQRImageUtil工具类：** 把字符串转换成二维码图片的工具类

- **DensityUtil工具类：** 能够使得像素和dp两者相互转换

- **GsonUtil工具类：**  解析Gson格式的数据

- **ImagerLoaderUtil工具类：** 图片加载框架，底部是使用icasso实现的

- **LogUtil工具类：** 打印Log日志的工具类，如果单次打印次数过多，则分段打印，不会因为内容过多而把后面的内容拦腰截断

- **Md5Util工具类：** 可以获取文件的Md5和字符串的MD5

- **PreferencesUtil工具类：** SharedPreferences文件读取工具类

- **ToastUtil工具类：** 对Toast进行二次封装，使用更加方便

- **SnackBarUtil工具类：** 对SnackBar进行二次封装，使用更加方便

- **StringUtil工具类：** 字符串工具类，可以对字符串进行一些常见的操作

- **SystemUtil工具类：** 系统工具类，可以获取当前应用版本号，包名，判断SD是否存在，返回系统默认Download路径，判断是否有网络连接，获取网络连接类型，获取手机时间，获取设备唯一码

- **statebar文件夹下面的StateBarUtil工具类：** 可以控制系统状态的属性，状态栏的字体颜色

### 常用自定义控件

- **charting** 一款开源的图表控件，具体参考MPAndroidChart开源项目

- **loadingprogress+LoadingProgressDialog** 进度条控件

- **slider** 轮播图样式控件

- **swiperefreshview** 组合自定义控件，把ListView，GridView，RecyclerView，ScrollView控件和SwipeRefreshLayout相组合，产生的上拉加载更多，下拉刷新的控件

- **treeview** 折叠类型的ListView

- **viewPagerIndicator** ViewPager的指示标点，类似于ViewPager底部的标识点

- **FlowTagLayout**  一个流式布局，可以单选，也可以多选的标签控件

- **FragmentOKAndCancelDialog**  带有确定和取消按钮的对话框，继承自DialogFragment，兼容性更好

- **FragmentViewDialog**  带有确定和取消按钮的对话框，继承自DialogFragment，兼容性更好，并且可以自定义对话框正文内容

- **FragmentViewDialog**  带有确定和取消按钮的对话框，继承自DialogFragment，兼容性更好，并且可以自定义对话框正文内容

- **ShapedImageView**  可以实现圆角和圆角矩形的ImageView

- **TabLayoutView**  和TabLayout类似的控件

- IOSDailog   http://blog.csdn.net/zhangphil/article/details/44940339















