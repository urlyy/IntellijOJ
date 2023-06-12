import networkx as nx
import matplotlib.pyplot as plt

# 创建链表的节点
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

# 创建链表
def create_linked_list(b,e):
    head = Node(b)
    current = head
    for i in range(b+1, e):
        new_node = Node(i)
        current.next = new_node
        current = new_node
    return head

# 绘制链表
def draw_linked_list(G,head):
      # 使用有向图来表示链表的指向关系
    current = head
    while current:
        G.add_node(current.data)
        if current.next:
            G.add_edge(current.data, current.next.data, arrowstyle='->')
        current = current.next

def get_g_data(g):
    print(g.nodes)  # 列出图中节点，结构为列表
    print(g.nodes())
    print(g.edges)  # 边的列表
    print(g.degree)  # 节点度的列表
    print(nx.degree_histogram(g))  # 节点度的值的分布，叫做直方图
    print(g.number_of_nodes())
    print(g.number_of_edges())

if __name__ == '__main__':
    # 创建图
    G = nx.DiGraph()
    # 创建链表并放入数据
    head = create_linked_list(1,7)
    head2 = create_linked_list(20,24)
    draw_linked_list(G,head)
    draw_linked_list(G,head2)
    # 画图
    pos = nx.spring_layout(G)
    nx.draw_networkx(G, pos, with_labels=True, node_color='lightblue', node_size=500, arrows=True)
    plt.axis('off')
    plt.show()
    # 导出图的邻接矩阵
    adj_matrix = nx.adjacency_matrix(G)
    print(adj_matrix.todense())
    get_g_data(G)