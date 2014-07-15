DoorAccessControlSystem
=======================
简单的门禁管理系统

# 需求说明
设计一个 Android 端的门禁管理系统，模拟不同人员对门的控制方式。  

## 角色
**管理员 Admin**  
对系统整体进行管理。雇员的录入、制卡、删除；经理手势录入；访客手动放行等。  
**经理 Manager**  
经过用户名和手势验证通过门禁。  
**雇员 Employee**  
经过刷卡通过门禁。  
**访客 Visitor**  
按门铃提示进入。  

# 感谢
感谢实训老师`蔡老师`，感谢一起努力的小伙伴们。  

# 模块说明
将系统设计中用到的比较经典的模块说明一下。没有经典应用的将不被列出。  



## [BaseActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/BaseActivity.java)
系统中所有 Activity 的基类，使用类 `建造者模式` 的方法，实现了 Activity 初始化的大部分功能。  
预留 `getLayoutResID()` 和 `initView()` 供子类提供布局和初始化布局的操作。  
提供了常用的功能方法。  

> `void startActivity(Class)` 启动一个 Activity。  
> `void startActivityForResult(Class, int)` 以回调方式启动一个 Activity  
> `void showToast(String)` 显示一个 Toast  
> `boolean isFirst()` 判断是否是第一次运行  

经典应用：  

1. SharedPreferences 偏好设置  
2. Editor 偏好设置修改器  

