# Using FlatBuffers

### Introduction
[FlatBuffers](https://google.github.io/flatbuffers/flatbuffers_guide_tutorial.html) was born for performance critical applications like Game, mobile applications etc. The main advantages of Flatbuffers are like;
* Access to serialized data without unpacking entired binary data.
* Performance
* Backward/Forward compatibility
* Small Code footprint
* Cross platform support
* Ease of use

### How to use it
This is not very different than Google's [Protobuf](https://github.com/google/protobuf)[There is separate Github repository for Google Protobuff, can be found [here](https://github.com/guptaami/protobuf). 
Following steps to be followed;
 * Schema Creation, compilation
Flatbuffer schema to be populated from bottom to top. The sample schema is mentioned below;

```cpp
namespace com.bss.flatbuffer.dept;

enum FBGenre : byte { 
	Educational = 0, 
	Romantic, 
	Thriller 
	}
    
table FBBook {
  bookId:string;
  authorId:string;
  authorDesc:string;
  genre:FBGenre;
}

table FBDepartment {
	departmentName:string;
	departmentTag:string;
	departmentId:int;
	books:[FBBook];
}

root_type FBDepartment;
```
The root object should be the which encapsulates all other variables/objects. Few things to note here;
* [x] Table represents the composite class in Java/Cpp/C#.
* [x] Table can potentially have the another table inside it. This is called as **Vector**.
* [x] If you guys can recall the structure definition in C++, the similar structure can be defined in Flatbuffer as well (with struct). However struct only encapsulates primitive data types not wrapper classes. `[I do not see that is very helpful, specially when you are using it with java]`
* [x] Every enumeration has to have the default value denoted by `Zero(i.e. 0)`
* [x] No sequencing of memberes in schema required as mandatory in Protobuff.

### Shall one use Flatbuffer over Protobuffer?
Protobuff is yet another decent tool for serialization and deserialization.  This is fast enought to be used in performance critical applications. Please see criterias used in comparison between two technologies.

| Criteria | Protobuff | FlatBuffer |
|--------|--------|--------|
|     `Code Base`   |  Small      |  Very small compared to Protobuf      |
| `Ease of Use (serialization and deserializationb)` | Too simple to use | One need to be extra careful when using dealing with complex and composite objects |
|`Size of serialized data`| Considerably compared with flatbuffer| Small |
|`Performance`|Performant enough to use in low latency high throughput applications| Extremly performant |
|`Backward/Forward compatibility`| Compatible| Compatible|
| `Zero Copy support` | No | Yes |


**`One need to be extra careful about sequence of data getting streamed in byte buffer (during serialization).`**

### Java MicroBenchmark Test Harness (JMH) Details
**Note: Benchmark test are carried out on developer box, I will soon be publishing the results of PROD grade boxes.**

1. Flatbuffer Serialization

```
JMH version: 1.19
VM version: JDK 1.8.0_131, VM 25.131-b11
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: <none>
Warmup: 2 iterations, 10 s each
Measurement: 2 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 4 threads, will synchronize iterations
Benchmark mode: Throughput, ops/time
Benchmark: com.bss.flatbuffer.dept.transformer.jmh.test.FlatBufferBenchmarkSerializationTest.serialization

Iteration   1: 604722.277 ops/s
Iteration   2: 602168.485 ops/s

Result "com.bss.flatbuffer.dept.transformer.jmh.test.FlatBufferBenchmarkSerializationTest.serialization":
  603445.381 ops/s

Run complete. Total time: 00:00:42

Benchmark                                            Mode  Cnt       Score   Error  Units
FlatBufferBenchmarkSerializationTest.serialization  thrpt    2  603445.381          ops/s

```
2. Flatbuffer De-Serialization

```
JMH version: 1.19
VM version: JDK 1.8.0_131, VM 25.131-b11
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: <none>
Warmup: 2 iterations, 10 s each
Measurement: 2 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 4 threads, will synchronize iterations
Benchmark mode: Throughput, ops/time
Benchmark: com.bss.flatbuffer.dept.transformer.jmh.test.FlatBufferBenchmarkDeSerializationTest.deSerialization

Iteration   1: 1380557.568 ops/s
Iteration   2: 1396750.939 ops/s

Result "com.bss.flatbuffer.dept.transformer.jmh.test.FlatBufferBenchmarkDeSerializationTest.deSerialization":
  1388654.253 ops/s

Run complete. Total time: 00:00:42

Benchmark                                                Mode  Cnt        Score   Error  Units
FlatBufferBenchmarkDeSerializationTest.deSerialization  thrpt    2  1388654.253          ops/s

```
3. Protobuf Serialization

```
JMH version: 1.19
VM version: JDK 1.8.0_131, VM 25.131-b11
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: <none>
Warmup: 2 iterations, 10 s each
Measurement: 2 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 4 threads, will synchronize iterations
Benchmark mode: Throughput, ops/time
Benchmark: com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkSerializationTest.serialization

Iteration   1: 1055746.635 ops/s
Iteration   2: 1100917.700 ops/s

Result "com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkSerializationTest.serialization":
  1078332.168 ops/s

Run complete. Total time: 00:00:42

Benchmark                                             Mode  Cnt        Score   Error  Units
ProtoBufferBenchmarkSerializationTest.serialization  thrpt    2  1078332.168          ops/s

```
4. Protobuf De-Serialization

```
JMH version: 1.19
VM version: JDK 1.8.0_131, VM 25.131-b11
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: <none>
Warmup: 2 iterations, 10 s each
Measurement: 2 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 4 threads, will synchronize iterations
Benchmark mode: Throughput, ops/time
Benchmark: com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkDeSerializationTest.deSerialization

Iteration   1: 1265415.718 ops/s
Iteration   2: 1266254.586 ops/s

Result
JMH version: 1.19
VM version: JDK 1.8.0_131, VM 25.131-b11
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: <none>
Warmup: 2 iterations, 10 s each
Measurement: 2 iterations, 10 s each
Timeout: 10 min per iteration
Threads: 4 threads, will synchronize iterations
Benchmark mode: Throughput, ops/time
Benchmark: com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkDeSerializationTest.deSerialization

Iteration   1: 1265415.718 ops/s
Iteration   2: 1266254.586 ops/s

Result "com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkDeSerializationTest.deSerialization":
  1265835.152 ops/s

Run complete. Total time: 00:00:43

Benchmark                                                 Mode  Cnt        Score   Error  Units
 "com.bss.flatbuffer.dept.transformer.jmh.test.ProtoBufferBenchmarkDeSerializationTest.deSerialization":
  1265835.152 ops/s

Run complete. Total time: 00:00:43

Benchmark                                                 Mode  Cnt        Score   Error  Units
ProtoBufferBenchmarkDeSerializationTest.deSerialization  thrpt    2  1265835.152          ops/s
```

### Best cases in which FLatbuffer can be used
Looking at Benchmark results it is clear that 
