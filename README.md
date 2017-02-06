# AndroidBaseFrame #

----------

###AndroidBaseFrame简介###
AndroidBaseFrame是一个简易的Android基础项目，方便您快速进行开发。 包含以下内容：
>     BaseActivity、BaseFragment
>     Activity栈管理
>     异常信息收集
>     日志打印
>     丰富的工具类
>     Android6.0权限管理


----------

### 添加AndroidBaseFrame gradle依赖 ###

AndroidBaseFrame已经更新到[jitpack](https://jitpack.io/)上，使用AndroidStudio导入即可.

**Step 1** 在项目根目录 <B>build.gradle</B> 中添加
>     allprojects {
>         repositories {
>     	    ....
>     	    maven { url "https://jitpack.io" }
>             }
>         }

**Step 2** 在App项目引用 <B>build.gradle</B> 中添加
>     dependencies {
>             compile 'com.github.24kpk:AndroidBaseFrame:1.0.7'
>     }

**Step 3** 初始化

    public class YourApplication extends Application {
    
	    @Override public void onCreate() {
	    super.onCreate();
	    
	    /**
	     * 默认配置
	     * 内部调用了: initDir() initLog(false) initExceptionHandler()三个方法
	     */
	    BasicConfig.getInstance(this).init();
	    
	    or
	    
	    /**
	     * 自定义配置
	     * initDir() 初始化SDCard缓存目录
	     * initLog() 初始化日志打印
	     * initExceptionHandler() 初始化异常信息收集
	     */
	    BasicConfig.getInstance(this)
	       .initDir() // or initDir(rootDirName)
	       .initExceptionHandler()
	       .initLog(true); 
	    
	    //其它初始化日志方法：
	    /**
	     * @param tag 日志标示
	     */
	    initLog(tag)
	    
	    /**
	     * @param tag 日志标示
	     * @param isDebug true:打印全部日志，false:不打印日志
	     */
	    initLog(tag, isDebug)
	    
	    /**
	     * @param tag 日志标示，可以为空
	     * @param methodCount 显示方法行数，默认为：2
	     * @param isHideThreadInfo 是否显示线程信息，默认显示
	     * @param adapter 自定义log输出
	     * @param isDebug true:打印全部日志，false:不打印日志
	     */
	    initLog(tag, methodCount, isHideThreadInfo, adapter, isDebug)
	    }
    }

----------

### 代码示例 ###
###### Activity示例 ######
    public class TestActivity extends BaseActivity {
	    private RecyclerView mRecyclerView;
	
	    @Override public int getLayoutResId() {
	        return R.layout.activity_main;
	    }
	
	    //初始化一些数据
	    @Override public void initData() {
	        super.initData();
	        Intent intent = getIntent();
	        params = intent.getStringExtra(...);
	    }
	
	    //初始化view
	    @Override public void initView(Bundle savedInstanceState) {
	        super.initView(savedInstanceState);
	        mRecyclerView = (RecyclerView) findViewById(R.id.main_rv);
	        LinearLayoutManager manager = new LinearLayoutManager(this);
	        manager.setOrientation(LinearLayoutManager.VERTICAL);
	        mRecyclerView.setLayoutManager(manager);
	        mRecyclerView.setHasFixedSize(true);
	        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	
	        if(savedInstanceState == null){
	            /**
	             * 使用Fragment
	             * 参数1：被替换为Fragment的视图id
	             * 参数2：BaseFragment对象
	             */
	            changeFragment(R.id.fragment_layout, new ImageFragment());
	        }
	
	    }
	
		//是否使用透明状态栏
	    @Override 
		protected boolean translucentStatusBar() {
	        return false;
	    }
	
		//是否使用app统一的Title layout，如果需要自定义，复写该函数
		 @Override 
	    protected boolean useComTitleLayout() {
	        return true;
	    }
	
	    //以下为可选方法，根据需要进行重载.
	    //方法执行顺序：
	    //initPre()  --> initUI(Bundle savedInstanceState) --> initData()-->  addListener() --> register()
	
	    //只有第一次才会执行，这里可以做一些界面功能引导
	    @Override public void onFirst() { }
	    //这个方法会在setContentView(...)方法之前执行
	    @Override public void initPre() { }
	    @Override public void initData() { }
	    @Override public void initUI(Bundle savedInstanceState) { }
	    @Override public void showProgress() { }
	    @Override public void hideProgress() { }
	    //这里可以注册一些广播、服务
	    @Override public void register() { }
	    //注销广播、服务,在onDestroy()内部执行
	    @Override public void unRegister() { }
	    //注册监听器
	    @Override public void addListener() {}
	    //view点击事件统一处理
	    @Override public void viewClick(View v) { }
	    @Override public void onPermissionsGranted(int requestCode, List<String> perms) { }
	    @Override public void onPermissionsDenied(int requestCode, List<String> perms) { }
	
	    @Override public void onClick(View v) {
	        viewClick(v);
	    }
	}

###### Fragment示例 ######
	public class TestFragment extends BaseFragment {
	    private TextView mTitle;
	
	    @Override public int getLayoutResId() {
	        return R.layout.activity_listview_item;
	    }
	
	    @Override public void initView(View parentView, Bundle savedInstanceState) {
	        super.initView(parentView, savedInstanceState);
	        mTitle = (TextView) parentView.findViewById(R.id.item_title_tv);
	    }
	
	    //以下为可选方法，根据需要进行重载.
	    //方法执行顺序：
	    //initUI(View parentView, Bundle savedInstanceState) --> initData() --> addListener() -->  register()
	
	    //只有第一次才会执行，这里可以做一些界面功能引导
	    @Override public void onFirst() { }
	    @Override public void initData() { }
	    @Override public void initUI(View parentView, Bundle savedInstanceState) { }
	    //这里可以注册一些广播、服务
	    @Override public void register() { }
	    //注销广播、服务, 在onDestroyView()内部执行
	    @Override public void unRegister() { }
	    //Fragment被切换到前台时调用
	    @Override public void onFragmentShow() { }
	    //Fragment被切换到后台时调用
	    @Override public void onFragmentHide() { }
	    //注册监听器
	    @Override public void addListener() {}
	
	    @Override public void showProgress() { }
	    @Override public void hideProgress() { }
	    //view点击事件统一处理
	    @Override public void viewClick(View v) { }
	}
###### 启动页示例 ######
 

    public class SplashActivity extends BaseSplashActivity {

	    @Override protected void setSplashResources(List<SplashImgResource> resources) {
	        /**
	         * SplashImgResource参数:
	         * mResId - 图片资源的ID。
	         * playerTime - 图片资源的播放时间，单位为毫秒。。
	         * startAlpha - 图片资源开始时的透明程度。0-255之间。
	         * isExpand - 如果为true，则图片会被拉伸至全屏幕大小进行展示，否则按原大小展示。
	         */
	        resources.add(new SplashImgResource(R.mipmap.splash,1500,100f,true));
	        resources.add(new SplashImgResource(R.mipmap.splash1,1500,100f,true));
	        resources.add(new SplashImgResource(R.mipmap.splash2,1500,100f,true));
	    }
	
	    @Override protected boolean isAutoStartNextActivity() {
	        return false;
	    }
	    @Override protected Class<?> nextActivity() {
	        return null;
	        //如果isAutoStartNextActivity设置为true,这里需要指定跳转的activity
	        //return MainActivity.class;
	    }
	
	    @Override protected void runOnBackground() {
	        //这里可以执行耗时操作、初始化工作
	        //请注意：如果执行了耗时操作，那么启动页会等到耗时操作执行完才会进行跳转
	        //try {
	        //  Thread.sleep(15 * 1000);
	        //} catch (InterruptedException e) {
	        //  e.printStackTrace();
	        //}
	    }
	} 

###### 打印日志 ######
    Logger.d(content);
	Logger.e(content);
	Logger.w(content);
	Logger.v(content);
	Logger.wtf(content);
	//打印json数据
	Logger.json(jsonContent);
	//打印xml数据
	Logger.xml(xmlContent);
注意事项：确保包装选项是禁用的 
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/log.png)

### Android6.0权限管理 ###
[更多使用方法点这里](https://github.com/googlesamples/easypermissions)
	
	//以使用相机为例，在Activity/Fragment添加以下代码
	
	private static final int REQUEST_CODE_CAMERA = 101;//请求相机权限的requestCode
	
	@AfterPermissionGranted(REQUEST_CODE_CAMERA)
	public void useCamera() {
	    if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
	        ToastUtil.showToast(getApplicationContext(), "相机权限已授权,可以开始使用相机了");
	    } else {
	        //请求权限
	        EasyPermissions.requestPermissions(this, "应用需要访问你的相机进行拍照",
	                                           REQUEST_CODE_CAMERA, Manifest.permission.CAMERA);
	    }
	}
	
	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
	    //用户授权成功
	}
	
	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
	    //用户拒绝授权
	}

### License ###
	Copyright 2015 classic
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.