package umg.edu.gt.ProyectoFinal;

import java.util.ArrayList;

public class DBNodo {
    private int t;
    private ArrayList<String> keys;
    private ArrayList<DBNodo> children;
    private boolean leaf;

    public DBNodo(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public ArrayList<String> GetKeys() {
        return keys;
    }

    public ArrayList<DBNodo> GetChildren() {
        return children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void SetLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void InsertNonFull(String key) {
        int i = keys.size() - 1;

        if (leaf) {
            keys.add("");
            while (i >= 0 && key.compareTo(keys.get(i)) < 0) {
                keys.set(i + 1, keys.get(i));
                i--;
            }
            keys.set(i + 1, key);
        } else {
            while (i >= 0 && key.compareTo(keys.get(i)) < 0) {
                i--;
            }
            i++;
            if (children.get(i).keys.size() == 2 * t - 1) {
                splitChild(i, children.get(i));
                if (key.compareTo(keys.get(i)) > 0) {
                    i++;
                }
            }
            children.get(i).InsertNonFull(key);
        }
    }

    public void splitChild(int i, DBNodo y) {
        DBNodo z = new DBNodo(y.t, y.leaf);
        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.remove(t));
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.remove(t));
            }
        }
        children.add(i + 1, z);
        keys.add(i, y.keys.remove(t - 1));
    }

    public DBNodo search(String key) {
        int i = 0;
        while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
            i++;
        }
        if (i < keys.size() && key.compareTo(keys.get(i)) == 0) {
            return this;
        }
        if (leaf) {
            return null;
        }
        return children.get(i).search(key);
    }
}