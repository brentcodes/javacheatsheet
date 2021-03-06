package tocheatsheet.collection;

import java.util.*;
import java.util.stream.Collectors;

// Like a hashset, but allows entries to have variable cardinality.
//  Assumes objects which are equal are the same
public class ValueHashList<T> implements Iterable<T> {
    private int size = 0;
    private HashMap<T, Integer> map = new HashMap<>();
    private int hash = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<T> keys = map.keySet().iterator();
            T iKey;
            int iCount;

            @Override
            public boolean hasNext() {
                if (iKey == null || iCount == 0) return keys.hasNext();
                return true;
            }

            @Override
            public T next() {
                if (iKey == null || iCount == 0) {
                    iKey = keys.next();
                    iCount = map.get(iKey);
                }
                --iCount;
                return iKey;
            }
        };
    }

    public void add(T t) {
        ++size;
        hash += t.hashCode();
        map.compute(t, (k,v) -> v == null ? 1 : ++v);
    }

    public void remove(Object o) {
        if (!map.containsKey(o))
            return;
        hash -= o.hashCode();
        --size;
        Integer count = map.compute((T)o, (k,v) -> --v);
        if (count == 0)
            map.remove(o);
    }

    public void addAll(Iterable<T> many) {
        for (T t: many) {
            add(t);
        }
    }

    public void clear() {
        map.clear();
    }

    public List<T> toList() {
        ArrayList<T> out = new ArrayList<>(size);
        for (T t : this)
            out.add(t);
        return out;
    }

    public Set<T> toSet() {
        return map.keySet();
    }

    public int cardinality(T t) {
        return map.getOrDefault(t, 0);
    }

    @Override
    public String toString() {
        return "(" +
                String.join(",", toList().stream().map(x -> String.valueOf(x)).collect(Collectors.toList())) +
                ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ValueHashList)) return false;
        ValueHashList other = (ValueHashList)o;
        if (size() != other.size()) return false;
        for (T key : map.keySet())
            if (cardinality(key) != other.cardinality(key))
                return false;
        return true;
    }
}
