"# IN4030-oblig-3" 


###Notes about prime/factorisation

A prime number is a whole number which is only dividable by itself and 1

Any number higher than 1 is possible to factorise as a product of prime numbers
N = p1 * p2 * p3 * px.
E.g 4 = 2*2 (2 is a prime number)
E.g 6 = 3*2 (3 and 2 is prime number)
If you ignore the order of the factorised numbers, there's only one possible combination (so 6 can be factorised as 3*2 or 2*3 but if you ignore the order they are the same). In this submission we order them from highest to lowest
If there's only one number in this factorization, the number itself is a prime number.


###How does Sieve of Eratosthenes work?
Given the numbers 1-17
- We ignore 1 (x)
- Second number is 2, we underline it. It's a prime
- Then we jump by 2, cross that out, jump by 2, cross that out, jump by 2, cross that out... (2,4,6,8,10,12,14,16 are crossed out)
- Then we go to next number which has not been crossed out (3). That's a prime number and cross out every jump by 3 (6, 9, 12, 15 crossed out if not already)
- Then we go to next number which is not crossed out, (5) which is a prime number and cross out every jump by 5 (10, 15 if not crossed out already)
- Then we go to 7, but we've reached the square root of the maximum number now (17). It means that the remaining numbers which are not crossed out, is primes (11, 13, 17)


