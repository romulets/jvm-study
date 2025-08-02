### Interview Questions
- How do you deal with I/O?
  - Do you use Virtual Threads at all?
- I see unit tests and IT tests. Also heard about quality gates. How would you describe the feedback loop of the development of a feature?
- How do you test concurrency issues?
- Do you use mutation tests?
  - The testing tool makes small, systematic changes (mutations) to your source code. For example, it might replace a + with a -, or change a true to false.
- Process to production
- Monitoring 
- Impact assessment 
- How do you propose changes

## Concurrency tips
- Volatile:
  - No caching
  - Visibility Guarantee
  - Cheaper than `synchronized` for simple visibility guaranee
- Happens before volatile guarantee
  - `volatile` properties to flush changes directly to main memory
    - Not all properties are needed to be declared volatile, if one is, all the properties above are also flushed to memory
    - Happens before also happens `synchronized` blocks
- Compare and Swap CAS
  - Only set value if the value in memory is the expected one.
- [Heap vs Stack](https://www.digitalocean.com/community/tutorials/java-heap-space-vs-stack-memory)
  - Heap objects
  - Ref to objects and native types (int, short, char, etc...)
  - [Structured Concurrency](https://docs.oracle.com/en/java/javase/21/core/structured-concurrency.html#GUID-AA992944-AABA-4CBC-8039-DE5E17DE86DB)
  

### Useful Classes
- `ThreadLocal<T>` keep non shared thread values in the same object
  - `ThreadLocal.withInitial`
- `Lock` impl `ReentrantLock` (`true` to guarantee fairness - big overhead)
  - `ReentrantReadWriteLock` for distinct read and write lock
- `BlockingQueue` interface and `ArrayBlockingQueue` implementation
  - `ConcurrentLinkedQueue` 
- `ExecutorService` for ThreadPools
  - `ThreadPoolExecutor`
- `ForkJoinPool` for for fork join alg
- `Atomic*`
- `ConcurrentHashMap`
  - `ConcurrentSkipListMap` to keep key order
- `CopyOnWriteArrayList` or `CopyOnWriteArraySet`
- `Semaphore` for locking certain amount of resources (no busy wait)
- `CyclicBarrier` for waiting specific amount of threads to arrive (no busy wait)
  - `Phaser` is better for dynamic and multi checking
- `Exchanger` for direct communication between two threads (no busy wait)

#### General

- `Queue` impl `LinkedList`;
  - peek
  - add
  - poll
- `Stack` impl Stack
  - peek
  - pop
  - push

![thread model](https://jenkov.com/images/java-concurrency/false-sharing-in-java-1.png)

#### Concurrency

[Course](https://jenkov.com/tutorials/java-concurrency/index.html)
[Code examples](https://github.com/jjenkov/java-examples/tree/main/src/main/java/com/jenkov/java/concurrencyfi)

- Java 21 LTS, latest 24
- Use Virtual Threads
```java
    Thread.ofVirtual().start(()-> {
     // do something;
    })
```

## To see
- Byte enconding
- Read about database patterns
- Merge
- Exponential Algorithms

### Questions
- [When should I not use virtual threads?](https://stackoverflow.com/questions/76180420/thread-currentthread-getname-returns-empty-string-for-virtual-threads-cre)
- When to use atomic, synchronized, volatile

### Watch
- https://www.youtube.com/watch?v=3BFcYTpHwHw
