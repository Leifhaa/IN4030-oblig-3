# IN4030 - Oblig 3
## 1. Introduction – what this report is about.
This report is about the oblig 3 in the UiO coarse IN4030 (Efficient parallel programming). In this report I'm solving the process of finding large prime numbers & factorizing large numbers.
It demonstrates of using the Sieve of Eratosthenes and paralellization can make this process more efficient.

## 2. User guide – how to run your program (short, but essential), include a very simple example.
The program can be run using the command:
```
java -cp target/IN4030-oblig-3-1.0-SNAPSHOT.jar src.Main <n> <t> <m>
```
- n - Program will generate all primes up to up and factorize the 100 largest numbers less than n*n
- t - How many threads to use. if zero then the program uses the number of cores on the machine
- m - Which mode to use. Can be the following:
    - 0: runs the program sequentially
    - 1: runs the program in parallel
    - 2: runs benchmarks and prints these times
    - 3: runs tests
    
Example usage for running sequentially for 20000000:
```
java -cp target/IN4030-oblig-3-1.0-SNAPSHOT.jar src.Main 20000000 0 0
```

Example usage for running parallel for 20000000 with 4 threads:
```
java -cp target/IN4030-oblig-3-1.0-SNAPSHOT.jar src.Main 20000000 4 1
```

Example usage for running benchmarks:
```
java -cp target/IN4030-oblig-3-1.0-SNAPSHOT.jar src.Main 20000000 0 2
```

Example usage for running tests:
```
java -cp target/IN4030-oblig-3-1.0-SNAPSHOT.jar src.Main 20000000 0 3
```

### Parallel Sieve of Eratosthenes – how you did the parallelization – consider including drawings.
Given that the number n should be factorized.
![alt text](docs/images/sieve-parallel.png)
1. First I used the sequential sieve to obtain all prime numbers up to m (red line). In order to obtain these numbers, the sequential sieve
had to mark all numbers from 0 to p and then collect the primes (numbers which was not marked). This sequential part is relatively fast as it's only iterating double square root of n. The iteration is also skipping:
    - Skipping even numbers
    - Skipping by 2p
    - Starting to read from p*p


![alt text](docs/images/sieve-threads.png)
2. Now that I had primes from 0 to m (in red), I could start marking the numbers from 0 to n. In order to parallelize it, I decided to split 0 to n so each thread only calculates for a certain portion. Since we're using bytearrays for splitting and skipping even numbers,
it's important that we split so each thread doesn't start at the middle of a byte, but rather starts at the following 16th byte.

![alt text](docs/images/sieve-process.png)
3. Now that we've marked all numbers from 0-n, we have a bitarray of marked numbers (numbers which is not prime numbers). All we have to do at the end is sequentially convert this bitarray to an array of integer with prime numbers from 0-n


### 4. Parallel factorization of a large number – how you did the parallelization – consider including drawings
1. Before starting this process, it's important to know the prime numbers which was generated using the sieve.
   ![alt text](docs/images/primes-distribution.png)
2. The prime numbers were evenly distributed among the threads to each thread would be responsible for about the same amount of prime numbers to mark
   ![alt text](docs/images/thread-responsiblity.png)
3. In each thread, the thread would check it's assigned primes and add these to a an array if it's a factor in n.
   ![alt text](docs/images/factorization-process.png)
4. After all threads were finished, they had a local array of factors which they would. The last thing we have to do is to merge these arrays and add the remainder if there is any. Then we have the complete factorization and are finished.


### 5. Implementation – a somewhat detailed description of how your Java program works & how tested.
The main method should be used for launching the program. See 2. (User guide) on how to launch it. Based on the input the program either:
1. Runs sequentially where it's using the precode for the sieve. Then it factorizes using the sequentially code which I created.
2. Runs parallel. In this process, It's using the parallel sieve which I've built to obtain the prime numbers. After this, it uses the result of the prime numbers to factorize in parallel.
3. Runs benchmarks. In this process, it's taking every individual process (sequential sieve, sequential factorization, parallel sieve, parallel factorization) and measures the time for each of these. It also displays the speedsup achieved. It calculates the median runtime of total 7 runs.
4. Runs tests. In this case it's doing the whole procedure of factorizing sequential and factorizing in parallel. After completed, it compares the outout of the results for each sequential & parallel and ensures these are the time.



### 6. Measurements – includes discussion, tables, graphs of speedups for the four values of N, number of cores used.
Note: Time in in milliseconds
#### Cores used: 8

#### Sieve:
| n     | Sequential    | Parallel  | Speedup|
|-------|---------------|-----------|--------|
|2000000|7.9108         |7.4203     |1.066   |
|20000000|76.587        |68.110     |1.124   |
|200000000|903.154      |553.767    |1.630   |
|2000000000|13447.481   |7862.125   |1.710   |

Chart:
![alt text](docs/speedup-sieve.png)


#### Factorization:
| n     | Sequential    | Parallel  | Speedup|
|-------|---------------|-----------|--------|
|2000000|7.807         |10.938     |0.713   |
|20000000|36.994        |115.233     |0.321   |
|200000000|234.110      |927.936    |0.252   |
|2000000000|1036.304   |9401.941   |0.110   |


