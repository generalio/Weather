## MVVM框架学习------天气App

> 这个天气APP是根据《第一行代码 第三版》上的实战写的。

框架用的`MVVM`，在书和学长的指导下终于对这个`MVVM`框架，写法规范以及分包规范等有了一定的了解。

`View`界面基本上是直接照搬书上的（当时没有`ConstraintLayout`写起来真冗杂啊），主要自己不太会设计界面，并且这个项目主要是为了学习`MVVM`的使用，故在`View`上面并没有下什么功夫，但还是从书上学到了一些有用的这方面的知识。

基本框架是`Retrofit+RxJava+ViewModel`，数据存储就直接用的sp，因为书上是用的协程，导致`Model`层一大部分是自己琢磨出来的，后果就是一开始出了一大堆问题（比如把`livedata`写在了model层里面，还是学长指出来的），从开始到结束一共花了三天不到的时间（不用思考View就是好啊），还是学到了很多东西的。

分包如下:

```bash
├─model
│  ├─bean
│  ├─dao
│  └─network
├─ui
│  ├─activity
│  ├─adapter
│  └─fragment
├─utils
└─viewmodel
```

其中bean装的是数据类，dao是封装了sharedPreference的一些方法，network装的是请求接口以及仓库层，util是常用工具类，比如Retrofit的封装以及全局获取`Context`以及`Token`等。viewmodel层是mvvm的中间层，用于回调。

> mode层的网络请求只需要去进行请求，拿到这个Observable或者Call实例，而数据的处理通常是放在viewmodel中进行处理的，viewmodel处理后通过livedata让view层进行观察，这个过程相比MVP很大程度了减少了耦合性，因为MVP需要你自己在Presenter层进行手动回调给View层，而MVVM只需要View层进行观察就可以了。

> model层不能使用livedata的原因：livedata是有生命周期的，而model通常只是一个拿数据的地方，一般情况是不能让他有生命周期的，故我们一般只在viewmodel层创建livedata，从model层获取Observable对象再进行赋值给livedata就好了。

这个项目中的viewmodel里面的livedata是可变的，但是我们通常不会让可变的livedata暴露在外部，而是应该暴露给外部一个不可变的livedata，如:

```kotlin
private val _livedata : MutableLiveData<>() = mutableLiveData() //创建不可变的livedata
val livedata: LiveData<>() get() = _livedata //通过设置属性getter()方法来让不可变的livedata暴露给外部
```

## 总结

第一行代码真的是本好书，跟着一步一步学还是能学到一点东西的。