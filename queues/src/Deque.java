import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private ItemNode head;
    private ItemNode foot;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        ItemNode itemNode = new ItemNode(item, null, null);
        if (head == null) {
            head = itemNode;
            foot = itemNode;
        } else {
            head.setNext(itemNode);
            itemNode.setPrev(head);
            head = itemNode;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        ItemNode itemNode = new ItemNode(item, null, null);
        if (head == null) {
            head = itemNode;
            foot = itemNode;
        } else {
            foot.setPrev(itemNode);
            itemNode.setNext(foot);
            foot = itemNode;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item ret = head.getValue();
        ItemNode prev = head.getPrev();

        if (prev == null) {
            head = null;
            foot = null;
        } else {
            if (prev == foot) {
                head = foot;
            } else {
                head = prev;
            }

            prev.setNext(null);
        }

        size--;

        return ret;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item ret = foot.getValue();
        ItemNode next = foot.getNext();

        if (next == null) {
            head = null;
            foot = null;
        } else {
            if (next == head) {
                foot = head;
            } else {
                foot = next;
            }

            next.setPrev(null);
        }

        size--;

        return ret;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(2);
        deque.addLast(1);

        deque.removeFirst();

        deque.removeFirst();

        deque.removeLast();

        deque.removeLast();

        deque.removeFirst();

        deque.addLast(0);

        deque.removeFirst();

        deque.addFirst(1);

        deque.removeLast();
    }

    private class ItemNode {
        private Item value;
        private ItemNode next;
        private ItemNode prev;

        ItemNode(Item value, ItemNode next, ItemNode prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        Item getValue() {
            return value;
        }

        void setValue(Item value) {
            this.value = value;
        }

        ItemNode getNext() {
            return next;
        }

        void setNext(ItemNode next) {
            this.next = next;
        }

        ItemNode getPrev() {
            return prev;
        }

        void setPrev(ItemNode prev) {
            this.prev = prev;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private ItemNode current;
        private boolean isFirstTime = true;

        DequeIterator(ItemNode head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null &&
                    (isFirstTime || current.getPrev() != null);
        }

        @Override
        public Item next() {
            if (hasNext()) {
                if (!isFirstTime) {
                    current = current.getPrev();
                }

                isFirstTime = false;

                return current.getValue();
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}