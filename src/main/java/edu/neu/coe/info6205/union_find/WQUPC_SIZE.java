package edu.neu.coe.info6205.union_find;

import java.util.Arrays;

// Weighted quick union with path compression alternatives

public class WQUPC_SIZE implements UF {
	/**
     * Ensure that site p is connected to site q,
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     */
    public void connect(int p, int q) {
        if (!isConnected(p, q)) union(p, q);
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n               the number of sites
     * @param pathCompression whether to use path compression
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC_SIZE(int n, boolean pathCompression, int pass) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        this.pass=pass;
        this.pathCompression = pathCompression;
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     * This data structure uses path compression
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC_SIZE(int n, int pass) {
        this(n, true, pass);
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], size[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int components() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        // TO BE IMPLEMENTED
        int root = p;
        if(pass==1) {
        	root = doPathCompression1(p);
        }
        else if(pass==2) {
        	root = doPathCompression2(p);
        }
        return root;
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        // CONSIDER can we avoid doing find again?
        mergeComponents(find(p), find(q));
        count--;
    }

    @Override
    public int size() {
        return parent.length;
    }

    /**
     * Used only by testing code
     *
     * @param pathCompression true if you want path compression
     */
    public void setPathCompression(boolean pathCompression) {
        this.pathCompression = pathCompression;
    }

    @Override
    public String toString() {
        return "UF_HWQUPC:" + "\n  count: " + count +
                "\n  path compression? " + pathCompression +
                "\n  parents: " + Arrays.toString(parent) +
                "\n  heights: " + Arrays.toString(size);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Used only by testing code
     *
     * @param i the component
     * @return the parent of the component
     */
    private int getParent(int i) {
        return parent[i];
    }

    private final int[] parent;   // parent[i] = parent of i
    private final int[] size;   // height[i] = height of subtree rooted at i
    private int count;  // number of components
    private boolean pathCompression;
    private int pass;

    private void mergeComponents(int i, int j) {
    	i=find(i);
    	j=find(j);
    	if (size[i] < size[j]) {
    		parent[i] = j;
    		size[j] = size[j] + size[i];
    	}
    	else {
    		parent[j] = i;
    		size[i] = size[i] + size[j];
    	}
    	
    }

    /**
     * This implements the single-pass path-halving mechanism of path compression
     */
    private int doPathCompression1(int i) {
    	int root = i;
    	
    	while(true) {
    		if(root==getParent(root)) {
    			break;
    		}
    		else {
    			if(pathCompression==true) {
    				parent[root] = parent[parent[root]];
    			}
    			root=getParent(root);
    		}
    	}
    	return root;
    }
    
    private int doPathCompression2(int i) {
    	int root=i;
    	
    	while(true) {
    		if(root==getParent(root)) {
    			break;
    		}
    		else {
    			root=getParent(root);
    		}
    	}
    	if(pathCompression==true) {
    		while(i!=root) {
    			int newp = parent[i];
    			parent[i] = root;
    			i=newp;
    		}
    	}
    	return root;
    }
}
