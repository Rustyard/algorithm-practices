# An algorithm for solving dynamic connectivity problems
homework percolation (89/100)

**NOTE 1**: according to the [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/percolation/faq.php), my implementation got at least one thing wrong: not using provided `WeightedQuickUnionUF.java` but used my own implementation of `QuickUnion` written inside of the `Percolation.java`, which caused the timing test not working (all failed).

**NOTE 2**: from FAQ: 
> **It's OK to use an extra row and/or column to deal with the 1-based indexing of the percolation grid.** Though it is slightly inefficient, it's fine to use arrays or union–find objects that are slightly larger than strictly necessary. Doing this results in cleaner code at the cost of slightly greater memory usage.

See more in the assignment FAQ: I probably should rewrite the whole percolation program later...
