# Balanced Search Trees

1. 2-3 tree: too complicated, not going to implement here, but it guarantees logarithmic operations.

2. Red-Black BSTs: a representation of 2-3 tree using colored binary tree (much simpler!),
   implemented [here](RedBlackBST.java).

3. B-Tree: mostly used on file system.

## Interview Questions

1. **Red–black BST with no extra memory.**
   Describe how to save the memory for storing the color information when implementing a red–black BST.

   > Since all nodes in Red-black BST have this property: bigger than its left
   > child and smaller than its right child. We can modify all red nodes to be
   > smaller than its left child and bigger than its right child, and leave
   > black ones unchanged. Then we can distinguish them by checking this
   > property.

2. **Document search.**
   Design an algorithm that takes a sequence of `n` document words and a sequence of `m` query words and find the
   shortest interval in which the `m` query words appear in the document in the order given. The length of an interval
   is the number of words in that interval.

   > Can't understand what the question says.

3. **Generalized queue.**
   Design a generalized queue data type that supports all the following operations in logarithmic time (or better)
   in the worst case.

    - Create an empty data structure.
    - Append an item to the end of the queue.
    - Remove an item from the front of the queue.
    - Return the ith item in the queue.
    - Remove the ith item from the queue.
   
   > use red-black tree, when appending, use a self-increasing integer as key, also maintain a
   > ordered queue of keys existing.
   >
   > when deleting, look for ith key in that ordered queue, and call delete with that key, also
   > delete the key in that ordered queue.