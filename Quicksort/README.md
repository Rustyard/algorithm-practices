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