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


####Approach for programming the sieve
- The sieve could be an array of boolean which represents if a number is crossed out or not.
- The issue is that each boolean will take 32 bits, so using a 1 billion large sieve would require 32 billion bits when we only need 1
single bit for each entry in the sieve.
- We need to optimize this. We only need 1 bit as a flag if a number is crossed out or not
- Instead we use a array of bytes where each bit position indicates wether the number has been crossed out or not. So if a number is crossed out, we simply set that bit to 1.
- This requires some administration. We have special case where we start at the number 3. We also skip every second bit
- If we want to find the bit number, So we have the calculation (nr - 3) / 2. We can also reverse this and see which value a particular bit's value is.
- So e.g 3 is (3-3)/2 = bit position 0. 5 is (5-3)/2 = bit position 1. 7 is (7-3)/2 = bit position 2
- If we want to find the byte number we do bo: math.floor(bitnumber/8). Remember to round down. 
- For finding the bit number within the byte number which we found, we retrieve the remainder from the math.floor when finding byte number, and this remainder is the bit number within the particular byte which we found.
- Using & and | operations is useful for manipulating these bits.
- Parts of this code is already in the precode.


### Synchronization of the sieve
When paralleling the sieve, having too many synchronizations can be expensive.



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




### Todo:
document that we're making it more efficient by:



