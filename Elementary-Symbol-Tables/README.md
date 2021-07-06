# Interview problems

1. **Java autoboxing and equals().** Consider two **double** values `a` and `b` and their corresponding **Double**
   values `x` and `y`.

   - Find values such that `(a==b)` is **true** but `x.equals(y)` is **false**.
   - Find values such that `(a==b)` is **false** `x.equals(y)` is **true**.

   > 1. `-0.0 == 0.0 is true but equals() will return false`
   > 2. `Double a = 2.0; Double b = 2.0;`

2. **Check if a binary tree is a BST.** Given a binary tree where each `Node` contains a key, determine whether it is a
   binary search tree. Use extra space proportional to the height of the tree.

   > Recursively compare a node to its children.
   > ``` java
   > public Node getRoot() {
   >      return root;
   > }
   > 
   > public boolean isBST(BST<Key,Value> bst){
   >     return isBST(bst.getRoot());
   > }
   > 
   > private boolean isBST(Node x){
   >     if (x == null) return false; // null root
   >     // return instantly when result is confirmed to be false
   >     if (x.left != null && x.left.key.compareTo(x.key) >= 0) return false;
   >     if (x.right != null && x.key.compareTo(x.right.key) >= 0) return false;
   > 
   >     // return until the whole left subtree and right subtree is examined
   >     boolean result = true;
   >     if (x.left != null) result = isBST(x.left);
   >     if (!result) return result; // return instantaneously
   >     if (x.right != null) result = isBST(x.right);
   >     return result;
   > }
   > ```

3. **Inorder traversal with constant extra space.** Design an algorithm to perform an inorder traversal of a binary
   search tree using only a constant amount of extra space.

   > [Morris Inorder Tree Traversal](https://www.youtube.com/watch?v=wGXB9OWhPTg) (a bit intricate)
   ``` text 
   1. Initialize current as root  
   2. While current is not NULL  
          If the current does not have left child  
              a) Print current’s data  
              b) Go to the right, i.e., current = current->right  
          Else  
              a) Find rightmost node in current left subtree OR node whose right child == current.  
          If we found right child == current  
              a) Update the right child as NULL of that node whose right child is current  
              b) Print current’s data  
              c) Go to the right, i.e., current = current->right  
          Else  
              a) Make current as the right child of that rightmost node we found
              b) Go to this left child, i.e., current = current->left
   ```  

4. **Web tracking.** Suppose that you are tracking n websites and m users, and you want to support the following API:

   - User visits a website.
   - How many times has a given user visited a given site?

   What data structure or data structures would you use?

   > Symbol Tables
