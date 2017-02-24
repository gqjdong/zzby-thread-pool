## 使用多线程实现了一个这样的场景：

有一组相关的任务，使用线程池异步执行


每个任务执行成功，从组中移除;


当任务全部执行过一次后，


如果该组任务有执行失败的任务，则将该组任务放到失败任务集合中


会有定时任务去执行失败的任务集合



失败任务如果执行成功，会从该组失败任务中移除


如果重试3次后还失败，则停止重试，移除该组失败任务(也可以记录下来)


其中使用了模版模式，执行任务的类只关心具体的任务，像成功移除等操作在父类模版中进行


## 多线程需要注意的问题：

堆中的共享对象会有线程安全问题。


线程安全有两个方面：1、内存不可见性  2、非原子操作的重排序


对于只有一个地方进行原子的写操作，其它地方都是读的共享对象，可以使用volatile关键字来保证安全，节省性能


尽量使用原子性类


同步块范围尽量小，使用的共享锁范围也尽量小


使用CountDownLatch类来使线程同步
