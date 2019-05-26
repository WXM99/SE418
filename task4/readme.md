# Multithread Service Container

## 1. Requset Container Stucture:

> Line-9 in ```Main```
>
> ```java
> LinkedBlockingDeque<myReq> myContainer = new LinkedBlockingDeque<>(100);
> ```

- An optionally-bounded [blocking deque](#) based on linked nodes.

## 2. Serving Strategy

### FILO for a big amount of requests

> Line-38 in ``Producer``
>
> ```java
> if(this.servings.size() > SWITCH_FIFO) {
>   myReq request = this.servings.getLast();
> 	this.tmpReq.updateAndGet(x -> (x - request.size));
> 	this.servings.remove(request);
> 	this.servedNum.incrementAndGet();
> }
> ```

###  FIFO for a small amount of requests

> Line-60 in ``Producer``
>
> ```java
> else {
> 	myReq request = this.servings.getFirst();
> 	this.tmpReq.updateAndGet(x -> (x - request.size));
> 	this.servings.remove(request);
> 	this.servedNum.incrementAndGet(); 
> }
> ```

### DROP request that waits too long

> Line-83 in ``Producer``
>
> ```java
> long now = System.currentTimeMillis();
> myReq request = this.servings.getFirst();
> if(now - request.timestamp > TIMEOUT) {
> 	this.servings.takeFirst();
> 	this.failedNum.incrementAndGet();
> }
> ```

## 3. Multithread Service-Simulation Application

### Class ```Runnable```

> The `Runnable` interface should be implemented by any class whose instances are intended to be executed by a thread. The class must define a method of no arguments called `run`.
>
> This interface is designed to provide a common protocol for objects that wish to execute code while they are active. For example, `Runnable` is implemented by class `Thread`. Being active simply means that a thread has been started and has not yet been stopped.
>
> In addition, `Runnable` provides the means for a class to be active while not subclassing `Thread`. A class that implements `Runnable` can run without subclassing `Thread` by instantiating a `Thread` instance and passing itself in as the target. In most cases, the `Runnable` interface should be used if you are only planning to override the `run()` method and no other `Thread` methods. This is important because classes should not be subclassed unless the programmer intends on modifying or enhancing the fundamental behavior of the class.

### Class ```ExecutorService```

> An [Executor](#) that provides methods to manage termination and methods that can produce a [Future](#) for tracking progress of one or more asynchronous tasks.
>
> An `ExecutorService` can be shut down, which will cause it to reject new tasks. Two different methods are provided for shutting down an `ExecutorService`. The [shutdown](#) method will allow previously submitted tasks to execute before terminating, while the [shutdownNow](#) method prevents waiting tasks from starting and attempts to stop currently executing tasks. Upon termination, an executor has no tasks actively executing, no tasks awaiting execution, and no new tasks can be submitted. An unused `ExecutorService` should be shut down to allow reclamation of its resources.
>
> Method `submit` extends base method [Executor.execute(Runnable)](#) by creating and returning a [Future](#) that can be used to cancel execution and/or wait for completion. Methods `invokeAny` and `invokeAll` perform the most commonly useful forms of bulk execution, executing a collection of tasks and then waiting for at least one, or all, to complete. (Class [ExecutorCompletionService](#) can be used to write customized variants of these methods.)
>
> The [Executors](#) class provides factory methods for the executor services provided in this package.

### Run with Multithread

> Line-23 in ``main``
>
> ```java
> ExecutorService multiThreadServices = Executors.newCachedThreadPool();
> multiThreadServices.execute(p1);
> multiThreadServices.execute(p2);
> multiThreadServices.execute(p3);
> multiThreadServices.execute(c1);
> multiThreadServices.execute(c2);
> multiThreadServices.execute(c3);
> ```

