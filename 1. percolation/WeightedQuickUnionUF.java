public class WeightedQuickUnionUF {
    private int[] parent;
    private int[] sz;
    private int count;
    
    public WeightedQuickUnionUF( int n){
        count = n;
        parent = new int[n];
        sz = new int[n];
        for (int i = 0; i<n; i++) {
            parent[i] = i;
            sz[i] = 1;
        }
    }
    
    public int count(){
        return count;
    }
    
    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
    
    private int find(int p){
        while ( p!=parent[p]) p = parent[p];
        return p;
    }
    
    public void union(int p, int q){
        int root_p = find(p);
        int root_q = find(q);
        // Меньший корень должен указывать на больший
        if (sz[root_p] < sz[root_q]) { parent[root_p] = root_q; sz[root_q] += sz[root_p];}
        else                         { parent[root_q] = root_p; sz[root_p] += sz[root_q];}
        count --;
    }
}
        