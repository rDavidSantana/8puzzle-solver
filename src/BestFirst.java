import java.util.*;

class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> emAbertos;   //NOVO

    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {
        private Ilayout layout;
        private State father;
        private int g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father!=null)
                g = father.g + l.getG();
            else g = 0;
        }

        public String toString() { return  layout.toString(); }

        public int getG() {return g;}

        public int hashCode() {
            return toString().hashCode();
        }

        public boolean equals (Object o) {
            if (o==null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for(Ilayout e: children) {
            if (n.father == null || !e.equals(n.father.layout)){
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;

        abertos = new PriorityQueue<>(11, (s1, s2) -> Integer.compare(s1.getG(), s2.getG()));
        fechados = new HashMap<>();
        emAbertos = new HashMap<>();

        State inicial = new State(s, null);
        abertos.add(inicial);
        emAbertos.put(s, inicial);

        while (!abertos.isEmpty()) {
            State actual = abertos.poll();
            emAbertos.remove(actual.layout);

            // objetivo?
            if (actual.layout.isGoal(objective)) {
                // reconstruir caminho de 'inicial' a 'goal'
                List<State> path = new ArrayList<>();
                State cur = actual;
                while (cur != null) {
                    path.add(cur);
                    cur = cur.father;
                }
                Collections.reverse(path);
                return path.iterator();
            }

            // expandir
            fechados.put(actual.layout, actual);
            List<State> sucs = sucessores(actual);
            for (State suc : sucs) {
                if (fechados.containsKey(suc.layout)) continue;
                if (emAbertos.containsKey(suc.layout)) continue;
                abertos.add(suc);
                emAbertos.put(suc.layout, suc);
            }
        }
        return null;
    }
}