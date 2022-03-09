"# IN4030-oblig-3" 





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



