# Quicksort

## What is better than mergesort

1. no extra space needed

2. generally a bit faster

## What is worse than mergesort

1. not stable (because of not using any extra space)

## Speed

Best case: compares `~nlgn` times  
Worst case: compares `~1/2 n^2` times  
Note that the worst case is *extremely unlikely* to happen because we **shuffle** the array before sorting, worst case
happens only when the random shuffle happens to put everything in the desired order, which is less likely than ***a
thunder strike*** on me.

## Other application

### 1. Quick-select (find the top-k item in linear time)

See `select()` in `Quick.java` for more detail.

### 2. Sorting an array with huge amount of dupe keys

1. How is `mergesort` doing it:  
   always between `1/2 nlgn` and `nlgn` compares

2. How about `quicksort`?  
   goes quadratic unless partition stops on equal keys!  
   *How to solve it:* Remember the *Dutch National Flag* Problem in the Elementary-Sorts section? Its solution is
   actually very useful in this problem!

## How about Java's Array.sort()?

Uses quicksort for primitive types, uses mergesort for objects.

## Interview Problems

### 1. Nuts and Bolts

A disorganized carpenter has a mixed pile of `n` nuts and `n` bolts. The goal is to find the corresponding pairs of nuts
and bolts. Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together, the
carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly). Design an
algorithm for the problem that uses at most proportional to `nlogn` compares (probabilistically).

> Similar to quicksort: To start, shuffle the mixed pile, pick a nut and
> hold that in hand, now do this: pick a bolt and try if it fits the nut in your hand,
> if so, hold that in hand and don't stop picking bolts until no more bolt can be picked, if bigger, put the
> bolt into a 'bigger pile', if smaller, put the bolt into a 'smaller pile'. Now after
> every bolt is picked, we found a pair, then you will want to do this: pick another nut
> (if there is) and compare it to the fitting bolt in your hand, if bigger (or smaller), take this new nut
> and go to the bigger pile (or smaller pile), and do everything recursively until all
> fitting pairs are found (before you go to another pile, put the fitting pair between bigger pile and smaller pile so
> that you won't forget where they were).

### 2. Selection in two sorted arrays

Given two sorted arrays `a[]` and `b[]`, of lengths `n1` and `n2` and an integer `0 <= k < n1 + n2`, design an algorithm
to find a key of rank k. The order of growth of the worst case running time of your algorithm should be `logn`,
where `n = n1 + n2`.

- Version 1: `n1 = n2` (equal length arrays) and `k = n / 2` (median).
- Version 2: `k = n / 2` (median).
- Version 3: no restrictions.

> - Approach A: Compute the median in `a[]` and the median in `b[]`. Recur in a subproblem of roughly half the size.
> - Approach B: Design a constant-time algorithm to determine whether `a[i]` is a key of rank `k`. Use this subroutine and binary search.
>
> This problem is really tricky, I didn't get it.

### 3. Decimal dominants

Given an array with `n` keys, design an algorithm to find all values that occur more than `n/10` times. The expected
running time of your algorithm should be linear.

> Solution 1: Find the `(n/10)th` largest key in `a` (NOTE: this means we find `a[n/10]` when a is sorted i.e.
> No care about duplicates), and iterate through `a` to check if `a[n/10]` appeared more than `n/10`
> times, if it does, add it to the return list, else do nothing. Now do the same thing for
> `(2n/10)th` largest key, and `(3n/10)th` and so on (DO CARE ABOUT DUPLICATES in the return list)
> until `(9n/10)th` largest key. This process takes `~9n` (or `10n`? I'm confused.) time which is linear.
>
> Solution 2: use 9 counters (I didn't investigate a bit on this).
