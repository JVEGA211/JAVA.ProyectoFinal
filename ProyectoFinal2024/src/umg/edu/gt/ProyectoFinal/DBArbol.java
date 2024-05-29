package umg.edu.gt.ProyectoFinal;

public class DBArbol {
    private DBNodo root;
    private int t;

    public DBArbol(int t) {
        this.root = null;
        this.t = t;
    }

    public DBNodo getRoot() 
    {  return root;  }

    public void insert(String key) {
        if (root == null) {
            root = new DBNodo(t, true);
            root.GetKeys().add(key);
        } else {
            if (root.GetKeys().size() == 2 * t - 1) {
                DBNodo s = new DBNodo(t, false);
                s.GetChildren().add(root);
                s.splitChild(0, root);
                int i = 0;
                if (s.GetKeys().get(0).compareTo(key) < 0) {
                    i++;
                }
                s.GetChildren().get(i).InsertNonFull(key);
                root = s;
            } else {
                root.InsertNonFull(key);
            }
        }
    }

    public DBNodo search(String key) {
        if (root == null) {
            return null;
        } else {
            return root.search(key);
        }
    }
}