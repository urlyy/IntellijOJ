import networkx as nx
import matplotlib.pyplot as plt

def test():
    G = nx.Graph()
    # G.add_node(1)
    G.add_nodes_from([1, 2])
    G.add_edge(1, 2)
    nx.draw(G.to_directed(),with_labels=True)
    plt.show()

def test2():
    K_5 = nx.complete_graph(5)
    nx.draw(K_5, with_labels=True, font_weight='bold')
    nx.draw_shell(K_5, nlist=[range(5, 10), range(5)], with_labels=True, font_weight='bold')
    plt.show()


def test3():
    G = nx.petersen_graph()
    nx.draw(G, with_labels=True, font_weight='bold')
    nx.draw_shell(G, nlist=[range(5, 10), range(5)], with_labels=True, font_weight='bold')
    plt.show()


if __name__ == '__main__':
    test()