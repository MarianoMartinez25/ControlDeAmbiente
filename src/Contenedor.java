import java.util.LinkedList;
import java.util.ListIterator;

/** 
 * contenedor de locales 
 */

public class Contenedor<T> extends LinkedList<T>{
    public ListIterator listIterator() {
        return new ListIterator(){
            private int indice = 0;

            public void add(Object o) {}

            public boolean hasNext() {return true;}

            public boolean hasPrevious() {return true;}

            public Object next() {
                return indice == size() ? get(indice = 0) : get(indice++);
            }

            public int nextIndex() {return indice == size() - 1 ? 0 : indice + 1;}

            public Object previous() {return null;}

            public int previousIndex() {return indice == 0 ? size() - 1 : indice - 1;}

            public void remove() {}

            public void set(Object o) {}
        };
    }
}
