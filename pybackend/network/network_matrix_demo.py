import numpy as np
import networkx as nx
import matplotlib.pyplot as plt

A = np.array([[0, 1, 1], [1, 0, 1], [1, 1, 0]])
G = nx.from_numpy_array(A)
nx.draw(G, node_size=800, with_labels=True)
plt.draw()
