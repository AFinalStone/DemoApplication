之前自己写了个简单的[用户图片上传功能](http://blog.csdn.net/abc6368765/article/details/51375305)的demo，后来发现在部分机型上面，选取图片之后返回页面无法获取到图片路径，这次通过[PhotoPicker框架](https://github.com/donglua/PhotoPicker)重写实现本地图片选择的功能。

首先是这次的效果图:
![效果图](/screen/GIF.gif)

Gradle

```gradle
dependencies {
	compile 'me.iwf.photopicker:PhotoPicker:0.9.5@aar'
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'com.github.bumptech.glide:glide:3.7.0'
}
```
appcompat-v7 version >= 23.0.0

PhotoPicker框架的github项目仅支持AS，对于eclipse的态度就一张图片：

![过时了](/screen/1.jpg)

Pick Photo
```java
PhotoPicker.builder()
    .setPhotoCount(9)
    .setShowCamera(true)
    .setShowGif(true)
    .setPreviewEnabled(false)
    .start(this, PhotoPicker.REQUEST_CODE);
```
Preview Photo
```java
ArrayList<String> photoPaths = ...;

PhotoPreview.builder()
    .setPhotos(selectedPhotos)
    .setCurrentItem(position)
    .start(MainActivity.this);
```

onActivityResult
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);

  if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
    if (data != null) {
      ArrayList<String> photos = 
          data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
    }
  }
}
```
manifest
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    >
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA" />
  <application
    ...
    >
    ...

    <activity android:name="me.iwf.photopicker.PhotoPickerActivity"
      android:theme="@style/Theme.AppCompat.NoActionBar" 
       />

    <activity android:name="me.iwf.photopicker.PhotoPagerActivity"
      android:theme="@style/Theme.AppCompat.NoActionBar"/>

  </application>
</manifest>
```
Custom style
```xml
<style name="actionBarTheme" parent="ThemeOverlay.AppCompat.Dark.ActionBar">
  <item name="android:textColorPrimary">@android:color/primary_text_light</item>
  <item name="actionBarSize">@dimen/actionBarSize</item>
</style>

<style name="customTheme" parent="Theme.AppCompat.Light.NoActionBar">
  <item name="actionBarTheme">@style/actionBarTheme</item>
  <item name="colorPrimary">#FFA500</item>
  <item name="actionBarSize">@dimen/actionBarSize</item>
  <item name="colorPrimaryDark">#CCa500</item>
</style>
```

以上基本都是该框架的github项目介绍，下面开始把该项目用到一个简单的demo中。

####一、添加项目依赖：
```build
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "24.0.0"

    defaultConfig {
        applicationId "com.shi.androidstudio.selectpicture"
        minSdkVersion 16
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
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

    compile 'me.iwf.photopicker:PhotoPicker:0.9.5@aar'
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'com.github.bumptech.glide:glide:3.7.0'

}
```
####二、SelectPictureActivity.java代码

```java
public class SelectPictureActivity extends AppCompatActivity {

    ListView listView;
    List<String> listImageUrl = new ArrayList<>();
    ImageView imageView;
    AdapterListView adapterListView;
    ImageCropper mImageCropper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        initOnClick();
        initImageCropperWithTitle();
        imageView = (ImageView) findViewById(R.id.imageView);
        listView = (ListView) findViewById(R.id.listView);
        adapterListView = new AdapterListView();
        listView.setAdapter(adapterListView);

    }

    private void initOnClick(){

        findViewById(R.id.btn_selectPicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });

        findViewById(R.id.btn_selectPictureByCut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE+1);
            }
        });

        findViewById(R.id.btn_selectMulPicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE+2);
            }
        });
    }


    private void initImageCropperWithTitle() {
        mImageCropper = ImageCropper.Helper.with(this)
                .setTitle("剪切图片")
                .setTranslucentStatusHeight(Utils.calcStatusBarHeight(this))
                .setCallback(new ImageCropper.Callback() {
                    @Override
                    public void onPictureCropOut(Bitmap bitmap, String tag) {
                        imageView.setImageBitmap(bitmap);
                        try {
                             // 保存图片到本地，记得申请权限
                            Utils.saveBitmapToTargetFile(Utils.getDiskCacheDir(SelectPictureActivity.this,"header"),bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).create();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {

                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                Log.e("图片地址", photos.get(0));
                mImageCropper.crop(photos.get(0),imageView.getWidth(),imageView.getHeight(),false,"");
                try {
                    Uri url = Uri.parse(photos.get(0));
                    imageView.setImageURI(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PhotoPicker.REQUEST_CODE+1) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                mImageCropper.crop(photos.get(0),imageView.getWidth(),imageView.getHeight(),false,"");
            }   else if (requestCode == PhotoPicker.REQUEST_CODE+2) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                listImageUrl.clear();
                listImageUrl.addAll(photos);
                adapterListView.notifyDataSetChanged();
            }
        }

    }


    private class AdapterListView extends BaseAdapter{

        @Override
        public int getCount() {
            return listImageUrl.size();
        }

        @Override
        public Object getItem(int position) {
            return listImageUrl.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(SelectPictureActivity.this);
            Uri url = Uri.parse(listImageUrl.get(position));
            imageView.setImageURI(url);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }
    }

}

```

####三、对应的布局文件activity_select_picture.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.shi.androidstudio.selectpicture.SelectPictureActivity">

    <Button
        android:id="@+id/btn_selectPicture"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="打开相册，获取图片" />

    <Button
        android:id="@+id/btn_selectPictureByCut"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="打开相册，获取图片，进行剪切" />

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:scaleType="fitCenter" />

    <Button
        android:id="@+id/btn_selectMulPicture"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="打开相册，选择多个图片" />

    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorPrimary" />


</LinearLayout>

```

四、还需要在AndroidManifest.xml中声明两个activity的路径
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.shi.androidstudio.selectpicture">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".SelectPictureActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity android:name=".MainActivity">
        </activity>

        <activity android:name="me.iwf.photopicker.PhotoPickerActivity"
            android:theme="@style/Theme.AppCompat.NoActionBar"
            />

        <activity android:name="me.iwf.photopicker.PhotoPagerActivity"
            android:theme="@style/Theme.AppCompat.NoActionBar"/>
    </application>

</manifest>

```


为项目添加图片剪切功能

[uCrop框架的使用](https://github.com/Yalantis/uCrop)

[可能是最详细的UCrop源码解析](http://www.jianshu.com/p/1d3fb16fb412)

