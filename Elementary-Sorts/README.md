# Elementary Sorts

We went through these sort methods:

1. Selection sort
    - `~1/2 N^2` on all cases (**quadratic**)
    - not going to implement here
2. Insertion sort
    - `~1/4 N^2` compares, `~1/4 N^2` exchanges, on average (**quadratic**)
    - N-1 compares and 0 exchanges, on the best case
    - `~1/2 N^2` compares, `~1/2 N^2` exchanges, on the worst case (slower than selection sort)
    - 2 times quicker than selection sort, on average
    - **linear for partially sorted arrays**
    - not going to implement here
3. Shell sort
    - similar to insertion sort, but comparing is different (compare increment h in `h-sort` is different every time,
      must do a 1-sort at last)
    - `~N^(3/2)` for worst-case, with `3x+1` increment sequence
4. Shuffle sort
    - first try: randomize array using sort (result: not fast enough)
    - **linear method**: for every item i in the array, randomly swap that with any item in between 0 and i-1, or
      between i and N-1 (inclusive)  
      **NOTE that swap i and a random item between 0 and N-1 (the whole array) doesn't make it uniformly random (the
      odds will be a bit off, proven in math)**
    - **uniformly** random shuffle is hard, don't think it's easy to get right  