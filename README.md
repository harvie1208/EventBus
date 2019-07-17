## EventBus核心功能加注释详解

### 核心api
```
register(Object object) //注册
unRegister(Object object) //注销
@Subscribe(thread = ThreadModel.BACKGROUND) //订阅
postEvent(Object object) //发送事件
```

### 使用步骤：
### Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
### Step 2. Add the dependency //添加依赖
```
dependencies {
	implementation 'com.github.harvie1208:EventBus:1.0.0'
}
```
### Step 3. register and unRegister //注册 注销
一般在activity onCreate()方法中

`
EventBus.getInstance().register(this);
`

一般在activity onDestory()方法中

`
EventBus.getInstance().unRegister(this);
`
### Step 4. subscribe 订阅

1.在需要接收事件的方法上添加@Subscribe 注解，方法名随意

2.可指定该方法运行的线程ThreadModel.BACKGROUND表示子线程，ThreadModel.MAIN表示主线程

3.只能定义一个形参，类型随意

```
@Subscribe(thread = ThreadModel.BACKGROUND)
public void haha(LoginEvent loginEvent){
	//此处处理业务代码
	Log.e("EventBus",loginEvent.getLoginStatus()+Thread.currentThread().getName());
}
```

### Step 5. postEvent 发送事件

发送事件类型要与订阅事件类型一致

`
EventBus.getInstance().postEvent(new LoginEvent("登录成功"));
`
  