Chart:
![alt text](docs/images/speedup-factorization.png)


#### Sieve + Factorization
| n     | Sequential    | Parallel  | Speedup|
|-------|---------------|-----------|--------|
|2000000|22.691         |25.209     |0.900   |
|20000000|182.282        |245.738     |0.741   |
|200000000|1550.827      |1880.819    |0.824   |
|2000000000|17005.628   |18030.864   |0.943   |


#### Discussion of the results
From the results we can first see that paralleling the sieve seems to give a speedup. Once n increases, the efficiency of multithreading the program is also increasing. At 2 billion, we have a speedup for around 1.7 which means that the
algorithm almost runs twice the speed due to running multithreaded. For smaller numbers however, it's not giving large speedsup. For e.g 2000000 we see that we only achieve speedup of 1.066.
We can also see that the parallel sieve does complete at 9401.941 which is below the requirement of the sieve completing in 30 seconds.

Over to the factorization part, we can see that the speedup actually decreases as n becomes larger. I was expecting the speedup to also increase as n increased for this algorithm, however the opposite occurred. I believe this occurs as
the sequential algorithm performs quite fast (1036.304 for 2 billion) and is able to break earlier if it recognizes that it's completed. For the parallel algorithm however, it's not able to do the same which influences that it's slower as n increases.
It is however able to complete in around 9.4 seconds, or a total of 18030.864 for parallel sieve + parallel factorization which is below the requirement of 60 seconds. Due to this, I don't believe there's an bug in the algorithm, but simply that the sequential algorithm has internal code where it's able to conclude that it's finished earlier (only possible when running sequential)


### 7. Conclusion – just a short summary of what you have achieved
We can see that parallelling the sieve does give a speedup, especially once n becomes larger.
Paralleling the factorization however did not give a speedup for my algorithms. As a result, the algorithms performs at about the same rate. Sequential factorization is faster in my tests and parallel is faster in my tests.



### 8. Appendix – the output of your program.
Here is an example of output which outputs when running benchmark's: <br />
Running benchmarks. This can take a while, please wait... <br />
Starting benchmarking sieves... <br />
Sequential Sieve used median time 9.1063ms for n = 2000000 <br />
Parallel Sieve used median time 7.973ms for n = 2000000 <br />
Speedup: 1.1421422300263389 <br />
Sequential Sieve used median time 73.8185ms for n = 20000000 <br />
Parallel Sieve used median time 65.9596ms for n = 20000000 <br />
Speedup: 1.119147174937386 <br />
Sequential Sieve used median time 969.9284ms for n = 200000000 <br />
Parallel Sieve used median time 556.3265ms for n = 200000000 <br />
Speedup: 1.74345173203146 <br />
Sequential Sieve used median time 13854.7897ms for n = 2000000000 <br />
Parallel Sieve used median time 11244.9455ms for n = 2000000000 <br />
Speedup: 1.2320904267610724 <br />
Finished benchmarking sieves... <br />
-------------------------------- <br />
Starting benchmarking factorization... <br />
Sequential Factorization used median time 7.9705ms for n = 2000000 <br />
Paralell Factorization used median time 12.4736ms for n = 2000000 <br />
Speedup: 0.6389895459209852 <br />
Sequential Factorization used median time 33.9302ms for n = 20000000 <br />
Paralell Factorization used median time 95.321ms for n = 20000000 <br />
Speedup: 0.35595723922325617 <br />
Sequential Factorization used median time 254.0066ms for n = 200000000 <br />
Paralell Factorization used median time 1066.6903ms for n = 200000000 <br />
Speedup: 0.23812591152277282 <br />
Sequential Factorization used median time 1072.6763ms for n = 2000000000 <br />
Paralell Factorization used median time 8425.6287ms for n = 2000000000 <br />
Speedup: 0.12731112872324887 <br />
Finished benchmarking factorization... <br />
-------------------------------- <br />
Starting benchmarking sieve + factorization... <br />
Sequential sieve + factorization used median time 17.9441ms for n = 2000000 <br />
Parallel sieve + factorization used median time 21.7546ms for n = 2000000 <br />
Speedup: 0.8248416426870638 <br />
Sequential sieve + factorization used median time 121.1307ms for n = 20000000 <br />
Parallel sieve + factorization used median time 178.5425ms for n = 20000000 <br />
Speedup: 0.6784418275760674 <br />
Sequential sieve + factorization used median time 1284.4311ms for n = 200000000 <br />
Parallel sieve + factorization used median time 1590.0737ms for n = 200000000 <br />
Speedup: 0.8077808594658223 <br />
Sequential sieve + factorization used median time 14573.8955ms for n = 2000000000 <br />
Parallel sieve + factorization used median time 19010.3113ms for n = 2000000000 <br />
Speedup: 0.7666310808913476 <br />
Finished benchmarking sieve + factorization <br />
-------------------------------- <br />
<br />
Process finished with exit code 0 <br />