## [ActivitySplash.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/ActivitySplash.java)
经典应用：  
**ViewPaper**  以滑动的方式显示每一页 View
使用方法：  

 1. 在[布局文件](https://github.com/sumy7/DoorAccessControlSystem/blob/master/res/layout/activity_splash.xml)中设置 ViewPaper 的项目，标签为 `  <android.support.v4.view.ViewPager>`  
 2. 在代码中引用 ViewPaper 
 3. 重载 PagerAdapter 类，并实现其中的某些方法
 4. 设置 ViewPaper 的 Adapter
 
----------

**HorizontalScrollView** 配合 ViewPaper 实现背景图片随滑动而滚动  
在 Adapter 的 [void onPageScrolled(int, float, int)](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/ActivitySplash.java#L92) 方法里对滚动的距离进行计算，并修改 Scroll 的滚动值。  

----------

**其它**  
`ViewPaper.setCurrentItem(int)` 可以使 ViewPaper 跳转到指定页  



## [MainMenuActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/MainMenuActivity.java)
**GridView** 网格布局的使用 *与 ViewPaper 大同小异，不再重复*  
为 GridView 的每一项设置缩放动画 Animation  

    // LINE 106
    // 为 GridView 的每一项设置动画
    Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
    animation.setDuration(1500);
    LayoutAnimationController controller = new LayoutAnimationController(animation);
    gridView.setLayoutAnimation(controller);
    animation.start();

--------

**[双击 Back 退出](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/MainMenuActivity.java#L118)**  
第一次捕获到 Back 时设置退出标志，并添加一个定时任务，若 2000毫秒 内未检测到另一次 Back，则取消退出标志。  

--------
**判断设备是否支持 NFC**



## [AdminActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/AdminActivity.java)
**ListView** 的应用  
**ListView 的项目点击监听** `setOnItemClickListener()`  
**AlertDialog** 的创建及显示  



## [EmployeeNFCEnterActivity](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/NFCInputActivity.java) [EmployeeNFCEnterActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/EmployeeNFCEnterActivity.java)
**判断设备是否支持 NFC**
**NFC设置前台响应**  
**NFC取消前台响应**  
**NFC回调处理** `void onNewIntent(Intent)`  



## [InputEmployeeActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/InputEmployeeActivity.java)
**ListView长按显示上下文菜单**  

1. 为 ListView 设置 `setOnItemLongClickListener()` ，在 OnItemLongClickListener 中根据长按的项目位置(position)判断是否显示上下文菜单 `setOnCreateContextMenuListener()`  
2. 在 OnCreateContextMenuListener 中创建需要显示的上下文菜单  
3. 在 `void onContextItemSelected()` 中响应上下文菜单的点击事件  

--------

**调用相机拍摄照片**

    // LINE 200
    // 创建隐式意图调用系统相机
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    Uri uri = Uri.fromFile(tempFile);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    startActivityForResult(intent, REQUEST_CODE);

在 `void onActivityResult(int, int, Intent)` 中处理回调信息。

--------

**调用图库选择照片**

    // LINE 207
    // 创建意图启动图库
    intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    startActivityForResult(Intent.createChooser(intent, "选择一张图片"),REQUEST_CODE);

在 `void onActivityResult(int, int, Intent)` 中处理回调信息。被选中照片的 Uri 可以通过 `Intent.getDate()` 获得。  

--------

**将 Uri 转换成存储路径**

    // LINE 231
    Uri originalUri = data.getData();// 得到图片的URI
    String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
    Cursor cursor = this.managedQuery(originalUri, imgs, null, null, null);
    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    photoPath = cursor.getString(index);

代码中的一个方法以被弃用，所以可能有更好的方法代替。  

--------

**产生一个UUID** `UUID.randomUUID().toString()`  
**判断输入框是否为空** `TextUtils.isEmpty(EditText)`  


## [InputManagerActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/InputManagerActivity.java)  [ManagerCheckInActivity.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/activity/ManagerCheckInActivity.java) [LockScreenView.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/view/LockScreenView.java)
**锁屏手势** 使用的是老师提供的 View，自己添加了一个接口监听器 `OnDrawLockPinFinishedListene` 当手势绘制完成时自动调用。  
**两次绘制手势的实现**  


## [/adapter](https://github.com/sumy7/DoorAccessControlSystem/tree/master/src/com/sumy/dooraccesscontrolsystem/adapter)
**重载系统 Adapter 来新建基类，简化之后的 Apater 创建**  
**自定义 Adapter**  

### [GridViewAdapter.java](https://github.com/sumy7/DoorAccessControlSystem/blob/6a3e03a43952d9d99bef164b36b657adf738576c/src/com/sumy/dooraccesscontrolsystem/adapter/GridViewAdapter.java)
**设置字体**  
字体文件需放在 `assets` 目录下  

    Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/wryh.ttf");
    textView.setTypeface(tf);


## [DoorSystem.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/business/DoorSystem.java#L73)
**接口回调的方式实现个性化操作**  



## [/DAO](https://github.com/sumy7/DoorAccessControlSystem/tree/master/src/com/sumy/dooraccesscontrolsystem/dao)
对 SQLite 数据库的使用操作。  

### [CheckInHelper.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/dao/CheckInHelper.java)
重载 `SQLiteOpenHelper` 基类，实现三个方法：  

- `构造函数` 指明 上下文、数据库名称、游标工厂、数据库版本号  
- `void onCreate(SQLiteDatabase)` 在数据库第一次被创建时调用  
- `void onUpgrade(SQLiteDatabase, int, int)` 当数据库版本号发生变化时调用  

### [CheckInDao.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/dao/CheckInDao.java)
对数据库实现增删查，暂时没有修改数据的操作。  

## [Ring](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/entity/Ring.java)
**播放音频**  

    MediaPlayer mediaplayer = MediaPlayer.create(context, resid);
    mediaplayer.start();


## [ImageTools.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/utils/ImageTools.java)
通过路径获取图片信息，首先解析图片获取图片的宽和高，然后计算缩放比例，使图片能被放置到提供的宽和高中，最后根据缩放比例读取图片。  

## [XMLTools.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/utils/XMLTools.java)
进行 XML 的读写操作。  
使用 **XmlSerializer** 写 XML  
使用 **XmlPullParser** 读 XML  
*请参照代码的注释说明。*  

## [NFCTools.java](https://github.com/sumy7/DoorAccessControlSystem/blob/master/src/com/sumy/dooraccesscontrolsystem/utils/NFCTools.java)
对 NFC Tag 的文本进行读写。  

# 实训笔记
![实训笔记](https://raw.githubusercontent.com/sumy7/DoorAccessControlSystem/master/pratice_note.png)