# Mergesort

Proposition: `NlgN` compares, `6NlgN` array accesses. Good.  
Memory: `N`, not *in-place* (`<= clogN`, insertion sort, selection sort and shellsort are *in-place*). Not good.

## Interview problems

1. Merging with smaller auxiliary array. Suppose that the subarray `a[0]` to `a[n−1]` is sorted and the subarray `a[n]`
   to `a[2∗n−1]` is sorted. How can you merge the two subarrays so that `a[0]` to `a[2∗n−1]` is sorted using an
   auxiliary array of length `n` (instead of `2n`)?

   > Use the auxiliary array to store only one of the sorted subarray (0 to n-1), then merge the auxiliary array and latter sorted subarray into original array.

2. Counting inversions. An inversion in an array `a[]` is a pair of entries `a[i]` and `a[j]` such that `i < j`
   but `a[i] > a[j]`. Given an array, design a linearithmic algorithm to count the number of inversions.

   > In mergesort, when aux[i] is bigger than aux[j], inversions count += mid - i + 1.

3. Shuffling a linked list. Given a singly-linked list containing `n` items, rearrange the items uniformly at random.
   Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional
   to `nlogn` in the worst case.

   > This one is not easy, see [this](https://stackoverflow.com/questions/12167630/algorithm-for-shuffling-a-linked-list-in-n-log-n-time),
   > still similar to mergesort but when you merge, you "flip coins".

## Programming assignment TODO list

1. [x] Finish Point.java

2. [x] Brute force algorithm

3. [x] Fast algorithm

4. [x] debug to get 100/100