### Java measurement harness
### How to run Java Measurement harness
Run the command
```
java -jar target/benchmarks.jar
```

### Details about the procude of running
The measurement begins with warming up in order to make sure that:
- Program is in memory/cache
- JIT has done it's optimizations
- The data has been read from disk
- The data has been through memory/cache

It warms up once. After this, it runs 3 iterations of the benchmarks and collects the results of these runs. We can see the results as following for n=200_000_000:
#### Sequential sieve + factorization
```
Iteration   1: 1.271 s/op
Iteration   2: 1.339 s/op
Iteration   3: 1.252 s/op


Result "src.JbhBenchmarks.testSequential":
  1.287 ±(99.9%) 0.832 s/op [Average]
  (min, avg, max) = (1.252, 1.287, 1.339), stdev = 0.046
  CI (99.9%): [0.455, 2.119] (assumes normal distribution)

```
#### Parallel sieve + factorization
```
Iteration   1: 1.165 s/op
Iteration   2: 1.185 s/op
Iteration   3: 1.180 s/op


Result "src.JbhBenchmarks.testParallel":
  1.177 ±(99.9%) 0.185 s/op [Average]
  (min, avg, max) = (1.165, 1.177, 1.185), stdev = 0.010
  CI (99.9%): [0.992, 1.362] (assumes normal distribution)
```

#### Comparison
```
Benchmark                     Mode  Cnt  Score   Error  Units
JbhBenchmarks.testParallel    avgt    3  1.177 ± 0.185   s/op
JbhBenchmarks.testSequential  avgt    3  1.287 ± 0.832   s/op
```
From the benchmarks we can see that we can run the sequential procedure around 1.287 per second the parallel procedure 1.177 per second.
We can also see that we've achieved a speedup of around 1.10 by running it parallel. We concluded these results by seeing the time it took on average running a total of 3 runs for each
parallel and sequential. Due to the warmup, I believe the variance for each run is lower between each run. We can also see from the results that there is not much variance between each run. As there's
not much variance, using the average time seems like a good fit. We could potentially run the methods more than 3 times, but for this benchmarking I thought it was sufficient. As a summary, the results can be used
to argue that paralleling this procedue for such number n does improve the speed of the algorithm.




The benchmarks are set to report the average time usage



## How does factorizationa nd Sieve of Sieve of Eratosthenes work? (Not neccesairy to read for examiner)
###Prime number
A prime number is a whole number which is only dividable by itself and 1

####Finding prime numbers using Sieve of Eratosthenes?
Given the numbers 1-17
- We ignore 1 (x)
- Second number is 2, we underline it. It's a prime
- Then we jump by 2, cross that out, jump by 2, cross that out, jump by 2, cross that out... (2,4,6,8,10,12,14,16 are crossed out)
- Then we go to next number which has not been crossed out (3). That's a prime number and cross out every jump by 3 (6, 9, 12, 15 crossed out if not already)
- Then we go to next number which is not crossed out, (5) which is a prime number and cross out every jump by 5 (10, 15 if not crossed out already)
- Then we go to 7, but 7  is greater than of the maximum number now (17). It means that the remaining numbers which are not crossed out, is primes (11, 13, 17)
- We should also remove the even numbers before starting this process (besides 2).
- We can do the stepping by starting looking at numbers from p*p (e.g for 3 we start at 9, or for 7 we start at 49)
- If we're stepping forward with 3 we hit an odd number each 2 time (3, 6(odd), 9, 12(odd)) so we can skip jump by 6 all the time (2*p)

###Factorization
Any number higher than 1 is possible to factorise as a product of prime numbers
N = p1 * p2 * p3 * px.
E.g 4 = 2*2 (2 is a prime number)
E.g 6 = 3*2 (3 and 2 is prime number)
If you ignore the order of the factorised numbers, there's only one possible combination (so 6 can be factorised as 3*2 or 2*3 but if you ignore the order they are the same). In this submission we order them from highest to lowest
If there's only one number in this factorization, the number itself is a prime number.

####Finding factorization number
If we wanna factorize e.g 532 the way we do is:
- Start taking numbers from the sieve, starting with 2
- Try divide that into 532: 532/2 = 266. So current factorization is 2
- Then we retry to divide it by the number again: 266/2 = 133. Current factorization is 2*2
- Then we try again 133/2 which doesnt work.
- So we step to the next number in the sieve (3) and check that it's not greater than the square root of by multiple 3*3 (9) and checking that its not higher than
- 3 is also not dividable, so we go to next number in sieve (5), not dividable by 133.
- So we go to next number (7). It's dividable by 7! So we end up with 19 and the factorisation 2 * 2 * 7.
- Then we try 7 again and check if it divides by 17. But before this, we have to check if we should keep trying:
- We should stop trying whenever the candidate is greater than the square root of the number we're working on (square root of 19 is less than 5. And 7 is greater than this so we should stop trying)
- So we have reached out stop condition. If there is a number left (19 in this case) we know it's a prime number and therfore also a factor.
- So the factorization is now 2*2*7*19.

