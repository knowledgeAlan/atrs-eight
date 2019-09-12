# HashSet使用介绍

环境 jdk8

##1.概要

本文介绍`HashSet`是最受欢迎实现`Set ` 接口之一并且集成在java集合框架中

## 2.`HashSet`介绍

`HashSet` 是java集合框架中基础数据结构之一，下面是最重要方面

- 存储元素是唯一性不存在重复和可以存储空元素
- `HashMap`存储元素
- 迭代时候不能保持插入顺序
- 非线程安全

注意`HashSet` 初始化时候内部`HashMap`被初始化

```java
 public HashSet() {
        map = new HashMap<>();
   }
```

## 3.api接口

本章将介绍使用最多的方法和看下简单例子。

### 3.1 增加 add()

`add()` 方法增加元素，如果增加元素不在`set`中，则返回true，其他false，下面增加元素例子：

```java
@Test
public void whenAddingElement_shouldAddElement() {
    Set<String> hashset = new HashSet();
  
    assertTrue(hashset.add("String Added"));
}
```

从实现角度来看，增加方法是非常重要，`HashSet`实现细节如何工作和高效是使用`HashMap`  `put()`方法

```java
 public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
```

map变量是`HashSet` 内部引用存放元素

```java
 private transient HashMap<E,Object> map;
```



上面通过`HashMap`存储，就可以知道通过`hashcode`来存储基本数据结构，下面总结:

- `HashMap`是一个篮子数组并且缺省大小16，每个篮子代表不同hashcode值
- 如果变量有相同hashcode值，他们会存储在相同篮子里面
- 如果存储数据大小大于负载因子，新建数组大小是原来两倍并且所有元素重新hash和重新分配
- 获取一个值时，已经有一个key，然后修改他从篮子中，查找可能存在相同元素在链表上

### 3.2  *contains()*

**contains**方法判断元素是否在`HashSet`中 ，放回**true**表示元素存在其他**false**，下面判断元素在`HashSet`：

~~~java
@Test
public void whenCheckingForElement_shouldSearchForElement() {
    Set<String> hashsetContains = new HashSet();
    hashsetContains.add("String Added");
  
    assertTrue(hashsetContains.contains("String Added"));
}
~~~

无论怎么调用该方法，hash 值总会被计算，元素查找会涉及到遍地查询

### 3.3. 删除 **remove()**

该方法删除指定元素如果存在，该方法返回**true** 如果`hashSet` 包含指定元素，下面例子：

```java
@Test
public void whenRemovingElement_shouldRemoveElement() {
    Set<String> removeFromHashSet = new HashSet();
    removeFromHashSet.add("String Added");
  
    assertTrue(removeFromHashSet.remove("String Added"));
}
```

### 3.4 . 清空*clear()* 

当删除所有元素时候使用该方法，以下实现仅仅清除所有元素,下面示例：



~~~~java
@Test
public void whenClearingHashSet_shouldClearHashSet() {
    Set<String> clearHashSet = new HashSet();
    clearHashSet.add("String Added");
    clearHashSet.clear();
     
    assertTrue(clearHashSet.isEmpty());
}
~~~~



### 3.5 . `hashSet`大小 **size()** 



该方法是基础方法之一，统计元素个数，下面列子:

```java
@Test
public void whenCheckingTheSizeOfHashSet_shouldReturnThesize() {
    Set<String> hashSetSize = new HashSet();
    hashSetSize.add("String Added");
     
    assertEquals(1, hashSetSize.size());
}
```



### 3.6 判断是否为空 **isEmpty()**

该方法弄清楚给定`HashSet` 是否为空，如果返回为true，`HashSet`不包含元素：

```java
@Test
public void whenCheckingForEmptyHashSet_shouldCheckForEmpty() {
    Set<String> emptyHashSet = new HashSet();
     
    assertTrue(emptyHashSet.isEmpty());
}
```



### 3.7. 迭代**iterator()**

方法返回迭代`set`中元素，元素访问是无序并且迭代器是快速失败，下面是随机迭代例子：

```java
@Test
public void whenIteratingHashSet_shouldIterateHashSet() {
    Set<String> hashset = new HashSet();
    hashset.add("First");
    hashset.add("Second");
    hashset.add("Third");
    Iterator<String> itr = hashset.iterator();
    while(itr.hasNext()){
        System.out.println(itr.next());
    }
}
```

如果`set` 在迭代之后任意时候被修改会抛出异常，迭代器抛出**ConcurrentModificationException**

下面例子：

```java
@Test(expected = ConcurrentModificationException.class)
public void whenModifyingHashSetWhileIterating_shouldThrowException() {
  
    Set<String> hashset = new HashSet();
    hashset.add("First");
    hashset.add("Second");
    hashset.add("Third");
    Iterator<String> itr = hashset.iterator();
    while (itr.hasNext()) {
        itr.next();
        hashset.remove("Second");
    }
}
```

当使用迭代删除方法，不会抛出异常：

```java
 @Test
public void whenRemovingElementUsingIterator_shouldRemoveElement() {
  
    Set<String> hashset = new HashSet();
    hashset.add("First");
    hashset.add("Second");
    hashset.add("Third");
    Iterator<String> itr = hashset.iterator();
    while (itr.hasNext()) {
        String element = itr.next();
        if (element.equals("Second"))
            itr.remove();
    }
  
    assertEquals(2, hashset.size());
}
```

线程安全不能靠迭代器抛出异常来，快速失败尽最大努力抛出异常，如果写代码依赖异常

是错误方式

###4.`HashSet` 如何保持唯一性

当添加元素时候，更加元素**hashcode**值判断是否存在，每个**hashcode**值放在一个篮子中，不同篮子对应不同**hashcode**值，两个相同**hashcode**可能对应不同元素，因此在相同篮子中通`equals()`比较相等。

### 5.`HashSet`性能比较

影响`HashSet`主要有两个参数，初始容量和负载因子，增加元素时间复杂度是O(1)删除元素时间复杂度O(n)在最坏情况，因此很有必要设置合适初始化值，jdk8中最差情况O(log n) ,这个负载因子描述放最大元素，达到最大需要重新分配空间大小，可以创建自定义初始化容量和负载因子`HashSet`：

```java
Set<String> hashset = new HashSet();
Set<String> hashset = new HashSet(20);
Set<String> hashset = new HashSet(20, 0.5f);
```

第一行，使用是缺省默认值，初始容量16和0.75负载因子，第二覆盖缺省容量，最后一个覆盖所有，初始化小容量可以减少空间复杂度增加频繁重新hash，重新hash是非常昂贵过程。

另一方面，高容量初始化增加查询时间和初始化内存空间浪费，下面是总结：

-  大初始换容量可以存放很多元素减少遍历次数
- 小初始化容量存放少量元素可以支持遍历多次



因此重要是平衡两者，默认值是已经优化过性能很好，我们使用要调整这两个参数适合自己场景。

### 6.总结

本文列出`HashSet`使用大纲，同时了解里层原理，可以更好使用，查找是性能很好并且可以避免保存重复元素。了解一些重要方法，帮助开发者使用它。

[原文地址](https://www.baeldung.com/java-hashset) 