package com.poc.chapter_08_part03;

import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.stringtemplate.v4.ST;

import java.util.Set;

public class Graph {
    private Set<String> nodes; // list of functions
    private MultiMap<String, String> edges; // caller->callee

    public Graph() {
        nodes = new OrderedHashSet<>();
        edges = new MultiMap<>();
    }

    public void edge(String source, String target) {
        edges.map(source, target);
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public String toString() {
        return "edges: " + edges.toString() + ", functions: " + nodes;
    }

    public String toDOT() {
        StringBuilder buf = new StringBuilder();
        buf.append("digraph G {\n");
        buf.append("  ranksep=.25;\n");
        buf.append("  edge [arrowsize=.5]\n");
        buf.append("  node [shape=circle, fontname=\"ArialNarrow\",\n");
        buf.append("        fontsize=12, fixedsize=true, height=.45];\n");
        buf.append("  ");
        for (String node : nodes) { // print all nodes first
            buf.append(node);
            buf.append("; ");
        }
        buf.append("\n");
        for (String src : edges.keySet()) {
            for (String trg : edges.get(src)) {
                buf.append("  ");
                buf.append(src);
                buf.append(" -> ");
                buf.append(trg);
                buf.append(";\n");
            }
        }
        buf.append("}\n");
        return buf.toString();
    }

    public ST toST() {
        ST st = new ST("\n" +
                "digraph G {\n" +
                "  ranksep=.25; \n" +
                "  edge [arrowsize=.5]\n" +
                "  node [shape=circle, fontname=\"ArialNarrow\",\n" +
                "        fontsize=12, fixedsize=true, height=.45];\n" +
                "  <funcs:{f | <f>; }>\n" +
                "  <edgePairs:{edge| <edge.a> -> <edge.b>;}; separator=\"\\n\">\n" +
                "}\n"
        );
        st.add("edgePairs", edges.getPairs());
        st.add("funcs", nodes);
        return st;
    }
}
