package linkedlists.lockbased;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import contention.abstractions.AbstractCompositionalIntSet;

public class HandsOverHands extends AbstractCompositionalIntSet {
    private Node head;
    private Node tail;

    public HandsOverHands(){
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        head.next = tail;
    }

    @Override
    public boolean addInt(int item) {
        head.lock();
        Node pred = head;
        try {
            Node curr = pred.next;
            curr.lock();

            try {
                while (curr.key < item) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }

                if (curr.key == item) {
                    return false;
                }

                Node newNode = new Node(item);
                newNode.next = curr;
                pred.next = newNode;
                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean removeInt(int item) {
        head.lock();
        Node pred = head;
        Node curr = pred.next;
        try {
            curr.lock();
            try {
                while (curr.key < item) {
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (curr.key == item) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean containsInt(int item) {
        head.lock();
        Node pred = head;
        Node curr = pred.next;
        try {
            curr.lock();
            try {
                while (curr.key < item) {
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                return (curr.key == item);
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }
    
    @Override
    public int size() {
        int count = 0;

        Node curr = head.next;
        while (curr.key != Integer.MAX_VALUE) {
            curr = curr.next;
            count++;
        }
        return count;
    }

    @Override
    public void clear() {
       head = new Node(Integer.MIN_VALUE);
       head.next = new Node(Integer.MAX_VALUE);
    }

    private class Node {
        private Lock lock = new ReentrantLock();

        public int key;
        public Node next;

        Node(int item) {
            key = item;
            next = null;
        }

        void lock(){
            this.lock.lock();
        }

        void unlock(){
            this.lock.unlock();
        }
    }
}