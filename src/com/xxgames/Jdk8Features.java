package com.xxgames;

import java.util.ArrayList;
import java.util.List;

public class Jdk8Features {

    public void testStream() {
        List<Node<String>> nodes = new ArrayList<>();
        nodes.add(new Node<>("xm"));
        nodes.add(new Node<>("xx"));
        nodes.add(new Node<>(null));
        nodes.add(new Node<>("xd"));
        nodes.stream().filter(e -> e.item != null).forEach(e -> {
            System.out.println(e.item);
        });
    }

    static class Node<E> {
        E item;

        Node(E item) {
            this.item = item;
        }

    }

    public static void main(String[] args) {
        Jdk8Features jdk8Features = new Jdk8Features();
        jdk8Features.testStream();
    }
}
